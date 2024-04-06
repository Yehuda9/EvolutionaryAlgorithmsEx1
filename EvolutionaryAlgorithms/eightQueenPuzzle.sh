#!/usr/bin/env bash

if [ ! -d bin ]; then
  mkdir -p bin
fi

javac -sourcepath ./src/ -d ./bin/ ./src/EightQueenPuzzle/Main.java

cd bin || exit

py ../src/plot/plot.py "$(java EightQueenPuzzle.Main)"