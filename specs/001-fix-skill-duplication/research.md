# Research: Fix SKILL File Duplication in OpenCode-to-Claude Sync

**Date**: 2025-11-12
**Feature**: Fix SKILL file duplication and OpenCode structure bugs
**Branch**: 001-fix-skill-duplication

## Research Questions

### 1. What is the root cause of the duplication bug?

**Decision**: The find command in `opencode-to-claude.md` (line 27) matches ALL .md files including already-converted SKILL.md files.

**Current Implementation** (buggy):
```bash
find .claude/skills -name "*.md" -exec sh -c 'dir=$(dirname "$1"); base=$(basename "$1" .md); mv "$1" "$dir/SKILL.md"' _ {} \;
```

**Problem**: This renames:
- `accessing-github.md` → `SKILL.md` (correct)
- `SKILL.md` → `SKILL.md` (unnecessary, causes issues on re-run)
- Any other `.md` file → `SKILL.md` (wrong)

**Rationale**: The pattern `*.md` is too broad and doesn't exclude already-converted files.

**Alternatives Considered**:
- Add logic to check if SKILL.md exists before renaming → More complex, harder to maintain
- Use a temporary directory for conversion → Over-engineered for this simple task
- **SELECTED**: Filter to exclude SKILL.md files from the find results → Simple, idempotent

### 2. What is the correct OpenCode directory structure per official documentation?

**Decision**: OpenCode uses a flat structure where agent files are placed directly under `.opencode/agent/`

**OpenCode Standard** (from https://opencode.ai/docs/agents/):
```
.opencode/agent/
├── agent-name-1.md
├── agent-name-2.md
└── agent-name-3.md
```

**Current Buggy Implementation** (nested):
```
.opencode/agent/
└── agent-name/
    └── agent-name.md
```

**Rationale**: The official OpenCode documentation shows all agent files at the same level under the agent/ directory. The current implementation uses `cp -r .claude/skills/* .opencode/agent/` which copies the nested directory structure from Claude format.

**Alternatives Considered**:
- Keep nested structure and update OpenCode tools → Not our responsibility, breaks compatibility
- Support both structures → Over-complicates, violates Simplicity First principle
- **SELECTED**: Flatten to match OpenCode standard → Ensures compatibility, follows official spec

### 3. How to handle legacy nested OpenCode structures during conversion?

**Decision**: Support both flat (standard) and nested (legacy) structures when converting from OpenCode to Claude

**Rationale**: Some projects may have already run the buggy claude-to-opencode conversion and have nested structures. We should handle both gracefully to avoid breaking existing setups.

**Implementation Approach**:
1. First, look for flat files: `.opencode/agent/*.md`
2. Then, look for nested files: `.opencode/agent/*/*.md`
3. For nested files, extract the base filename before creating SKILL.md

**Alternatives Considered**:
- Only support flat structure, require manual migration → Breaks existing projects
- Only support nested structure → Doesn't align with OpenCode standard
- **SELECTED**: Support both during transition → Maintains backward compatibility

### 4. Best practices for idempotent shell file operations

**Decision**: Use selective find patterns and overwrite semantics

**Best Practices Applied**:
1. **Selective matching**: Exclude files that are already in target format
   - Use `! -name "SKILL.md"` to exclude already-converted files
   - Use specific patterns to match only source files

2. **Idempotent overwrites**: Use `mv -f` (force) to overwrite existing files
   - Allows re-running without errors
   - User can recover from partial failures by re-running

3. **Error handling**: Let shell commands fail naturally and report via exit codes
   - Don't suppress errors with `2>/dev/null` on critical operations
   - Keep `2>/dev/null || true` only for optional cleanup operations

4. **Output messages**: Echo success/error messages for user feedback
   - Consistent with FR-011 requirement

**Alternatives Considered**:
- Transactional operations with rollback → Too complex for shell scripts
- Pre-flight validation → Adds complexity, shell idiom is fail-fast
- **SELECTED**: Simple idempotent operations → Unix philosophy, easy to understand

### 5. Shell command patterns for flattening directory structures

**Decision**: Loop through nested directories and copy files to flat structure

**Pattern**:
```bash
for dir in .claude/skills/*/; do
    agent_name=$(basename "$dir")
    cp "$dir/SKILL.md" ".opencode/agent/$agent_name.md"
done
```

**Rationale**: This approach:
- Explicitly handles the transformation from nested to flat
- Clear and readable
- Doesn't rely on complex find commands
- Easy to debug

**Alternatives Considered**:
- Complex find with dirname/basename manipulation → Harder to read and maintain
- Use rsync with --flatten → External dependency, not available everywhere
- **SELECTED**: Simple for-loop → Straightforward, no external dependencies

## Summary

The research confirms that both bugs can be fixed with straightforward shell command modifications:

1. **Duplication bug**: Add `! -name "SKILL.md"` filter to the find command
2. **Structure bug**: Replace `cp -r` with an explicit loop that flattens the structure

No additional research needed - implementation can proceed to Phase 1.
