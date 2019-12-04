#!/bin/bash
#
# Build and run the submitted solutions
#
# Usage: build_and_run.sh [--verbose] [submission-dir ...]
#
# supporting files:
#
#   autograde.sh
#	by default we will build and run everything that
#	has a main() method, but if tehre is an autograde.sh
#	in the grading directory we will copy it into the
#	submission directory and let it run things.
#
#   report.sh
# 	after doing our compile and run we will invoke
#	a report method (with what did/didn't work) to
#	record the results (e.g. create a .autos file).
#
#   PACKAGE
#	Most submissions contain only a single package.
#	If there are multiple packages in a submission,
#	this file contains the name of the only one we
#	should build and run.
#
HEADDIR=`pwd`
	
verbose=0
if [ "$1" == "--verbose" ]
then
	verbose=1
	shift
fi

# get the report generator (for recording results)
if [ -f ./report.sh ]
then
	. ./report.sh
else
	path=`dirname $0`
	if [ -f $path/report.sh ]
	then
		. $path/report.sh
	else
		echo "This script requires a copy of the report.sh script"
		exit 1
	fi
fi

# do we know which (of multiple) package to build and run
if [ -f PACKAGE ]
then
	only_package=`cat PACKAGE`
else
	only_package=""
fi

# do we have an (assignment specific) autograder script
if [ -f autograde.sh ]
then
	autograder=`pwd`/autograde.sh
else
	autograder=""
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
	srcdir=`find "$1" -type d -name src`
	if [ ! -d "$srcdir" ]
	then
		echo "$1 does not appear to be an Eclipse project"
		shift
		continue
	fi

	# see if we can infer a student name
	student=`echo $1 | cut -d- -f1`

	if [ $verbose -eq 1 ]
	then
		echo "Submission directory $1: "
	fi

	# see if there seems to be a single package under src
	count=0
	for file in $srcdir/*
	do
		if [ -d $file ]
		then
			package=`basename $file`
			count=$((count+1))
		fi
	done
	if [ $count -eq 1 ]
	then
		sourcedir=$srcdir/$package
	elif [ $count -gt 1 ]
	then
		if [ -n "$only_package" -a -d "$srcdir/$only_package" ]
		then
			sourcedir=$srcdir/$only_package
		else
			echo "WARNING: $srcdir seems to contain multiple packages ... create PACKAGE file"
			shift
			continue
		fi
	else
		sourcedir=$srcdir
	fi

	# enumerate the java sources and find main class(es)
	sources=""
	runnable=""
	for file in "$sourcedir"/*.java
	do
		if [ -f "$file" ]
		then
			# add it to list of files to compile
			sources="$sources $file"
			
			# see if we should run it
			count=`grep -c 'static void main(' $file`
			if [ $count -eq 1 ]
			then
				class=`basename $file .java`

				# append this to the runnable list
				runnable="$runnable $class"
			fi
		fi
	done
	if [ -z "$sources" ]
	then
		echo "WARNING: $sourcedir does not contain any java sources"
		report $student no_submission
		shift
		continue
	fi

	# try to build the submission
	if [ -s $1/OUTPUT ]
	then
		rm $1/OUTPUT
	fi
	echo "SUBMISSION $1" > "$HEADDIR/$1/OUTPUT"
	echo >> "$HEADDIR/$1/OUTPUT"

	cd "$sourcedir"
	echo "   $1 ... attempting to compile " *.java
	echo "===================" >> "$HEADDIR/$1/OUTPUT"
	echo "COMPILATION RESULTS" >> "$HEADDIR/$1/OUTPUT"
	echo "===================" >> "$HEADDIR/$1/OUTPUT"
	javac *.java >> "$HEADDIR/$1/OUTPUT" 2>&1
	if [ $? -eq 0 ]
	then
		if [ -n "$autograder" -o -n "$runnable" ]
		then
			# try to run the submission
			cd "$HEADDIR/$srcdir"

			# note where the output is expected to be left
			where="$1/OUTPUT"

			# run the runnable classes
			echo 			   >> "$HEADDIR/$1/OUTPUT"
			echo "===================" >> "$HEADDIR/$1/OUTPUT"
			echo "AUTOGRADER RESULTS"  >> "$HEADDIR/$1/OUTPUT"
			echo "===================" >> "$HEADDIR/$1/OUTPUT"
			echo 			   >> "$HEADDIR/$1/OUTPUT"
			errors=0
			if [ -n "$autograder" ]
			then
				echo "   $1 ... running $autograder"
				$autograder >> "$HEADDIR/$1/OUTPUT" 2>> "$HEADDIR/$1/ERRORS"
				if [ $? -ne 0 ]
				then
					errors=1
				fi
			else
				for c in $runnable
				do
					if [ -n "$package" ]
					then
						c=$package.$c
					fi
					echo >> "$HEADDIR/$1/OUTPUT"
					echo "   $1 ... attempting to run $c"
					echo "=====================================" >> "$HEADDIR/$1/OUTPUT"
					java $c >> "$HEADDIR/$1/OUTPUT" 2> "$HEADDIR/$1/ERRORS"
					echo "=====================================" >> "$HEADDIR/$1/OUTPUT"
					if [ $? -eq 0 ]
					then
						echo "$1/$c ... exit status 0" >> "$HEADDIR/$1/OUTPUT"
					else
						errors=$((errors+1))
						echo "$1/$c ... non-zero exit status" >> "$HEADDIR/$1/OUTPUT"
					fi
					echo >> "$HEADDIR/$1/OUTPUT"
				done
			fi

			if [ -s "$HEADDIR/$1/ERRORS" ]
			then
				echo "=====================================" >> "$HEADDIR/$1/OUTPUT"
				echo "$1/$c ... output to stderr" >> "$HEADDIR/$1/OUTPUT"
				where="$1/OUTPUT (and ERRORS)"
			else
				rm -f "$HEADDIR/$1/ERRORS"
			fi

			if [ $errors -gt 0 ]
			then
				report $student run_error
			else
				report $student run_ok
			fi
			echo "   $1 ... compilation and execution results in $where"
		else
			echo "   $1 ... build successful"
			report $student build_ok
		fi
	else
		echo
		>&2 echo "ERROR: build failed, results in $sourcedir"
		report $student build_error
	fi

	# and move on to the next project
	echo
	cd $HEADDIR
	shift
done
