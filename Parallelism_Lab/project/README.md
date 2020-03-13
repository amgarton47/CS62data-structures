# Lab 08: Parallelism

## Introduction

In this lab, we'll once again be playing with sorting algorithms! This time our goal is to make them more efficient by using parallelism. You may work in pairs on this lab.

We'll also be using `git` a tiny bit more in this lab.

## Getting Started

To start the lab, open up your terminal and navigate to your Eclipse workspace directory using the `cd` command. (Don't forget to use `TAB` to autocomplete to save time.) Then enter the following command to create a clone of the starter code:

```
git clone https://github.com/pomonacs62/lab08.git
```

As we saw in the command line lab, `git` keeps track of your code as you create commits and push them to github. On more complicated software projects, multiple programmers may be working on independent features and it would be annoying to have every other programmer constantly breaking things so that code cannot be run. So `git` supports creating multiple versions of the same code that can be independently worked on and later combined back together. These are called branches.

By cloning a repository, you have created a copy of the starter code, along with the entire history of changes, in such a way that you can create your own edits and send them back ot the original repository.

Let's look at the history of the starter code by typing `git log`. This will show you a record of each commit, which is a set of changes that someone added to the repository. When you do this, you'll notice that something isn't quite right. The most recent commit says something about introducing errors! (If only all commit messages were so helpful.)

Luckily with `git` we can go back in time if we made a mistake. TO do this, type:

```
git checkout 466d560
```

This will temporarily update your code to a specific version. The number `466d560` is the unique start of one of the commit numbers from the `git log` output. You can also tab-complete it by pressing `TAB` after typing `46`. By running this command you can look at a specific version of the code, but if you type `git status`, you'll notice that it says `HEAD detached`. This somewhat disturbing message means the current version you're looking at (called `HEAD`) isn't the most recent version. When you make changes, it's best to add them to the end of a branch so that things don't become too confusing.

To list the current branches for the repository, use `git branch`. You'll see that there's a branch called `error`, which you're currently on, and another called `master`, which is the default branch in git. So let's see what's in the `master` branch: run `git checkout master` and then `git log`. Now you should see a commit that reverts the errors introduced in the `error` branch. Effectively, the `error` branch is a dead-end that's now out-of-date. Note, if we wanted to see what the errors were, we could run `git diff 1f2ce3c 5ef789b` (or just `git diff master error`) to have `git` print out the exact differences between the two versions. If we wanted to convince ourselves that the error really was reverted, we could run `git diff master 466d560` and hope that it prints out nothing.

This is as far as we'll go with `git` in this lab. At this point, you can either choose to continue working on the command line or import the code into Eclipse as usual.

## The Quicksort Code

You will notice that this version of `Quicksort` is a bit clunkier than earlier versions you have seen. It is invoked by creating a new `QSManager` and then invoking its `run` method.  Similarly each recursive call creates a new `QSManager` and then calles its `run` method. The reason for the extra overhead of creating these new objects is to make it easier to generalize for parallel execution.

The program also prints out the first 10 elements of the sorted array so that you can make sure the array is correctly sorted after you make modifications to the code later in the lab.

Start by running the `main` method of `QSManager` to get timing information on `Quicksort`. Notice that the code runs the sort routine 10 times to warm up the code, and then runs it another 10 times to get timing values. It reports each of those times as well as the minimum of those 10 times. Please create a text file in your repository and answer these questions:

1. Why is there variance in these numbers? (Hint: it is more than just the application continuing to warm up.)
2. Write down the minimum time for 10,000 and 20,000 elements by changing the value of `NUM_ELEMENTS`. Do these numbers make sense given our analysis of the big-O worst case complexity of quicksort?

## Running in Parallel

Modify the code in `QSManager` so that the recursive calls run in parallel. This can be accomplished by making `QSManager` extend `Thread` and invoking it with `start` rather than `run` when you want to start a separate thread. (Refer to "Parallelism and Concurrency" text or your lecture notes for additional details.)

We would like the code to run as efficiently as possible, so only create a single new thread when you make recursive calls (and the initial call can also run in the same thread as the rest of the main program). Your code should be very similar  to that of our final attempt at summing an array using Java's `Thread` class. Don't forget to wait for the new thread to complete before returning from the `run` method. Also, be careful of the order that you write the code to ensure that it really runs in parallel and not sequentially. Using this version of the program, write down the minimum times for sorting 10,000 and 20,000 elements. Then answer the following question in your text file.

3. Explain why you think this version of `Quicksort` is faster or slower (depending on your results) than the previous version.

## Using the `ForkJoin` Environment

Now that you have it running in parallel, make it even faster using the `ForkJoin` framework from Java 7. This version should be similar to the code examples from lectures except that your class will extend `RecursiveAction` rathen than `RecursiveTask` (because compute needs no return value). Make sure that `QSManager` imports the appropriate classes. Using this version of the program, again record the minimum times for sorting 10,000 and 20,000 elements in the array and please answer the following question in your text file.

4. Explain why you think this version of `Quicksort` is faster or slower (depending on your results) than the previous versions.

## Final Handin

Create a commit with your final `QSManager` code and textfile of times and question answers. Then push the commit to github. Don't forget to also edit the `lab08.json` file.
