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

!`if ls .opencode/agent/*.md 1> /dev/null 2>&1; then for agent_file in .opencode/agent/*.md; do if [ -f "$agent_file" ]; then agent_name=$(basename "$agent_file" .md); mkdir -p ".claude/skills/$agent_name"; cp -f "$agent_file" ".claude/skills/$agent_name/SKILL.md"; fi; done; fi`

!`for agent_dir in .opencode/agent/*/; do if [ -d "$agent_dir" ]; then agent_name=$(basename "$agent_dir"); if [ -f "$agent_dir/$agent_name.md" ]; then mkdir -p ".claude/skills/$agent_name"; cp -f "$agent_dir/$agent_name.md" ".claude/skills/$agent_name/SKILL.md"; fi; fi; done`

!`cp AGENTS.md CLAUDE.md 2>/dev/null || true`

!`echo "Mapping completed successfully! OpenCode agents converted to Claude skills."`