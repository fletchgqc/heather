---
description: Primes context with HITL TDD rules for supervised, AI-driven TDD
---

# Test-Driven Development (TDD) Rules

## TDD Mindset and Common Challenges

### Expected Psychological Resistance
TDD practices will feel counterintuitive and uncomfortable:
- **Hardcoded returns feel "too simple"** - Returning `0` or `1` seems wasteful, but it's the correct minimal step
- **The urge to implement ahead is strong** - You'll want to solve multiple test cases at once; resist this
- **Minimal steps feel inefficient** - Taking tiny steps seems slow but actually accelerates development
- **Predictions feel unnecessary** - Stating what will fail seems obvious, but builds crucial understanding
- **Push through this discomfort** - These feelings indicate you're following the discipline correctly

### Why This Discipline Works
Understanding the deeper purposes helps maintain discipline:
- **Baby steps reveal simpler solutions** - Implementing only what tests demand often uncovers approaches simpler than over-engineered first attempts
- **One-test-at-a-time prevents complexity** - Not thinking ahead eliminates unnecessary features and abstractions
- **Predictions build confidence** - Explicit expectations create deeper understanding of what you're testing and why
- **Refactoring becomes natural** - Mandatory improvement attempts prevent technical debt accumulation
- **The process fights harmful instincts** - Programming instincts often lead to premature optimization and over-engineering

### Common TDD Failure Modes
Watch for these violations of discipline:
- **Planning beyond base functionality** - Including advanced features in initial test list instead of focusing on core functionality
- **Multiple active tests** - Converting more than one `it.todo()` to executable test code at once
- **Implementing beyond tests** - Adding features or logic not demanded by current failing test
- **Skipping predictions** - Running tests without explicitly stating expected failures
- **Avoiding refactoring** - Moving to next test without attempting at least one improvement
- **Premature abstraction** - Creating complex solutions when simple ones pass tests
- **Ignoring the uncomfortable** - Abandoning discipline when it feels "too simple" or "too slow"

### Remember
- **Discomfort is a signal you're doing it right** - TDD should feel constraining at first
- **Trust the process** - Simple steps compound into elegant solutions
- **Discipline over instinct** - Follow the rules even when they feel wrong
- **Each violation compounds** - Small shortcuts lead to big complexity problems

## Core TDD Process

1. **Test List First**
   - Create a list of test cases using `it.todo()` for BASE FUNCTIONALITY ONLY before writing any implementation
   - This helps understand the scope of the core feature (not advanced features)
   - Example for String Calculator base functionality:
     ```typescript
     describe("String Calculator", () => {
       it.todo("should return 0 for empty string");
       it.todo("should return number for single number");
       it.todo("should return sum for two numbers");
       it.todo("should return sum for multiple numbers");
       // NOT: advanced features like custom delimiters, ignore >1000, etc.
     });
     ```

2. **One Test at a Time**
   - Convert exactly ONE `it.todo()` to executable test code at a time
   - All other tests remain as `it.todo()` descriptions
   - Never have more than one failing test in red phase
   - Implement only what's needed to make that single test pass
   - Don't think ahead or implement features for future tests

3. **Red-Green-Refactor Cycle**
   - **Red Phase (Compilation Error)**
     - Start with a non-existent function
     - Test should fail with compilation error
     - This ensures we're truly starting from scratch

   - **Red Phase (Runtime Error)**
     - Create empty function that returns undefined/wrong value
     - Test should fail with assertion error
     - This verifies our test is working as expected

   - **Green Phase**
     - Implement minimal code to make the test pass
     - Don't add features for future tests
     - Don't optimize or refactor yet

   - **Refactor Phase**
     - MUST attempt at least one refactoring
     - If no improvement is possible, document why
     - **Naming Evaluation (First Priority)**:
       - Ask: "Does this name clearly describe what the function actually does based on all tests we have so far?"
       - Ask: "Has the function's purpose become clearer/more specific through the latest test?"
       - Rename if the name doesn't capture the current full intent
       - Especially critical when new functionality changes the nature of what the function does
     - Apply ATP (Absolute Transformation Premise) to measure improvements:
       - Calculate mass before and after refactoring
       - Aim for lower mass where possible
       - Document mass changes
     - Apply 4 Rules of Simple Design:
       - Tests must pass
       - No duplication
       - Reveals intent
       - Fewest elements
     - If no refactoring improves the code:
       - Document why no refactoring was possible
       - Explain why current state is optimal
       - Move to next test

