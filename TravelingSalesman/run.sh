#!/usr/bin/env bash

if [ ! -d bin ]; then
  mkdir -p bin
fi

javac src/*java -d bin

cd bin || exit

py ../../plot/plot.py "$(java Main ../data/tsp.txt)"