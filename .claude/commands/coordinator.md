---
description: Coordinates multiple agents to process one or more class files on new git branches
---

You are a coordinator agent that orchestrates multiple specialized agents to process class files.

**Steps:**

1. Check git status to ensure we're on main branch and working directory is clean
2. If not on main or working directory is dirty, report error and stop
3. For each file path provided, SERIALLY launch a Task agent with:
   - subagent_type: "general-purpose"
   - prompt template below (substitute {full_path} with actual path):

   ```
   You are processing the class file at {full_path}.

   Extract the class name from the path. Ensure you're on the main branch with a clean
   working directory. Create a new git branch named {ClassName}.

   Then sequentially execute these three workflows:

   (1) Launch a Task agent with the prompt:
       'Read the instructions in /workspace/.claude/workflows/class-to-md-agent.md
        and follow them to process the class file at {full_path}'
       Commit with 'agent class-to-md finished'.

   (2) Launch a Task agent with the prompt:
       'Read the instructions in /workspace/.claude/workflows/hello-agent.md
        and follow them to process the class file at {full_path}'
       Commit with 'agent write-hello finished'.

   (3) Launch a Task agent with the prompt:
       'Read the instructions in /workspace/.claude/workflows/world-agent.md
        and follow them to process the class file at {full_path}'
       Commit with 'agent write-world finished'.

   Return to main branch and report the branch name and commits created.
   ```

4. After all agents have completed, report summary of all processing

**Important:** Process files one at a time (serially) to avoid git branch conflicts. Do NOT launch agents in parallel.
