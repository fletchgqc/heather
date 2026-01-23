#!/usr/bin/env bash
set -euo pipefail

# Usage: ./parallel-claude.sh "prompt"
# Creates two git worktrees, runs Claude Code in each with the same prompt
# (branch 1 uses sonnet, branch 2 uses haiku), then runs a final Claude Code
# process with "say three"

if [[ $# -ne 1 ]]; then
    echo "Usage: $0 <prompt>"
    echo "Example: $0 'add a test file'"
    exit 1
fi

PROMPT="$1"
TIMESTAMP=$(date +%s)
BRANCH1="worktree-1-${TIMESTAMP}"
BRANCH2="worktree-2-${TIMESTAMP}"
WORKTREE_DIR1="/tmp/agentbox-worktree-1-${TIMESTAMP}"
WORKTREE_DIR2="/tmp/agentbox-worktree-2-${TIMESTAMP}"

cleanup() {
    echo "Cleaning up worktrees..."
    if [[ -d "$WORKTREE_DIR1" ]]; then
        git worktree remove "$WORKTREE_DIR1" --force 2>/dev/null || true
    fi
    if [[ -d "$WORKTREE_DIR2" ]]; then
        git worktree remove "$WORKTREE_DIR2" --force 2>/dev/null || true
    fi
    git branch -D "$BRANCH1" 2>/dev/null || true
    git branch -D "$BRANCH2" 2>/dev/null || true
}

trap cleanup EXIT

echo "Creating git worktrees..."
git worktree add -b "$BRANCH1" "$WORKTREE_DIR1"
git worktree add -b "$BRANCH2" "$WORKTREE_DIR2"

echo "Running Claude Code in parallel..."
echo "  Branch 1 ($BRANCH1) with sonnet: $PROMPT"
echo "  Branch 2 ($BRANCH2) with haiku: $PROMPT"

# Run Claude Code in each worktree directory with different models
(cd "$WORKTREE_DIR1" && claude --output-format stream-json --dangerously-skip-permissions --verbose --model sonnet -p "$PROMPT" | jq --unbuffered -r '
    if .type == "assistant" then
      .message.content[]? |
      if .type == "text" then "[Sonnet] 💭 \(.text)"
      elif .type == "tool_use" then "[Sonnet] 🔧 \(.name): \(.input.file_path // .input.pattern // .input.command // (.input | tostring | .[0:80]))"
      else empty end
    elif .type == "result" then
      "[Sonnet] \n✅ Done in \(.duration_ms)ms"
    else empty end
  ' | stdbuf -oL uniq) &
PID1=$!

(cd "$WORKTREE_DIR2" && claude --output-format stream-json --dangerously-skip-permissions --verbose --model haiku -p "$PROMPT" | jq --unbuffered -r '
    if .type == "assistant" then
      .message.content[]? |
      if .type == "text" then "[Haiku] 💭 \(.text)"
      elif .type == "tool_use" then "[Haiku] 🔧 \(.name): \(.input.file_path // .input.pattern // .input.command // (.input | tostring | .[0:80]))"
      else empty end
    elif .type == "result" then
      "[Haiku] \n✅ Done in \(.duration_ms)ms"
    else empty end
  ' | stdbuf -oL uniq) &
PID2=$!

# Wait for both processes to complete
echo "Waiting for both Claude processes to complete..."
wait $PID1
EXIT1=$?
echo "Branch 1 completed with exit code: $EXIT1"

wait $PID2
EXIT2=$?
echo "Branch 2 completed with exit code: $EXIT2"

# Run final Claude Code process to review and merge
echo ""
echo "Running final Claude Code process to choose best implementation..."
claude --output-format stream-json --dangerously-skip-permissions --verbose -p "Two parallel implementations of the task \"$PROMPT\" were created:

Implementation 1 (sonnet model): $WORKTREE_DIR1
Implementation 2 (haiku model): $WORKTREE_DIR2

Your task:
1. Compare both implementations by examining the git diff in each directory (use 'git -C <path> diff' to see changes)
2. Choose the better implementation based on code quality, correctness, and completeness
3. Copy all changed files from the chosen implementation to the current working directory
4. Create a new git branch with a brief name (2-4 words, kebab-case) based on the original task: \"$PROMPT\"
5. Commit all changes to that new branch with an appropriate commit message

Start by examining the diffs in both implementations." | jq --unbuffered -r '
    if .type == "assistant" then
      .message.content[]? |
      if .type == "text" then "[Reviewer] 💭 \(.text)"
      elif .type == "tool_use" then "[Reviewer] 🔧 \(.name): \(.input.file_path // .input.pattern // .input.command // (.input | tostring | .[0:80]))"
      else empty end
    elif .type == "result" then
      "[Reviewer] \n✅ Done in \(.duration_ms)ms"
    else empty end
  ' | stdbuf -oL uniq

echo ""
echo "All done!"
echo "Worktree branches created: $BRANCH1, $BRANCH2"
echo "Worktree directories will be cleaned up on exit"
