# confirm we can find the analysis
found=`find ../.. -name '*.txt'`
if [ $? -eq 0 -a -n "$found" ]
then
	ls -l $found
else
	echo
	echo "UNABLE TO FIND analysis.txt"
	echo
fi

# if we have copies of the starter, diff the changes
if [ -n "$STARTER" -a -d "$STARTER" ]
then
	for f in DLL_Node.java Tester.java
	do
		echo "Changes to $f ..."
		diff -b $STARTER/$f linkedlist/$f
		echo
	done
fi

# run the program to see if race conditions have been eliminated
echo "... Running Tester.main"
java -cp . linkedlist.Tester --races=ir
ret=$?
echo "==================================="
echo "test suite exit code: $ret"

exit $ret
