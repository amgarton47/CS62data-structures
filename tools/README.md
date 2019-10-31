# Grading process

    1. create a grading directory containing copies of the submission repos 
       to be graded.
    2. pull in a copy of classroom.json
    3. copy in everything from the test_suite directory for that project

## projects covered by full autograder suites

    4. run the run_junit.sh script, and see if it seems to be correctly
       processing all of the assignments.  
       
       The most common problems involve compilation errors or files that 
       students moved to a different place in the hierarchy.  I make notes
       of these (for point deductions) but then fix them so that testing
       can continue.

       If there were compliation errors, you might want to edit the
       stuff in comple_error.json into the affected .autos files.

    5. use the merge(socres.py program to transfer the .autos results 
       from the autograder into more complete per-submission .json files.

## projects not covered by full autograder suites

    4. run the compile_and_run.sh script (which will probably require
       a copy of either a generic or per-project report.sh script.

       Again, te most common problems involve compilation errors or files 
       that students moved to a different place in the hierarchy.  I make 
       notes of these (for point deductions) but then fix them so that 
       testing can continue.

       The resulting .auto reports will only cover whether or not
       the resulting projects built and ran.  All additional scoring
       will be done manually.

    5. You have a decision to make on how you want to do the rest of the
       scoring:

       5a) use the auto2csv.py program to create a spreadsheet for all of
           the submissions, and do the remainder of the grading on the
	   spreadsheet.

	   When you are done, use the csv2json.py program to produce the
	   per-submission .json files.

       5b) use the merge_scores.py to create a preliminary .json file
           from each of the autograder-produced .auto files.

       Either way, you can continue by directly editing
       the per-submission .json files.  Editing JSON files is error
       prone (commas, quotes, and braces matter), but makes it possible
       for you to provide detailed comments.

## final report generation

   6. use the score_report.py program to turn per-submission json
      files into (more human-readable) .txt files.

   7. if, in addition to score summaries, you want a pdf (e.g. so
      that you can provide detailed feedback on the submitted
      code), you can use the make_pdfs.sh script to create, for
      each submission, a pdf containing all of the named source files.

# Grading tools

## run_junit.sh

    usage: run_junit.sh [ submission-directory ...]

    assumes: ./_autos contains a JUnit test suite

    Run the (locally installed) JUnit test suite on the named submission(s)
       1. Attempt to verify that each specified directory contains sources for 
          the package in the test suite.
       2. copy all of the required materials into a temporary directory
       3. compile the submission with the JUnit test suite modules
       4. run the test suite main module
       5. capture the json output in a file in the _output directory,
          with the same base-name as the submission, and the suffix .autos.

    If no submission-directory names are listed, this script will try to process
    every subdirectory (of the current working directory) as a submission.


## compile_and_run.sh

    usage: compile_and_run.sh [submission directory ...]

    For assignments that do not actually have an auto_grader
       1. Attempt to verify that each specified directory contains java sources.
       2. Attempt to infer the package name (assuming there is only one)
       3. compile the sources
       4. if any main classes are found, run them (unless they appear in DO_NOT_RUN)
       5. capture output in a file called OUTPUT in the submission directory
       6. create a (per-student) _ouptput/SID.autos file w/the build results


## merge_scores.py

    usage: python merge_scores.py [-t suite-description] [-f] [raw-JUnit-output-file ...]

    assumes: ./assignment.json (or template specifed with -t option) contains a 
             test suite description

    Merge the most recent JUnit test results into complete grading files.

    It is common for assignment rubric (from a suite-description file) to contain many
    more items (e.g. style and process) than are covered by the automated test suite.
    A complete set of scores for a submission are stored (in the _output directory)
    in files with the .json suffix.

    If such a file does not yet exist, a new one will be created (based on the default
    or supplied suite-description).  Then the scores from the specified raw JUnit results
    will be merged into that complete set of scores.  Any results not included in the
    raw JUnit results will be left unchanged.

    You are free to edit these submission.json files to update scores not assessed
    by the automated test suite.  Your changes will be preserved across all subsequent
    merges.

    If you expect most students to earn full credit on the rubric items not assessed by
    the automated test suite, you can specify the -f (fullcredit) option.  If a new
    .json file is created, any scores not coming from the raw JUnit results will be
    automatically set to full-credit.  To warn you that this has taken place, each
    such score will also have a comment that a default value has been assumed, and
    that the score must be reviewed.


## csv2json.py

   For non-auto-graded submissions, this tool lets you accumulate scores in a
   spreadsheet (one row per student, one column per rubric item), and then
   creates <SID>.json files that can be processed by the standard means below.

   usage: python csv2json.py [--template=assignment.json] [--roster=classroom.json]
	
	print (to stdout) a new CSV file with a column for every test in the 
	template and a row for every student in the roster.

   usage: python csv2json.py [--template=assignment.json] scores.csv ...

   	create, for each student in each CSV files a <SID>.json file
	that includes all of the scores listed in the CSV files

## score_report.py

    usage: python score_report.py [submission.json ...]

    assumes: ../class.json describes all students with submissions

    Convert the detailed test and score results (in a submission.json file)
    into a more human-readable .txt description.

    For each specified .json file, the program will create a file 
    (in the _output directory) with a name of the form
    <lab>-<submitter>.txt, where the <submitter> name is taken from 
    the base name of the .json score file, and the <lab> name is
    taken from the assignment attribute in that file.

    The program will look up the submitter name (from the base-name of the .json file)
    in the student roster in order to get student name and e-mail address, to put at the
    top of the page.

    It will then list each rubric item, along with the earned and possible scores, and
    end with a total points earned.


## make_pdfs.sh

    usage: make_pdfs.sh source-file ... [submission-directory ...]

    For each submission-directory, find the enumerated source-files,
    and create a .pdf listing of those files (in the _output) 
    directory with a name of the form submission.pdf.

## configuration files

   Many of these scripts require an assignment.json (as for the autograder)
   file that describes all of the rubric items in this assignment.

   A few of these scripts also require a classroom.json describing, for
   each student-ID, a full-name and e-mail address.
