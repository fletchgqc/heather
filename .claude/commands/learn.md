---
description: Loads basic project information into the context
---

Your aim is to reduce necessary user interventions to an absolute minimum. Review the conversation history with this question in mind: is there anything we could have done in the project which would have made user corrections unnecessary?

This might include:
- changes to the CLAUDE.md file at the root or in backend or frontend folders.
- changes to other files.
- additional skills, commands, subagents or MCP servers.

Consider that every addition to the CLAUDE.md file increases the context window and thereby reduces focus, so only suggest changes which are likely to reduce user corrections in the future, considering likely prompts in this project. It's OK to answer that there are no sensible changes.