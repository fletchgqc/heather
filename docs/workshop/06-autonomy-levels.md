# Autonomy Levels

## Exercise
Discuss in groups for 10 minutes:
1. What skills will you need now and in the future?
2. How can you ensure you build and maintain them?
3. How do you want to train others: juniors, or perhaps "seniors" :-)?
4. What team size and makeup makes sense?
5. Where in your current work can you see applications of different levels of control?

## Theory
Level of agent autonomy comparable to 5 levels of autonomous driving.

### 5 levels of autonomous driving:
0. No automation
1. Driver assistance
2. Partial automation
3. Conditional automation
4. High automation
5. Full automation

### To code or to vibe?
Aim for maximum possible automation.

Realistically: Different situations, different approaches. 

### "Autonomy slider" concept (Andrej Karpathy)
Example could be:
- **Hand-written** - Critical auth logic, core algorithms
- **Hand-written, AI improved** - Complex business logic
- **AI-written, manually edited** - Feature scaffolding, API endpoints
- **AI-written, human-reviewed** - Standard CRUD operations
- **AI-generated, AI-tested** - Utility functions, simple data transformations
- **AI-generated only** - Throwaway prototypes, spike investigations

These examples are from mid-2025. By now everything might be a step more automated.

### Non-functional requirements
Major driver: **non-functional requirements**, also known as **quality attributes**. How much AI-autonomy can you allow and still have all relevant quality attributes at required levels? Examples: Scalability, Cost efficiency, Auditability, Usability, Security, Maintainability.

### Human Skill Management
- Learning gap: "AI does it, seems to work, approve."
- Skill-rot: A [study published in The Lancet](https://www.thelancet.com/journals/langas/article/PIIS2468-1253(25)00164-5/abstract) found that after three months of using AI assistance, physicians' diagnostic performance had declined by 20%.
	- Pilot example: to mitigate skill rot from extensive use of autopilot, pilots must fly a given amount of hours yearly in flight simulator. We can also force ourselves to code by hand with a rule:
		- "all JIRA-Issues which end with a 0 or 5 must be coded by hand" or "First ticket in every sprint."
		- Regular Katas would be the equivalent of the flight simulator. However, as katas tend to be demo-code, they are costly.
		- Conversely, we might need rules to force ourselves to use AI to initially get into the habit.
- Anchoring and creativity drain if we always kick things off with AI:
	- General writing: first think and write brief / raw text. Then let the AI do it independently and compare.
	- Likewise for coding / planning.
	- Parallel programming (two developers, two machines): one codes by hand, one prompts AI, then compare.
- Make the decision consciously: “Am I optimising for speed or skill on this task?”
- Will developers still be needed in the future? How can we as individuals, and how can our company, continue to offer value not available elsewhere?
	- Help in upskilling and enabling customers.
	- Be aware of these issues, teach and spread them.
	- Operate at the next level of abstraction. Build the AI-SDLC-pipelines.

## Team discussion/decisions
- Do we need to (and how will we) force ourselves:
    - to use AI?
    - write code manually?
- How do/will we train juniors?
- How do we achieve fastest possible value and safeguard against dangerous "lazy approves"?
- What biases do I have (fear of irrelevance?) and how might they cloud my judgement about AI?