# How to Write Implementation Plans

## Purpose

Before implementing a feature, the AI agent should create an **implementation plan** in collaboration with the human engineer. This is separate from the spec and serves a different purpose.

## Spec vs Implementation Plan

| Spec | Implementation Plan |
|------|-------------------|
| Written before implementation | Created at start of implementation |
| Describes WHAT and WHY | Describes HOW and WHEN |
| Guardrails and constraints | Specific steps and files |
| Stable over time | Evolves during implementation |
| 1-2 pages maximum | Can be more detailed |

## When to Create an Implementation Plan

Create an implementation plan for:
- Features with multiple interconnected components
- Changes that affect existing code in multiple places
- Features where the implementation approach isn't immediately obvious
- Complex features that benefit from breaking into phases

Skip the implementation plan for:
- Simple, single-file changes
- Obvious implementations with clear steps
- Bug fixes with known solutions

## What to Include in an Implementation Plan

1. **Implementation Steps** (ordered list)
   - Specific files to create or modify
   - Dependencies between steps
   - What to implement in each step

2. **Key Decision Points**
   - Where alternative approaches were considered
   - Why specific choices were made during planning

3. **Testing Strategy**
   - How to test incrementally during implementation
   - Integration testing approach
   - There can be **HINTS** about what testing methology (Unit, Property, Integreation) are viable.

4. **Risks and Unknowns**
   - Areas where the approach might need adjustment
   - Dependencies on external factors

## Writing Guidelines

**DO:**
- Break work into logical, testable steps
- Identify dependencies between steps
- Note potential issues before starting
- Update the plan as you learn during implementation
- Keep it practical and actionable

**DON'T:**
- Include actual code (that goes in the implementation)
- Make it a task checklist (focus on approach, not granular tasks)
- Overplan - some details emerge during implementation
- Treat it as immutable - adjust as needed

## Where to Store Implementation Plans

Implementation plans are working documents:
- Store in `specs/` directory with suffix `-implementation-plan.md`
- Example: `specs/bash-tool-implementation-plan.md`
- Can be deleted after implementation is complete
- Or kept as reference for understanding implementation decisions
