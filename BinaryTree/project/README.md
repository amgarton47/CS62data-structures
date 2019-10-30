# Lab 08: Binary Trees

## Introduction

In this lab, we will experiment with creating a basic implementation of a Binary Search Tree in the provided `BSTExercise` class. Review the code we have given you carefully and pay special attention to the constructors and these fields and methods:

* `item`
* `left`
* `right`
* `size()`
* `toString()`
* `height()`

We will use this class to construct some examples of binary *search* trees whose values are integers. For a binary search tree the value contained in a node *n* is

* greater than all the values contained in nodes of the left subtree rooted at *n*, and
* less than all the values contained in nodes of the right subtree rooted at *n*.

We will not be allowing duplicate values.

Notice that the textbook has a class called `BST` which automates most of the operations on binary search trees. We will not use it today because we want you to experience the joy of manipulating trees directly. We will also assume that BSTs hold only a key and not a key-value pair.

## Getting started

We have left TODO comments with things you need to fix. Start by filling in:
* `size(Node x)`
* `locate(Node x, Item item)`
* `insert(Node x, Item item)`

The provided `main` method does some minimal testing.  Add more to convince
yourself of the correctness of your implementations. 

## Bigger trees

Now that everything is working write a method `construct128intTree` that creates a `BSTExercise` with 128 random integers.  We have already created an object of the `java.util.Random` class for you and an upper bound for the random number generator for you to use.

## Tree heights

Finally, conduct some experiments on heights of trees. Fill the method called `randomTreeHeights`. Inside this method, you should determine the heights of several randomly-constructed 128-node trees and see how they compare. What is the theoretical minimum height? What is the theoretical maximum height? How do your trees relate? Can you create a tree with the exact minimum or maximum height?

Calculate the average height of 100 random trees. Is the average height closer to the minimum or the maximum? We stated in class that a binary search tree with randomly added data maintains a height that is `O(log n)`. Does your data support this?

Write the answer to these questions in the  comments for the `randomTreeHeights` method.


## What to hand in

Commit and push the class `BSTExercise` created containing the above methods.

## Grading

Your submission will be graded based on the following criteria:


| Criterion                                   | Points |
| :------------------------------------------ | :----- |
| clean compilation w/no warnings             | 1      |
| correctly implements DLL_Node methods       | 4      |
| comprehensivness of DLL_Node test cases     | 3      |
| format, names, clarity, code quality        | 2      |
|                                             |        |
| **Extra Credit**                            |        |
| correctly implements Ordered_DLL methods    | 2      |
| comprehensiveness of Ordered_DLL test cases | 3      |

NOTE: Code that does not compile will not be accepted! Make sure that your code compiles before submitting it.
