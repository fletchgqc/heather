# Feature Specification: Fix SKILL File Duplication in OpenCode-to-Claude Sync

**Feature Branch**: `001-fix-skill-duplication`
**Created**: 2025-11-12
**Status**: Draft
**Input**: User description: "Fix this bug. A recent commit allowed syncing claude code and opencode files so that both solutions can be used. When converting in one of the directions, claude code SKILL files are duplicated. This should not happen."

## Clarifications

### Session 2025-11-12

- Q: Should the conversion fix the OpenCode directory structure to match the official flat standard instead of using nested subdirectories? → A: Yes, fix structure to match OpenCode's flat standard (`.opencode/agent/accessing-github.md` instead of `.opencode/agent/accessing-github/accessing-github.md`)
- Q: When error occurs during conversion (e.g., permission denied, disk full), what should happen to partially completed files? → A: Leave partial conversions in place and report error; user can re-run to complete
- Q: Should the conversion commands output progress/status messages, or run silently? → A: Output brief success/error messages only

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Sync OpenCode to Claude Without Duplication (Priority: P1)

A developer working with OpenCode format wants to sync their agent definitions to Claude format. They run the opencode-to-claude conversion command, and the system correctly converts each OpenCode agent file (e.g., `accessing-github.md`) to the corresponding Claude SKILL file (`SKILL.md`) within the appropriate skill directory without creating duplicate files.

**Why this priority**: This is the core bug fix. Without this working correctly, the dual format support is broken and users cannot reliably convert between formats.

**Independent Test**: Can be fully tested by running the opencode-to-claude conversion command with an existing OpenCode agent structure and verifying that each agent directory contains exactly one SKILL.md file with no duplicates.

**Acceptance Scenarios**:

1. **Given** an OpenCode agent directory structure with agents (e.g., `.opencode/agent/accessing-github/accessing-github.md`), **When** the opencode-to-claude conversion is executed, **Then** each Claude skill directory contains exactly one `SKILL.md` file
2. **Given** the conversion has been run once successfully, **When** the conversion is run a second time, **Then** no duplicate SKILL.md files are created and the existing files are properly updated
3. **Given** multiple agent files in the OpenCode structure, **When** the conversion is executed, **Then** each agent is converted to its own skill directory with a single SKILL.md file

---

### User Story 2 - Use OpenCode Standard Flat Structure (Priority: P2)

A developer converts Claude skills to OpenCode format and expects the output to match OpenCode's official documentation (flat structure: `.opencode/agent/agent-name.md`). The conversion creates files directly under the agent directory without nested subdirectories, making them compatible with standard OpenCode tooling.

**Why this priority**: The current implementation creates a non-standard nested structure that doesn't match OpenCode's official documentation, which could cause compatibility issues with OpenCode tools.

**Independent Test**: Can be tested by running the claude-to-opencode conversion and verifying that agent files are created as `.opencode/agent/[agent-name].md` (flat) rather than `.opencode/agent/[agent-name]/[agent-name].md` (nested).

**Acceptance Scenarios**:

1. **Given** a Claude skill at `.claude/skills/accessing-github/SKILL.md`, **When** converted to OpenCode format, **Then** the output file is `.opencode/agent/accessing-github.md` (not `.opencode/agent/accessing-github/accessing-github.md`)
2. **Given** multiple Claude skills, **When** converted to OpenCode format, **Then** all agent files are placed flat under `.opencode/agent/` directory

---

### User Story 3 - Verify Bidirectional Sync Integrity (Priority: P3)

A developer needs to ensure that converting from Claude to OpenCode and back to Claude produces the same result without data loss or duplication. They can run both conversion commands sequentially and verify that the original structure is preserved.

**Why this priority**: Ensures that the sync mechanism is reliable and reversible, which is essential for teams that may need to switch between formats or use both.

**Independent Test**: Can be tested by starting with Claude format, converting to OpenCode, then back to Claude, and comparing the original and final file structures.

**Acceptance Scenarios**:

1. **Given** a Claude skills directory structure, **When** converted to OpenCode format and then back to Claude format, **Then** the resulting structure matches the original with no duplicate or missing files
2. **Given** skill metadata and content, **When** performing bidirectional conversion, **Then** all content is preserved accurately without corruption or loss

