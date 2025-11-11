---
description: Map Claude directory structure to OpenCode format
subtask: true
---
Map the .claude/ directory structure to .opencode/ format for dual support.

## Mapping Rules:
- .claude/commands/ → .opencode/command/
- .claude/skills/ → .opencode/agent/
- CLAUDE.md → AGENTS.md (OpenCode standard)

## Steps:
1. Create .opencode directory if it doesn't exist
2. Map commands from .claude/commands/ to .opencode/command/
3. Map skills from .claude/skills/ to .opencode/agent/
4. Copy CLAUDE.md to AGENTS.md for OpenCode compatibility

Execute the mapping now:
!`mkdir -p .opencode/command .opencode/agent`

!`cp -r .claude/commands/* .opencode/command/ 2>/dev/null || true`

!`cp -r .claude/skills/* .opencode/agent/ 2>/dev/null || true`

!`cp CLAUDE.md AGENTS.md 2>/dev/null || true`

!`find .opencode/agent -name "SKILL.md" -exec sh -c 'dir=$(dirname "$1"); base=$(basename "$dir"); mv "$1" "$dir/$base.md"' _ {} \;`

Mapping completed successfully! Both Claude and OpenCode formats are now available.