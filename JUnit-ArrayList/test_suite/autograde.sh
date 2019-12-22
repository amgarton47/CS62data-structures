RUNNER="org.junit.platform.console.ConsoleLauncher"
TESTCASE=ArrayListTest
 
java -cp '../../../dependencies/*' $RUNNER -c $TESTCASE
if [ $? -eq 0 ]
then
	echo " ... SUCCESSFUL"
else
	echo "... FAILURE"
fi
