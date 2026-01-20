# How to Write Feature Specifications

## Purpose

Feature specs serve as **interface documents for human engineers to guide AI agents** during implementation. They are NOT exhaustive implementation documentation. Instead, they provide:

- Clear feature descriptions
- Key architectural constraints and design decisions
- Critical guardrails to prevent common mistakes
- Success criteria for testing

The human engineer reads the spec, then guides the AI agent to implement the feature. Keep specs **short and concise** - focus on what matters for achieving the desired outcome.

## Spec Structure

Each spec file should include:

### Required Sections

1. **Overview** (2-4 sentences)
   - What the feature does and why it exists
   - Primary use case

2. **Key Constraints & Design Decisions**
   - Architectural decisions that must be respected
   - Security or safety requirements
   - Performance considerations

3. **Usage** (brief examples)
   - How a user interacts with the feature
   - 1-2 concrete examples showing typical usage
   - Expected input/output behavior

4. **Testing**
   - Clear success criteria
   - 3-5 key test scenarios
   - How to verify the feature works correctly

### Optional Sections (only if critical)

- **Dependencies** - Required packages or external services
- **Configuration** - Required environment variables or settings
- **Integration Points** - Where this feature connects to existing code

## Writing Guidelines

**DO:**
- Keep it brief - aim for 1-2 pages maximum
- Focus on constraints and decisions that guide implementation
- Provide clear success criteria
- Highlight potential pitfalls
- Use concrete examples for usage

**DON'T:**
- Include detailed code snippets or function signatures
- Document every file path and line number
- Write exhaustive implementation steps
- Duplicate information that's obvious from the overview
- Include information that will quickly become outdated

## Creating a New Spec

When adding a new feature spec:
1. Create a new `.md` file in the `specs/` directory using the structure above
2. Keep it concise - if it's longer than 2 pages, it's too detailed
3. Focus on guardrails and constraints, not implementation details
4. Add an entry to the Components table in `specs/README.md`
