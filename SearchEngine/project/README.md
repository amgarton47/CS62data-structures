# Assignment 06 - Search Engine

## Learning Goals

* Implement some of the linked list methods.
* Show how data structures can get integrated into a practical application.
* Learn about how search engines work.

## Search Engine Basics

### Inverted Index

The key data structure that allows search engines (like google) to identify pages that match a query quickly is called an inverted index.  An inverted index is a mapping from words to the documents that contain those words, which is called a "postings list".  For example, take the following three documents:

Document 0: a b c d
Document 1: a b d
Document 2: d f g
