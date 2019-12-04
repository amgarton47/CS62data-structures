echo "Running Tester.main"
java linkedlist.Tester --races=ir
ret=$?
echo "==================================="
echo "test suite exit code: $ret"
exit $ret
