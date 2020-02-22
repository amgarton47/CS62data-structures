RUNNER="org.junit.platform.console.ConsoleLauncher"
TESTCASE=ArrayListTest

# CLASSPATH has been set by compile_and_run.sh
#   but we must add the ArrayListTest class in this directory
java -cp .:$CLASSPATH $RUNNER -c $TESTCASE
if [ $? -eq 0 ]
then
	echo " ... SUCCESSFUL"
else
	echo "... FAILURE"
fi
