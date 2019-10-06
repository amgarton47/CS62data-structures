# Lab 09: Parallelism

## Important Dates

* Release Date: April 16, 2019
* Due Date: April 17, 2019

## Introduction

In this lab, we'll once again be playing with sorting algorithms! This time our goal is to make them more efficient by using parallelism. You may work in pairs on this lab. Import to Ecliple the code we provide, as usual. 

## The Quicksort Code

You will notice that this version of `Quicksort` is a bit clunkier than earlier versions you have seen. It is invoked by creating a new `QSManager` and then invoking its `run` method.  Similarly each recursive call creates a new `QSManager` and then calls its `run` method. The reason for the extra overhead of creating these new objects is to make it easier to generalize for parallel execution.

The program also prints out the first 10 elements of the sorted array so that you can make sure the array is correctly sorted after you make modifications to the code later in the lab.

Start by running the `main` method of `QSManager` to get timing information on `Quicksort`. Notice that the code runs the sort routine 10 times to warm up the code, and then runs it another 10 times to get timing values. It reports each of those times as well as the minimum of those 10 times. Please create a text file in your repository and answer these questions:

1. Why is there variance in these numbers? (Hint: it is more than just the application continuing to warm up.)
2. Write down the minimum time for 10,000 and 20,000 elements by changing the value of `NUM_ELEMENTS`. Do these numbers make sense given our analysis of the big-O worst case complexity of quicksort?

## Running in Parallel

Modify the code in `QSManager` so that the recursive calls run in parallel. This can be accomplished by making `QSManager` extend `Thread` and invoking it with `start` rather than `run` when you want to start a separate thread. (Refer to "Parallelism and Concurrency" text or your lecture notes for additional details.)

We would like the code to run as efficiently as possible, so only create a single new thread when you make recursive calls (and the initial call can also run in the same thread as the rest of the main program). Your code should be very similar to that of our final attempt at summing an array using Java's `Thread` class. Don't forget to wait for the new thread to complete before returning from the `run` method. Also, be careful of the order that you write the code to ensure that it really runs in parallel and not sequentially. Using this version of the program, write down the minimum times for sorting 10,000 and 20,000 elements. Then answer the following question in your text file.

3. Explain why you think this version of `Quicksort` is faster or slower (depending on your results) than the previous version.

## Using the `ForkJoin` Environment

Now that you have it running in parallel, make it even faster using the `ForkJoin` framework. This version should be similar to the code examples from lectures except that your class will extend `RecursiveAction` rathen than `RecursiveTask` (because `compute` needs no return value). Make sure that `QSManager` imports the appropriate classes. Using this version of the program, again record the minimum times for sorting 10,000 and 20,000 elements in the array and please answer the following question in your text file.

4. Explain why you think this version of `Quicksort` is faster or slower (depending on your results) than the previous versions.

## Final Handin

Create a commit with your final `QSManager` code and textfile of times and question answers. Then push the commit to github. Don't forget to also edit the `lab09.json` file.
