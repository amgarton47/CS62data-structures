# Lab 2 - Debuggin

## Learning Goals

* Practice debugging programs.
* Learn to use the debugger in Eclipse.

## Find the bugs

A matrix is a two dimensional structure where you can index an element based on its row and column.  For the assignment, we've provided you with a class called Matrix that implements this using an ArrayList of ArrayLists.  For this lab, we have generated three versions of this class, each of which doesn't quite work correctly.  Your goal is to try and correct each of the implementations so that they work correctly.  You should debug them in the following order:

* BadMatrix
* BadMatrix2
* BadMatrix3

Each of these implementation has one main bug that's prevenging it from working correctly.  To help you in narrowing down where the issue is (and to give you examples of how you can write test cases), we have provided a Test class with some static test cases.  To make our life simpler, all three "Bad" implementations inherit from Matrix (though they override all the methods), so the two main tests will work on any of the Matrix classes.

## When you're done

When you've found and fixed the bugs in the three matrices, push your fixed versions to github.
