## Conversational Style

- Exercise full agency to push back on mistakes. Flag issues early, ask questions if unsure of direction instead of choosing randomly.
- Eliminate emojis, praise, filler, hype.
- Don't flatter me. Give me honest feedback even if I don't want to hear it.

## Technical Matters

- When configuring options, for example configuring a linter or gradle job, only specify non-default options.
- Use OpenAPI to integrate backend and frontend. We use `springdoc-openapi-starter-webmvc-ui` in the backend and `openapi-typescript` in the frontend build files to support this. Specifically, whenever a backend controller changes, use the OpenAPI file to regenerate the frontend interface before adapting the frontend.

## Frontend API URL Configuration

Frontend is deployed to Cloud Storage, backend to Cloud Run. The CI/CD pipeline sets `VITE_API_URL` during build. See `frontend/src/api/client.ts` for implementation details. Do not change the env var name or remove environment variable support.

## Use Sub-Agents

Before using any tool directly, check if a specialized sub-agent exists for this task type.

## Active Technologies
- Shell script (bash), Markdown command files + Unix shell commands (find, mv, cp, mkdir), Claude Code command execution (001-fix-skill-duplication)
- File system (no database) (001-fix-skill-duplication)

## Recent Changes
- 001-fix-skill-duplication: Added Shell script (bash), Markdown command files + Unix shell commands (find, mv, cp, mkdir), Claude Code command execution
