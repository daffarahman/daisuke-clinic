#!/bin/bash
rm -rf out
mkdir -p out
find src -name "*.java" > sources.txt
javac -d out -cp "lib/*" @sources.txt
rm sources.txt
java -cp "out:lib/*" daisukeclinic.Main