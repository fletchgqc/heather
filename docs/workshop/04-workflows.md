# Workflows
This is a collection of inspirational ideas to get you thinking of what might help in your project.

## GitHub OSS interaction
- Let interact with GitHub / GitLab for you. [Authentication tips](https://github.com/fletchgqc/agentbox?tab=readme-ov-file#authenticating-to-git-or-other-scc-providers).

## Pipeline-fixer
Sick of waiting for pipelines to fail and put error into LLM? Prompt successfully used by Christoph Muth.
> “I’m currently always getting an ‘OOMKilled’ error in the GitLab pipeline. Can you use glab to read the logs, fix it, push the changes, and repeat that until it’s fixed?” (I had previously set up glab on my machine via a GitLab access token.)
> He then also immediately wanted to fix other things as well, but if necessary you can always revert those yourself later.

## Simultaneous agents
- git worktree
- Clone your project twice
- Background agents - cursor, claude

## LLM as a judge
- Try the `/recursive-review` command.
- Putting it together: spin up multiple implementations and then automatically compare them and select the best.

## Business Empowerment
- Identify new workflows:
    - PO-dev pairing prototyping refinement.
    - Build a pipeline which allows the PO to prompt something and it to be deployed to a dev environment without help.
    - Offer templated PO-prompts?
    - How can you give the PO more power to get feedback himself?
    - Professional vibe coding?

## Deterministic steps
- Use hooks (claude code) or plugins (opencode) to deterministically ensure certain things run at certain times.

## Other
- [Playwright MCP](https://github.com/microsoft/playwright-mcp) for LLM-powered frontend tests.
- AI is great for resolving merge conflicts.
- What prompts should you share and maintain as a team? Create slash-commands.

## Pitfalls
- *Too much* code, doco, tests, everthing. LARGE LANGUAGE model. Costs come later. Reviewing prompts:
    - "Look for opportunities where it should have edited existing code rather than adding new code."
    - "Is this implementation overly complex?"
    - "We are still pre-customer - any unnecessary fallbacks, unnecessary versioning, testing overkill in this?"
    - Keywords like: simple, lean, YAGNI
- Workslop and shift-right.

## Brainstorming ideas
- Throwaway code (Felix's change to Agentbox - prompts to allow us to produce own extensions).
- Who needs POs? Why not just let the LLM create issues based on metrics?

## Reading
- https://code.claude.com/docs/en/common-workflows
