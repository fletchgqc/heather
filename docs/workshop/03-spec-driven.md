# Spec Driven Development

## Exercises
1. Use planning mode in your agent to develop and then implement a plan.
2. Prompt the files in the docs/specs directory and create specs like that (avoid word "plan").
3. Write and use your own spec template.
4. Try one of the frameworks listed below.
5. Decide: where is your spec sweet spot?

## Inspiration
- Form developer-PO pairs - the future of refinement?
- Create a branch for each exercise, commit WIP after every step!

### DIY Speccing
- Make a plan to do ...
- Make a plan to do ... Write it to a markdown file in the folder docs/stories. The filename should start with the first available three-digit number.
- Make a plan to do ... Write it to a markdown file in the folder docs/stories. The filename should start with the first available three-digit number. Base your file on this template:
```
# Story: <title>

As a <USER>
I want <FEATURE>
so that <PURPOSE>

## Acceptance Criteria
- Criteria 1
- Criteria 2

## Business Notes
- BusNote 1

## Technical Notes
- TechNote 1
```

- Try the command `/create-simple-story.md`
- Compare with the other smilar commands. Now, write your own slash command. Tip: keep it simple! The frameworks tend to have a lot of junk. You may need to restart you editor for your slash command to become available. Some statements you could include:
    - Make a detailed plan to accomplish this. Think hardest.
    - How will we implement only the functionality we need right now?
    - Identify files that need to be changed.
    - Do not include plans for legacy fallback or backwards compatibility unless required or explicitly requested.
    - Write a short overview of what you are about to do.
    - Write function names and 1-3 sentences about what they do.
    - Write test names and 5-10 words about behavior to cover.
    - Don't just say "what I'm going to do" but rather focus on concept, architecture.
- Consider size of change - just do it, or multiple PRs, or even larger.
- Breaking things into smallest possible changes is almost always a good idea in software engineering.

#### Iterate on plan
Try stuff like:
1. Get feedback on the plan
2. Ask in a fresh context for a lean plan with minimal edits
3. Ask in a fresh context to decide between them
4. Save each to markdown
5. Have a 4th context review all plans

### Try a framework
- Try one tool:
    - [BMAD](https://github.com/bmad-code-org/BMAD-METHOD/tree/main). Tip: start with the quickest possible flow.
    - [Antigravity](https://antigravity.google/).
    - [GitHub SpecKit](https://github.com/github/spec-kit)
    - [AWS Kiro](https://kiro.dev/)