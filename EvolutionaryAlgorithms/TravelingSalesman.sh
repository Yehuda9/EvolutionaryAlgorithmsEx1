#!/usr/bin/env bash

if [ ! -d bin ]; then
  mkdir -p bin
fi

javac -sourcepath ./src/ -d ./bin/ ./src/TravelingSalesman/Main.java

cd bin || exit

py ../src/plot/plot.py "$(java TravelingSalesman.Main ../data/tsp.txt)"