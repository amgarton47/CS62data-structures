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


## Getting started

1. Follow the same steps for previous labs/assignments to clone the github 
   repository for this assignment. 

2. Study the included [DLL_Node starter](LinkedList/src/DLL_Node.java), 
   with its well-formed-list invariants, method descriptions, and
   instance variables.

3. Implement the `insert`, `remove` methods, and then implement the
   `listToString` method and create a few more test cases in the `main`
    method. Think of the edge cases.

4. based on the experience you gained implementing the `listToString`
   method, implement the `iterator` method, and the `DLL_Node_Iterator` class.

5. Describe (e.g. in a block comment at the end of the `DLL_Node`
   class) the *complete* set of test cases you would use to satisfy yourself
   of the correctness of this implementation.



## When you're done

After you have implemented and tested the DLL_Node methods, do a final commit of your DLL_Node.java file.

