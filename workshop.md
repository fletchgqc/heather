## Goal
- Pique interest for AI-Driven development.
- Experience it as productive, fun and interesting.
- Increase skills with claude code.
- Find applications in current project: better ways of getting things done with AI.

## Metrics:
- Participants work with AI support tomorrow
- Participants increasingly work with AI support, exchange tips, influence others

## Setup:
- clone https://github.com/fletchgqc/heather
- Open the code in your IDE and look around.
- In claude: `/install`
- Outside claude/agentbox: `scripts/start-all.sh`
- Visit http://localhost:5173/

## Excercises:
1. Prompt claude to add a life expectancy generator, based on birthday. Just use average age.
2. Ask claude to commit, to branch 'life-expectency', but not push.
3. Try: `/review changes in the last commit`
4. Develop the feature again (don't be afraid to delete code), or expand it (e.g: use statistics and user inputs like gender, diet, lifestyle, health), using supervised TDD:
	1. `/clear`
	2. `! cd backend` (or frontend)
	3. `/start-tdd`
	4. Put an example file like @AgeCalculatorTest into the context and tell it what you want to do.
5. Note: Interested in AI-driven-TDD? Check out [TDD-guard](https://github.com/nizos/tdd-guard/tree/main)
6. Re-implement the feature, or add another feature, using a spec:
	1. `/feature "your description here"`
	2. Review the spec file created. Once you are happy:
	3. /implement @spec-file.md
7. Note: Interested in spec-driven-development? Check out [GitHub spec kit](https://github.com/github/spec-kit) and [BMAD Method](https://github.com/bmad-code-org/BMAD-METHOD)
8. Claude can handle images:
	1. Try pasting an image into the CLI.
	2. Create the file test1.txt in the root of the project (`! touch test1.txt`)
	3. `@test-prompt.png`. Surprise! What happened?

*Note: whenever you manually edit files in the .claude folder, you need to restart claude for changes to be noticed.*

## Full Agentic Mode
Sick of approving permission dialogs? Start claude in full agentic mode; use one of the following options:

- with agentbox (safer):
	- clone https://github.com/fletchgqc/agentbox
	- create shell alias `ab` to agentbox executable
	- in heather directory: `ab`
- as a devcontainer (safer):
	- open the project in your IDE. It should prompt you to "open as devcontainer". If not, an internet search for IDE doco should help.
- simple and hey, YOLO:
	- in heather directory: `claude --dangerously-skip-permissions`

## Challenges:
(Tip: `/help` and `/config` are your friends)
1. Ensure that you can type newlines without submitting the prompt.
2. Thinking:
	1. Write a prompt which causes claude to think (thinking on)
	2. Toggle thinking on/off manually
2. Execute `git status` command from inside claude:
	1. Must run in < 1 second.
	2. No quitting claude.
3. Stop claude code (ctrl-c).
	1. Start it again, continuing your current session, in one command.
	2. Without stopping claude, load and continue a previous session.
4. Set up claude to always show your current git branch
5. Add "after every response, run the /learn command" to CLAUDE.md from within claude.
6. Sick of CTRL-o? Configure verbose output.
7. Display an ascii-art representation of your current context.
8. Run claude as a single-use command: `claude --dangerously-skip-permissions -p "your prompt here"`

## MCP
1. Have claude add a dependency to build.gradle.
2. Manually check whether it's the latest release (it won't be).
3. Remove the dependency.
4. Add this MCP server https://github.com/arvindand/maven-tools-mcp (both claude code and the README have documentation to help you). This relies on docker and won't work in agentbox.
5. Now when claude code adds the dependency, it should be the latest version.

## Hooks:
- Write a hook which runs the linter every time a file is written/edited. (See claude code doco for help)
	- What are the pros/cons?
	- What other options are available?

## Other Ideas:
- Anytime you have corrected claude code once or twice, try running `/learn`
- Claude ignoring important system prompt instructions? Try https://github.com/lexler/claude-code-user-reminders
- You may want to try out the claude code plugin for their IDE. I don't use it.

## Slash, Skill, SubAgent
### Why Slash Command
- Deterministic
- No context pollution when not in use
### Why Skill
- Detail *how* to do something, but let agent decide *when* to do it.
- Keep work in context
### Why SubAgent?
Keep work out of context, only reporting results. Same concept as here with tool use:

> when I ask a question and claude code uses the webfetch tool, does all the fetched web page end up in the main context? 

> No, the full fetched web page does not end up in the main context.

>  Based on the WebFetch tool description, here's how it works:

>  1. WebFetch fetches the URL content and converts HTML to markdown
>  2. It then processes the content with a prompt using a small, fast model (not me, the main Sonnet model)
> 3. Only the model's response about the content gets returned to the main context

>  So WebFetch acts as a filter - it uses a separate lightweight model to extract and summarize the relevant information based on the prompt, and only that processed
response enters the main context. This prevents large web pages from consuming excessive context tokens.

>  For example, if I use WebFetch to get information from a large documentation page, the entire HTML/markdown of that page doesn't get loaded into my context.
  Instead, only the smaller model's answer to my specific query about that page gets returned to me.


`/init`
brainstormen, was wir machen können.
