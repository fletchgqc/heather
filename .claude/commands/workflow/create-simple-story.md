# Create a User Story for $ARGUMENT.

Your task is to guide the user in writing a good user story.
Follow these steps.

1. Understand
Clarify with the user the exact purpose of the story. What is it for, what should it achieve?

2. Create Story
Based on these insights, propose an initial user story. Ensure the story follows this pattern:

As a USER
I want FEATURE
so that PURPOSE

Let the user review and modify the story.

3. Acceptance Criteria
Add acceptance criteria to the story. Consider what could go wrong, what edge cases might exist, and what side effects should be avoided.
Let the user review, modify, or add to the criteria.

4. Create Markdown file
When everything is complete and the user agrees, document it in a markdown file in the project directory /docs/stories. The file should have the next-highest available 3-digit prefix above 000, for example, `003-show-stars.md` if `002` is the highest prefix of a file in that directory.

Split the acceptance criteria into two parts:

- testable criteria: Each of these must have at least one test case.
- other criteria: Each of these must be verified during code review.

All other insights that emerged during the discussion and are important for the ticket, write at the end under a section with the heading:
`# Notes`, if they are general or domain-specific insights
or
`# Technical`, if they concern technical details or implementation
