# test suite needs to open ../Creatures, but the autograder
# runs one directory below where Eclipse does, so we have to
# move up and adjust our classpath accordingly.
#	
cd ..
if [ -d src/darwin -a -s src/darwin/AutograderCompTest.class ]
then
	echo running darwin/AutogradeCompTest
	java -cp src darwin.AutograderCompTest
	ret=$?
	echo "==================================="
	echo "test suite exit code: $ret"
else
	echo "ERROR: unexpected project directory structure!"
	exit 1
fi
