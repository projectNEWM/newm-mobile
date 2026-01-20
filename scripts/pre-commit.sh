#!/bin/sh

# Run Spotless Apply to format code before commit
echo "Running Spotless Apply..."
./gradlew spotlessApply

# Add any changes made by Spotless back to the commit
git add -u
