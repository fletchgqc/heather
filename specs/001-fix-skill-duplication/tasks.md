# Tasks: Fix SKILL File Duplication in OpenCode-to-Claude Sync

**Input**: Design documents from `/specs/001-fix-skill-duplication/`
**Prerequisites**: plan.md, spec.md, research.md, quickstart.md

**Tests**: No automated tests requested. Manual integration testing via quickstart.md validation.

**Organization**: Tasks are grouped by user story to enable independent implementation and testing of each story.

## Format: `[ID] [P?] [Story] Description`

- **[P]**: Can run in parallel (different files, no dependencies)
- **[Story]**: Which user story this task belongs to (e.g., US1, US2, US3)
- Include exact file paths in descriptions

## Path Conventions

- Command files: `.claude/commands/`
- OpenCode agents: `.opencode/agent/` (flat structure)
- Claude skills: `.claude/skills/` (nested structure)

---

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Project initialization and documentation setup

- [X] T001 Read and understand current command files at .claude/commands/opencode-to-claude.md
- [X] T002 Read and understand current command files at .claude/commands/claude-to-opencode.md
- [X] T003 Update CLAUDE.md to reflect shell script work per constitution

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Backup and prepare for changes

**⚠️ CRITICAL**: No user story work can begin until this phase is complete

- [X] T004 Create backup branch or ensure git working tree is clean
- [X] T005 Create test directory structure for validation (.test-sync/)
- [X] T006 [P] Create sample Claude skill for testing at .test-sync/.claude/skills/test-agent/SKILL.md
- [X] T007 [P] Create sample OpenCode agent for testing at .test-sync/.opencode/agent/test-agent.md

**Checkpoint**: Foundation ready - user story implementation can now begin in parallel

---

## Phase 3: User Story 1 - Sync OpenCode to Claude Without Duplication (Priority: P1) 🎯 MVP

**Goal**: Fix the duplication bug where opencode-to-claude conversion creates duplicate SKILL.md files

**Independent Test**: Run opencode-to-claude conversion twice and verify each skill directory contains exactly one SKILL.md file

### Implementation for User Story 1

- [X] T008 [US1] Modify find command in .claude/commands/opencode-to-claude.md to exclude SKILL.md files
- [X] T009 [US1] Add -type f flag to ensure only files are matched
- [X] T010 [US1] Add -f flag to mv command for idempotent overwrites
- [X] T011 [US1] Remove unnecessary basename logic from rename operation
- [X] T012 [US1] Add success message output at end of conversion
- [X] T013 [US1] Test: Run conversion once and verify correct SKILL.md creation
- [X] T014 [US1] Test: Run conversion twice and verify no duplicates created
- [X] T015 [US1] Test: Verify existing SKILL.md files are not renamed

**Checkpoint**: At this point, User Story 1 should be fully functional and testable independently

---

## Phase 4: User Story 2 - Use OpenCode Standard Flat Structure (Priority: P2)

**Goal**: Fix the structure bug where claude-to-opencode creates nested directories instead of flat structure

**Independent Test**: Run claude-to-opencode conversion and verify agent files are created as .opencode/agent/[agent-name].md (flat) rather than nested

### Implementation for User Story 2

- [X] T016 [US2] Replace cp -r command with for-loop in .claude/commands/claude-to-opencode.md
- [X] T017 [US2] Add directory existence check in loop
- [X] T018 [US2] Add file existence check for SKILL.md before copying
- [X] T019 [US2] Use basename to extract agent name from skill directory
- [X] T020 [US2] Copy SKILL.md to flat .opencode/agent/[agent-name].md structure
- [X] T021 [US2] Add -f flag to cp command for idempotent overwrites
- [X] T022 [US2] Remove old find/mv command that renamed SKILL.md to agent-name.md
- [X] T023 [US2] Add success message output at end of conversion
- [X] T024 [US2] Test: Verify flat structure .opencode/agent/agent-name.md is created
- [X] T025 [US2] Test: Verify no nested directories are created
- [X] T026 [US2] Test: Verify multiple agents all placed flat under .opencode/agent/

**Checkpoint**: At this point, User Stories 1 AND 2 should both work independently

---

## Phase 5: User Story 3 - Verify Bidirectional Sync Integrity (Priority: P3)

**Goal**: Ensure converting from Claude to OpenCode and back to Claude produces same result without data loss

**Independent Test**: Convert Claude → OpenCode → Claude and compare original with final structure

### Implementation for User Story 3

