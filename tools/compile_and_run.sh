#!/bin/bash
#
# Build and run the submitted solutions
#
# Usage: build_and_run.sh [--save] [--verbose] [submission-dir ...]
#
#  plus, we expect to find two files in the grading directory:
#	PACKAGE	... name of the package (if any) to be tested
#	MAINCLASS ... name of the main class (if any) to be run
#
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

# if we weren't given directories to process, try everything
if [ -z "$1" ]
then
	set `ls`
fi

# figure out what package we are expecting to find
if [ -s PACKAGE ]
then
	package=`cat PACKAGE`
	echo "Grading package $package"
else
	package=""
	echo "Assuming this assignment uses default package"
fi

# figure out what class we are expected to run
if [ -s MAINCLASS ]
then
	mainclass=`cat MAINCLASS`
	if [ -n "$package" ]
	then
		mainclass="$package.$mainclass"
	fi
else
	mainclass=""
	echo "No MAINCLASS defined, submissions will not be run."
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
	srcdir=`find "$1" -type d -name src`
	if [ ! -d "$srcdir" ]
	then
		echo "$1 does not appear to be an Eclipse project"
		shift
		continue
	fi

	# see if we can find the package name
	if [ -z "$package" ]
	then
		sourcedir="$srcdir"
	elif [ -d "$srcdir/$pacakge" ]
	then
		sourcedir=$srcdir/$package
	else
		echo "WARNING: $1 does not appear to contain the $package package"
		shift
		continue
	fi

	# enumerage the java sources
	sources=""
	for file in "$sourcedir"/*.java
	do
		if [ -f "$file" ]
		then
			sources="$sources $file"
		fi
	done
	if [ -z "$sources" ]
	then
		echo "WARNING: $sourcedir does not contain any java sources"
		shift
		continue
	fi

	# try to build the submission
	if [ -s $1/OUTPUT ]
	then
		rm $1/OUTPUT
	fi
	cd "$sourcedir"
	javac *.java > "$HEADDIR/$1/OUTPUT"
	if [ $? -eq 0 ]
	then
		if [ -n "$mainclass" ]
		then
			# try to run the submission
			echo "... attempting to run submission $1"
			cd "$HEADDIR/$srcdir"
			java $mainclass >> "$HEADDIR/$1/OUTPUT"
			if [ $? -eq 0 ]
			then
				echo "... exit status 0" >> "$HEADDIR/$1/OUTPUT"
			else
				echo "NON-ZERO EXIT STATUS" >> "$HEADDIR/$1/OUTPUT"
			fi
		else
			echo "... successful build of submission $1"
		fi
	else
		echo
		>&2 echo "ERROR: build failed, results in $sourcedir"
	fi

	# and move on to the next project
	echo
	cd $HEADDIR
	shift
done
