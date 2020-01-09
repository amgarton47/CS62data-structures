# Introduction to CLI, Eclipse, and Java

## Learning Goals

* Introduction to the shell command line interface.
* Introduction to Eclipse.
* Introduction to Github and assignment repos.
* Building and running simple Java programs.
* Project submission (committing changes and pushing them back to github.

## Key Terms and Concepts
* `CLI` - [Command Line Interface](https://en.wikipedia.org/wiki/Command-line_interface): running 
   programs by typing commands and arguments to a command intepreter like 
   the Linux/OSX [shell](https://en.wikipedia.org/wiki/Unix_shell).
* `Eclipse` - an [Integrated Development Environment](https://en.wikipedia.org/wiki/Integrated_development_environment) 
   that includes powerful tools for editing, running, and debugging programs. In this course we will be using it as a Java IDE.
* `Git` - a distributed [version control system](https://en.wikipedia.org/wiki/Version_control) 
   for tracking, coordinating, and exchanging file updates among groups of collaborating developers.
* `Github` - a web-based version control and collaboration platform for software
   developers, particularly popular for open-source projects and other shared
   code efforts.

## Overview
The goals  of this assignment are to:
   1. learn to run a few basic commands in a Linux/OSX terminal window.
   2. bring up Eclipse as an Integrated Development Environment.
   3. establish (if you do not already have one) a free personal
      [github account](https://help.github.com/en/articles/signing-up-for-a-new-github-account).
   4. import a cloned of the master-copy for this project into Eclipse.
   5. fill in the missing code in a simple Java program.
   6. submit your work by committing and pushing it back to github.

## Classes

In this project you will be making simple changes to complete a program
that is comprised of two classes:

### `Token`
A `Token` is a virtual chip with a (randomly chosen) `color` and `value`.
   - It supports methods to `get` and `set` both the `color` and `value`.
   - It supports a `toString` method to enable it to be printed.
   - It also includes a `main` method that can be used (if this
     class is run as an application) to exercise this implementation.

### `Bag`
A `Bag` is a set of `Token`s, with several methods (all of whose implementations
require the enumeration of the `Token`s in the set).  It supports several
methods:
   - `firstChip` ... print out the first `Token` in the `Bag`.
   - `allChips` ... print out all of the `Tokens` in the `Bag`.
   - `allChipsWhile` ... the same as `allChips`, but to be implemented as a while loop.
   - `addChips` ... the sum of the values of all `Tokens` in the `Bag`.
   - `chipHighValue` ... the number of high-value `Tokens` in the `Bag`.
   - `firstGreen` ... the index of the first `green` `Token` in the `Bag`.
   - This class also has a `main` method that can be used to exercise
     the functionality of the other methods.

As provided, several of these methods are incomplete (and have **// TODO** comments explaining
what needs to be done).  You are to fill in the missing code (mostly a few simple
assignments and loops) to complete all of the methods so that they work correctly.

## Getting Started

1. Click the terminal window icon on the Desktop menu bar.  A new window
   with a shell prompt will appear.  Create a new workspace directory
   on your desktop, by typing the following commands:
```
cd Desktop
mkdir cs062
chmod 700 cs062
cd cs062
mkdir workspace
```
   The effect of these commands are:
   * Create a new directory for this course on your Desktop 
     (Though you might prefer to create it within your Documents folder).
   * Change the protection on this folder so that *only you* are able to access it.
   * Within the new `cs062` folder, create a `workspace` folder, where
     Eclipse will store all of the information associated with your projects.

   Note that you could have achieved much of this by clicking through menus,
   but it is valuable for you to learn to work directly with the shell:
   * shell scripting is a powerful and commonly used programming language.
   * the commands available from the shell are much more powerful than those
     available with the *Finder* or other file access Graphical User Interfaces.

   For more information on the advantages and design of Command Line Interfaces,
   please see this paper on [CLI design](http://htmlpreview.github.io/?https://github.com/markkampe/Big-Software/blob/master/supp/cli.html).

2. If you do not already have a github account, go to github.com and
   [register a new personal user account](https://help.github.com/en/articles/signing-up-for-a-new-github-account).

3. You will receive an email invitation to join each lab.  Follow that
   URL and you should see a message like:

![Accept Invitation](http://cs.pomona.edu/classes/cs62/boilerplate/assignment_invitation.png)
   
   Once you accept the invitation, you will be provided with a unique URL for
   your (private) version of this project.  Click it.

   ![Accepted Invitation](http://cs.pomona.edu/classes/cs62/boilerplate/accepted_invitation.png "Invitation Accepted")

   You will see a unique copy of your lab repository. Click on the green `Clone or download` button and copy the returned
   HTTPS URL:

   ![HTTPS_Clone](http://cs.pomona.edu/classes/cs62/boilerplate/https_clone.png "HTTPS Clone")

4. Start Eclipse on your local machine.  It will ask you to choose
   a workspace.   You should browse to the workspace folder that you 
   created above.  Eclipse will remember this selection
   and prompt it as a default (or in the list of Recent Workspaces)
   when you start Eclipse in the future.

5. Clone your new github repo onto the machine where you are working.

   ![Git View](http://cs.pomona.edu/classes/cs62/boilerplate/git_view.png "Git View")

   Click on `Clone a Git repository ` to see the following window appear

   ![Source Git](http://cs.pomona.edu/classes/cs62/boilerplate/source_git.png "Source Git")

   Paste the URL you copied from Github. It will automatically populate the fields. 
   Add your Github username and password. 
   Make sure to check `Store in Secure Store` unless you want to re-enter your 
   credentials every time you interact with the repository. 
   Feel free to add a password hint if you consider it necessary, once prompted. 
   (**Note:** If you have two-factor authentication enabled for your Github account, 
   you will need to disable it prior to entering your credentials here)

   ![Populate Source Git](http://cs.pomona.edu/classes/cs62/boilerplate/populate_source_git.png "Populate Source Git")

   Follow the Wizard by clicking `Next`. 
   ***Make sure you choose your newly-created workspace as the destination before 
   clickinging `Finish`.***

   In the `Projects` section, check the box that is entitled
   `Import all existing Eclipse projects after clone finishes`.

   You will now see the repository appearing in your Git repositories.

   ![Destination](http://cs.pomona.edu/classes/cs62/boilerplate/destination.png "Destination")

6. Edit the `Token.java` and `Bag.java` files to add the missing code (which
   is indicated by **// TODO** comments).  If you are not yet sure how to code
   a particular type of statement (e.g. a Java `for` loop), Google for examples
   or ask the instructor for assistance.

   If your program contains any obvious syntax errors, Eclipse will give you
   red warning indications on the affected lines of code.  If the errors are
   distributed over multiple modules you can see all of them by selecting the
   `Problems` tab in the bottom part of the Eclipse window.
   
   If you want to try compiling and running one of your programs:

      - select (in the *Package Explorer* on the left) the module you want to
        compile and run.
      - select the `Run` item from the top menu bar.
      - the output from your program should appear in the `Console` tab
        of the bottom part of the Eclipse window.

   You should fill in the missing code in `Token.java` first because
   `Bag.java` depends on `Token.java`.  When you start building `Token.java`
   you will be warned that there are still errors in the project (because
   you have not yet fixed `Bag.java`).  You can safely ignore this warning.

## Submitting your work

8. *Commit* your changes and *Push* them back to Github.
   

   Make sure you edit the `.json` file you are given with every lab/assignment to include 
   your username, your partner's username (if collaboration is allowed), and indicate 
   if you did any extra credit work.

   Right click on the repository and then click `Commit`.

   ![Commit](http://cs.pomona.edu/classes/cs62/boilerplate/commit.png "Commit")

   Transfer all your files from `Unstaged Changes` to `Staged Changes`. 
   If you don't see options for `Unstaged Changes` and `Staged Changes`, 
   click `Open Git Staging View` in the bottom left-hand corner or the 
   `Git Staging` tab in the bottom window.

   You should see a list of *Unstaged Changes* 
   (changes you have made but not yet *committed*).  
   If you highlight one (or more) of those files you can
   add a commit message (describing the changes you have made) and then
   click the `Commit` button.  
   You can make as many *commits* as you like.
   It is common to do different *commits* for different sets of changes.
   Provide a meaningful description of each commit.

   These changes will only be on your local machine until you do a *Push* back
   to the [origin](https://www.git-tower.com/learn/git/glossary/origin) on github.


   ![Push](http://cs.pomona.edu/classes/cs62/boilerplate/push.png "Push")

   When you click the `Push` (or `Commit and Push`) button, all committed
   changes will be pushed back to Github ... at which they will be saved
   and visible to us.  We will notice and grade your last on-time *commit*.

   If you want to confirm that we can see your work, go to the URL that you were given 
   on github. You should see the latest commit you pushed.


   Submitting correctly is your responsibility, and if you forget to submit or 
   submit unsuccessfully without following up, everyone will be unhappy. 

   **Important:** The last commit you push by the due date is the one we will look at and grade.


## Helpful Considerations

* Saving your work - Make sure to commit and push your work to GitHub MULTIPLE TIMES throughout the process! Not only does this help us see your unique progress, but it ensures that you have frequent backups of your work.
**AVOID** one big push at the end of your work.   
We want to be able to see the progressive development process that you followed.

## Grading
Your submission (in your own personal github repo) will be graded based on the following criteria:

| Criterion                                         | Points |
| :------------------------------------------------ | :----- |
| programs compile with no errors                   | 1      |
| all Token methods work correctly                  | 3      |
| all Bag methods work correctly                    | 3      |
| submitted correctly                               | 2      |
| [Style and formatting*](https://github.com/pomonacs622019fa/Handouts/blob/master/style_guide.md)                               | 1      |
| **Total**                                         | **10** |

\* Code Style and Formatting refers to the correct use of Java constructs including booleans, loop constructs, etc. Think of it as good writing style for programs.

NOTE: Code that does not compile will not be accepted! Make sure that your code compiles and runs before submitting it.

You must comment your code. We will be using the JavaDoc commenting style. To be compliant with JavaDoc, you must have the following:

   Each comment on a method or class should start with `/**` and end with `**/`. 
   Every line in between should start with a `*` and be appropriately indented. 
   (Comments on variables and constants do NOT have to use this style unless they are public.)

   A comment describing the class right before the class declaration 
   (i.e. after the `import` statements). This comment should include the `@author` tag 
   after the class description, and the `@version` tag after the author tag.

   A comment for each method describing what the method does. 
   This comment should describe the what but not the how.
   `@param`, `@return` and `@throws` tags for each method (when appropriate)
   `pre-` and `post`- conditions as appropriate

Double-check that your work is indeed pushed in Github! It is your responsibility to ensure that you do so before the deadline.
