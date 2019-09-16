#!/bin/bash
#
# Build and run the junit tests for submitted solutions
#
# Usage: testbuild.sh [--save] [--verbose] [solution-source ...]
#
TEMPDIR="/tmp/JUNIT_$$"
HEADDIR=`pwd`

	
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

	# copy the needed libraries
	for file in $HEADDIR/_autos/dependencies/*
	do
		if [ -f $file ]
		then
			if [ $verbose -gt 0 ]
			then
				echo "... symlinking $file"
			fi
			ln -s $file $TEMPDIR
		fi
	done

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
			cp $file $TEMPDIR/$package
		fi
	done

	if [ $verbose -gt 0 ]
	then
		echo "... building JUNIT Test suite for package $package"
	fi
	cd $TEMPDIR
	javac -cp './*' junitmods/*.java $package/*.java

	if [ $? -eq 0 ]
	then
		if [ $verbose -gt 0 ]
		then
			echo "... running JUNIT Test suite for package $package"
		fi
		java -cp '.:junitmods:./*' junitmods.PomonaRunner $name.autos
		
		# pretty-print the output
		python3 -m json.tool $name.autos > $HEADDIR/_output/$name.autos

		# and report the bottom line
		passed=`grep passed_count $HEADDIR/_output/$name.autos | cut -d: -f2 | cut -d, -f1 | tr -d ' '`
		failed=`grep failed_count $HEADDIR/_output/$name.autos | cut -d: -f2 | cut -d, -f1 | tr -d ' '`
		total=`grep total_count $HEADDIR/_output/$name.autos | cut -d: -f2 | cut -d, -f1 | tr -d ' '`
		echo "... passed: $passed/$total, failed: $failed/$total"

		if [ $save -eq 0 ]
		then
			if [ $verbose -gt 0 ]
			then
				echo "... deleting temporary directory $TEMPDIR"
			fi
			rm -rf $TEMPDIR
		fi
	else
		echo
		>&2 echo "ERROR: build failed, results in $TEMPDIR"
	fi

	# and move on to the next project
	cd $HEADDIR
	shift
done
