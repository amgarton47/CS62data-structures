# Lab 05: Command Line Tools

Today, we will be learning about several command lines tools. The command line used to be the primary way users interacted with their computers before the advent of graphical user interfaces. Many advanced computer users still use the command line because it provides a more powerful and (once mastered) faster way of interacting with your computer.

Today we will be learning about the basic commands:

```
cd         ls
mv         cp
rm         rmdir
mkdir      man
pwd        touch
ssh        scp
```

We will also learn how to redirect output and some useful commands like:

```
less
wc
grep
xargs
```

Finally, we will be learning the basics of text editing with `vim`, and how to compile and run Java code using `javac` and `java`.

## Set up

We will be doing all of our work on `little.cs.pomona.edu`, which is one of the department's common servers. Because the server isn't hooked up to a physical monitor, we will be forced to use the command line to interact with it. (Technically this is a bit of a lie...) Start by opening up the terminal and connecting to `little.cs.pomona.edu` using the following command:

```
ssh yourusername@little.cs.pomona.edu
```

You will need to be on the Pomona network or use a VPN. If you are connecting from a lab machine, you can leave off the `yourusername@` part, because `ssh` will default to your current local username. You will have to type your password, and then you will have a terminal that is running on the remote machine. Because your home directory (`/home/yourusername/`, which can be abbreviated as ~) is actually mounted remotely from a common file server, the files in your home directory on `little.cs.pomona.edu` are the same as those on the lab machines. You can see this if you run the command:

```
touch ~/Desktop/hello.txt
```

This will create a file called `hello.txt` in your home directory's Desktop folder which should appear on the lab machines desktop within a few seconds. This is a small demonstration of how your files are shared among the differnt machines. For this lab, we will be working exclusively through the terminal to gain experience. To delete this file, you can type `rm ~/Desktop/hello.txt`.

The very first command to mention is called `man`, which stands for 'man'ual. It provides information about most other command line programs. Try typing `man cp` to see the manual page for the `cp` program. This will show you how the command should be used (in the "SYNOPSIS" section) and list all of the different possible arguments along with what they do. Throughout the lab if you want to further understand what a command does or how it works, try using `man`. For now quickly run `man` on each command in the list above to briefly see what each is useful for.

Notice that at the bottom of the manual display it says "press h for help or q to quit". The command `man` looks up a manual page, but then uses another command called `less` to display it. `less` is nice because it allows you to search for things: just type '/' followed by the text you want to search for. In `man cp`, try typing `/-r`. You can view any file using `less` by typing `less <filename>`.

Use the `cd` command, ('c'hange 'd'irectory) to navigate into your Desktop folder, i.e., type `cd ~/Desktop`. You can check which directory you are in by typing `pwd`. Now, normally, we would setup a git project in Eclipse, but we won't be using Eclipse today. Instead we can create our git repository by first clicking the "Clone or download" button near the top right of your github lab05 project page and copying the url. Now in the terminal create a directory to store your project by typing the following:

```
mkdir cs62-labs
```

We can navigate into this new directory by typing `cd cs62-labs`. Now type `git clone ` and without hitting enter then right click and select paste. This will create a copy of the git repository within a new folder called `lab05-yourusername`. Move into this new directory to start the lab.

## Sorting files

To help you get familiar with some basic shell commands, the starter files include some simple tasks. The first is to separate out some image and text files. Go ahead and `cd` into the `images-and-text/` directory.

Now is a good time to tell you that you can use the "Tab" key to auto-complete the names of files and directories when typing in a shell. So you can type `cd lab05/im<Tab>` instead of `cd images and text`. This both saves you keystrokes and ensures that you don't accidentally misspell a filename. If you just hit "Tab" several times without typing anything, the shell should list out all the possibilities. You can use this when navigating with `cd` to go deep into a directory structure very quickly.

Now that we are in the `images-and-text/` directroy, use `ls` to 'l'i's't the directory contents. As you can see, there are a mix of `.jpg` and `.txt` files in this directory.

Some shell commands can have their behavior altered by *flags* or *switches*. Try typing the command `ls -l`. The `-l` portion of the command is the *flag*. As you can see the new command prints different information. It shows file size, permissions, who owns the file, and creation time. Quickly glance through `man ls` to find out about other functionality of `ls` (e.g., `ls -a`).

Other arguments are just listed out in order, and they tell the program what to do or operate on. In the case of `cp`, the 'c'o'p'y command, the first argument(s) are the thing(s) to copy, while the last argument is the directory to put them in (or the new name to copy a single source file as). The `-r` flag tells `cp` to copy directories recursively (everything in the current directory and all subdirectories, and all subsubdirectories, etc.) instead of just copying individual files in the directory.

