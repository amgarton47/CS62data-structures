# Lab: Timing Sorting Algorithms

## Objectives

In this lab, we'll be playing with some of the sorting algorithms we've been discussing in class. In addition, you'll get some familiarity with the `merge` method of `MergeSort`, which you will be implementing an on-disk version of for the next assignment.

You may again work in pairs on this lab, but choose a partner that you have not worked with before.

Note: this lab assumes you've kept up with the reading for the class! In particular, we'll be looking at selectionsort, insertionsort, quicksort, and mergesort but we'll also encounter bubblesort and heapsort.

## Getting Started

After you've setup your project, spend 5 minutes looking at the different classes to orient yourself.

* Look at the interface `Sorter`.
* Look at how the `Quicksort` and `Mergesort` classes implement the interface.
* Look at how the `main` method of the `SortTimer` class is able to print out data for an arbitrary number of `Sorter` classes. (This is the benefit of using an interface!)
* Notice that the `SortTimer` class does a check for correctness after sorting. If you make a mistake in implementing your `merge` method, you will get an error here.

## Finish `MergeSort`

You've been given all of the code for this lab except the `merge` method, which you should now implement. Give it a good effort, but if you get stuck, ask a mentor or Professor for help.

Once this is done, you should be able to run the `SortTimer` class.

## Play with the Timing

Notice that we have sent the `printTimes` method twice to the `SortTimer` object. Run the `SortTimer` class. What explains the very different answers obtained in the two runs for small values of size? Does the data obtained from the second run look like you would expect? Which one is faster?

This should give you some confidence that `Quicksort`'s average case works as we expect. As an additional test, change the `printTimes` method to generate sorted data instead of random data. For example, have it fill the array with numbers from 1 to `size`. How does this change your timing data? Is this what you expected?

## Play with the Sorting Algorithms

In Eclipse, navigate to the `coinSort` package and click on the file `CoinSorter.java`. Now click on the green run button in the top toolbar.

You will see a window similar to the one for the Silver Dollar Game, except that all the squares are filled, and the coins have different sizes. Use the keystrokes below to shuffle and sort the coins. Experiment with several of the sorting algorithms.

* `c`: sort the coins using a randomly-selected algorithm
* `b`: sort the coins using bubble sort
* `i`: sort the coins using insertion sort
* `q`: sort the coins using quicksort
* `h`: sort the coins using heapsort
* `s`: sort the coins using selection sort
* `r`: rearrange the coins into a random order
* `x`: exit the program

The program you are using has a few additional features. Typing `f` (for "freeze") stops the sorting; typing `t` (for "thaw") resumes the sorting. Typing `f` when the sorting is frozen advances the algorithm by one step. You can continue to type `f` to proceed step-by-step, or `t` to resume normal execution.

Typing `c` selects one of the sorting algorithms at random and executes it. Practice with the `c` command to develop your skill in identifying the algorithm from the pattern of comparisons and swaps.

## Analysis

Create a file (in the top level directory of your repo) called `analysis.txt`, in which you (briefly) discuss:

   1. the motivation for ten-runs-per <sort,size> pair, and (quantitatively) what that tells you about the 
      reliability of your results.
   2. for each algorithm, the time as a function of the number of items to sort.
   3. why each algorithm has that performance.


## Submission Instructions

Please fill out the `assignment.json` file. Include your Github name in the collaborators list and your partner's username as well if you worked with someone. If you have anything you want to say to the graders, put it in the notes field. Don't forget to put your name(s) in a comment at the top of the `MergeSort` class.

## Grading

Your submission will be graded based on the following criteria:


| Criterion                                | Points |
| :--------------------------------------- | :----- |
| clean compilation w/no warnings          | 1      |
| correctly implements MergeSort.merge     | 3      |
| plausible results (and analysis)         | 3      |
| comments (for added methods/code)        | 2      |
| style and formatting                     | 1      |

## If You Still Have Time

Try implementing a new class for one of the `O(n^2)` running time sorting methods that `extends` our `Sorter` interface. Add this new class into the `SortTimer` class and compare its runtime to the other sorting methods.