---

### Edge Cases

- **Multiple files in source**: When converting from OpenCode to Claude, if `.opencode/agent/` contains both standard flat files and legacy nested directories, the conversion should handle both gracefully
- **Existing target files**: If a SKILL.md file already exists when converting from OpenCode, it should be overwritten with the source content (idempotent behavior)
- **Non-standard filenames**: Additional .md files in Claude skill directories (e.g., README.md, notes.md) should not be copied during Claude-to-OpenCode conversion
- **Empty directories**: Empty agent/skill directories should be ignored during conversion
- **Partial failure recovery**: If conversion fails midway (disk full, permissions), partial files remain; user re-runs command to complete (idempotent overwrites handle this)

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: System MUST convert each OpenCode agent file to exactly one Claude SKILL.md file per skill directory
- **FR-002**: System MUST NOT create duplicate SKILL.md files when conversion is run multiple times
- **FR-003**: System MUST properly identify and rename agent files from OpenCode format (agent-name.md) to Claude format (SKILL.md)
- **FR-004**: Conversion process MUST preserve file content during transformation
- **FR-005**: System MUST handle the find/rename command correctly to avoid renaming already-converted SKILL.md files
- **FR-006**: Conversion MUST only rename agent-specific files, not unrelated .md files in the directory structure
- **FR-007**: Claude-to-OpenCode conversion MUST create flat structure (`.opencode/agent/agent-name.md`) matching OpenCode's official standard
- **FR-008**: OpenCode-to-Claude conversion MUST handle both flat (standard) and nested (legacy) OpenCode structures
- **FR-009**: Conversion errors (permission denied, disk full) MUST leave partial work in place and report error message to user
- **FR-010**: Conversion MUST be idempotent (re-running overwrites existing files to allow recovery from partial failures)
- **FR-011**: Conversion commands MUST output brief success message on completion and error messages on failure

### Key Entities

- **Claude Skill Directory**: A nested directory structure containing a SKILL.md file (e.g., `.claude/skills/accessing-github/SKILL.md`)
- **OpenCode Agent File (Standard)**: A markdown file placed flat under the agent directory (e.g., `.opencode/agent/accessing-github.md`) per OpenCode official documentation
- **OpenCode Agent File (Legacy)**: A markdown file in a nested subdirectory (e.g., `.opencode/agent/accessing-github/accessing-github.md`) created by the current buggy conversion
- **Conversion Command**: The command/script that transforms between OpenCode and Claude formats

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: Running the opencode-to-claude conversion command multiple times on the same directory structure produces identical results with zero duplicate files
- **SC-002**: 100% of OpenCode agent files are successfully converted to Claude SKILL.md files without duplication
- **SC-003**: Bidirectional conversion (Claude → OpenCode → Claude) completes with 100% file integrity
- **SC-004**: Conversion process completes in under 5 seconds for a typical project with up to 20 agents
- **SC-005**: Claude-to-OpenCode conversion produces flat structure matching OpenCode's official documentation standard

## Assumptions

- The duplication bug is in the `opencode-to-claude.md` conversion command, specifically in the find/rename logic (line 27)
- The structure bug is in `claude-to-opencode.md` which uses `cp -r` to copy nested directories instead of flattening them
- The current opencode-to-claude command renames ALL .md files to SKILL.md, including already-converted SKILL.md files, which may cause duplication or unexpected behavior
- According to OpenCode's official documentation, each agent file should be placed flat under `.opencode/agent/` (e.g., `.opencode/agent/accessing-github.md`)
- Each Claude skill should be in its own nested directory with a single SKILL.md file (e.g., `.claude/skills/accessing-github/SKILL.md`)
- The conversion scripts use shell commands executed within the command file
- Legacy nested OpenCode structures may exist and should be handled gracefully during conversion

## Technical Constraints

- Must maintain compatibility with existing Claude and OpenCode directory structures
- Must work with standard Unix shell commands (find, mv, cp)
- Must handle edge cases gracefully (missing directories, existing files, etc.)

## Out of Scope

- Creating a GUI for the conversion process
- Automatic synchronization or file watching
- Validation of agent/skill file content or format
- Migration of existing duplicated files (users must clean up manually)
- Support for other file formats beyond markdown
