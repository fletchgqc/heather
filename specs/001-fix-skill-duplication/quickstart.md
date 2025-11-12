# Quickstart: Fix SKILL File Duplication Bug

**Feature**: 001-fix-skill-duplication
**Status**: Implementation Ready
**Date**: 2025-11-12

## Prerequisites

- Git repository with `.claude/commands/` directory
- Bash shell (Linux/macOS)
- Existing Claude skills in `.claude/skills/` OR OpenCode agents in `.opencode/agent/`

## Quick Test - Before Fix

### Test the duplication bug

```bash
# Start with a fresh OpenCode structure (or use existing)
mkdir -p .opencode/agent
echo "# Test Agent" > .opencode/agent/test-agent.md

# Run the buggy opencode-to-claude conversion
# (This will be in .claude/commands/opencode-to-claude.md)

# Expected bug: After running twice, you may see duplicate or incorrectly renamed files
```

### Test the structure bug

```bash
# Start with a Claude skill
mkdir -p .claude/skills/test-skill
echo "# Test Skill" > .claude/skills/test-skill/SKILL.md

# Run the buggy claude-to-opencode conversion
# (This will be in .claude/commands/claude-to-opencode.md)

# Expected bug: Creates .opencode/agent/test-skill/test-skill.md instead of .opencode/agent/test-skill.md
ls -la .opencode/agent/
```

## Implementation Steps

### 1. Fix the duplication bug (opencode-to-claude.md)

**File**: `.claude/commands/opencode-to-claude.md`

**Current buggy line 27**:
```bash
find .claude/skills -name "*.md" -exec sh -c 'dir=$(dirname "$1"); base=$(basename "$1" .md); mv "$1" "$dir/SKILL.md"' _ {} \;
```

**Fixed version**:
```bash
find .claude/skills -type f -name "*.md" ! -name "SKILL.md" -exec sh -c 'dir=$(dirname "$1"); mv -f "$1" "$dir/SKILL.md"' _ {} \;
```

**Changes**:
- Added `! -name "SKILL.md"` to exclude already-converted files
- Added `-type f` for clarity (only match files, not directories)
- Added `-f` flag to `mv` for idempotent overwrites
- Removed unnecessary `basename` logic

### 2. Fix the structure bug (claude-to-opencode.md)

**File**: `.claude/commands/claude-to-opencode.md`

**Current buggy lines 23 and 27**:
```bash
cp -r .claude/skills/* .opencode/agent/ 2>/dev/null || true

find .opencode/agent -name "SKILL.md" -exec sh -c 'dir=$(dirname "$1"); base=$(basename "$dir"); mv "$1" "$dir/$base.md"' _ {} \;
```

**Fixed version**:
```bash
# Flatten Claude nested structure to OpenCode flat structure
for skill_dir in .claude/skills/*/; do
    if [ -d "$skill_dir" ]; then
        agent_name=$(basename "$skill_dir")
        if [ -f "$skill_dir/SKILL.md" ]; then
            cp -f "$skill_dir/SKILL.md" ".opencode/agent/$agent_name.md"
        fi
    fi
done
```

**Changes**:
- Replace `cp -r` with explicit loop to flatten structure
- Check for directory and file existence before copying
- Use `-f` flag for idempotent overwrites
- Creates flat structure: `.opencode/agent/agent-name.md`

### 3. Add output messages

Add after each conversion section:

**For opencode-to-claude.md** (add at end):
```bash
echo "Mapping completed successfully! OpenCode agents converted to Claude skills."
```

**For claude-to-opencode.md** (add at end):
```bash
echo "Mapping completed successfully! Claude skills converted to OpenCode flat structure."
```

## Quick Test - After Fix

### Test idempotency

```bash
# Run conversion from OpenCode to Claude
# Run /opencode-to-claude command

# Run it again - should produce identical results
# Run /opencode-to-claude command again

# Verify no duplicates
find .claude/skills -name "*.md" | sort
```

### Test flat structure

```bash
# Run conversion from Claude to OpenCode
# Run /claude-to-opencode command

# Verify flat structure
ls -la .opencode/agent/
# Should see: agent-name.md (not agent-name/agent-name.md)
```

### Test bidirectional conversion

```bash
# Start with Claude format
ls -la .claude/skills/

# Convert to OpenCode
# Run /claude-to-opencode

# Convert back to Claude
# Run /opencode-to-claude

# Compare with original - should be identical
ls -la .claude/skills/
```

## Validation Checklist

- [ ] Duplication bug fixed: Running opencode-to-claude twice produces identical results
- [ ] Structure bug fixed: claude-to-opencode creates flat `.opencode/agent/agent-name.md`
- [ ] Legacy support: opencode-to-claude handles both flat and nested OpenCode structures
- [ ] Idempotency: Both conversions can be run multiple times without errors
- [ ] Output messages: Both commands output success messages
- [ ] File content preserved: All content remains intact through conversions
- [ ] Edge cases handled: Empty directories ignored, existing files overwritten

## Performance Validation

```bash
# Create 20 test agents
for i in {1..20}; do
    mkdir -p .claude/skills/test-agent-$i
    echo "# Test Agent $i" > .claude/skills/test-agent-$i/SKILL.md
done

# Time the conversion
time /claude-to-opencode

# Should complete in < 5 seconds
```

## Rollback

If the fix causes issues:

```bash
# Revert the command files
git checkout .claude/commands/claude-to-opencode.md
git checkout .claude/commands/opencode-to-claude.md

# Clean up OpenCode directory and regenerate
rm -rf .opencode/agent/*
# Run old conversion
```

## Next Steps

After validation:
1. Run `/speckit.tasks` to generate implementation task list
2. Implement the changes to both command files
3. Test with real project agents
4. Commit changes
5. Document any migration notes for users with existing nested structures
