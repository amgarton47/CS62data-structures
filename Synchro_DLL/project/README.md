# Lab: Race Conditions and Synchronization

## Learning Goals

* Gain an understanding of correctness-affecting race conditions.
* Demonstrate correctness-affecting races when multi-threading an MT-unsafe class
* Gain experience with Java *synchronized methods* and *blocks*.

## Key Terms and Concepts

* `Race Condition` - a situation where the results of a computation depend on the
   scheduling of parallel activities.
* `Critical Section` - a portion of the code wherein parallel excution from multiple
   concurrent threads or processes could result in incorrect results.
* `Mutual Exclusion` - a state in which only one actor at a time can
   perform a particular operation.  All others are *excluded* until 
   the currently-in-progress operation has completed.
* `Synchronization` - the process controlling the order in which parallel
   threads or processes perform related operations.

## Background

Local variables (e.g. those allocated on the stack when a method is invoked) have
separate copies for every instantiation, even if the same method is simultaneously
being executed by a thousand parallel threads.  This is not the case for shared 
objects or (in some languages) global variables.  If multiple parallel threads
can make changes to shared data, a few types of problems are likely to arise:

   1. A thread that is attempting to accumulate the state of a complex system
      may find its enumeration to yield an inconsistent state 
      (e.g. if elements are being added to or removed from a list while it 
      is being iterated).

   2. If multiple updates are required to perform a single operation
      (e.g. adding a student to a course involves changes to both the
      student course list and the class roster), another operation 
      (betweent those two updates) may find the system in an inconsistent state.

   3. A read-modify-write operation may produce incorrect results if the
      value to be modified changes between the read and the write.

   4. If multiple multi-update operations are in progress at the same
      time, one may over-write or discard the in-progress work of another,
      resulting in incorrect results or even a corrupted data structure.

A situation where different results could be obtained based on the order in
which threads are executed is called a *race condition*:  

   * Some race conditions are relatively harmless.  If a bakery has only
     four tripple-chocolate-croisantes, which people get them depends on
     the order in which they arrive.  Being the fifth person to arrive
     might be disappointing, but that outcome would not be *incorrect*.

   * Other race conditions, however, can be more critical.  If two
     electricians are working on the same circuit, and A turns the
     circuit off to do some repairs, and (unaware of this) B turns the
     circuit back on to do some testing, the result could be disastrous.

A set of operations that must be performed *all-or-none* (with no
interference from other actors until they are complete) is called a 
*critical section*.  We need such operations to be performed
*atomically* ... with no possibility of interruption part way through
the process.  We achieve this by forcing (otherwise independent)
parallel actors to take turns.  Preventing a second actor from 
operating on a protected system until the first actor has finished
is called *mutual exclusion*.

Long ago, when there were slower and far fewer computers in the world, programs that
allowed multiple threads (or processes) to operate (in parallel) on
complex data were rare.  As a result, few people thought
about such problems, and much code was written that would not work
correctly in a parallel computing environment.  Multi-threaded and
distributed applications are no-longer rare ... which means that 
more developers must be made aware of the problems that can arrise
in such systems.

## Description

A few weeks ago we worked on a Doubly Linked List implementation, where
most operations required updates to (as many as) four distinct pointers.
This was a single-threaded application, and no thought was given to what
might happen if multiple threads were doing concurrent *insert* and *remove*
operations in the same list.  It should therefore come as little surprise 
to learn that, when we tested that implementation in a multi-threaded
application, the results were disastrous.  This implementation definitely
not *MT-safe* (it cannot be safely used in a multi-threaded application).

In this lab you will:

   1. Examine the (quite simple) code in the `DLL_Node.insert()` and `DLL_Node.remove()`
      methods, and make a list of corruptions or failures that might occur 
      if multiple instances of these operations were in progress at the same time.

   2. Run a list operation stress-test program (with different numbers of 
      parallel threads) to see how likely such failures might be.

   3. Re-run the same tests, using testing options that will force the
      running thread to `yield` (allow another thread to run) in the
      middle of those critical sections, and see how this affects
      the probability of such failures.

   4. Explore a few approaches to synchronizing access to render these critical
      sections *MT-safe*.

