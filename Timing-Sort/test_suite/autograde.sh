echo "Running SortTimer.main"
java -cp . sortCompare.SortTimer
ret=$?
echo "==================================="
echo "test suite exit code: $ret"
exit $ret
