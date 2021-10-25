# Lab: Binary Trees

## Learning Goals

* Gain experience with the maintainance and traversal of Binary Trees,
  and their performance under different patterns of data insertion.

## Key Terms and Concepts

* `Binary Tree` - a data structure, descending from a single node, where each node has
   (possibly null) left and right children.  When used as for sorting and searching:
   * each node has a value
   * the sub-tree to the left has lower values
   * the sub-tree to the right has higher values

## Description

In this lab, we will experiment with creating a basic implementation of a Binary Search Tree in the provided `BSTExercise` class. Review the code we have given you carefully and pay special attention to the constructors and these fields and methods:

* `item` ... the item represented by this node (in this lab, integers)
* `left` ... reference to the left sub-tree (containing lower values)
* `right` ... reference to the right sub-tree (containing higher values)
* `size()` ... the total number of nodes in a tree (or sub-tree)
* `height()` ... the maximum depth of a tree (below its root node)
* `toString()` ... (in this lab) the sorted contents of the tree (or a sub-tree)

You will notice that there are both public and private versions of most of these functions.
The most natural methods operate reccursively from a specified node.  But the insert/locate/size/height
operations on a binary tree almost always want to operate on the entire tree.  For this
reason we define (and give you code for) public methods that operate on the entire
tree.  The real underlying implementations go into the private, per-node, recursive methods.

We will use this class to construct some examples of binary *search* trees whose values are integers. For a binary search tree the value contained in a node *n* is

* greater than all the values contained in nodes of the left subtree rooted at *n*, and
* less than all the values contained in nodes of the right subtree rooted at *n*.

We will not store duplicate values.  If a value is already in the tree, it will not be added again.

Notice that the textbook has a class called `BST` which automates most of the operations on binary search trees. We will not use it today because we want you to experience the joy of manipulating trees directly. We will also assume that BSTs hold only a key and not a key-value pair.

We have left TODO comments with things you need to fix or add.

Finish the implementation of the `BSTExercise` class:

   1. Implement the (very simple) `size` method by recursively enumerating all of the
      nodes in the tree, and returning a count of the total number of nodes
      found.

   2. Implement the (almost as simple) `height` method, by a similar recursive
      descent.  But keep in mind:
      * we have defined height to be the number of edges between the root 
        and the bottom-most node.  Thus height of a single node is 0.
      * the left and right sub-trees of a node may be of different heights.
        The `height` method should return the maximum depth.
   
   3. Implement the `locate` method, which searches the tree for a specified
      *item*.  This recursive descent will compare the item (which is 
      `Comparable`) with the value of each node in the tree, descending
      left or right depending on whether the item is less than or greater
      than the current node. 
  
   4. Implement the `insert` method, which does a (similar to `locate`) search
      for the correct insertion point, and (assuming it is a new value) adds
      a new node for that item at the appropriate point in the tree.

   5. At this point you should be able to run the basic set of tests provided
      in the starter's `main` method.  You may want to add additional test cases
      to more thoroughly exercise your implementation.

Exercise your implementation by creating and measuring the heights of larger
trees:

   6. Complete the implementation of the `ConstructIntTree` method.
      We have already created an object of the `java.util.Random` class for you and an upper bound for the 
      random number generator for you to use.
      Re-run the `main` exerciser to create a tree to sort 128 random integers.  
      After you are done, Look at the output, and confirm that all the values 
      have been correctly sorted.

      Note that doing 128 inserts may not necessarily put 128 nodes in the tree.
      These are randomly generated integers, and if one happens to be equal to
      a value already inserted, it will not be inserted again.  Do not consider
      it a bug if, after building the tree, it contains fewer nodes than you 
      would have expected.

   7. Complete the implementation of the `randomTreeHeights` method, by creating
      numerous trees (with calls to `ConstructIntTree`). 
      Measure the minimum, maximum, and mean heights of the resulting trees,
      and report those results.

Analysis of Binary Search Tree performance:

   8. Given your now (considerable) understanding of Binary Search Tree insertion
      works, what are the theoretical best-case and worst-case heights for a tree 
      of 128 numbers?  Extend your implementation of `randomTreeHeights` to print
      out these numbers (e.g. "Theoretical heights for 128 nodes: best-case=#, worst-case=#")

## If You Want More Practice (not required):

   9. Further extend your `randomTreeHeights` implementation to actually 
      construct and measure the heights of best-case and worst-case trees,
      and report on the actual measured heights.


## Submitting your work

Double-check that your work is indeed pushed to Github! 
Don't forget to commit and push your chages as you go,
and to edit the provided `.json`.