4. **Guessing Game**
   - Before running tests, explicitly state:
     - Which test will fail
     - Type of error (compilation/assertion)
     - Expected vs actual values
     - Expected diff output
   - Run the test
   - Compare actual result with prediction
   - This helps understand what we're testing and why

5. **Baby Steps**
   - Make the smallest possible change to get to green
   - If a test fails, make it pass with the simplest implementation
   - Don't try to solve multiple problems at once
   - Each step should be clear and verifiable

## Best Practices

1. **Test Structure**
   - One assertion per test when possible
   - Clear, descriptive test names
   - Tests should be independent
   - No test should depend on another test's state

2. **Implementation**
   - Start with the simplest possible implementation
   - Don't add features until there's a test for them
   - Don't optimize until all tests are green
   - Keep the code clean and maintainable

3. **Documentation**
   - Tests serve as documentation
   - Test names should describe the behavior
   - Comments should explain why, not what
   - Document mass calculations and refactoring decisions
   - Document when no refactoring is possible

## Common Pitfalls to Avoid

1. **Writing too many tests at once**
   - Stick to one test at a time
   - Don't implement multiple features simultaneously

2. **Skipping the red phase**
   - Always start with a failing test
   - Don't write implementation before test

3. **Over-engineering**
   - Don't add features without tests
   - Don't optimize prematurely

4. **Not refactoring**
   - Take time to clean up code
   - Remove duplication
   - Improve readability
   - Always attempt refactoring after green phase
   - Use ATP to measure improvements

## Example Workflow

1. Create test list:
   ```typescript
   describe("Calculator", () => {
     it.todo("should return 0 for empty input");
     it.todo("should return number for single input");
     it.todo("should add two numbers");
   });
   ```

2. Activate first test:
   ```typescript
   it("should return 0 for empty input", () => {
     expect(calculate([])).toBe(0);
   });
   ```

3. Predict failure:
   - Test will fail with compilation error
   - Function doesn't exist yet

4. Create empty function:
   ```typescript
   function calculate(numbers: number[]): number {
     return undefined as any;
   }
   ```

5. Predict failure:
   - Test will fail with assertion error
   - Expected: 0
   - Received: undefined

6. Implement minimal solution:
   ```typescript
   function calculate(numbers: number[]): number {
     return 0;
   }
   ```

7. Refactor:
   - Calculate initial mass
   - Attempt at least one refactoring
   - Calculate new mass
   - Document improvements or explain why none were possible
   - Ensure all tests still pass

8. Move to next test

# Rules of Simple Design

## Description
Kent Beck's Four Rules of Simple Design - fundamental principles for writing clean, maintainable code. These rules are applied in priority order and work synergistically with TDD.

## The Four Rules (in priority order)

### 1. Tests Pass
- All tests must pass
- The code must work correctly
- This is the highest priority rule - never compromise working code
- If tests don't pass, fix them before applying other rules

### 2. Reveals Intent
- Code should clearly express what it does
- Use meaningful names for variables, functions, and classes
- Structure code to be self-documenting
- Prefer explicit over clever code
- Comments should explain "why", not "what"

### 3. No Duplication (DRY)
- Don't repeat yourself
- Extract common functionality into reusable components
- Look for both obvious duplication and conceptual duplication
- Knowledge should have a single, unambiguous representation

### 4. Fewest Elements
- Minimize the number of classes, methods, and other code elements
- Remove unnecessary abstractions
- Keep it simple - don't over-engineer
- Only add complexity when it serves a clear purpose

## Application Guidelines

### Priority Order
- Apply rules in order: 1 → 2 → 3 → 4
- Never violate a higher-priority rule to satisfy a lower-priority one
- If rule #3 conflicts with rule #2, choose clarity over DRY

### Integration with TDD
- **Red Phase**: Focus on rule #1 (make tests pass)
- **Green Phase**: Still focus on rule #1 (minimal working code)
- **Refactor Phase**: Apply rules #2, #3, #4 while preserving #1

## Examples

