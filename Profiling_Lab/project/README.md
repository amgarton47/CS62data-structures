# Lab: Execution Profiling

## Learning Goals

* Gain experience with execution profiling as a performance management tool.
* Use execution profiling to gain a deeper understanding of hashing lookup implementations 
  and their performance under different patterns of data insertion.

## Key Terms and Concepts

* Open Hashing with Linear Probing - All dictionary entries are stored in a single array, but the the location of a 
  particular entry within that array is not fixed by its hash value (the location is *open*).
  Rather, the hash value is used to compute a starting index, and the entry will be placed
  (by *linear probing*) in the first unused slot after that index.
  This has the potential to be very fast, but slows down as the array fills up, the number of conflicts
  increase, and the searches become longer.

* Bucket Hashing with Linked List (Separate) Chaining - The name-space is divided into some number of *buckets*
  (chosen by taking a hash value modulo the number of buckets).  Each bucket contains a linear linked
  list (chain) of the entries that hashed into that bucket.  If the number of buckets is chosen to be a reasonable
  fraction of the total number of (expected) entries, the lists should be relatively short.

* Hash Collisions - A situation where multiple keys hash to the same value, necessitating
  additional comparisons and searches to find the correct entry.

## Introduction

Imagine a program which is not running as quickly as we would like.  How can we improve it?
We could use a stop-watch but this
does not tell us where most of the computation time is spent on.  As the designers and implementers of this program, we
may be aware of things that could have been done better, for example, an inefficient method. But intuition is, at best,
an unreliable tool for performance management.  That poorly written method may indeed
be 100x slower than a better version might be, but if it is only very seldomly called, 
it doesn't matter how slow it is.  We need fine-grained data about which code is consuming
what fraction of the total time.

Analyzing the performance of a complex system may involve considerable instrumentation,
but a pair of standard tools can often provide us with a great deal of insight:

* _call counting_ - we can ask the compiler to add a little bit of extra code to
  each method to increment a counter each time it is called.

* _execution profiling_ - we can set a timer to go off regularly (e.g., every few 
  milliseconds), make note of the call stack, and increment a counter associated with that region of code.

These counters can be written out to a file after the program has completed its
execution, and a profiling analysis tool can then be run to:

   * tabulate the number of calls to each method.

   * estimate, based on the random profiling samples, how much time was
     spent in each method.

   * report on the number of calls to each method, the amount (and fraction)
     of time spent in that method, and an estimated time (in ns or us) per
     call.

Having real data on how often every method is called and the run-time cost
of each call will direct our attention to the places where the costs are 
greatest, and where improvement yield the greatest benefits.


## Description

In this lab, we will look at multiple implementations of a symbol table (dictionary) for keeping track of references
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

   * a list implementation (linear, sorted, open, bucket)
   * (optional) the number of random words to create and use
   * (optional) the number of random word references to generate

Execution profiling of Java software is both difficult and noisy due to the fact
that Java is an interpreted language with run-time garbage collection.  To give you
cleaner data, this test program and set of list implementations have been written in C. 
If you haven't seen C code before, don't panic. Although there are similarities with Java, 
we don't expect you to follow along everything.

The compilation and execution should all be automated by the supplied `Makefile`.

### Files

* Makefile - rules to:

   * compile the test program and list implementations,
   * run the program to exercise each of the implementations,
   * process the raw profiling data into a per-implementation report, and
   * create a combined report from the per-implementation reports

  If you type the command `make`, it will build all these rules.  If you only 
  want to rebuild a single report, you can specify what you want to build 
  on the command line (e.g., `make bucket.txt`).

* list_tester.c - this is the tester program. The `Makefile` causes it
  to be compiled with execution profiling and call counting enabled.
  When you run it, it:

   1. generates a set of random words.
   2. makes the specified number of add_reference calls for randomly chosen words.
   3. counts and reports the total number of references for all words.
   4. writes out the profiling data (`gprof.out`)

