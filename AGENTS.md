## Conversational Style

- Exercise full agency to push back on mistakes. Flag issues early, ask questions if unsure of direction instead of choosing randomly.
- Eliminate emojis, praise, filler, hype.
- Don't flatter me. Give me honest feedback even if I don't want to hear it.

## Technical Matters

- When configuring options, for example configuring a linter or gradle job, only specify non-default options.
- Use OpenAPI to integrate backend and frontend. We use `springdoc-openapi-starter-webmvc-ui` in the backend and `openapi-typescript` in the frontend build files to support this. Specifically, whenever a backend controller changes, use the OpenAPI file to regenerate the frontend interface before adapting the frontend.

## Frontend API URL Configuration

Frontend is deployed to Cloud Storage, backend to Cloud Run. The CI/CD pipeline sets `VITE_API_URL` during build. See `frontend/src/api/client.ts` for implementation details. Do not change the env var name or remove environment variable support.

## Use Skills and sub-agents

Before using any tool directly, check if a specialized sub-agent or skill exists for this task type.

## Code comments

Code comments are used sparingly in this project, since they add to code bloat. Comprehensible and expressive code (eg. consistent, logical naming) is preferred to comments.

Comments are still added when they contribute to much faster, better understanding in two cases:
- To explain why something was done, when it is not apparent from the context.
- To explain what is being done, if the code is necessarily difficult to understand for an advanced programmer or agent.

If a log line is written explaining what is happening, any comment above that line which essentially says the same thing is removed, since a developer has the same information from the log line.

Developers challenge comments to ensure they match the criteria. Existing comments are cleaned up according to the boy-scout rule.

## Documentation

Read README.md to better understand the project structure. The technical documentation in this project is always concise, delivering maximum meaning with a minimum amount of words and examples.

The documentation follows these principles:

- assume the reader is a knowledgeable developer.
- document project-specific knowledge, do not replicate world knowledge.
    - good: "The frontend is built with React."
    - bad: "React is a framework published by Facebook."
- inform the user of genuinely important/helpful information, not promote the project by listing every internal implementation detail.
- weigh additions against the knowledge that the longer the documentation is, the less likely that anyone will read it at all.
