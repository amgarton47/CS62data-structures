#!/bin/bash
#
# Build and run the submitted solutions
#
# Usage: build_and_run.sh [--save] [--verbose] [submission-dir ...]
#
#  plus, we may look for one more file in the grading directory
#	DO_NOT_RUN list of classes (with main) that should not be run
#
# TODO: automatically create appropriate .autos files
#
#
HEADDIR=`pwd`
	
verbose=0
if [ "$1" == "--verbose" ]
then
	verbose=1
	shift
fi
save=0

# is there any main class we should not run?
if [ -f DO_NOT_RUN ]
then
	never_run=`cat DO_NOT_RUN`
else
	never_run=""
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

	if [ $verbose -eq 1 ]
	then
		echo "Submission directory $1"
	fi

	# see if there seems to be a single package under src
	count=0
	for file in "$srcdir/*"
	do
		if [ -d $file ]
		then
			package=`basename $file`
		fi
		count=$((count+1))
	done
	if [ $count -eq 1 ]
	then
		sourcedir=$srcdir/$package
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

				# is this class on the never-run list?
				skip=0
				for n in $never_run
				do
					if [ "$class" == "$n" ]
					then
						skip=1
					fi
				done

				if [ $skip -eq 0 ]
				then
					runnable="$runnable $class"
				fi
			fi
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
	javac *.java > "$HEADDIR/$1/OUTPUT" 2>&1
	if [ $? -eq 0 ]
	then
		if [ -n "$runnable" ]
		then
			# try to run the submission
			cd "$HEADDIR/$srcdir"

			# run the runnable classes
			for c in $runnable
			do
				if [ -n "$package" ]
				then
					c=$package.$c
				fi
				echo "   $1 ... attempting to run $c"
				java $c >> "$HEADDIR/$1/OUTPUT"
				if [ $? -eq 0 ]
				then
					echo "   $1/$c ... exit status 0" >> "$HEADDIR/$1/OUTPUT"
				else
					echo "   $1/$c ... NON-ZERO EXIT STATUS" >> "$HEADDIR/$1/OUTPUT"
				fi
			done
		else
			echo "   $1 ... build successful"
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
