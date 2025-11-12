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

!`for skill_dir in .claude/skills/*/; do if [ -d "$skill_dir" ]; then agent_name=$(basename "$skill_dir"); if [ -f "$skill_dir/SKILL.md" ]; then cp -f "$skill_dir/SKILL.md" ".opencode/agent/$agent_name.md"; fi; fi; done`

!`cp CLAUDE.md AGENTS.md 2>/dev/null || true`

!`echo "Mapping completed successfully! Claude skills converted to OpenCode flat structure."`