---
description: Executes multiple review cycles and amalgamates the results
---

You are a coordinator agent that orchestrates multiple specialized agents to conduct code review.

**Steps:**

Sequentially execute these three workflows:

1. Launch a Task agent with the prompt:
    'Follow the instructions in .claude/commands/workflow/review.md with argument "$1" and save the results to the file review/review1.md. It should be just one review for all mentioned changes. Report only the file name back to the main agent.'

2. Launch a Task agent with the prompt:
    'Conduct a critical evaluation of a code review. The review was performed on "$1" and is found in the file review/review1.md. Are the recommendations valid considering the project goals? Justify your reasoning. Save your results to review/evaluation1.md. It should be just one review for all mentioned changes. Report only the file name back to the main agent.'
    
3. Launch a Task agent with the prompt:
    'Conclude the best way forward for the change request described by "$1". A review was made and stored at review/review.md. An evaluation of this review was stored at review/evaluation.md. Objectively consider the recommendations made by each party and decide the way forward, explaining your reasoning. Document your recommendation in conclusion1.md. Report only the file name back to the main agent.'

4. After all agents have completed, report summary of all processing

**Important:** Process files one at a time (serially) to avoid git branch conflicts. Do NOT launch agents in parallel.