### Overview of the Code

The `DLL_Node` class should be almost identical to your solution to the
*Doubly Linked List* lab you did a few weeks ago.  Recall that an object
instance in this class is not a *list*, but merely a node (with *next*
and *previous* pointers).  The only changes (from your previous lab)
are the addition of a few instance variables to insert `yield()` calls
in the middle of the `insert()` and `remove()` critical sections.

The new code is in the `Tester` class, which is intended to exercise
doubly linked lists in ways that will cause and detect errors.  The
most important methods are:

   * `main()` ...  parses command line arguments, creates a set
      of (labeled) test nodes, and then choreographs multiple
      parallel threads through alternating stress and audit
      processes.

   * `run()` ... the main loop of each test thread, alternating
     cycles of numerous insertions and removals, separated by
     breaks during which the main thread audits the correctness
     of the list.

   * `audit()` ... carefully examine the correctness of the list,
      both in terms of next/prev pointer consistency and which 
      nodes are and are not in the list.  It is hoped that this
      method will detect any error resulting from conflicting
      updates.

There is also a `TestNode` class that is merely a `DLL_Node` that
adds a `value` (which node from which test-thread) field, and a
few booleans to track which nodes should be, and were actually
found in the list.

### 1. Vulnerability Analysis

One of the fundamental skills involved in writing MT-safe code
is recognizing what data is at risk in what situations; more specifically:

   * multi-step operations (e.g. the updating of four distinct
     pointers) that must be completed atomically to assure the 
     integrity of the affected data structures.

   * operations on common data (e.g. multiple `insert` or 
     `remove` calls) that could potentially interfere with 
     one-another.

   * distinct steps (e.g. the exhaustion of an *Iterator*) 
     whose results could be compromised if the underlying
     data structures changed between their start and completion.

You will:

    1. Study the methods in the `DLL_Node` class

    2. Identify the *critical sections* (regions of code during which
       conflicting parallel updates could result in errors).

    3. Choose one of those *critical sections* and Briefly describe two 
       distinct errors that could happen if another operation where to
       happen at the same time:

       * the order of the conflicting operations

       * the (specific) consequences to the list structure

### 2. Multi-Threaded Stress Test

Failures due to race conditions are merely a risk.  They are not
guaranteed to happen.  Some runs may succeed and others fail, depending
in the exact point at which one thread or process happens be preempted.
Any potential for such failure is unacceptable, but the likelihood of
a failure during any particular run is statistical.  The
fact that one run succeeded does not mean that there are no race
conditions.

The tester program will run a specified number of parallel threads
for a specified number of cycles.  After each cycle it will audit
the integrity of the list.  If it finds any errors, it will report
them and stop.  If it finds no errors, it will continue, and report 
success after the specified number of cycles.

Run the tester, multiple times, for varying numbers of threads and
cycles, to get some sense of the likelihood of failure during any
particular test.  You need not plot these numbers as graphs.
You can simply list your runs and report a range of numbers
(threads, error-free cycles, per-cycle detection probability).

You can run the `Tester` program directly from Eclipse, but you will
surely want to run it many times with different parameters.  This can
be done (in Eclipse) by changing the *Arguments* in the *Run configurations*,
but you can do it much more easily by running the tester from the 
command line.  If, for instance, we wanted to run ten cycles with 32 threads, 
we might:

```
cd <repo directory>/synchro_dll/bin
java linkedlist.Tester --threads=32 --cycles=10
```

### 3. Worst-Case Scheduling

Depending on *luck* as to whether or not a test suite finds a bug
seems imprudent.   We would like to greatly improve the chances
that, if bugs are present, our test cases will exercise them.

