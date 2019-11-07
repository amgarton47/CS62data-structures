#
# function to generate autograder-style results (for further processing)
#
#	arg1: student name (for output file creation)
#	arg2: no_submission, build_error, run_error, run_ok
#
#   It might be easier to use a spreadsheet to accumulate non-autograder
#   results, but if someone prefers to use the json tools, this kicks off
#   that process.  But, note that it assumes names for the BUILD and RUN
#   rubric items.
#
# it can also be run directly as a script to produce .autos with a
# specified status for a list of people
#	
#   usage: report.sh status user ...
#

# this script has to know where to put the output
if [ -n "$HEADDIR" -a -d "$HEADDIR" ]
then
	# we have been told where
	OUTPUT_DIR="$HEADDIR/_output"
elif [ -d _output ]
then
	# current working directory looks good
	OUTPUT_DIR=`pwd`/_output
else
	echo "ERROR: report function requires HEADIR to be defined"
	exit
fi
OUTPUT_SFX="autos"

function report() {

	BUILDS="\"imports and builds with no errors/warnings\""
	RUNS="\"runs with plausible results\""
	REVIEW="\"PLEASE REVIEW\""
	SCOREFILE="$OUTPUT_DIR/$1.$OUTPUT_SFX"

	echo "   $student ... producing .$OUTPUT_SFX results"

	# make sure we have an output directory
	if [ ! -d $OUTPUT_DIR ]
	then
		mkdir $OUTPUT_DIR
		chmod 775 $OUTPUT_DIR
	fi

	# figure out how many scores we expect to see
	if [ "$2" == "no_submission" -o "$2" == "build_error" ]
	then
		pass=0
		fail=2
	elif [ "$2" == "build_ok" ]
	then
		pass=1
		fail=0
	elif [ "$2" == "run_error" ]
	then
		pass=1
		fail=1
	elif [ "$2" == "run_ok" ]
	then
		pass=2
		fail=0
	fi

	cat <<- EOF > $SCOREFILE
	{
	    "total_count": $((pass+fail)),
	    "passed_count": $pass,
	    "failed_count": $fail,
	    "time": 0,
	    "serializer": "None",
	    "version": "0.0.0",
	EOF

	echo "    \"failures\": ["			>> $SCOREFILE
	if [ "$2" == "no_submission" -o "$2" == "build_error" ]
	then
		echo "        { \"testname\": $BUILDS,"	>> $SCOREFILE
		echo "          \"message\": $REVIEW },">> $SCOREFILE
		echo "        { \"testname\": $RUNS,"	>> $SCOREFILE
		echo "          \"message\": $REVIEW }"	>> $SCOREFILE
	elif [ "$2" == "run_error" ]
	then
		echo "        { \"testname\": $RUNS,"	>> $SCOREFILE
		echo "          \"message\": $REVIEW }"	>> $SCOREFILE
	fi
	echo "    ],"		   			>> $SCOREFILE

	echo "    \"passes\": ["   >> $SCOREFILE
	if [ "$2" == "build_ok" -o "$2" == "run_error" ]
	then
		echo "        $BUILDS"			>> $SCOREFILE
	elif [ "$2" == "run_ok" ]
	then
		echo "        $BUILDS,"			>> $SCOREFILE
		echo "        $RUNS"			>> $SCOREFILE
	fi
	echo "    ]"		  			>> $SCOREFILE
	echo "}"					>> $SCOREFILE
}

# if we are run as a script
if [ "$0" == "bash" ]
then
	echo "just sourcing"
else
	if [ -z "$1" -o -z "$2" ]
	then
		echo "usage report.sh status name ..."
		echo "      status: no_submission build_error build_ok run_error run_ok"
	else
		# generate .autos for each listed name
		status="$1"
		shift
		while [ -n "$1" ]
		do
			echo $1 ... $status
			report "$1" "$status"
			shift
		done
	fi
fi
