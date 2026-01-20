# Component Specifications

This directory contains detailed specifications for each component in the dotfiles repository.

## Dotfile Components

| Component | Spec File | Code Location | Description |
|-----------|-----------|---------------|-------------|
| Hourly Forecast | [hourly-forecast.md](hourly-forecast.md) | `backend/src/main/kotlin/de/codecentric/heather/weather/`<br>`frontend/src/components/` | 24-hour hourly weather forecast with temperature and conditions |

## Purpose of These Specs

Feature specs are **interface documents for human engineers to guide AI agents** during implementation. They provide clear descriptions, architectural constraints, critical guardrails, and success criteria - NOT exhaustive implementation documentation.

## Documentation Guidelines

When working with features:

- **Writing a new feature spec?** → Read [how-to-write-specs.md](how-to-write-specs.md)
- **Planning feature implementation?** → Read [how-to-write-implementation-plans.md](how-to-write-implementation-plans.md)

## Quick Reference

| Document Type | Purpose | When to Create | Length |
|--------------|---------|----------------|--------|
| **Feature Spec** | Define WHAT and WHY | Before implementation starts | 1-2 pages |
| **Implementation Plan** | Define HOW and WHEN | At start of implementation | As needed |

## Contributing

When working with a spec (Creating, Updating, Reviewing) use these guidelines: [how-to-write-specs.md](how-to-write-specs.md).

When adding a new features or specs:
1. Create or update a spec using the guidelines
2. Add an entry to the Components table above
3. Create an implementation plan if needed (see [how-to-write-implementation-plans.md](how-to-write-implementation-plans.md))
4. Stop. Implementation is triggered in a new session
