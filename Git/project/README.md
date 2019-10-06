# Lab 08: Git

## Important Dates

* Release Date: April 2, 2019
* Due Date: April 3, 2019


## Introduction

`Git` is a version-control system, an essential tool in everyday software development, especially when multiple people are working on a project at once.
Accordingly, you must work with a partner on this lab (or in a group of three if you have to), as you will be learning techniques to collaborate. Additionally, you and your partner will have to work on a lab computer for this lab in order for sharing things to work properly.

## Background

`Git` is a tool for "version control." This means that it keeps track of multiple versions of files, and allows you
to switch between them freely. In general, we have two sources of file versions. First, over time, files change,
so we have historical versions. Second, when multiple people work on the same file, they make different
changes, so we have per-user versions, which may have different histories but at some point may be merged
into one version again. git keeps track of both of these kinds of versions for us, and can even automatically
merge changes in some cases!
For this lab, we will work in pairs and make diverging edits to a file, but then use `git` to
produce a combined version, all the while tracking the history of changes we have made.

##  Setting up git

`Git` is primarily a command-line tool, so we will start by opening up the terminal. Both partners should go ahead and use `cd` to get into your Eclipse workspace directory (possibly somewhere like `Documents/cs062/workspace`
depending on what you did at the beginning of the semester; if you go to `Project – Properties` in Eclipse
the `Location` item should tell you where this is). 

Now we will have one partner set up the project: leave
the terminal there and create a new Java project in Eclipse called `Lab08`. It is important that only one
partner does this step! (The other partner should follow along for a bit.)
Whoever created the `Lab08` project should add a file called `Random.java` and then go back to the terminal
and use `cd` to enter the newly created `Lab08` directory. In the terminal from within that directory, type:

```
git init
```

This command tells `git` that we want to start a new version-controlled repository in this folder. At this point, `git` may ask you to set up some basic information, like an author name. Follow the instructions it
gives to do this. Once you are done with this step, it should say something about "Initialized empty git
repository in?" Having a repository is nice, but we also need to tell `git` which files it should keep track of.

To see the state of our repository we can use:

```
git status
```

This should print something like:

```This should print out something like the following:
On branch master

Initial commit

Untracked files:
    (use "git add <file>..." to include in what will be committed)

    .classpath
    .project
    bin/
    src/

    nothing added to commit but untracked files present (use "git add" to track)
```
This status report is telling us that a `git` repository exists here, but it has not been told to keep track
of anything yet. To fix this, we will type:

```
git add .
```

Here the `.` means "the current directory" and by extension, "everything in the current directory." Afterwards, if we run `git status` again it should give us a big list of new file stuff. At this point, we have
told `git` it should keep track of those files, but we have not yet told it that it should take a snapshot of
them. We said earlier that `git` tracks versions over time, but it would not make sense for it to keep track of
every individual letter that you type, so `git` waits for you to tell it when to capture a snapshot. We do this
by using:

```
git commit -m "The message you want to remember this commit by"
```

At this point, we have saved a version of our project and our partner can now get a copy of it. We have one more step: we need to make our repository accessible to our partner. To do this, we
will create a link to the repository from our home directory, and change permissions to let anyone access it.
First, from the project directory (where you already are in the terminal), type:

```
chmod -R 775 .
```

This will set permissions on this folder and all included files to `775`, which enables anyone in the same
group as us to have full permissions (including write permissions!) to those files. This is not normally
something you would set up, but it is useful for this lab. Next, we need to make an accessible path from
our home directory all the way to the project directory. To do this, use the following commands (or similar
commands if your workspace is in a different place):

```
chmod 750 ~
chmod 750 ~/Documents
chmod 750 ~/Documents/cs062
chmod 750 ~/Documents/cs062/workspace
```
If your workspace is somewhere else, use the correct path to the `Lab08` project directory, which you can
find by typing `pwd` into the other terminal (keep that one around as we will be using it again).
At this point, your partner, on their own computer, should be able to do the following (from their Eclipse
workspace directory; `cd`  there if necessary):

```
git clone /home/partner/Documents/cs062/workspace/Lab08
```

Here, replace `partner` with your partner’s username. The `git clone` operation creates a copy of a repository, but it remembers the original source, so that you can send back updates later. At this point, both you and your partner have a copy of the same (empty) `Random.java` file, and in fact identical histories of that file (albeit with only a single entry each). In other words, `git clone` did not just copy the files,
it copied their entire histories. 

If you are curious about efficiency, rest assured that git is pretty smart: instead of storing actual copies of different versions of files, it only stores the differences between versions, which allows it to reconstruct different versions when it wants to without storing too much data.

For the remaining sections of the lab, go ahead and work separately from your partner, each person
picking one of the two tasks below and working on their own copy of the `Lab08` project in Eclipse.

Do not forget that you may have to tell Eclipse to "refresh" on occasion in order to see the changes that
`git` makes. Right after cloning the repository, you may also have to tell Eclipse that you want to open the
project. Use `Import > Existing Projects` and select `Lab08`.


## Task 1: Pseudo-random numbers

Before starting this task, use `cd` to get into the project directory, and then execute the following commands:

```
git branch nextint
git checkout nextint
```

This will create a new "branch" for working on this feature, so that our changes won't interfere with our
partner. The second command switches over to the new branch.

