# CS62_projects

This is a private repo containing projects from CS62 and the tools used to manage and grade them.

Structure

  * *labname* ... contains all parts of an assignment
     - project ... eclipse project(s) to be cloned by students
        - README.md ... front page for the lab/assignment
        - src/*package* ... directory containing starter files
     - test_suite ... auto-grader test suites
     - solution ... a full-score solution (for reference and grader testing)
  * tools ... scripts/programs to put-up assignments, download submissions, grade and post results
  * autograder ... a grading GUI used between 2015 and 2019

## How to deploy a lab/assignment

1. Make any relevant changes to the core projects: https://github.com/PomonaCS/CS62_projects/

2. Create a new repository in the semester specific github group (e.g., https://github.com/organizations/pomonacs622020fa).  The repository should be private and should be empty (no README).

3. Run tools/create_project.sh. For example:
      
      tools/create_project.sh Timing-ArrayList pomonacs622020fa/lab3

4. Create a new assignment in github classroom.  It should be private and select starter code for how to import. For example:

      pomonacs622020sp/lab3

5. Share invitation URL with students.

## How to Grade

- Update classroom.json at the beginning of the semester. 
- Use classroom assistant to download student repos. If possible clean unnecessary files before you upload them to little /common/cs/cs062/admin/submissions/assignmentX

- always git pull CS62_projects on little before you start grading

      cd submissions/assignmentX
      #make sure that classroom.json is within submissions/

      cp -r /common/cs/cs062/admin/CS62_projects/assignmentX/test_suite/* .
      #e.g., cp -r /common/cs/cs062/admin/CS62_projects/Introduction/test_suite/* .

      # run the main method
      /common/cs/cs062/admin/CS62_projects/tools/compile_and_run.sh

      # run junits
      /common/cs/cs062/admin/CS62_projects/tools/run_junit.sh | tee all_submissions.txt

      # merge main output plus junits
      python /common/cs/cs062/admin/CS62_projects/tools/merge_scores.py -v _output/*.autos
      
      # create a csv for manual grading of all assignments. Skip step and edit output directly if you want to edit them individually
      python /common/cs/cs062/admin/CS62_projects/tools/json2csv.py > assignment.csv 
      open assignment.csv
      # merge csv and prexisting output
      cd _output


      python /common/cs/cs062/admin/CS62_projects/tools/merge_scores.py -v -t ../assignment.json ../assignment.csv
      cd ..

      # edit the report files in _output/
      python /common/cs/cs062/admin/CS62_projects/tools/score_report.py -v _output/*.json

      # create pdfs that contain main method output + report + source files
      /common/cs/cs062/admin/CS62_projects/tools/make_pdfs.sh -lab/assignment number OUTPUT REPORT file1.java file2.java file3.java
      # e.g., /common/cs/cs062/admin/CS62_projects/tools/make_pdfs.sh -lab 0 OUTPUT REPORT Token.java Bag.java


## Migrating an Autograder.java from Junit4 to Junit5

Imports are totally different.  You probably want (at least)

      import static org.junit.jupiter.api.Assertions.*;
      import org.junit.jupiter.api.BeforeEach;
      import org.junit.jupiter.api.Test;

      @Before	-> @BeforeEach
      @After	-> @AfterEach
      @BeforeClass -> @BeforeAll
      @AfterClass  -> @AfterAll

The old assert*(message, other parms) are now
   assert*(other parms ..., message)

Changes to boilerplate

    junitmods.JSONListener implements the new listener entry points

    junitmods.JUnitJSONObject interprets the new TestExecutionResult

    <package>.PomonaRunner is very different, due to the new Discovery
    	and Launcher APIs, but the only per-project changes are, as
	before, the package and test-class names.

Changes to dependencies

    remove the junit jar, as this is now expected to be taken from
    the classpath.  By default, the run_junit and compile_and_run
    scripts try to use your Eclipse class path, if you include a
    CLASSPATH file (in the top level grading directory) that will
    be used instead.
