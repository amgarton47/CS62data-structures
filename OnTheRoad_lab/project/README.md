# Lab 11: Graph Algorithms

## Important Dates

* Release Date: April 30, 2019
* Due Date: May 1, 2019


## Introduction

This lab will serve as a warm-up for your last assignment. You will be working with Bailey's `Graph` and `GraphListDirected` classes to implement two graph algorithms. The first algorithm is edge reversal, i.e., the algorithm takes an input graph and returns a graph which is identical to the input, but all of the edges are reversed. You will use this function as part of the last assignment to test strong connectivity. The second algorithm is breadth-first search. In Assignment 11, you will be implementing Dijkstra's algorithm which is very similar to breadth first search, but uses a priority queue instead of a queue to decide which vertex to explore next.

## Getting Started

First, create a git Eclipse project as usual. Then, read about the `Graph` and `GraphListDirected` in the Bailey Structure5 [api](http://www.cs.williams.edu/~bailey/JavaStructures/doc/structure5/) and [source code](http://www.cs.pomona.edu/classes/cs062/structure5/).

## Edge Reversal

Your edge reversal should run in `O(n+m)` time when there are `n` nodes and `m` edges. There are several ways to implement this method; if you get stuck coming up with an algorithm, you can use the hints provided in the comments. Make sure you thoroughly test this method! Feel free to create some test cases and draw them on the whiteboards or on paper.

## Breadth-First Search

You will be implementing the breadth-first search algorithm as described in class. In lab, you only be implementing the "basic" breadth-first search algorithm, i.e., you will explore all of the vertices reachable from a single vertex without restarting to explore the entire graph. Recall, that the "basic" breadth-first search takes a connected graph and produces a spanning tree represented using an array of parents. The entire solution to this problem is contained in the lecture slides, but try to implement as much as you can on your own. Also make sure you understand every line of code you write. You should thoroughly test this method as well, and compare your results with examples on the whiteboards.

## The Assignment

You may freely use code from this lab while implementing the driving directions assignment. The lab code should have some of the desired behavior.

## Submission

Edit the `.json` file and push your code regularly.
