JUNIT="junit-platform-console-standalone-1.5.2.jar"
RUNNER="org.junit.platform.console.ConsoleLauncher"
TESTCASE=ArrayListTest

TOP=`pwd`
for s in */ArrayList/src
do
	echo $s
	if [ -f $s/$TESTCASE.class ]
	then
		cd $s
		java -cp ".:$JUNIT" $RUNNER -c $TESTCASE > /dev/null
		if [ $? -eq 0 ]
		then
			echo " ... SUCCESSFUL"
		else
			echo "... FAILURE"
		fi
		cd $TOP
	else
		echo "... ERROR: no $TESTCASE.class"
	fi
	echo
done
