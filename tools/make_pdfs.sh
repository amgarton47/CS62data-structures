#!/bin/bash
#
# create grading pdfs for submitted assignments
#
# Usage: make_pdfs.sh [-v] source-file ... [submission-dir ...]
#
HEADDIR=`pwd`

# confirm we have the required programs
for needed in enscript ps2pdf
do
	which $needed > /dev/null
	if [ $? -ne 0 ]
	then
		echo "ERROR - required program $needed not in search PATH"
		exit 1
	fi
done

verbose=0
if [ "$1" == "--verbose" -o "$1" == '-v' ]
then
	verbose=1
	shift
fi

out=""
if [ "$1" == "-lab" ]
then
	echo "hello"
	shift
	out="lab${1}"
	shift
fi

if [ "$1" == '-assignment' ]
then
	shift
	out="assignment${1}"
	shift
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

# generate a list of source files to print
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

	# enumerate the files we were told to print
	sources=""
	list=""
	for file in $printfiles
	do
		found=`find $1 -name $file`
		if [ -n "$found" -a -s "$found" ]
		then
			sources="$sources $found"
			list="$list $file"
		elif [ "$file" == "REPORT" ]
		then
			found=`ls _output/*.txt | grep $1`
			if [ -n "$found" -a -s "$found" ]
			then
				file=`basename $found`
				sources="$sources $found"
				list="$list $file"
			else
				echo "ERROR: unable to find report for $1 in _output"
			fi
		else
			echo "WARNING: unable to find $file under $1"
		fi
	done

	# figure out the student name
	name=$1
	if [ -n "$sources" ]
	then
		echo "... _output/${name}_${out}.pdf	<-$list"
		enscript -B -r -o - $sources 2>/dev/null | ps2pdf - "_output/${name}_${out}.pdf"
	else
		echo "... no printable files in submission $1"
	fi

	# and move on to the next project
	shift
done
