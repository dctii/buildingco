#!/bin/zsh

# Define the package and classpath
PROJECT_NAME="buildingco"
PACKAGE="com.solvd.${PROJECT_NAME}"
CLASSPATH="src/main/java"
OUT_DIR="out"

SCRIPT_DIR=$(dirname "$0")
cd "$SCRIPT_DIR" || exit

# Find 'PROJECT_NAME' directory and then 'src' directory
while [ "$(basename "$(pwd)")" != "${PROJECT_NAME}" ]; do
  cd ..
  if [ "$(pwd)" = "/" ]; then
    echo -e "\033[31mReached root directory, '${PROJECT_NAME}' not found.\033[0m"
    exit 1
  fi
done

echo -e "\033[32mFound root directory. Attempting clean, compile, and run...\033[0m"

# Function to test the status of the previous command
test_status() {
  local pass
  local fail

  while [ "$#" -gt 0 ]; do
    case "$1" in
      pass=*)
        pass="${1#pass=}"
        ;;
      fail=*)
        fail="${1#fail=}"
        ;;
    esac
    shift
  done

  if [ $? -eq 0 ]; then
    echo -e "\033[32m\u2022 ${pass}\033[0m"
  else
    echo -e "\033[31m\u2022 ${fail}\033[0m"
    exit 1
  fi
}

clean_project() {
  local pass=""
  local fail=""

  while [ "$#" -gt 0 ]; do
    case "$1" in
      pass=*)
        pass="${1#pass=}"
        ;;
      fail=*)
        fail="${1#fail=}"
        ;;
    esac
    shift
  done

  if [ -d "${OUT_DIR}" ]; then
    rm -rf ${OUT_DIR}
  fi
  find . -name "*.class" -exec rm -f {} +
  test_status pass="${pass}" fail="${fail}"
}

# Pre-cleaning: remove existing 'out/' directory and '.class' files
clean_project pass="Pre-cleaning process successful." fail="Pre-cleaning process failed."

# Compile all .java files under src/main/java
javac "$(find . -name "*.java")"
test_status pass="Compilation successful." fail="Compilation failed."

# Run
java -classpath ${CLASSPATH} ${PACKAGE}.Main
test_status pass="Program ran successfully." fail="Java program execution failed."

# Post-cleaning: remove existing 'out/' directory and '.class' files
clean_project pass="Post-cleaning process successful." fail="Post-cleaning process failed."

# Unset variables
unset PACKAGE CLASSPATH SCRIPT_DIR OUT_DIR
