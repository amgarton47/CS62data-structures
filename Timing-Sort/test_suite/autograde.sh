echo "Running SortTimer.main"
java sortCompare.SortTimer
ret=$?
echo "==================================="
echo "test suite exit code: $ret"
exit $ret
