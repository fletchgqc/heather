#!/bin/sh

echo "Setting up git hooks..."

# Configure git to use .githooks directory for this repository
git config core.hooksPath .githooks

# Make sure hooks are executable
chmod +x .githooks/pre-commit
chmod +x .githooks/pre-push

echo "✓ Git hooks configured successfully"
echo ""
echo "Hooks enabled:"
echo "  - pre-commit: Runs lint-staged for frontend, detektTwice for backend"
echo "  - pre-push: Runs validate for frontend, build for backend"