- [X] T027 [US3] Add support for flat OpenCode structure in opencode-to-claude.md
- [X] T028 [US3] Add support for legacy nested OpenCode structure in opencode-to-claude.md
- [X] T029 [US3] Update find command to handle both .opencode/agent/*.md and .opencode/agent/*/*.md
- [X] T030 [US3] Ensure content preservation through both conversion directions
- [X] T031 [US3] Test: Convert Claude → OpenCode → Claude and verify file count matches
- [X] T032 [US3] Test: Verify file content is identical after bidirectional conversion
- [X] T033 [US3] Test: Verify metadata and permissions preserved

**Checkpoint**: All user stories should now be independently functional

---

## Phase 6: Polish & Cross-Cutting Concerns

**Purpose**: Improvements that affect multiple user stories

- [X] T034 [P] Run full quickstart.md validation checklist
- [X] T035 [P] Test performance with 20 agents (should complete in <5 seconds)
- [X] T036 [P] Test edge case: multiple files in source directory
- [X] T037 [P] Test edge case: existing target files (idempotency)
- [X] T038 [P] Test edge case: empty directories are ignored
- [X] T039 Verify all success/error messages display correctly
- [X] T040 Clean up test directory structure .test-sync/
- [X] T041 Final verification: both commands work end-to-end with real project

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: No dependencies - can start immediately
- **Foundational (Phase 2)**: Depends on Setup completion - BLOCKS all user stories
- **User Stories (Phase 3+)**: All depend on Foundational phase completion
  - User stories can then proceed in parallel (if staffed)
  - Or sequentially in priority order (P1 → P2 → P3)
- **Polish (Final Phase)**: Depends on all user stories being complete

### User Story Dependencies

- **User Story 1 (P1)**: Can start after Foundational (Phase 2) - No dependencies on other stories
- **User Story 2 (P2)**: Can start after Foundational (Phase 2) - Independent of US1 (different file)
- **User Story 3 (P3)**: Should start after US1 and US2 complete - Validates integration of both fixes

### Within Each User Story

- Implementation tasks before test tasks
- Tests run after implementation complete
- Each story complete before moving to next priority

### Parallel Opportunities

- T006 and T007 can run in parallel (different files)
- US1 and US2 can be implemented in parallel (different command files)
- T034-T038 can run in parallel (independent edge case tests)

---

## Parallel Example: User Story 1 & 2

```bash
# US1 and US2 can be implemented in parallel since they modify different files:
Task: "Modify opencode-to-claude.md for duplication fix"
Task: "Modify claude-to-opencode.md for structure fix"

# Polish phase tests can run in parallel:
Task: "Run full quickstart.md validation checklist"
Task: "Test performance with 20 agents"
Task: "Test edge case: multiple files"
```

---

## Implementation Strategy

### MVP First (User Story 1 Only)

1. Complete Phase 1: Setup
2. Complete Phase 2: Foundational (CRITICAL - blocks all stories)
3. Complete Phase 3: User Story 1
4. **STOP and VALIDATE**: Test User Story 1 independently via quickstart.md
5. Deploy/demo if ready

### Incremental Delivery

1. Complete Setup + Foundational → Foundation ready
2. Add User Story 1 → Test independently → Deploy/Demo (MVP: duplication fixed!)
3. Add User Story 2 → Test independently → Deploy/Demo (structure standardized!)
4. Add User Story 3 → Test independently → Deploy/Demo (bidirectional integrity verified!)
5. Each story adds value without breaking previous stories

### Sequential Single-Developer Strategy (RECOMMENDED)

Since this is a small feature (2 command files):

1. Complete Setup + Foundational together
2. Complete User Story 1 → Validate immediately
3. Complete User Story 2 → Validate immediately
4. Complete User Story 3 → Validate integration
5. Polish phase → Final validation

---

## Notes

- [P] tasks = different files, no dependencies
- [Story] label maps task to specific user story for traceability
- Each user story should be independently completable and testable
- Manual testing via quickstart.md validation steps
- Commit after each user story completes
- Stop at any checkpoint to validate story independently
- No automated tests - this is shell script modification with manual integration testing

---

## Task Summary

- **Total tasks**: 41
- **Phase 1 (Setup)**: 3 tasks
- **Phase 2 (Foundational)**: 4 tasks
- **Phase 3 (US1)**: 8 tasks
- **Phase 4 (US2)**: 11 tasks
- **Phase 5 (US3)**: 7 tasks
- **Phase 6 (Polish)**: 8 tasks
- **Parallel opportunities**: 9 tasks marked [P]
- **MVP scope**: Phase 1 + Phase 2 + Phase 3 (User Story 1 only) = 15 tasks
