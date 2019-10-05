Grading Workflow
================

## Installation

> These installation steps need only be done once. Updates will be push through
> `git` throughout the semester.

To install the autograder, please log on to one of the machines in Edmunds and
open a Terminal window.

Execute the following command to retrieve the latest copy of the framework:
```bash
$ cd $HOME/Desktop
$ git clone $(whoami)@project2.cs.pomona.edu:/common/cs/cs062/admin/autograder/ag.git
$ $HOME/Desktop/ag/grader/setup  # execute the setup script
```

This will place a directory on your `Desktop` labeled `ag`. Please **do NOT move
this directory.** It will also add an alias to your `.bash_profile` called
`gradeit`. (Sorry if you already have this alias! Just open your `.bash_profile`
and change the last line!)

## Workflow

1. Make a working directory. For each time you grade, you should make a
   new working directory.

   > Today we will be grading SilverDollar. Since it is the first assignment I
   > will make a directory `assign01` on my `Desktop` -- although the location
   > and name is not important, you just need to start with a fresh directory.

2. Copy a `Suite` folder from the `dropbox` located at
   `/common/cs/cs062/admin/autograder/suites` to somewhere of your choosing
   within your account. In this folder, you should find a folder per assignment.
   Copy the inner folder that pertains to your assignment not the whole directory.

3. Now, into the relevant suite folder you copied, copy the student submissions
   you are to grade and rename the submission to simply be the DCI account name.

   > In the future this will be automatic, but manifest files have not been
   > introduced into the assignments yet.

4. Run the grader. Simply open a Terminal window, `cd` into your suite directory
   and run the following command:

   ```
   $ gradeit
   ```

   > For you grading convenience, the class has been broken into sections.
   > When you run `gradeit`, pass it a `--class <section file>` to limit the
   > scope of your grading.
   >
   > If I'm grading section one, I will execute:
   > ```
   > $ gradeit --class /common/cs/cs062/admin/roster/class.json
   >```

5. Open [http://localhost:5000](http://localhost:5000) to access the grading
   interface.

6. Finishing. When you are done, close the server with `ctrl-c`.
