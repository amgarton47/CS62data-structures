# Lab: Execution Profiling

## Learning Goals

* Gain experience with execution profiling as a performance management tool.
* Use execution profiling to gain a deeper understanding of hashing lookup implementations.
  and their performance under different patterns of data insertion.

## Key Terms and Concepts

* Open Hashing with Linear Probing - All entries are stored in a single array, but the the location of a 
  particular entry within that array is not fixed by its hash value.  Rather the hash value is used to
  compute a starting index, and the entry will be placed in the first unused slot after that index.
  This has the potential to be very fast, but slows down as the array fills up, the number of conflicts
  increase, and the searches become longer.

* Bucket Hashing with Linked List Chaining - The name-space is divided into some number of *buckets*
  (chosen by taking a hash value modulo the number of buckets).  Each bucket contains a linear linked
  list of the items that hashed into that bucket.  If the number of buckets is chosen to be a reasonable
  fraction of the total number of (expected) entries, the lists should be relatively short.

* Hash Collisions - A situation where multiple keys hash to the same value, necessitating
  additional comparisons and searches to find the correct entry.

## Introduction

A program is running not running as quickly as we would like.  How can we improve it?
Stop-watch type measurements can enable us to compute operations-per-second, but this
does not tell us where the cycles are going.  As the designers and implementers, we
may be aware of things that could have been done better ... but intuition is, at best,
an unreliable tool for performance management.  That poorly written function may indeed
be 100x slower than a better version might be, but if it is only very seldom called, 
it doesn't matter how slow it is.  We need fine-grained data about which code is consuming
what fraction of our total time.

Analyzing the performance of a complex system may involve considerable instrumentation,
but a pair of standard tools can often provide us with a great deal of insight:

* _call counting_ - we can ask the compiler to add a little bit of extra code to
  each routine/method to increment a counter each time it is called.

* _execution profiling_ - we can set a timer to go off regularly (e.g. every few 
  milliseconds), note the address at which the program was executing when it was
  interrupted, and increment a counter associated with that region of code.

These counters can be written out to a file after the program has completed its
execution, and a profiling analysis tool can then be run to:

   * tabulate the number of calls to each method/routine.

   * estimate, based on the random profiling samples, how much time was
     spent in each method/routine.

   * report on the number of calls to each method, the amount (and fraction)
     of time spent in that method, and an estimated time (in ns or us) per
     call.

Having real data on how often every routine is called, and the run-time cost
of each call will direct our attention to the places where the costs are 
greatest, and where improvement yield the greatest benefits.


## Description

In this lab, we will look at multiple implementations of a list for keeping track of references
to strings.  Each known string will have an entry that contains the string, and the number of
references.  The implementations we are supplying are:

* an un-sorted linear list: very easy to implement, but subject to long searches.
* a sorted linear list: with potentially shorter search times (because we can stop 
  when we have passed the point where the desired entry should have been found).
* an Open Hash Table: which should have much shorter searches, but which will
  slow down as the table fills up and the collision rate increases.
* hash buckets with linear chains: which, assuming a large enough number
  of buckets, should provide reliably short searches.

The supplied `list_tester` program takes three command line parameters:

   * a list implementation (linear, sorted, open bucket)
   * (optional) the number of random words to create and use
   * (optional) the number of random word references to generate

This program has been built to enable execution profiling and call counting.
When run, it:

   1. generates a set of random words.
   2. makes the specified number of add_reference calls for randomly chosen words.
   3. counts and reports the total number of references for all words.
   4. writes out the profiling data (`gprof.out`)

The included `Makefile` has rules to:

   * compile the test program and list implementations
   * run the program to exercise each of the implementations
   * process the raw profiling data into a report
   
Execution profiling of Java software is both difficult and noisy, due to the fact
that Java is an interpreted language with run-time garbage collection.  To give you
cleaner data, the test program and list implementations have been written in C, 
which is similar enough to java that you should not have much trouble reading it.
The compilation and execution should all be automated by the supplied `Makefile`.

You will notice that the `Makefile` runs the hashed implementations for many 
more updates than the linear-list implementations.  The hashed implementations
are much more efficient, and so must be run for many more iterations to generate
a reasonable number of execution profiling samples.

### Assignment

You must do this work on a Linux or MacOS system.  You will not be able to do it
on a Windows system.  If you do not have access to such a system, you can *ssh*
to a departmental Linix Virtual machine where this work can be done.

   1. Read and understand the four list implementations: `linear_list.c`, `sorted_list.c`,
      `open_hash.c` and `bucket_hash.c`.

   2. Build the software, run the tests, and generate the reports.  

   3. Analyize and understand the results.  (Note that for the Open Hashing implementation
      the execution time is the sum of the time for `open_add` and `open_find_entry`)

   4. Write up an analysis (to be submitted as an ASCII text file `analysis.txt`) in which
      you address the following questions.

   5. Commit and push back and updated repo, which is to include:
      
      * the raw reports you generated (`linear.txt`, `sorted.txt`,
        `open.txt` and `bucket.txt`)
      * your discussion of the following questions (`analysis.txt`)
      * any additionatl reports you generated for extra credit.

### Analysis

1. Describe (a) the time-to-add performance results between the sorted and 
   un-sorted linear-list implementations and (b) suggest what in the 
   implementations would explain those differences.

2. Describe (a) the time-to-add performance results between the linear-list
   and hashed implementations and (b) suggest what in the implementations
   would explain those differences.

3. Describe (a) the time-to-add performance results between the Open and
   Bucket hahing implementations and (b) suggest what in the implementations
   would explain those differences.

### Extra Credit

4. Explain the importance of the `OVER_ALLOCATE` macro in the `open_hash.c`
   implementation, do additional runs with larger and smaller margins, and
   show how the resulting data supports your analysis.

5. Explain the importance of the `BUCKET_SIZE` parameter in the `bucket_hash.c`
   implementation, suggest a quantitative relationship between that parameter
   and the time-to-add performance, do additional runs with larger and smaller
   values, and show how the resulting data supports your analysis.

## Grading

Your submission will be graded based on the following criteria:

| Criterion                                   | Points |
| :------------------------------------------ | :----- |
| generated results                           | 2      |
| analysis: sorted/unsorted linear            | 2      |
| analysis: linear vs hashed                  | 3      |
| analysis: open vs bucket hashing            | 3      |
|                                             |        |
| **Extra Credit**                            |        |
| open hash over-allocation                   | 2      |
| bucket size                                 | 2      |

NOTE: Code that does not compile will not be accepted! Make sure that your code compiles before submitting it.

## Submitting your work

Double-check that your work is indeed pushed to Github! 
It is your responsibility to ensure that you do so before the deadline.
Don't forget to commit and push your chages as you go,
and to edit the provided `.json`.
