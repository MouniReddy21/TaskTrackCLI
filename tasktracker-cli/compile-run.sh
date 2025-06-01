#!/bin/bash

# Exit immediately on errors
set -e

# Build the shaded jar
echo "Building the project with Maven..."
mvn clean package

# Path to the shaded jar (adjust if needed)
JAR_FILE="target/tasktracker-cli-1.0-SNAPSHOT-shaded.jar"

if [ ! -f "$JAR_FILE" ]; then
  echo "Error: JAR file not found at $JAR_FILE"
  exit 1
fi

# Run the jar with passed arguments
echo "Running the application..."
java -jar "$JAR_FILE" "$@"
