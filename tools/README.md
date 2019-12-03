# Background

A few years ago, TA's created a set of JUnit autograding test cases 
and tools to manage the grading.  The general model was:

    * the grading of an assignment is described by an "assignment.json"
      file that enumerates test cases and points for each.

    * the autograding is done by an (assignment specific) Autograder.java
      containing JUnit test cases.

    * each submission was copied into a temporary working directory,
      compiled (with JUnit and other libraries), and run under a
      customer JUnit runner.

   * the JUnit runner produced a <student>.autos (json) file
     that listed the passes and described the failures.

   * the <student>.autos file was processed into a <student>.json
     file (very similar to the assignment.json) that added in
     the points earned for the autograder items.

   * the grader would (using a grading GUI) fill-in and comment the 
     non-autograder scores until all rubric-items were covered.

   * the tool would then produce a per-submission pdf summary of
     the results.

This process was orchestrated by a Python GUI, which (while convenient)
was complex, not-portable, and difficult to maintain.   To get around
these problems, in 2019FA, I created a set of bash scripts and Python
programs as a simpler and more portable alternative to the GUI:

   * the underlying per-assignment configuration and tools are unchanged:

      - the assignment.json description of the gradables and their values

      - the JUnit extensions and Autograder.java test suites

      - the <student>.autos auto-grader output and <student>.json
        score sheets.

   * two scripts:  run_junit.sh and compile_and_run.sh (depending
     on whether or not a JUnit test is appropriate) do a very similar
     copy, compile, run, and generate <student>.autos output.

   * a new program: json2csv.py creates a CSV spreadsheet (that can
     be editied with your favorite spreadsheet program) for all of
     the non-autograder items in the rubric.

   * a new program: merge_scores.py can be used to built a
     <student>.json file from the <student>.autos files and/or
     the CSV file.  This combination of tools is capable of
     filling in all of the scores, but it may still be necessary
     to edit the <student>.json files to update the per-item
     grading comments.

   * a new program: score_report.py, processes the <student>.json
     files into (suitable for returning to the students)
     <assgtname>-<student>.txt files.

   * a new script: make_pdfs.sh can (if desired) create per-submission
     pdfs of the (above) grading results and selected source-code
     modules for further annotation.

# Grading process

    I use a per-semeseter grading directory, which contains:
      * a classroom.json list of all students in the class
      * a per-assignment sub-directory, into which I down-load all of the
        submissions (into to per-submission sub-sub-directories)

    1. If you download an (all submissions) zip from submit.cs.pomona.edu,
       unzipping it will create the per-assignment grading sub-directory 
       and per-submission sub-sub-directories.

    2. Copy everything in the (per-assignment) test_suite directory into
       the per-assignment grading directory.  This will include (at minimum)
       an assignment.json file that lays out the rubric.

## projects covered by JUnit autograder suites

    3. run the run_junit.sh script, and see if it seems to be correctly
       processing all of the assignments.  
       
       The most common problems involve compilation errors or files that 
       students moved to a different place in the hierarchy.  I make notes
       of these (for point deductions) but then fix them so that testing
       can continue.

       If there were compliation errors, you might want to edit the
       stuff in comple_error.json into the affected .autos files.

## projects not covered by full autograder suites

    3. run the compile_and_run.sh script (which will probably require
       a copy of either a generic or per-project report.sh script.

       Again, te most common problems involve compilation errors or files 
       that students moved to a different place in the hierarchy.  I make 
       notes of these (for point deductions) but then fix them so that 
       testing can continue. 

       The resulting .auto reports will only cover whether or not
       the resulting projects built and ran.  All additional scoring
       will be done manually.

## manual grading

    4. Editing score information into a JSON file is both brittle
       (e.g. missed commas or quotes) and time consuming.  It is
       therefore recommended that the manual grading (rubric items
       not assessed by JUnit test cases) be done in a spreadsheet.

       * use the json2csv.py program to create a (csv) spreadsheet
         for all of the non-autograder items in all the submissions.

       * use your favorite spreadsheet program to assign scores for
         those items.

## final per-submission comments and reporting

    5. Use the merge_scores.py program to transfer results from the
       <student>.autos files and the non-autograder-item spreadsheet
       into <student>.json files.

    6. Edit the comments in the <student>.json files to explain
       non-autograder point deductions.

## final report generation

   7. use the score_report.py program to turn per-submission json
      files into (more human-readable) .txt files.

   8. if, in addition to score summaries, you want a pdf (e.g. so
      that you can provide detailed feedback on the submitted
      code), you can use the make_pdfs.sh script to create, for
      each submission, a pdf containing all of the named source files.

   The resulting <assignment>-<student>.txt and .pdf files should
   be suitable for returing directly to students.

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
       4. if any main classes are found, run them
       5. capture output in a file called OUTPUT in the submission directory
       6. create a (per-student) _ouptput/SID.autos file w/the build results

    Because this is a catch-all grading mechanism, its behavior can be 
    controlled by a few additional configuration files in the per-assignment
    grading sub-directory:
       * PACKAGE ... for assignments that contain multiple packages, only
         one of which should be built and tested, this file contains the
	 name of the package to be built and tested.
       * the autograde.sh script will be run on every file listed in
         grading.sh;  it was expected that this would be a tester, 
	 but I have used this script to do more interesting things
	 like unpack zip files, and then do checking and processing
	 on the files within them.


## json2csv.py

   usage: python json2csv.py

   This program examines the assignment.json description, identifies
   all of the non-autograder (manually graded) rubric items, and prints
   (to stdout) a (CSV) spreadsheet with a row for each student and a 
   column for each non-autograder item.

   The row-1 column headings contain the name of each rubric item in
   assignment.json, and so may be very wide.  These are necessary to
   enable the correct merging of spreadsheet columns back into the
   <student>.json files.   Most spreadsheet editors will allow you
   to narrow the column widths.


## merge_scores.py

    usage: python merge_scores.py [-t suite-description] [-f] input-file ...

    assumes: ./assignment.json (or template specifed with -t option) contains a 
             test suite description

    Merge the most recent JUnit test (or CSV) results into complete grading files.

    For .autos input files, it wil create (or update) a corresponding .json file 
    in the same directory as the .autos file.  For .csv input files, it wil create
    (in the current working directory) <student>.json files, where the <student>
    names are taken from column 1 of the spreadsheet.

    If the .json file does not yet exist, a new one will be created (based on the 
    assignment.json template).  Then the scores from the specified input file(s)
    will be merged into that complete set of scores.  Any results not included in the
    input files will be left unchanged.

    You are free to edit these submission.json files to update comments or scores not 
    assessed by the automated test suite.  Your changes will be preserved across all 
    subsequent merges.

    If you expect most students to earn full credit on the rubric items not assessed by
    the automated test suite, you can specify the -f (fullcredit) option.  If a new
    .json file is created, any scores not coming from the raw JUnit results will be
    automatically set to full-credit.  To warn you that this has taken place, each
    such score will also have a comment that a default value has been assumed, and
    that the score must be reviewed.


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
