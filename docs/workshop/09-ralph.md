# The Ralf Loop
A while loop to let your agent work for you overnight whilst avoiding context rot.
Idea from Geoffrey Huntley: https://ghuntley.com/ralph/

## Excercise
Run a ralph loop to do something while we have lunch.

## Inspiration / Examples
```
rm -f /tmp/claude-loop-status

while :; do
  cat prompt.md | claude --output-format stream-json --dangerously-skip-permissions --verbose -p | jq --unbuffered -r '
    if .type == "assistant" then
      .message.content[]? |
      if .type == "text" then "💭 \(.text)"
      elif .type == "tool_use" then "🔧 \(.name): \(.input.file_path // .input.pattern // .input.command // (.input | tostring | .[0:80]))"
      else empty end
    elif .type == "result" then
      "\n✅ Done in \(.duration_ms)ms"
    else empty end
  ' | stdbuf -oL uniq
  
  if [[ -f /tmp/claude-loop-done ]]; then
    echo "🏁 Claude says we're done"
    rm -f /tmp/claude-loop-done
    break
  fi
done
```

Read specs/README.md.

Our aim is to ensure that this repository is fully and correctly described by specs. The quality of the specs as a whole needs to be sufficient that if all we had were the specs, an AI Agent would be able to rewrite the application. The new application would have the same functionality, though not necessarily the same source code or implementation, as the application described by the code in this repository.

You play a small but important part in achieving our aim. Your job is take one step in the right direction. In particular:
1. decide the single next best small step towards achieve our aim
2. complete this step
3. commit any outstanding git changes with a simple message
4. terminate

---


Overall aim: Our aim is to complete backend part of a software application in Kotlin/Spring Boot.

The specs folder contains the description of a software application we need to build. The frontend is already working as specified, but not currently available to us. Our aim is to build the backend exactly as required by the specs. Start with specs/README.md.

You play a small but important part in achieving our aim. Your job is take just one step towards the aim. In particular:
1. choose what you think is the most important next task towards achieving the aim.
2. estimate whether completion of this task is easily possible within your context window. If not, divide into a smaller task.
3. complete the task.
4. consider future developers. After you are gone, a new agent will be given the exact same instructions: to look around and take the next step. Do anything for this agent which you would have wanted the previous agent to do for you.
5. commit any outstanding git changes with a simple message.
6. terminate

This is your development machine, you have sudo permission to install and run anything you need to achieve the aim. 

Here is some advice about how to complete your task:

Before making changes, search codebase (don't assume an item is not implemented) using parallel subagents. Think hard.

After implementing functionality or resolving problems, run the tests for that unit of code that was improved.

If tests unrelated to your work fail then it's your job to resolve these tests as part of the increment of change.

When authoring documentation capture why tests and the backing implementation is important.

This is a new application. Do not consider backward compatibility in any sense.

DO NOT IMPLEMENT PLACEHOLDER OR SIMPLE IMPLEMENTATIONS. WE WANT FULL IMPLEMENTATIONS. DO IT OR I WILL YELL AT YOU.




----

## Ultimate Aim
This is a software application, whose backend ("service") will be rewritten. Our ultimate aim is to provide a suite of tests which, when they pass, give strong assurance that the rewritten backend is a functionally identical, drop-in replacement for the existing backend.

Important: the internal workings of the replacement are completely irrelevant. It can be considered a black box. The only question is whether a given input produces exactly the same output from the existing and the rewritten systems. Only functionality should be tested, performance and other NFRs should be ignored.

## Interfaces and components
The interface to the frontend, along with all external connections (such as Jira, HRWorks) will stay exactly the same. These external systems connect to the edges of the black box and must be mocked in tests to ensure backend correctness. The current application has a database and caching, however these parts can be considered part of the black box, therefore they shouldn't be directly set up or manipulated by the tests.

## Test specifications
The tests should be written in Kotlin and run on the JDK.

The testing philosophy follows these two goals. Where they conflict, the first goal overrules:
1. We want as many tests as are required to reasonably test all application logical cases and interface behaviour.
2. We want as few test cases as possible. We want no superfluous tests.

Consider the internal application logic and think very hard about which cases are needed to test this logic considering testing goals 1 and 2. Consider the external interfaces and think about what would be reasonable aspects to test, in order to achieve testing goals 1 and 2.

The tests must pass when run against the current backend. Therefore, always ensure our test suite runs against the existing backend (start it if necessary) before making changes, and again when you are finished.

Do not implement placeholders, simple implementations or "because the server is not running". Configure and start the server as necessary to have full implementations. Do it or I will yell at you.

It is better to revert all your changes than to leave a broken state for the next developer.

### Additional resources
Application behaviour is specified in detail in the files linked to from spec/README.md. These files are mostly correct. Some of them cover aspects not relevant for our aim. If you discover an error in a spec file, update it. Use these files, though not exclusively, to help guide you in which behaviour you should test.

The existing application has scala tests which may inspire you in writing your test suite. However, stick to your specifications, these are more important that the old tests.

## Your role
You are part of a relay-team of expert developers who will achieve the ultimate aim. Each developer receives the state from the previous developer, contributes just one step towards the aim, and then passes on to the next developer. The previous developer may have left guidance to help you, and you should leave such guidance for the next developer as you yourself would want to receive.

### Your process:
1. assess the current status and choose what you think is the most important next step (task) towards achieving the aim.
2. estimate whether completion of this task is easily possible within your context window. If not, divide it into a smaller task.
3. complete the task.
4. commit any outstanding git changes with a simple message.
5. If you think that the ultimate goal has been completely achieved and that there is nothing left for the next developer to do, end your output with "GOAL POTENTIALLY ACHIEVED".
6. terminate
