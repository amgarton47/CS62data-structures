RUNNER="org.junit.platform.console.ConsoleLauncher"
TESTCASE=ArrayListTest
 
if [ -d ../../../dependencies ]
then
	CLASSPATH='.:../../../dependencies/*'
elif [ -d ../../dependencies ]
then
	CLASSPATH='.:../../dependencies/*'
else
	echo " ... ERROR: uanble to find dependencies"
	exit -1
fi

java -cp $CLASSPATH $RUNNER -c $TESTCASE
if [ $? -eq 0 ]
then
	echo " ... SUCCESSFUL"
else
	echo "... FAILURE"
fi