Your first task is to sort these into an `images/` directory and a `text/` directory. Start by creating these directories using the `mkdir` command. Now use the `mv` command to move the `.txt` files into the `text/` directory and the `.jpg` files into the `images/` directory.

Moving each file individually is a bit of a chore. Instead, we can ask the shell to handle all files whose names match a pattern. The asterik `*` character in a shell argument is called a wildcard and will match any number of characters. So typing `mv *.txt text/` should move all of the `.txt` files into the `text/` directory.  Try using a second wildcard command to move all the `.jpg` files into the `images/` directory.

## Very Brief `git` Basics

Normally, as you edit code during a lab, we ask you to select commit and push to send changes to the github repository. In this lab, there is no code or Eclipse project, so we will have to send our changes using the command line.

Try typing `git status`. You should see a description of which files have changed since the last commit. Because you have not made any commits, this will be since the repository was cloned. Because `git` is unaware of the `mv` command, from `git`'s point of view several files have been deleted and several new ones have been added. Type `git add images` and `git add text` to tell `git` that you wish to add all of the files inside these two folders to the next commit.

At this point, if we were to just type `git commit`, the commit we would create would only be the creation of the new files and our repository would have two sets of files: the sorted set and the unsorted set. We will instead create a new commit by typing `git commit -a`. This command creates a new commit and the `-a` flag tells git to include changes to files already in the repository, i.e., to pick up the removal of the image and text files. The `git commit -a` command will bring up a text editor to enter a commit message. After typing a quick explanation of the commit, quit the editor and the commit will be created.

If you refresh your github project in a web browser, you might notice that the files are still unsorted. This is because a commit is only local to your machine. The best way to think about `git` is in three parts. There is the remote `git` repository (e.g., your github project), the local `git` repository, and the local files. As you edit the local files, you create commits by first using `git add` to add files and then `git commit` to create your commit. This commit updates only your local repository. To notify the remote repository, we need to notify them of our changes. Type `git push` to do so.

In environments where more than one user is working on the same repository, the remote repository may have newer versions of the files than what you have edited. When this happens, a `git push` command will return a non-fastforward error. This means we need to first request the changes from the remote repository using a `git pull` command. If users have all edited different files or even different regions of the same files, an automatic merge will happen and you will then be able to run the `git push` command. On the other hand, if multiple users make conflicting changes, you will encounter a merge conflict you need to resolve before you can push your changes.

## Counting Files

How many text files are there? We could count by hand, but instead we will use another shell trick to find out. There is a handy command called `wc`, which counts words (and/or lines or characters). Notice that if you run just `wc` nothing happens. In fact, it cuts off your terminal! Try typing `ls`! Luckily, there's a universal shortcut for interrupting the current process: hit `control-C` and the rogue `wc` process will be killed, giving us back our normal prompt.

What happened? Well, if we look at `man wc` we can see that if no FILE is given, it will read standard input. This means that when we ran `wc` it was expecting us to type in its input. That's all fine and good, but how do we tell it that we're done? Well, the terminal has another shortcut for that: `contorl-D` will tell a program that you are done giving input (be careful: if you hit `control-D` in the outer shell, it will quit the `ssh` session, and you will have to log in again). So we can type `wc` and then `a<enter>b<enter>c<enter><control-D>` and it should print out `3 3 6` which is the line-count, word-count, and character-count of the input. Notice from `man wc`, the command `wc -l` will just count lines.

But how can we get `wc` to count files? It can read what we type or something from a file, but we want it to read the output from the `ls` command. In the shell, there are two nices ways to do this. First, we can redirect output into a file using the `>` operator. So we could say `ls > list` to write the output of the `ls` command to the file `list` and then `wc -l list` will count the number of lines in the `list` file. However, this would leave around an extra `list` file everytime we did it. Instead we can redirect the output of `ls` to be the input to `wc` in one line using the pipe `|` operator. So to count how many text files there are, assuming we have moved them all into `text/`, we can do:

```
ls text | wc -l
```

This should print out "10". Note in this case that we did not give `wc` any arguments (besides the flag), so it works in "read from standard input mode". However, because of the `|`, the standard input came from the output of the `ls` command, instead of waiting for us to type. This pattern of redirection is quite useful, and it can be repeated multiple times, with several programs feeding their output to each other.

## Searching for Patterns

Now that we have sorted our text and image files, let's move over to the programs directory. It will be helpful to know that `..` refers to the parent directory of where we are currently located. So `cd ../programs` should move you from the `images-and-text` directory to the `programs` directory. In this directory, there are a few random Java programs that were downloaded from Pastebin. However, the directory is messy: it also has some `.class` files in it from compiling the `.java` files.