When you examined the code for `DLL_Node.insert()` and
`DLL_Node.remove()` you surely noticed calls to `yield()`.
The `Thread.yield()` operation asks the scheduler to immediately
stop running the current thread and to run another instead.
We have placed these calls at the points where we believe
(through our own analysis) that a a preemption would be most 
dangerous.  This may greatly increase the probability of an
unfortunate race condition, and that our test will find bugs.
This (not un-common) testing strategy is sometimes called 
*vindictive scheduling* ... trying to schedule threads in
the worst possible way.

The `Tester` program accepts command line parameters
to force `yield()` calls within the critical sections:

   * `--races=i` to force yields within `insert()`
   * `--races=r` to force yields within `remove()`
   * `--races=ir` to force yields in both places.

Re-run your tests (with varying numbers of threads and 
cycles) to see how these `yield()` calls change the probability
of detection.  Again, list your runs, and report a range of
numbers (theads, error-free cycles, per cycle detection probability).

### 4. Java synchronized Methods

Such problems are sufficiently common that the Java language has
a mechanism to address them: *synchronized* methods.  If a method is
declared to be `synchronized`, the referenced object (`this`) will be
locked for the duration of that method, so that no other `synchronized`
method can execute (on that object) until the in-progress operation completes.

Update the `DLL_Node` to make the `insert()` and `remove()` methods
to be `synchronized` and re-run your tests.

We expect that you will find these results to be disappointing:

   1. report your results (threads, error-free cycles).

   2.  Review the `DLL_Node` APIs and specifications for the
       `synchronized` methods, and suggest an explanation for
       why this did not completely elminate the problem.

### 5. Java Block Synchronization

(without explaining why `synchronized` methods failed,) 
Sadly, `synchronized` methods are some times inadequate
to control *race conditions*:
   
   * operations that are part of the *race condition* are not
     all method invocations on the **same** object.

   * the critical section spans multiple method invocations
     on the `synchronized` object, so that locking the object
     for the duration of a single invocation is inadequate.

In such cases synchronization must be performed at a higher level.
In addition to *synchronized methods*, Java also supports
*block synchronization*.  If you surround a critical section with

```
synchronize(object) {
    operations ...
}
```

Mutual exclusion (for the specified *object*) will be ensured for
all of the operations within the `synchronized` block.  This can solve
the above-mentioned problems by:

   * providing mutual exclusion based on some object other than the
     one whose methods are being invoked.

   * providing atomic execution across multiple method invocations.

Study at the `Tester.run()` method, and decide where to add
*block synchronization* around which calls to eliminate the
race conditions between competing `insert()` and `remove()`
operations.  Add that synchronization, re-run your tests,
and report your results (threads, cycles, and whether or not
each run was successful).
You should find that it is impossible to create failures, 
even if large numbers of threads are used and all `yield()`
calls are enabled.

## Submission

Create a file `analysis.txt` in which you record your 
conclusions and results for (the above) steps 1-5.

## Grading

Your submission will be graded based on the following criteria:

| Criterion                                   | Points |
| :------------------------------------------ | :----- |
| complete and clean submission               | 1      |
| vulnerability analysis                      | 2      |
| failure rate measurement (w/o yields)       | 1.5    |
| failure rate measurement (w/yields)         | 1.5    |
| method synchronization (and analysis)       | 2      |
| block synchronization (and results)         | 2      |

Most of the grading will be based on your discussions in `analsys.txt`, 
but you will also be graded on your (ineffective) implementation
of `synchronized` methods in `DLL_Node.java` and your (effective)
solution in `Tester.java`.

NOTE: Code that does not compile will not be accepted! Make sure that your code compiles before submitting it.

## Submitting your work

Double-check that your work is indeed pushed to Github, and that
you have added, committed and pushed the `analysis.txt` file.
It is your responsibility to ensure that you do so before the deadline.
Don't forget to commit and push your chages as you go,
and to edit the provided `.json`.
