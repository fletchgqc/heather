# Workflows
This is a collection of inspirational ideas to get you thinking of what might help in your project.

## Exercises
1. Write a command or process for code review.
2. Let two agent implementations be coded and use LLM as-a-judge to decide which is best. See scripts/parallel-claude.md for example.
3. Try agent interaction with your code host (github/gitlab etc). Decide how you want to work.
4. Write a hook or plugin which logs every prompt and tool use to a certain file named after your session id.

## Core Principle
- maximise automation, minimise wait.
- maximise enjoyable human value add, minimise tedious and tiring work.
- Where and how can we build it into our workflows? E.g.
	- Record meetings discussing requirements, put into LLM. (Requirements 80% time vs coding 20%)
	- Auto PR-Review on push?
	- Auto PR on issue creation?
    - What prompts should you share and maintain as a team? Create slash-commands.
    - AI is great for resolving merge conflicts.

## GitHub OSS interaction
- Let the agent interact with GitHub / GitLab for you. [Authentication tips](https://github.com/fletchgqc/agentbox?tab=readme-ov-file#authenticating-to-git-or-other-scc-providers).
- Example of inline code review: https://github.com/fletchgqc/agentbox/pull/43

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

## Pitfalls
- *Too much* code, doco, tests, everthing. LARGE LANGUAGE model. Costs come later. Reviewing prompts:
    - "Look for opportunities where it should have edited existing code rather than adding new code."
    - "Is this implementation overly complex?"
    - "We are still pre-customer - any unnecessary fallbacks, unnecessary versioning, testing overkill in this?"
    - Keywords like: simple, lean, YAGNI
- Workslop and shift-right.

## Throwaway Code
- Agentbox-supplied plan to add your own features.
- Throwing away code feels wron. Discard and try again.

## Reading
- https://code.claude.com/docs/en/common-workflows
- [Augmented coding patterns](https://lexler.github.io/augmented-coding-patterns/)
- [Playwright MCP](https://github.com/microsoft/playwright-mcp) for LLM-powered frontend tests.
- Learn from the pros, e.g. system prompts. One analysis of claude code prompt https://minusx.ai/blog/decoding-claude-code/
- Use the Hype.
    - "You need a good pipeline, good Tests, scans, doco, secret protection (git guardian etc)" -> nice excuse for good old crafting.
