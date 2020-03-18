# Lab Doubly Linked Lists

## Learning Goals

* Gain experience with the implementation and use of Doubly Linked Lists.

## Key Terms and Concepts

* `Doubly Linked List` - a data structure that consists of a set of sequentially
   linked records (called nodes).  Each node contains (at least) three fields:
   a forward link, a backwards link, and one or more fields of data information.

   The principle benefits of maintaining both forwards and backwards pointers
   are:
   * it becomes possible to insert and delete nodes anywhere in the list
     without having to traverse the entire list to find the affected elements.
   * the list can be traversed in either direction (e.g. LIFO or FIFO).

## Description

In previous labs and lectures, you have used Doubly Linked List 
classes to store information.  But you were actually given
the underlying Doubly Linked List implementation, which 
opaquely encapsulated the underlying data structures and
the implementations of insertion, deletion, and enumeration
operations.

In this project, you will do the low level implementation of a
*Circular Doubly Linked List*.  This is a much simpler implementation
than the ones to which you have previously been exposed, and
focuses more on the correct maintenance of inter-node references.

You will note that this implementation does not distinguish 
between a List and a Node in the List.  With Doubly Linked 
Lists, any node can (at some cost) be reached from any other
node, so the choice of whether to maintain head and/or tail
pointers can be a little more flexible.

Finish the implementation of the `DLL_Node` class:

   1. Based on the defined fields, and the correctness invariants, 
      implement the `insert` operation.
   2. Based on the defined fields, and the correctness invariants,
      implement the `remove` operation.
   3. Based on the defined fields, and the correctness invariants,
      implement the `DLL_Node_Iterator` class and `iterator`
      method.

Then, for extra credit, you can:

   4. Implement a new insert method that will maintain the nodes in 
      an order determined by their `ordinal` fields.  
      Do this using the methods in the `DLL_Node` class.
      Do not un-privatize the `next` and `prev` instance variables.
   5. Based on the code you used to find the correct insertion
      point, implement the `find` method.

## Getting started

1. Follow the same steps for previous labs/assignments to clone the github 
   repository for this assignment. 

2. Study the included [DLL_Node starter](LinkedList/src/DLL_Node.java), 
   with its well-formed-list invariants, method descriptions, and
   instance variables.

3. Implement the `insert`, `remove` methods, and then implement the
   `listToString` method and create a few more test cases in the `main`
    method.

4. based on the experience you gained implementing the `listToString`
   method, implement the `iterator` method, and the `DLL_Node_Iterator` class.

5. Describe (e.g. in a block comment at the end of the `DLL_Node`
   class) the *complete* set of test cases you would use to satisfy yourself
   of the correctness of this implementation.

## Extra Credit

6. Study the included [Ordered_DLL starter](LinkedList/src/Ordered_DLL.java)
   with its well-formed-list invariants, method descriptions, and
   instance variables.

7. Implement the `insert` and `find` methods.  These implementations will
   require you to traverse the `DLL_Node` list, but the `next` and `prev`
   fields in that class are `private`.  This means that you will have to
   use the `DLL_Node.iterator` method to find the desired element or
   insertion point.

8. Describe (e.g.  in a block comment at the end of the `Ordered_DLL` 
   class) the set of test cases you would use to satisfy yourself of the
   correctness of this implementation.

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

## Submitting your work

Double-check that your work is indeed pushed in Github! It is your responsibility to ensure that you do so before the deadline. Don't forget to commit and push your changes as you go and to edit the provided `json`.