### Rule 2: Reveals Intent
```typescript
// Bad
function calc(w: string[]): boolean {
  return w.every((x, i) => i === 0 || diff(x, w[i-1]) === 1);
}

// Good
function isValidWordChain(words: string[]): boolean {
  return words.every((word, index) =>
    index === 0 || differsByOneLetter(word, words[index - 1])
  );
}
```

### Rule 3: No Duplication
```typescript
// Bad
function differsByOneLetter(word1: string, word2: string): boolean {
  if (word1.length !== word2.length) return false;
  let differences = 0;
  for (let i = 0; i < word1.length; i++) {
    if (word1[i] !== word2[i]) differences++;
  }
  return differences === 1;
}

function isAdjacent(a: string, b: string): boolean {
  if (a.length !== b.length) return false;
  let diffs = 0;
  for (let i = 0; i < a.length; i++) {
    if (a[i] !== b[i]) diffs++;
  }
  return diffs === 1;
}

// Good - Extract common logic
function countDifferences(word1: string, word2: string): number {
  if (word1.length !== word2.length) return Infinity;
  return word1.split('').reduce((count, char, i) =>
    char !== word2[i] ? count + 1 : count, 0
  );
}

function differsByOneLetter(word1: string, word2: string): boolean {
  return countDifferences(word1, word2) === 1;
}
```

### Rule 4: Fewest Elements
```typescript
// Bad - Unnecessary abstraction
class WordValidator {
  validate(word: string): boolean { return word.length > 0; }
}
class ChainValidator {
  private wordValidator = new WordValidator();
  validate(chain: string[]): boolean { /* ... */ }
}

// Good - Simple functions
function isValidWord(word: string): boolean {
  return word.length > 0;
}

function isValidChain(chain: string[]): boolean {
  return chain.every(isValidWord) &&
         chain.every((word, i) => i === 0 || differsByOneLetter(word, chain[i-1]));
}
```

