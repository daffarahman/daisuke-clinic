#!/bin/bash

# Create required directories
mkdir -p out

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to compile the project
compile() {
    echo -e "${BLUE}Compiling project...${NC}"
    # Find all java files and compile them to out directory
    find src -name "*.java" > sources.txt
    javac -d out @sources.txt
    rm sources.txt
    
    if [ $? -eq 0 ]; then
        echo -e "${GREEN}Compilation successful!${NC}"
        return 0
    else
        echo -e "${RED}Compilation failed!${NC}"
        return 1
    fi
}

# Function to run the project
run() {
    if [ ! -d "out" ]; then
        echo -e "${RED}Project is not compiled. Compiling first...${NC}"
        compile
    fi
    
    echo -e "${BLUE}Running DaisukeClinic...${NC}"
    java -cp out daisukeclinic.Main
}

# Function to clean compiled files
clean() {
    echo -e "${BLUE}Cleaning compiled files...${NC}"
    rm -rf out
    echo -e "${GREEN}Clean complete!${NC}"
}

# Function to run all steps (clean, compile, run)
run_all() {
    clean
    compile && run
}

# Help message
show_help() {
    echo "DaisukeClinic Runner Script"
    echo "Usage: ./runner.sh [command]"
    echo
    echo "Commands:"
    echo "  compile    Compile the project"
    echo "  run        Run the project (will compile if needed)"
    echo "  clean      Clean compiled files"
    echo "  help       Show help message"
    echo
    echo "No arguments will clean, compile and run the project"
}

# Main script logic
case "$1" in
    "compile")
        compile
        ;;
    "run")
        run
        ;;
    "clean")
        clean
        ;;
    "help")
        show_help
        ;;
    "")
        run_all
        ;;
    *)
        echo -e "${RED}Unknown command: $1${NC}"
        show_help
        exit 1
        ;;
esac