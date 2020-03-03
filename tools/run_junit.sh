#!/bin/bash
#
# Build and run the junit tests for submitted solutions
#
# Usage: run_junit.sh [--verbose] [--save] [submission-dir ...]
#
TEMPDIR="/tmp/JUNIT_$$"
HEADDIR=`pwd`
JAVA_OPTS="-Xlint:unchecked"
	
verbose=0
if [ "$1" == "--verbose" ]
then
	verbose=1
	shift
fi
save=0
if [ "$1" == "--save" ]
then
	save=1
	shift
fi

# verify that we have a test suite
fatal=0
for subdir in _autos _autos/dependencies _autos/src _autos/src/junitmods
do
	if [ ! -d $subdir ]
	then
		>&2 echo "ERROR: unable to access test suite sub-directory $subdir"
		fatal=1
	fi
done

# figure out what our package is
list=`ls _autos/src | grep -v junitmods`
if [ -z "$list" -o ! -d "_autos/src/$list" ]
then
	>&2 echo "ERROR: unable to discover package in _autos/src"
	fatal=1
else
	package=$list
fi

# make sure we have an output directory
if [ ! -d "_output" ]
then
	if [ $verbose -gt 0 ]
	then
		echo "... creating new _output directory"
	fi
	mkdir "_output"
fi

# try to discover location of JUnit libraries
if [ -s CLASSPATH ]
then
	CLASSPATH=`cat CLASSPATH`
elif [ -f $HOME/.p2/pool/plugins/org.junit.jupiter.api*.jar ]
then
	# default place for Eclipse to download libraries
	CLASSPATH=$HOME/.p2/pool/plugins/'*'
elif [ -f /Users/csadmin/.p2/pool/plugins/org.junit.jupiter.api*.jar ]
then
	# default place for pre-loaded lab machines
	CLASSPATH='/Users/csadmin/.p2/pool/plugins/*'
fi
if [ -n "$CLASSPATH" ]
then
	export CLASSPATH
	echo Compiling JAVA sources against CLASSPATH=$CLASSPATH
else
	echo "WARNING: unable to find JUnit5 CLASSPATH, please create CLASSPATH file"
fi

# if we weren't given directories to process, try everything
if [ -z "$1" ]
then
	set `ls`
fi

while [ -n "$1" ]
do
	# we only process directories
	if [ ! -d "$1" ]
	then
		shift
		continue
	fi

	# we do not process _autos or _output
	if [ "$1" == "_autos" -o "$1" == "_output" ]
	then
		shift
		continue
	fi

	# see if this appears to be a java project
	sourcedir=`find $1 -type d -name src`
	if [ ! -d $sourcedir ]
	then
		echo "$1 does not appear to be an Eclipse project"
		shift
		continue
	fi

	# see if it seems to contain the expected project
	if [ ! -d $sourcedir/$package ]
	then
		echo "WARNING: $1 does not appear to contain the $package package"
		shift
		continue
	fi

	# enumerage the java sources
	sources=""
	for file in $sourcedir/$package/*.java
	do
		if [ -f "$file" ]
		then
			sources="$sources $file"
		fi
	done
	if [ -z "$sources" ]
	then
		echo "WARNING: $sourcedir/$package does not contain any java sources"
		shift
		continue
	fi


	# figure out the student name
	name=$1

	echo "Autograding submission $1 in $TEMPDIR"
	mkdir $TEMPDIR

	# link in the dependencies (jars and files)
	if [ $verbose -gt 0 ]
	then
		echo "... linking to dependencies directory"
	fi
	ln -s $HEADDIR/_autos/dependencies $TEMPDIR

	# copy our JUnit extensions
	mkdir $TEMPDIR/junitmods
	for file in $HEADDIR/_autos/src/junitmods/*.java
	do
		if [ -f $file ]
		then
			if [ $verbose -gt 0 ]
			then
				echo "... copying JUnit module $file"
			fi
			cp $file $TEMPDIR/junitmods
		fi
	done

	# copy the autograder sources
	mkdir $TEMPDIR/$package
	for file in $HEADDIR/_autos/src/$package/*.java
	do
		if [ -f $file ]
		then
			if [ $verbose -gt 0 ]
			then
				echo "... copying autograder source $file"
			fi
			cp $file $TEMPDIR/$package
		fi
	done

	# and finally, copy in the solution sources
	for file in $sources
	do
		if [ -f $file ]
		then
			if [ $verbose -gt 0 ]
			then
				echo "... copying solution source $file"
			fi
			to=`basename $file`
			sed 's/private/protected/' $file > $TEMPDIR/$package/$to
		fi
	done

	if [ $verbose -gt 0 ]
	then
		echo "... building JUNIT Test suite for package $package"
	fi
	cd $TEMPDIR
	javac $JAVA_OPTS -cp 'dependencies/*':$CLASSPATH junitmods/*.java $package/*.java

	if [ $? -eq 0 ]
	then
		if [ $verbose -gt 0 ]
		then
			echo "... running JUNIT Test suite for package $package"
		fi
		java -cp '.:junitmods:dependencies/*':$CLASSPATH -Dtesting=$TEMPDIR junitmods.PomonaRunner $name.autos
		
		# pretty-print the output
		which python3 > /dev/null
		if [ $? -eq 0 ]
		then
			python3 -m json.tool $name.autos > $HEADDIR/_output/$name.autos
		else
			python -m json.tool $name.autos > $HEADDIR/_output/$name.autos
		fi

		# and report the bottom line
		passed=`grep passed_count $HEADDIR/_output/$name.autos | cut -d: -f2 | cut -d, -f1 | tr -d ' '`
		failed=`grep failed_count $HEADDIR/_output/$name.autos | cut -d: -f2 | cut -d, -f1 | tr -d ' '`
		total=`grep total_count $HEADDIR/_output/$name.autos | cut -d: -f2 | cut -d, -f1 | tr -d ' '`
		echo "... passed: $passed/$total, failed: $failed/$total"

	else
		echo
		>&2 echo "ERROR: build failed"
	fi

	# see if we are supposed to keep the directory around
	if [ $save -eq 0 ]
	then
		if [ $verbose -gt 0 ]
		then
			echo "... deleting temporary directory $TEMPDIR"
		fi
		rm -rf $TEMPDIR
	fi

	# and move on to the next project
	cd $HEADDIR
	shift
done