For our `Random.java` file, we would like to implement a dead-simple pseudo-random number generator.
We will need a constructor, which will store an integer instance variable that is passed in as a parameter,
and a `nextInt` method:

```
private int state;

public Random(int seed) {
    this.state = seed;
}

public int nextInt() {
    // code here
}
```

Go ahead and add these methods, and then implement the following algorithm for the `nextInt` method:
1. To get the next "random" integer, we will first add a reasonably large prime number to our current
`state` value (four digits should suffice, you can use Wolfram alpha to find a large prime by giving a
query like "prime 1000").
2. Next, we will take our `state` value and multiply it by another largish prime (three digits should be good
enough).
3. That’s it, we just return the `state` value.

This random integer algorithm is not very random, especially for the first few results when given a small
`seed` value, but it could be used in some applications where we don't care much about quality.
When you’ve finished the `nextInt` function go ahead and commit your changes using `git commit` with
an appropriate message, such as "Implemented Random constructor and Random.nextInt." Remember
you have to add your changes to be tracked first using `git add` (in this case we could just say `git add
src/Random.java` since we have only changed a single file). If you are lazy, you can give git commit the
`-a` switch to tell it to automatically add all already-tracked files. 
sNow you can wait for your partner to be
done with their changes.

## Task 2: Choose an item

Before starting this task, use `cd` to get into the project directory, and then execute the following commands:

```
git branch choose
git checkout choose
```

This will create a new "branch" for working on this feature, so that our changes will not interfere with
our partner. The second command switches over to the new branch.

For our `Random.jav`a file, let's assume we have a working `nextInt` function that takes no arguments and
returns an integer, and implements a `choose` function which picks an item out of a `List`. Go ahead and add
a method:
```
public Object choose(List l) {
    // code here
}
```

Since our `nextInt` method might return any integer, positive or negative, we can just use a modulus
operation (%) to constraint it to the range we want (`l.size()`). We can use `l.get()` to get the appropriate
element as an `Object` and return it.

When you’ve finished the `choose` function go ahead and commit your changes using `git commit` with
an appropriate message, such as "Implemented choose function." Remember
you have to add your changes to be tracked first using `git add` (in this case we could just say `git add
src/Random.java` since we have only changed a single file). If you are lazy, you can give git commit the
`-a` switch to tell it to automatically add all already-tracked files. 

Now you can wait for your partner to be
done with their changes.

## Merging

Okay, now that both partners have made inconsistent changes to the `Random.java` file, we would like
`git` to merge these into one version. Luckily, our changes were inconsistent at a high level, but conceptually
they are not really opposed to each other: we worked on separate methods, and if you just put all the code
together, things should work. In this case, `git` should be able to figure that out and merge automatically.

To do this, **whoever did the `git clone` operation** should try to do:
`git push`

The `push` operation says: take my local changes, and push them back up to the place I got the code from
in the beginning. There is an analogous `git pull` operation that pulls versions from the original repository.

In this case, because the origin does not know about the new branch we created, git will tell us to do the
following (where `<branch>` is whichever branch we worked on):
```
git push --set-upstream origin <branch>
```

After doing this once, the "upstream" repository will know about the branch we created, and we can just
do a normal `git push`.
Next, whoever set things up originally should go ahead and merge things together. To do this, we will
first want to switch back to the "master" branch, by doing:

```
git checkout master
```

Notice that after doing this, all of your code changes are wiped out in Eclipse (you may have to refresh).
That’s scary, but with `git`, everything you've ever committed is saved, and it is just a question of how to
get it out again. In this case, we went back to the `master` branch which hasn't seen any of our changes yet.

To pull them in, we use `git merge`. Since our partner has already pushed their branch, if we type just:

```
git branch
```

We should see all three branches in our repository: `master`, `nextint`, and `choose`. So let’s start with
`nextint`:

```
git merge nextint
```
This should say something about "fast-forward" and pull in the changes from that branch, so that if we
refresh in Eclipse, they're visible. 

Next, merge the other branch, using:
```
git merge choose
```

This will pull our partner’s changes and try to merge them automatically. If it fails, it will show a warning
and will put both versions together into the file while marking where they differ, so that we can fix up the
disputed area and do a git commit.

If git asks you to, fix up any overlap areas, (they should be clearly marked) and then do another `git commit`. Now that we have reconciled our differences into the `master` branch, our partner can do a `git pull`
to see everything that we've done, followed by a `git checkout master` so that they're also on the `master`
branch again and can see all the changes.

### The `git` workflow

This is the usual workflow of `git`: you create a branch for working on a specific feature, commit (perhaps
several times) to that branch as you work on it, then eventually merge it back into the master branch, which
may take some manual fixing-up (but `git` does a lot for you). No matter how many people are working on
the same code, as long as they work mostly on different parts of it, they can all work separately this way,
and still synchronize their results periodically as branches merge together.
As a bonus, `git` keeps track of history for you, so it is unlikely that you will ever lose more work than
what you have done since the last time you ran `git` commit (for this reason, it is a good idea to commit
often). `git` stores all of its data in a hidden `.git` directory, so unless you wipe out that directory (and any
clones other people may have made) you will be able to recover your project.
Note that with most development environments, including Eclipse as we've used it so far, there are plugins you can add to use
git or other VCSs from within the IDE. These can be more convenient, but it is useful to know the raw
operations so that you know what you are doing when you use those plugins.