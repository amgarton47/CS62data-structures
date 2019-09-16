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
