---
description: Coordinates multiple agents to process one or more class files on new git branches
---

You are a coordinator agent that orchestrates multiple specialized agents to process class files.

**If multiple file paths are provided:** Launch a separate Task agent to handle each file independently with full isolation.

**If a single file path is provided:** Execute the workflow directly in this context.

IMPORTANT: Use the Task tool with subagent_type="general-purpose" to launch each agent. This ensures the work happens in isolation and doesn't pollute the main context.

Steps to follow:

**Initial Setup:**
1. Check git status to ensure we're on main branch and working directory is clean
2. If not on main or working directory is dirty, report error and stop
3. Parse the arguments to determine how many file paths were provided

**If multiple files:**
- IMPORTANT: Launch Task agents SERIALLY, one at a time. Wait for each agent to complete before launching the next. DO NOT launch agents in parallel as they will conflict when manipulating git branches.
- For each file path (processing one at a time), launch a Task agent with subagent_type="general-purpose" and this prompt:
  "You are processing the class file at {full_path}. Extract the class name from the path. Ensure you're on the main branch with a clean working directory. Create a new git branch named {ClassName}. Then sequentially: (1) Launch a Task agent with the prompt: 'Read the instructions in /workspace/.claude/agents/class-to-md-agent.md and follow them to process the class file at {full_path}'. Commit with 'agent class-to-md finished'. (2) Launch a Task agent with the prompt: 'Read the instructions in /workspace/.claude/agents/hello-agent.md and follow them to process the class file at {full_path}'. Commit with 'agent write-hello finished'. (3) Launch a Task agent with the prompt: 'Read the instructions in /workspace/.claude/agents/world-agent.md and follow them to process the class file at {full_path}'. Commit with 'agent write-world finished'. Return to main branch and report the branch name and commits created."
- After all agents have completed, report summary of all processing

**If single file:**

1. Extract the class name from the path (e.g., "MyClass" from "src/main/java/com/example/MyClass.java")
2. Create a new git branch with the class name: `git checkout -b {ClassName}`
3. Launch class-to-md agent using Task tool:
   - subagent_type: "general-purpose"
   - prompt: "Read the instructions in /workspace/.claude/agents/class-to-md-agent.md and follow them to process the class file at {full_path}"
4. Commit the results: `git add . && git commit -m "agent class-to-md finished"`
5. Launch hello agent using Task tool:
   - subagent_type: "general-purpose"
   - prompt: "Read the instructions in /workspace/.claude/agents/hello-agent.md and follow them to process the class file at {full_path}"
6. Commit the results: `git add . && git commit -m "agent write-hello finished"`
7. Launch world agent using Task tool:
   - subagent_type: "general-purpose"
   - prompt: "Read the instructions in /workspace/.claude/agents/world-agent.md and follow them to process the class file at {full_path}"
8. Commit the results: `git add . && git commit -m "agent write-world finished"`
9. Return to main branch: `git checkout main`
10. Respond with a summary of the branch created and commits made.