We want to find out which files contain drawing code. To do this, we will use a command called `grep`, which searches for patterns in its input. To search for the pattern "draw" we can use:

```
grep draw *.java
```

Recall that `*.java` matches all files in the current directory that end in `.java`. The entry for `man grep` tells us that it expects a "PATTERN" followed by one or more "FILEs". So this command will search all of the matching files for the pattern "draw". By default it prints out each line that matches, but in this case, we only want to know which files contain that pattern, not how many times or what their content is. Luckily `grep` has a `-l` flag which does exactly that: 'l'ists files that match. So we can use:

```
grep -l draw *.java
```

Notice that `grep` does not care about the order of the `-l` flag: you can put it first, second, or third and the other arguments will be interpreted the same way.

# Filtering Files

Now that we know which files contain some kind of drawing code, let's move them all to a new directory. Create a new directory called `draws/` using `mkdir`. Now all we need to do is issue a `mv` command targeting all of our `grep -l` files and put them into this directory. Unfortunately, our `grep -l` command produces output (which we could move around with a `|` or `>`) but what we want those things to be are arguments to the `mv` command, not input to the command. We could output `grep -l` to a file with `>`, then edit the file to have `mv` at the very start and `draws/` at the very end, but there is an easier way: a command called `xargs` exists just for this purpose.

As `man xargs` says, `xargs` is designed to take input and turn it into arguments. Normally, these arguments are passed to the target program after any default arguments, but in our case, we want the variable arguments to come before the destination argument of `mv` so we need to use `xargs -i` to tell it where to put the variable arguments. So we will construct a command that does the following:

* Use `grep -l draw *.java` to generate a list of files.
* Pipe that output into `xargs -iZZ`.
* Give `xargs` additional arguments to tell it to execute the `mv` command with our variable arguments followed by `draws/`. Use the `ZZ` pattern that we gave to the `-i` command to tell `xargs` where to put the additional arguments.
* The command at the end should look like `grep -l draw *.java | xargs -iZZ mv ZZ draws`.

