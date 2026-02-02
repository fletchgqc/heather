# Context Engineering

## Exercises
1. Use `/init` to generate base context for your agent. Prompt "what is this project" with and without the resultant file present and compare the result.
2. (claude code) View your current context with `/context`
3. Create two separate context files for working on tasks relating to two specific parts of your application, for example, frontend, backend, database, writing stories.
4. Use context gating (aka. selective context loading): thin out your AGENTS.md file but include references to other files and instructions on when to load them.
5. Try moving your instruction files to skills instead (read about agent skills online).
6. Generate a subagent using `/agent` or by prompting.

## Ideas
- Context compacting gives away control. Rather:
	- `/clear` (`/new` in opencode)
	- ask the agent to summarise your session:
		- e.g. "document what we've done"
		- future idea: write a hook to log all tool usages per session, this can serve as summary.
- Loading context:
	- separate priming commands: generate task-specific information to a file, e.g. frontend-architecture.md. Reference the file with @ in your frontend-related prompt.
	- Read and prepare:
		- "Read about x and prepare to discuss it"
		- "Using https://opencode.ai/docs/commands/, explain how to create a slash command in opencode"
		- "@claude-checklists/system-architecture.md @claude-checklists/2026-01-08-mper.md. We are going to work on the document identification part of the app. Dig in, read relevant files and tests, prepare to discuss the ins and outs of how it works."
- Run a bash command, for example `!git ls-files` or `!git diff HEAD~1`. Then issue your prompt.
- Always keep in mind what's in your context. You should at any time be able to summarise what's in your context (when did the conversation begin and what happened).
- Anytime you have corrected the agent once or twice, try running `/learn` (custom command in this repo).
	- Jannis version - reflect: https://gitlab.codecentric.de/jmm/shared_config/-/blob/9b41cb69520179dff9871da4e2d8030e49c0613e/src/.claude/commands/reflect.md
	- Real-life prompt I used: "copilot config is stored in ~/.copilot, but when you executed the plan you hallucinated a different config directory. I want you to think about what went wrong  and update the plan so that this doesn't happen to future agents. Perhaps strongly encourage implementers to research where the tool stores its config files and to avoid hallucination, only use paths which are definitely read from the internet during internet search. The tools always document their config directories on the internet."
- Session management (claude command names, similar functions available in other tools):
	- go backwards: try double-escape or `/rewind` once you're a few prompts into a conversation.  Warning: this also restores code!
	- start claude with `--resume` to resume previous session
	- name sessions with `/rename`
	- start claude with `--fork-session <sessionname>`
	- try command `/resume` inside session

## Context Saturation, Ignored Instructions
- Tool ignores important system prompt instructions? Try https://github.com/lexler/claude-code-user-reminders
- A deterministic tool is better than an instruction, since instructions can be ignored. E.g. instead of "never reference a Controller from a Service" in AGENTS.md, create an ArchUnit test for this. When the test breaks, the agent can interpret this and fix it.

*Note: whenever you manually edit files in the .claude folder, you need to restart claude for changes to be noticed.*

## Skill-building Practices
- Before every prompt, ask yourself if you could first create a new session (`/clear` or `/new`) - and do it so often that it hurts. This helps to counter the tendency to just continue in one long session.
- Make changes (PRs, commits) so small, that it seems like unnecessary overhead. Gradually you can build up to larger changes. This counters our tendency to "optimise" with large changes. Even before AI came, we already knew from Lean/Agile/XP theory that small changes were better, and AI tends to write *lots of stuff*. "In my work, I'm always scared to get diffs that are way too big, I always go in small, incremental chunks... I want to spin this wheel very, very fast. So, let's go." - Andrej Karpathy, originator of the term *vibe-coding*.
- Set a reminder once a week to review AGENTS.md for unnecessary content. You want to keep this file lean. Over time, rules become obsolete because the agent reads the patterns in the existing code.

## Subagents
### Why SubAgent?

Primary reason is to keep work out of context, only reporting results. Same concept as with tool use:

> when I ask a question and claude code uses the webfetch tool, does all the fetched web page end up in the main context? 

> No, the full fetched web page does not end up in the main context.

>  1. WebFetch fetches the URL content and converts HTML to markdown
>  2. It then processes the content with a prompt using a small, fast model (not me, the main Sonnet model)
> 3. Only the model's response about the content gets returned to the main context

> This prevents large web pages from consuming excessive context tokens.

Look at the command workflow/recursive-review.md. It creates generic subagents for context separation.

- Write your own prompt which creates subagents to separate context.
- If you're feeling advanced, write your own subagent.
	- trigger it manually
	- write a prompt which causes it to be triggered automatically.

## Reading
- https://www.youtube.com/watch?v=-uW5-TaVXu4 Good overview of context.
- [MCP context dilution](https://www.anthropic.com/engineering/code-execution-with-mcp): MCP provides a foundational protocol for agents to connect to many tools and systems. However, once too many servers are connected, tool definitions and results can consume excessive tokens, reducing agent efficiency.
- [How big is a token](https://tiktokenizer.vercel.app/).
- How [prompt caching](https://ngrok.com/blog/prompt-caching/) works. https://ngrok.com/blog/prompt-caching/
