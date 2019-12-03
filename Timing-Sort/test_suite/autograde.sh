echo "Running SortTimer.main"
rm -f ERRORS
java sortCompare.SortTimer
ret=$?
echo "==================================="
echo "test suite exit code: $ret"

if [ -s ../ERRORS ]
then
	echo "==================================="
	echo "TEST SUITE PRODUCED ERROR OUTPUT"
	echo
	exit 1
else
	exit $ret
fi