## Red Flags
- Code that's hard to name (violates rule #2)
- Copy-paste programming (violates rule #3)
- Classes with single methods (may violate rule #4)
- Deep inheritance hierarchies (may violate rule #4)
- Premature abstractions (violates rule #4)

## Remember
- Simple ≠ Easy
- Simple means "not complex" or "not compound"
- These rules help achieve simplicity through disciplined practice

# Absolute Priority Premise (APP)

## Description
Micah Martin's Absolute Priority Premise - an improvement on the Transformation Priority Premise that provides objective heuristics to compare code quality by assigning mass values to code components.

## Core Concept
The APP assigns "mass" to different code components. Lower total mass indicates better, simpler code. The premise is that code with less mass is objectively better than code with more mass.

## The Six Components (with Mass Values)

### 1. Constant (Mass: 1)
- Literal values in code
- Examples: `5`, `"hello"`, `true`, `[]`
- Lowest mass - preferred building block

### 2. Binding/Scalar (Mass: 1)
- Variables, parameters, local names
- Examples: `amount`, `userName`, `result`
- Names that refer to values

### 3. Invocation (Mass: 2)
- Function/method calls
- Examples: `calculate()`, `user.getName()`, `Math.max(a, b)`
- Calling existing functionality

### 4. Conditional (Mass: 4)
- Control flow decisions
- Examples: `if`, `switch`, `case`, `cond`, `?:`
- Branching logic

### 5. Loop (Mass: 5)
- Iteration constructs
- Examples: `while`, `for`, `forEach`, `map`
- Repetitive execution

### 6. Assignment (Mass: 6)
- Mutating variables (highest mass)
- Examples: `x = 5`, `count++`, `list.add(item)`
- State changes - most complex

## Calculation Rules

### Total Mass = Sum of All Components
```
Total Mass = (constants × 1) + (bindings × 1) + (invocations × 2) +
             (conditionals × 4) + (loops × 5) + (assignments × 6)
```

### Comparison Guidelines
- **Lower mass = Better code**
- **Functional style naturally scores lower** (no assignments/loops)
- **Immutable approaches preferred** over mutable ones
- **Simple expressions preferred** over complex control structures

## Special Counting Rules

### Method Declarations
- A method/function counts as both:
  - **Constant (1)** for the code it represents
  - **Binding (1)** for its name

### Assignment Distinctions
- **Not every assignment counts as Assignment (6)**
- `final` fields and local variables are just **Bindings (1)**
- Only **re-assignments that modify values** count as Assignment (6)
- Local variable assignments may count as **Binding (1)** in some cases

### Class Definitions
- Class definition = **Constant (1)** for code + **Binding (1)** for name
- (Usually ignored in algorithm comparisons)

## Real-World Example: Word Wrap Kata

### Recursive Implementation (Mass: 53)
```java
final char BLANK = ' ';           // constant(1) + binding(1) = 2
final char NEWLINE = '\n';        // constant(1) + binding(1) = 2

String wrapRecursive(String line, int maxLineLen) {
    if (line.length() <= maxLineLen) {     // conditional(4) + invocation(2) + constant(1) = 7
        return line;                       // binding(1) = 1
    }

    int indexOfBlank = line.lastIndexOf(BLANK, maxLineLen);  // binding(1) + invocation(2) = 3
    int split;                             // binding(1) = 1
    int offset;                            // binding(1) = 1
    if (indexOfBlank > -1) {              // conditional(4) + binding(1) + constant(1) = 6
        split = indexOfBlank;              // binding(1) + binding(1) = 2
        offset = 1;                        // binding(1) + constant(1) = 2
    } else {
        split = maxLineLen;                // binding(1) + binding(1) = 2
        offset = 0;                        // binding(1) + constant(1) = 2
    }
    return line.substring(0, split) + NEWLINE  // invocation(2) + invocation(2) + ... = 8
         + wrap(line.substring(split + offset), maxLineLen);
}
```

**Component Count**:
- Constants: 7 × 1 = 7
- Bindings: 8 × 1 = 8
- Invocations: 10 × 2 = 20
- Conditionals: 2 × 4 = 8
- Loops: 0 × 5 = 0
- Assignments: 0 × 6 = 0

**Total Mass: 53**

### Algorithm Comparison Results
- **Recursive solution**: 53 (lowest mass)
- **Tail recursive**: 71
- **Loop version**: 68
- **Optimized loop**: 80
- **Buffer loop**: 105
- **Regular expression**: 69
- **Highly structured**: 167

## Integration with TDD and Simple Design

### With TDD Process
- **Red Phase**: Focus on passing tests (mass irrelevant)
- **Green Phase**: Write minimal code (naturally low mass)
- **Refactor Phase**: Apply APP to guide improvements toward lower mass

### With Simple Design Rules
1. **Tests Pass** (Rule #1) - Always highest priority
2. **Reveals Intent** (Rule #2) - May increase mass for clarity
3. **No Duplication** (Rule #3) - Extract to reduce mass
4. **Fewest Elements** (Rule #4) - Aligns with APP's low mass goal

### Priority Resolution
- **Simple Design Rule #2 trumps APP** - Clarity over low mass
- **Use APP within refactor phase** to guide toward simpler solutions
- **APP helps choose between equivalent clear solutions**

## Language Considerations
- **Functional languages** (Clojure, Haskell) naturally score lower
- **Imperative languages** (Java, C#) may need adjusted weights
- **Consider language idioms** when applying APP

## Guidelines for Use

### When to Apply APP
- **During refactoring** to choose between working solutions
- **When comparing algorithms** of similar functionality
- **To guide toward simpler implementations**
- **Not during initial TDD red/green phases**

### When NOT to Use APP
- **Never sacrifice clarity** for lower mass
- **Don't optimize prematurely** based on mass alone
- **Performance requirements** may override mass considerations
- **Domain complexity** may require higher mass solutions

### Red Flags
- **Obsessing over mass** during initial development
- **Sacrificing readability** for lower scores
- **Ignoring performance** implications
- **Applying rigidly** without context

## Important Caveats

### Limitations of APP
- **Not a direct indication of readability** - simpler code is better, but context matters
- **Ignores performance characteristics** - memory usage, runtime performance not considered
- **Validity unclear for general purpose code** - works best for algorithm comparisons
- **Four Rules of Simple Design encourage explaining variables** - clarity more important than fewer elements
- **Favors functional programming** - penalty on loops and assignments may not suit all contexts

### When APP May Not Apply
- **Algorithms with different performance characteristics** (Word Wrap example)
- **Domain complexity requirements** may require higher mass solutions
- **Memory usage vs. code simplicity** trade-offs
- **Team familiarity** with certain patterns may trump mass considerations

## Benefits
- **Objective measurement** of code complexity
- **Guides toward functional approaches**
- **Encourages immutability**
- **Complements other design principles**
- **Helps compare equivalent solutions**

## Remember
- APP is a **tool, not a rule**
- **Context matters** more than absolute scores
- **Combine with other principles** for best results
- **Lower mass is generally better**, but not always
- **Use during refactoring**, not initial development
- **The best set of specific mass values are unknown** - these are Micah's suggested weights

## Sources and References

### Primary Sources
- **Micah Martin's original work** (2012) - [Transformation Priority Premise Applied](https:/8thlight.com/insights/transformation-priority-premise-applied) - 8th Light blog post with Coin Changer Kata example
- **8th Light University presentations** - Part One and Part Two (referenced but links not currently accessible)

### Secondary Sources
- **Peter Kofler's detailed analysis** - [Absolute Priority Premise, an Example](https:/blog.code-cop.org/2016/08/absolute-priority-premise-example.html) - comprehensive explanation with Word Wrap kata examples and tool implementation

## Development Workflow Integration

### TDD with TypeScript and Vitest
1. **Create specification file** with `.spec.ts` extension
2. **Import with explicit extensions** for local modules
3. **Use Vitest testing functions** (`describe`, `it`, `expect`)
4. **Follow TDD red-green-refactor** cycle
5. **Leverage TypeScript's type checking** during development

### Example Template
```typescript
// calculator.spec.ts
import { describe, it, expect } from "vitest";
import { calculate } from "./calculator.js";

describe("Calculator", () => {
  it.todo("should handle basic operations");
  it.todo("should validate input types");
  it.todo("should handle edge cases");
});
```

## IDE Configuration

### VSCode Settings
Ensure your IDE recognizes these conventions:
- **File associations**: `.spec.ts` files are treated as TypeScript
- **Test runner integration**: Configure to run Vitest tests
- **Import suggestions**: Should suggest `.js` extensions for local imports

## Benefits of These Rules

- **Consistency** - All TypeScript projects follow same conventions
- **Clarity** - File extensions and naming make purpose obvious
- **Performance** - Vitest provides fast test execution
- **Modern standards** - Aligns with current TypeScript/Node.js best practices
- **Tool integration** - Works seamlessly with build tools and IDEs
- **Documentation** - Tests as specifications improve code understanding

# Human-in-the-Loop TDD Rules

## Description
These rules ensure the human stays engaged and can provide guidance at critical decision points during Test-Driven Development. The AI should pause and explicitly ask for user feedback in these specific situations.

## Rule 1: End-of-Phase Confirmation

### When to Apply
At the **end of every TDD phase** (Red, Green, or Refactor), before proceeding to the next phase or test.

### What to Do
1. **Stop after completing the current phase**
2. **Summarize what was just completed in this phase**:

   **After Red Phase**:
   - Which test was activated
   - Prediction made and whether it was correct
   - Type of failure achieved (compilation/runtime error)

   **After Green Phase**:
   - Implementation approach taken (minimal code added)
   - Confirmation that test now passes
   - Any trade-offs or decisions made

   **After Refactor Phase**:
   - Refactorings attempted/completed:
     - Naming changes made
     - Mass calculations (before/after if applicable)
     - Structural improvements
     - Any refactoring opportunities that were rejected and why

3. **Explicitly ask for permission to continue**:
   - **After Red**: "Red phase complete. Should I proceed to Green phase?"
   - **After Green**: "Green phase complete. Should I proceed to Refactor phase?"
   - **After Refactor**: "Refactor phase complete. Should I proceed to the next test?"

### Why This Matters
- **Human maintains full control** - No phase proceeds without explicit approval
- **Educational opportunity** - Human can guide each individual step
- **Prevents over-implementation** - Each phase does only what's required
- **Quality assurance** - Human reviews every phase before proceeding
- **Fine-grained control** - Human can intervene at any point in the process

### Examples
```
🔴 Red Phase Complete:
**Test Activated**: "should return sum for two numbers"
**Prediction**: Runtime assertion error (Expected: 3, Received: 1) ✅ Correct
**Result**: Test fails as expected with assertion error

Red phase complete. Should I proceed to Green phase?
```

```
🟢 Green Phase Complete:
**Implementation**: Added split/map/reduce logic for comma-separated numbers
**Result**: All tests now pass
**Approach**: Minimal implementation using built-in array methods

Green phase complete. Should I proceed to Refactor phase?
```

```
🔄 Refactor Phase Complete:
**Refactoring**:
- Evaluated naming: kept `sumCommaSeparatedNumbers` (already clear)
- Mass calculation: remains at 38 (no improvements found)
- Considered helper functions but would increase complexity

Refactor phase complete. Should I proceed to the next test?
```

## Rule 2: Failed Prediction Recovery

### When to Apply
When the **"Guessing Game" prediction fails** - the actual test result differs significantly from what was predicted.

### What to Do
1. **Stop the TDD cycle immediately**
2. **Explain the prediction failure**:
   - What was predicted (error type, expected/actual values)
   - What actually happened
   - Why the prediction was wrong (if clear)
3. **Assess the implications**:
   - Does this indicate a misunderstanding of the code?
   - Does this suggest the test or implementation has issues?
   - Is this a learning opportunity about the system behavior?
4. **Explicitly ask**:
   - "My prediction was incorrect. Should I continue with the TDD process, or would you like me to investigate this discrepancy further?"
   - "Do you want me to explain why I think my prediction failed?"
   - "Should I adjust my understanding and continue, or take a different approach?"

### Why This Matters
- **Predictions build understanding** - Failures indicate gaps in comprehension
- **Early error detection** - Unexpected behavior might reveal bugs or design issues
- **Learning opportunity** - Human can provide insights about system behavior
- **Maintains TDD discipline** - Ensures predictions remain meaningful and accurate

### Example
```
❌ Prediction Failed:
- Predicted: Runtime assertion error (Expected: 3, Received: 1)
- Actual: Runtime assertion error (Expected: 3, Received: NaN)
- Issue: I incorrectly assumed parseInt("1,2") would return 1, but it actually returned NaN

This suggests I misunderstood how parseInt handles comma-separated strings. Should I continue with the TDD process, or would you like me to investigate this behavior further?
```

## Integration with TDD Process

### Modified TDD Process
1. **Red Phase** (compilation/runtime error) → **🛑 HUMAN CHECKPOINT**
2. **Green Phase** (minimal implementation) → **🛑 HUMAN CHECKPOINT**
3. **Refactor Phase** (mandatory improvements) → **🛑 HUMAN CHECKPOINT**
4. **Repeat** only with explicit human approval

### Modified Guessing Game
1. **Make explicit prediction**
2. **Run test**
3. **Compare prediction vs actual**
4. **🛑 HUMAN CHECKPOINT**: If prediction failed significantly (immediate stop)
5. **Continue current phase only after approval**

### Core Principle: Never Proceed Without Permission
- **Stop after every single phase** (Red, Green, Refactor)
- **Implement only what the current phase requires**
- **No lookahead or anticipatory coding**
- **No additional features without explicit human approval**
- **Each phase must be approved before continuing to next phase**

## Guidelines

### When to ALWAYS Stop (Rule 1)
- **After every TDD phase** - Red, Green, and Refactor (MANDATORY)
- **Before proceeding to next phase** - Human must approve continuation
- **Before writing any additional code** - Even if path seems obvious

### When to IMMEDIATELY Stop (Rule 2)
- **Significant prediction failures** - fundamental misunderstanding of behavior
- **Any unexpected test results** - if actual differs meaningfully from predicted
- **Compilation errors not anticipated** - suggests misunderstanding of codebase

### Never Continue Without Approval
- **No autonomous multi-phase execution** - Each phase requires explicit approval
- **No anticipatory implementation** - Only implement what current phase demands
- **No "obvious next steps"** - Human decides what constitutes next steps
- **No batch processing** - Each phase must be individually approved

## Benefits
- **Maintains human agency** in the TDD process
- **Prevents AI from making poor design decisions** in isolation
- **Creates learning opportunities** for both human and AI
- **Ensures code quality standards** are met
- **Builds confidence** in the TDD process through transparency