* words.c - implements methods to create a list of random words,
  choose words from that list, and compute (reasonably well-distributed)
  hash codes for those words.

* word_list.h - this header file defines the *interface* for a word-list
  manager.  This data structure contains:

     * a string to identify the chosen implementation
     * pointers to methods to add a new reference or return the number of references
     * a `void *` (could be anything) pointer to the underlying data 
       structures in the underlying implementation.

  Each implementation has a constructor, an add method, and a references method.

  This is an example of how *objects* (with instance variables and methods)
  were implemented in C before the advent of the (newer, object-oriented, and much closer to Java)
  C++ language.

 
* linear_list.c - the implementation of a dictionary of word-lists as a simple,
  un-sorted, singly-linked list .
* sorted_list.c - the implementation of a dictionary of word-lists as a sorted
  singly-linked list.
* open_hash.c - the implementation of a dictionary of word-lists as entries in
  a fixed-sized array, managed as an *open* hash table.
* bucket_hash.c - the implementation of a dictionary of word-lists as a collection
  of un-sorted, singly-linked lists.

You will notice that the `Makefile` runs the hashed implementations for many 
more updates than the linear-list implementations.  The hashed implementations
are much more efficient, and so must be run for many more iterations to generate
a reasonable number of execution profiling samples.

### Assignment

   1. Take a look at the four dictionary implementations: `linear_list.c`, `sorted_list.c`,
      `open_hash.c` and `bucket_hash.c`.

   2. Build the software, run the tests, and generate the reports.  On a *ix system
      (with *make*, *gcc*, and *gprof* installed) all you should need to do is type
      two commands:

      ```
      cd <your_repo_directory>
      make
      ```
      
      If you want to test the program with other options, you will find all
      of the necessary commands in the `Makefile`.

   3. Analyze and understand the results.  Note that:

      * for the Open Hashing implementation, the time to add a reference is the sum of the time 
        for `open_add`, `hash_word` and `open_find_entry`
      * for the Bucket hasing implementation, the time to add a reference is the sum of the time
        for `bucket_add` and `hash_word`

      These differences can be seen in the _gprof_ _self_ and _total_ times per call:  
      * The _self_ time is the time spent in that routine.
      * The _total_ time is the time spent in that routine, AND other routines it called.

   4. Write up an analysis (to be submitted as an ASCII text file `analysis.txt`) in which
      you address the questions described below.

   5. Commit and push back the updated repo, which is to include:
      
      * the raw reports you generated (`linear.txt`, `sorted.txt`,
        `open.txt` and `bucket.txt`)
      * your discussion of the questions described below (`analysis.txt`)
      * any additionatl reports you generated for extra credit.

### Analysis

1. Discuss the performance differences you would expect to see
   between the sorted and un-sorted linear list implementations of dictionaries.

   Present your time-to-add performance results between those two
   implementations, and discuss how they agree or disagree with 
   those predictions.

2. Discuss the performance differences you would expect to see
   between the linear-list and hashed implementations of dictionaries.

   Present your time-to-add performance results between those two
   implementations, and discuss how they agree or disagree with 
   those predictions.

3. Disucss the performance differences you would expect to see
   between the Open and Bucket hashing implementations.

   Present your time-to-add performance results between those two
   implementations, and discuss how they agree or disagree with 
   those predictions.

### Extra Credit

4. Explain the importance of the `OVER_ALLOCATE` macro in the `open_hash.c`
   implementation, do additional runs with larger and smaller margins, and
   discuss how the resulting data supports your analysis.

5. Explain the importance of the `BUCKET_SIZE` parameter in the `bucket_hash.c`
   implementation, suggest a quantitative relationship between that parameter
   and the time-to-add performance, do additional runs with larger and smaller
   values, and discuss how the resulting data supports your analysis.

## Submitting your work

Double-check that your work is indeed pushed to Github! 
It is your responsibility to ensure that you do so before the deadline.
Don't forget to commit and push your chages as you go.
