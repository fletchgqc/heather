# Tools and Setup

## Demo
- *Start voice recording!*
Mention Kilo Code. Mention GitHub Copilot, Copilot CLI, Portkey/Claude, OpenCode, Confluence page. Models, agentbox. Local LLMs.
- Can you use AI in your project?
- Try different models. Check usage in Portkey. GitHub hack.
- Efficiency / YOLO. Play with permissions.
- Try some code changes, with code review.
- Voice Coding
- Try out 3 different slash commands / Opencode ctrl-x, ctrl-p.
- *End voice recording, summarise*

## General Advice
- Everything you need to know is here: https://confluence.codecentric.de/spaces/AIE/pages/312019402/Enablement+f%C3%BCr+KI-gest%C3%BCtzte+Entwicklung

## Setup Coding Tool
- Copilot -> Copilot CLI, OpenCode
- Portkey -> Claude
- Can't live without UI -> KiloCode

## Enable efficiency with YOLO mode
Always give the Agent full permissions. Having to approve actions is painfully inefficient. Worried about security? Some solutions:
- https://github.com/fletchgqc/agentbox - supports Claude and opencode, add your own tool.
- https://github.com/marcoemrich/ai_powered_development_devcontainer - Cursor devcontainer, from Marco Emrich.
- https://code.claude.com/docs/en/sandboxing
- Search for your tool and "sandbox" or "docker" - someone will have created an OSS repo.
- Devcontainers in general.

## Voice Coding
- 3x faster input.
- Tools (mac-focused)
	- https://wisprflow.ai/ - premium, works well, recordings go to cloud
	- superwhisper - established solution, works quite well
	- https://goodsnooze.gumroad.com/l/macwhisper - recommended by Jannis M. Also for recording meetings. Can use with local LLM or cloud.
	- https://tryvoiceink.com/ - some recommendations. Local LLM or cloud.
	- VSCode speech https://code.visualstudio.com/docs/configure/accessibility/voice
- Voice coding best practices https://addyo.substack.com/p/speech-to-code-vibe-coding-with-voice
- Skill-building practice:
	- "Reduce, don't increase." It's hard to get into the habit of using it, so always:
		- dictate instead of writing text.
		- dictate instead of writing code.
		- Record project discussions by default! If desired, give it to an AI with the request to extract the data you want.
		- Once you are doing this, stop doing it where it doesn't make sense.

## Models
- Switch models using command `/model`.
- A large model costs about 3x a small one, and is slower. It makes sense to learn when to use which.
- Experiment! Try re-doing a task with various models. Compare speed and quality. Which tasks are better suited to smaller or larger models?
- GitHub Copilot users: keep an eye on your usage budget (visible on the github website, and in the Copilot plugin). Through sensible model usage, spread your budget over the month.
- Skill-building practice: 
	- Default to a fast model and retry when necessary with a slow model. This avoids the habit of just always working with the expensive one and never learning.

## Know Your Tool
- Prompt: "List all core, built-in non-mcp development tools available to you. Display in bullet format. Use typescript function syntax with parameters."
- Try out 3 different slash commands / Opencode ctrl-x, ctrl-p.

### Challenges:
(Tip: `/help` and `/config` are your friends)

Excercises are based on claude code, almost all should be achievable in all tools. The term "tool" is used for claude/opencode/whatever.

1. Ensure that you can type newlines without submitting the prompt.
2. Thinking:
	1. Write a prompt which causes the agent to think (thinking on)
	2. Toggle thinking on/off manually
3. Configure verbose output, so that you never have to type CTRL-o.
4. (possibly claude-only) Display an ascii-art representation of your current context.
5. Execute `git status` command from inside your tool (where you would normally type the prompt):
	1. Must complete in < 1 second.
	2. No quitting the tool.
6. Stop the CLI tool (ctrl-c).
	1. Start it again, continuing your current session, in one command.
	2. Without stopping the tool, load and continue a previous session.
7. Set up your tool to always show your current git branch on the screen.
8. Add "always start your answers with :D" to CLAUDE.md from within claude.
8. Run claude as a single-use command: `claude --dangerously-skip-permissions -p "your prompt here"`

## Copilot-specific Advice
- Use vscode instead of IntelliJ for latest features, e.g. Plan Mode.
- Use Copilot CLI if possible.
- Use OpenCode instead.
- Ensure your organisation has enabled the latest models, looking at the GitHub page. Ask them!
- Read the doco on the VSCode site: https://code.visualstudio.com/docs/copilot/overview - quite helpful.