#!/bin/bash
TEMPLATE="lab06.json"
STUDENT="student_one"

for i in 1
do
	rm -f $STUDENT.json
	python3 ../merge_scores.py -t $TEMPLATE test_$i.csv
	diff -b $STUDENT.json test_$i.json
	if [ $? -ne 0 ]
	then
		exit 1
	fi
done
