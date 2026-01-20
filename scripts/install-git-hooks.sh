#!/bin/sh

# Set the pre-commit hook
ln -sf ../../scripts/pre-commit.sh .git/hooks/pre-commit
chmod +x .git/hooks/pre-commit
chmod +x scripts/pre-commit.sh

echo "Git hooks installed successfully."
