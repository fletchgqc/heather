---
description: Map OpenCode directory structure to Claude format
subtask: true
---
Map the .opencode/ directory structure to .claude/ format for dual support.

## Mapping Rules:
- .opencode/command/ → .claude/commands/
- .opencode/agent/ → .claude/skills/
- AGENTS.md → CLAUDE.md (Claude standard)

## Steps:
1. Create .claude directory if it doesn't exist
2. Map commands from .opencode/command/ to .claude/commands/
3. Map agents from .opencode/agent/ to .claude/skills/
4. Copy AGENTS.md to CLAUDE.md for Claude compatibility

Execute the mapping now:
!`mkdir -p .claude/commands .claude/skills`

!`cp -r .opencode/command/* .claude/commands/ 2>/dev/null || true`

!`cp -r .opencode/agent/* .claude/skills/ 2>/dev/null || true`

!`cp AGENTS.md CLAUDE.md 2>/dev/null || true`

!`find .claude/skills -name "*.md" -exec sh -c 'dir=$(dirname "$1"); base=$(basename "$1" .md); mv "$1" "$dir/SKILL.md"' _ {} \;`

Mapping completed successfully! Both OpenCode and Claude formats are now available.