If you execute the command properly, you should be able to move all of the `.java` files that contain the text "draw" into the `draws/` directory. Note that there is another way to do this: in the shell the backtick \` character can be used to do command substitution, where the output of a command is used as part of another command. With this method, we can write our `mv` command and just use `` `grep -l draw *.java` `` where we want extra arguments to appear.

Try creating another commit and pushing it to your github repository. If you run into any trouble let us know.

## `javac`

Since we have these Java programs sitting here, let's see if we can get any to run. First, let's create a new directory `has_main/` and put all of the java files which declare a `public void main` method into that directory. We can try to use the same technique as we did with "draw", with one caveat: we want to search for the pattern `public static void main`, but that mattern has spaces in it. Spaces are also used to separate arguments, but the pattern is only a single argument. If we just say:

```
grep -l public static void main *.java
```

the first three lines will look like:

```
grep: static: No such file or directory  
grep: void: No such file or directory  
grep: main: No such file or directory  
```

To tell `grep` that we really want one big argument including spaces, we will use double quotes, like this:

```
grep -l "public static void main" *.java
```

Use this command with the pattern above to isolate the `.java` files which have main methods and move them into a `has_main/` directory.

Now let's try to compile them. Normally this is a process that Eclipse handles for us. So this process may help shed some light on some errors you have run into. To compile Java files, you use the command `javac` which produces `.class` files as output. Let's start by trying to compile all of the `.java` files:

```
javac *.java
```

Unfortunately, one of our `.java` files has an error in it (well, they were randomly downloaded from Pastebin). Let's skip that file for now by just changing its name (Using `mv` to have an extra 'a' in the `.java` part so that `*.java` won't pick it up). Now the `javac` command above will work. Note that you can query the return value of a command by running `echo $?`. A value of zero indicates success, any other value indicates some type of failure.

## `java`

To run a Java program, we unsurprisingly use the command `java`. However, `java` is a bit quirky: we need to tell it the name of a class to run, not the name of a `.class` file. Luckily in Java the name of a `.class` file should always be the name of the class it defines. Unluckily, Java requires that classes which are in packages be in a folder with the name of that package. To see which classes have packages, use `grep` to print out lines which contain the pattern `package` in `.java` files in the current directory.

## The Classpath

When compiling or running Java code, you often need to tell it where to find external code that your code depends on. For example, if you code depends on the `structure5` library, you need to tell it where to find that code (at least the `.class` files). Both `javac` and `java` commands require this information whenever you use `import` to import something that is not part of the Java standard library. You can give Java a list of places to look by using the `--classpath` flag (short version: `-cp`) followed by a colon-separated list of directories and/or `.jar` files to search. So if we want to import `structure5` along with other `.java` files in the `src/` directory, we'd give `javac`:

```
-cp /common/cs/cs062/bailey.jar:src/
```

If we also want to use JUnit, we'd write:

```
-cp /common/cs/cs062/bailey.jar:src/:/common/cs/cs062/junit.jar
```

As a final convenience, you can separate the `.class` files that `javac` produces from their `.java` files by giving the `-d` flag followed by a directory to put output in. Using `-d bin` as an argument along with `-cp` we can exactly duplicate what Eclipse does when we tell it to run our project. First, we'll run `javac` with the `-cp` and `-d` arguments, and then we'll run `java` with just the `-cp` argument. If all goes well, you should be able to run any Java program that does not require some kind of graphics, which are not available while `ssh`-ed into another machine.

Again create a commit and push it to the github repository.

## Editing Text

Now that you can move files around and even compile and run Java code from the terminal, what if you want to edit a file? As mentioned above, you can use `less` to view the contents of a file and even search within it, but it does not allow you to make changes.

To edit files on the command line, there are several options: `nano`, `vim`, `emacs`, and many more. For this lab, we will be learning a tiny bit of how `vim`, a very powerful and efficient text editor works.

## `vim` Basics 

To open a file with `vim`, type `vim <filename>`. In this case, `cd` into the `edit` directory from the starter materials and type `vim ed<TAB>`. `vim` can be a little unfriendly when you first encounter it. It's not obvious how to even quit the program. To quit type `:` followed by `q` and then press the enter key. This works because `:` in `vim` initiates a command, and `q` is the quit command.

`vim` is a modal editor, which means that while using it you switch between different modes. Upon opening the program you will be in the normal mode where any typing executes commands. To edit the file, you can switch into insert mode by pressing `i`. When you are done typing, you can press `ESC` to return back to the normal mode. Although this is confusing at first, the normal mode will allow us to use commands which result in more efficient production of text. In particular, `vim` has a set of commands for moving the cursor with no need for the mouse.

In the `edit-me.txt` file, you'll see a line that says "Delete me". While in normal mode, move to this line by using the `/` command, which starts a search. Type `/elete<enter>` and the cursor should move to the first 'e' of the word "Delete". Now you can type `dd` to delete the current line. At any point, if you want to undo a command, just type `u` (you can also redo a command with `<control>-R`).

Next, move down to the line that says "Add a line after this", either with a `/` command, or by moving the cursor manually using the arrows keys or the `h`/`j`/`k`/`l` keys (`hjkl` is chosen so that one can go from typing in insert mode rapidly to movement without having to move your right hand from the home row). Use the `o` command to start a new line immediately below the current one and move into insert mode. Type something in and hit `ESC` to switch back to normal mode.
Next go to the line that asks you to add to its end. Here, use the `A` command (shift-a) to jump to the end of the line and switch to insert mode (while `i` and `a` insert and append, while `I` and `A` do so at the beginning or end of the current line instead of at the cursor). Again type something and press `ESC`.

Finally, let's replace all of those targets with some other text. To do this, start by using a `/` command to find the first target. Then notice that you can use the `n` and `N` commands to move forward and backwards among the matches. Now, we'll use the `c` command (for 'c'orrect). The `c` command is special, because it wants to be told what to correct. After `c` you can enter any `vim` movement command to correct text from the cursor to the end of the movement. So `cl` would correct the character under the current cursor, while `cj` would correct text an entire line of text. We'll use a special movement command `iw` that means "the current word". So out whole command will be `ciw`, after which we can type some replacement text and then hit `ESC`.

Conveniently, this `ciw` command, up to the point we hit `ESC` counts as a single command and `vim` has a shortcut for repeating the last non-movement command: the `.` command. This `.` command makes `vim`'s other commands much more useful because it's to repeat them.

## Saving Your Work

One other important command in `vim` is `:w`, which writes out (saves) the current file. Once you're done editing `edit-me.txt`, you can save it and quit it with `:wq` which both saves and quits.

## What to Hand In

Fill in the `answers.txt` file with your username and the answers to the questions in that file. Make sure you put your answers exactly where the example answers are. It may be useful to know that the `D` command deletes text from the cursor to the end of the current line.

Edit the `lab05.json` file as usual.

Create a final commit with your `answers.txt` file and the `edit-me.txt` file. Do not forget to run a successful `git push` so that we will be able to see your changes on github.

