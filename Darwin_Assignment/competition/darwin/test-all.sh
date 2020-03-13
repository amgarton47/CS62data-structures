#!/bin/sh
for creature in `cat contestants.txt`
do
  grep "${creature}" passed.txt >/dev/null
  if [ $? -eq 0 ]
  then
    echo "Creature '${creature}' already passed testing."
    continue
  fi
  java -cp bailey.jar:objectdraw.jar:. Darwin ${creature} &
  PID=$!
  sleep 2
  kill $PID
  if [ $? -ne 0 ]
  then
    echo "Test failed for '${creature}'"
    exit
  else
    echo ${creature} >> passed.txt
  fi
done
