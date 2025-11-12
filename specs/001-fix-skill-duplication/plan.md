# Implementation Plan: Fix SKILL File Duplication in OpenCode-to-Claude Sync

**Branch**: `001-fix-skill-duplication` | **Date**: 2025-11-12 | **Spec**: [spec.md](spec.md)
**Input**: Feature specification from `/specs/001-fix-skill-duplication/spec.md`

**Note**: This template is filled in by the `/speckit.plan` command. See `.specify/templates/commands/plan.md` for the execution workflow.

## Summary

Fix two bugs in the Claude↔OpenCode conversion commands:
1. **Duplication bug**: `.claude/commands/opencode-to-claude.md` renames ALL .md files to SKILL.md, causing duplicates on repeated runs
2. **Structure bug**: `.claude/commands/claude-to-opencode.md` creates non-standard nested directories instead of OpenCode's flat structure

Technical approach: Modify shell find/mv commands to be selective and idempotent, and flatten OpenCode structure to match official standard.

## Technical Context

**Language/Version**: Shell script (bash), Markdown command files
**Primary Dependencies**: Unix shell commands (find, mv, cp, mkdir), Claude Code command execution
**Storage**: File system (no database)
**Testing**: Manual integration testing (run conversion commands and verify file structure)
**Target Platform**: Linux/macOS with bash shell
**Project Type**: Command-line tooling (Claude Code commands)
**Performance Goals**: <5 seconds for 20 agents
**Constraints**: Must use standard Unix commands only; must maintain backward compatibility with existing structures
**Scale/Scope**: Typically 1-10 agents per project, expected to handle up to 20 agents

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

### I. Simplicity First ✅ PASS
- Solution uses straightforward shell command modifications
- No new abstractions needed - just fixing existing find/mv logic
- Changes are minimal and localized to two command files

### II. Code Quality Over Coverage ✅ PASS
- Shell commands will be readable with clear variable names
- Pattern follows existing command file structure
- Changes will be reviewed before merge

### III. 80% Solutions ✅ PASS
- Targets the common case (standard agent conversion)
- Handles both flat and nested legacy structures gracefully
- Edge cases (multiple files, permissions) documented but don't block core functionality

### IV. Maintainability-Driven Design ✅ PASS
- No new dependencies added
- Uses platform shell commands (find, mv, cp)
- Simple enough to understand and modify in the future

### V. Balanced Testing ✅ PASS
- Integration tests required: run conversion commands and verify file structure
- No unit tests needed (shell script logic is straightforward)
- Manual testing sufficient for this scope

**Gate Decision**: ✅ PROCEED - All principles satisfied, no violations to justify

## Project Structure

### Documentation (this feature)

```text
specs/001-fix-skill-duplication/
├── plan.md              # This file (/speckit.plan command output)
├── spec.md              # Feature specification (already exists)
├── checklists/
│   └── requirements.md  # Quality checklist (already exists)
├── research.md          # Phase 0 output (to be created)
├── quickstart.md        # Phase 1 output (to be created)
└── contracts/           # Phase 1 output (N/A for this feature - no APIs)
```

### Source Code (repository root)

```text
.claude/
└── commands/
    ├── claude-to-opencode.md     # File to modify (structure bug fix)
    └── opencode-to-claude.md     # File to modify (duplication bug fix)

.opencode/
├── command/                       # Commands (not affected by this fix)
└── agent/                         # Agents (structure will change to flat)
    └── [agent-name].md           # NEW: Flat structure per OpenCode standard
```

**Structure Decision**: This is a command file modification feature. No new source directories needed. Changes are confined to two existing markdown command files in `.claude/commands/`. The OpenCode agent directory structure will transition from nested to flat.

## Complexity Tracking

> **No complexity violations** - this feature follows all constitution principles without exceptions.

## Post-Design Constitution Re-Check

*After Phase 1 design completion*

### I. Simplicity First ✅ PASS (Re-confirmed)
- Research confirmed: simple filter addition (`! -name "SKILL.md"`) fixes duplication
- Structure fix uses basic for-loop, no complex find gymnastics
- No abstractions added, just inline script improvements

### II. Code Quality Over Coverage ✅ PASS (Re-confirmed)
- Quickstart guide provides clear before/after examples
- Commands are self-documenting with clear variable names
- Output messages provide user feedback per FR-011

### III. 80% Solutions ✅ PASS (Re-confirmed)
- Handles both standard and legacy structures (backward compatible)
- Edge cases (permissions, disk space) handled via idempotent retry pattern
- Performance validated: for-loop over 20 agents completes in <5 seconds

### IV. Maintainability-Driven Design ✅ PASS (Re-confirmed)
- Zero new dependencies
- Standard shell patterns (find, for-loop, basename)
- CLAUDE.md updated with shell script context for future maintainers

### V. Balanced Testing ✅ PASS (Re-confirmed)
- Integration test plan in quickstart.md covers all scenarios
- Manual validation checklist provided
- No unit tests needed (shell scripts, simple logic)

**Final Gate Decision**: ✅ APPROVED FOR IMPLEMENTATION - Design maintains all constitutional principles. Ready for `/speckit.tasks`.
