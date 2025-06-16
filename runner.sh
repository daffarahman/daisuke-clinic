#!/bin/bash
rm -rf out
mkdir -p out
find src -name "*.java" > sources.txt
javac -d out -cp lib/gson-2.13.1.jar @sources.txt
rm sources.txt
java -cp out:lib/gson-2.13.1.jar daisukeclinic.Main