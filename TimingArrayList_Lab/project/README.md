# Timing ArrayList Additions

<!-- We encourage you to work in pairs on this lab as it is useful to learn from others and two pairs of eyes on a program are more likely to find errors.  Having someone to discuss the results of your program will also make it more likely that you'll get a deeper understanding of the results.  Don't forget to acknowledge your partner in the json file. -->

For this assignment, we'll be exploring the different variations of the `ArrayList` class.  In particular, we'll examine how resizing the list when the array fills up affects the overall run-time and how this relates to our theoretical calculations.

## Learning Goals

* Gain further experience with class `ArrayList'.
* Gain practice with timing operations and comparing the experimental results with the mathematical analysis of time complexity.


## Key Terms and Concepts

* `ArrayList` - An ArrayList is a resizable array-like data structure, where items can be added and removed regardless of the initialized size <!-- (See 1.3 pg. 136 in the textbook and--> the lecture notes for more information).


## `CapacityArrayList` Class

In this laboratory, we will use a `Stopwatch` class to measure the efficiency of the ArrayLists.  The Java version of arraylists (`java.util.ArrayList`) always doubles the size of the underlying array when add is called and the array is full.  To be able to play a bit with other options, we've included a basic arraylist class that also allows us to grow the arraylist by a constant amount when it is full and we need to increase the capacity. 

Take a look at the source code of the attached `CapacityArrayList` class. You will notice that we have added one more constructor that takes an additional argument `capacityIncrement`. This argument corresponds to the rate of growth of the ArrayList. If it is equal to 0, the ArrayList should double in size when full. Otherwise, it will just grow by the specified increment.

Our goal is to see how execution speed is affected when creating arraylist objects with different values for the 
`capacityIncrement` field.  


## `Stopwatch` Class

You will be using a `Stopwatch` class that represents a stopwatch and collects the running time for programs you write today. Feel free to use it in the future, too. Take a look at it; it has `start` method that starts the stopwatch and and `elapsedTime()` that returns the elapsed time.

## Your Program

Once you have everything setup, create a new class `ArrayListTimer` and specify its package to be `arraylist`.  To do this, right-click on the `arraylist` package and then select `New->Class` and enter `ArrayListTimer` as the class name.

`ArrayListTimer` will simply contain the `main` method and a few other static methods:

- `public static double run(int maxSize, int capacityIncrement)`

This `run` method will create a new empty arrayList of type `CapacityArrayList<String>` with the specified rate of growth. Set its initial capacity to 2. It should return the time that it takes to add `maxSize` strings to the `CapacityArrayList`. Use the `add` method, and always add the same constant string of your choice, for example, your name. To attempt to minimize the impact from garbage collection add the line: `System.gc();` in your run method right before you start the stopwatch. 

- `public static ArrayList<Double> trial(int maxSize, CapacityArrayList<Integer> capacityIncrements)`

Your `trial` method will compare the results from `run` for a specific number of strings to be added to the arrayList while varying its increments when it's full. It should make one call to `run` for each entry in the `capacityIncrements` ArrayList. The results of these trials should be returned in a `ArrayList` whose size is the same as that of `capacityIncrements` and the entry at position `i` in the returned arrayList should correspond to the trial with increment set to the entry at position `i` in `capacityIncrements`.

- `public static void main(String[] args)`

Your `main` method will run several trials and print the results to the console. Try starting with increments of 1, 10, and 0 (default: doubling); and size of 1000, 2000, 40000, 8000, etc. You may want to adjust the sizes and increments after you see your results.
*Java uses just-in-time compilation so you will need to first run several trials and discard the results.*

Static methods and static variables can be used when there is no need for the class to create more than one object. Simply add `static` to the declaration of your variables and methods and then you can call them directly from `main` without having to call a constructor first.

In programs structured this way, the main method does NOT create a new object from the class constructor (*notice we do not even have one!*). Instead, it contains the commands that might otherwise be put in the constructor, and methods are called directly, e.g., `trial(maxSize,capacityIncrements);` rather than having it sent as a message to an object.

## Presenting Results

Present your output in a table like the one below; *see the note below about formatting for some helpful tips*. 

The first column represents the various sizes for the strings added to an arrayList, while the other three the time it takes to increase it by 1 or 10 positions, or double it (argument is 0).

```
    size |  linear (1) | linear (10) |      double
 --------------------------------------------------
    1000 |    0.006000 |    0.000000 |    0.000000
    2000 |    0.009000 |    0.001000 |    0.000000
    4000 |    0.038000 |    0.004000 |    0.000000
    8000 |    0.127000 |    0.011000 |    0.000000
   16000 |    0.406000 |    0.050000 |    0.000000
   32000 |    1.351000 |    0.160000 |    0.000000
   64000 |    4.599000 |    0.535000 |    0.000000
  128000 |   18.694000 |    2.087000 |    0.002000
```

## A Note on Formatting Text Output

The object `System.out`'s type is `PrintStream` which has a method `format`. `format` is very general and makes it easy to print the lines in the table. The call

```
System.out.format("First: %8d, second: %-12s%n", num, str);
```

creates a string and prints it. The string is formed by

- replacing `%8d` with the numerical value of `num`, right justified in a field eight characters wide, and
- replacing `%-12s` with the string representation of `str`, left justified in a field twelve characters wide.

If `num` and `str` are 47 and `XLVII` respectively, then

```
First:       47, second: XLVII       
```

is the result of the method call above.

The letters after the percent sign, `d` and `s` in the example, indicate the kind of data being formatted; they are not variables. `f` represents floating-point numbers. The sequence `%n` is the OS independent newline character. You may have as many `%` expressions in the format string as you want. They are matched with the arguments that follow. There are *many* more options for format strings, see the Java documentation for the classes `PrintStream` and `Formatter` or the [Formatting Numeric Print Output Tutorial](https://docs.oracle.com/javase/tutorial/java/data/numberformat.html).

## Understanding the Results

- Look at the results of your program. Do the times increase monotonically as the size increases? If not, why do you think that is?
- What happens as the size of the input doubles in each of the three columns? Think of the doubling ratio hypothesis
- What is the running time (i.e. Big-Oh running time) of increment vs. double? Does your data reflect this?

We will discuss the results when most people have their timings working.

## Submission Instructions

<!--Please fill out the `assignment.json` file.
Include your Github name in the collaborators list and your partner's username as well if you worked with someone. If you have anything you want to say to the graders, put it in the notes field. -->

Put your name(s) in the `@author` tage at the top of the `ArrayListTimer` class and then commit and push your final code.

## More Fun!

Once everything is working, try a few additional things:

- What happens with other increments (besides 1 and 10)? Can you predict what the results will look like?
- Rather than just running one experiment per setting, you can run multiple experiments (say 5 to 10) and average the results in your run method. This will be a bit slower, but should give you more accurate results.
