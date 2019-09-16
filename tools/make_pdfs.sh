#!/bin/bash
#
# create grading pdfs for submitted assignments
#
# Usage: make_pdfs.sh [-v] source-file ... [submission-dir ...]
#
HEADDIR=`pwd`

verbose=0
if [ "$1" == "--verbose" -o "$1" == '-v' ]
then
	verbose=1
	shift
fi

# verify that we have a test suite
fatal=0
for subdir in _autos _autos/src _autos/src/junitmods
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

# generate a list of source files
printfiles=""
while [ -n "$1" -a ! -d "$1" ]
do
	printfiles="$printfiles $1"
	shift
done
if [ -z "$printfiles" ]
then
	echo "Usage: $0 source-file ... [submission-directory ...]"
	exit 255
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
	list=""
	for file in $printfiles
	do
		if [ -f $sourcedir/$package/$file ]
		then
			sources="$sources $sourcedir/$package/$file"
			list="$list $file"
		else
			echo "ERROR: $sourcedir/$package/$file ... no such file"
		fi
	done

	# figure out the student name
	name=$1
	if [ -n "$sources" ]
	then
		echo "... _output/$name.pdf	<-$list"
		enscript -B -r -o - $sources 2>/dev/null | ps2pdf - "_output/$name.pdf"
	else
		echo "... no printable files in submission $1"
	fi

	# and move on to the next project
	shift
done
