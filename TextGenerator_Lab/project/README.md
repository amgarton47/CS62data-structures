# Lab - Text Generator

## Objectives

For this lab, you will:
* Gain practice using Java Generics
* Gain practice with `HashMap`s
* Gain proficiency in using objects to build more complex data structures

## Description

In this lab, we will use a basic technique in Artificial Intelligence to automatically generate text.
You will write a program that reads in a piece of text and then uses that text as a basis to generate new
text. The method for generating new text uses simple probability.

First, we read in a piece of text word by word (we consider punctuation symbols to be words) keeping track
of how often each three-word sequence (trigram) appears. For example, consider the following excerpt from
Rudyard Kipling’s poem “If”:


    If you can keep your head when all about you
    Are losing theirs and blaming it on you,
    If you can trust yourself when all men doubt you,
    But make allowance for their doubting too;
    If you can wait and not be tired by waiting

In this excerpt, the trigrams are: “if you can”, “you can keep”, “can keep your”, “keep your head”, “your head when”, “head when all”, etc.

Once we have counted all of the trigrams, we can compute the probability that a word *w*<sub>3</sub> will immediately
follow two other words (*w*1*w*2)
 using the following equation:

*p*(*w*3|*w*1, *w*2) = *n*<sub>123</sub>/*n*<sub>12</sub>

where *n*<sub>123</sub> is the number of times we observed the sequence (*w*1*w*2*w*3) and *n*<sub>12</sub> is the number of times we
observed the sequence  *w*1*w*2.

For example, let’s compute the probability that the word “can” will immediately follow the words “if you”.
In the excerpt above, the sequence “if you can” occurs three times. The sequence “if you” also occurs three
times. So the probability is given by,

*p*(*can*|*if you*) = *3/3 = 1*

The probability that any other word comes immediately after “if you” is *0*. Consider another example. In
the excerpt above, the words “you can” appear *3* times. The first time followed by “keep”, the second time
by “trust”, and the last time by “wait”. Thus,

* *p*(*keep*|*you can*) = *1/3*
* *p*(*trust*|*you can*) = *1/3*
* *p*(*wait*|*you can*) = *1/3*

Again, the probability that any other word besides “keep”, “trust”, or “wait” appears after “you can” is zero.

Once we have the text processed, and stored in a data structure that allows us to compute probabilities, we
can generate new text. We first pick two words to start with (for example, the first two words in the input
text). Given these two words, and the probabilities computed above, we use a random number generator to
choose the next word. We continue this process until the new text is 400 words long (including punctuation).
When printing the new text, we generate a new line after every 20 words for readability purposes.

## Classes

### `FreqList`

`FreqList` contains a `HashMap` (make sure to carefully read the [documentation of the class](https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html) to find its relevant methods for insertion and search). Its keys will be words and its values the number of times that each associated word occurs. 

Your `FreqList` class also contains an instance variable that keeps track of the 
number of word references added.
This is equivalent to the sum of all the values (i.e. frequencies) in the `HashMap`.

The `add(String word)` method is responsible for inserting a new key-value pair where the key is the provided word in the dictionary.
If the provided word already exists in the dictionary, then its value (i.e. its frequency)
is incremented by 1. If it doesn’t exist, you have to add the word to the `HashMap` object with a value (i.e. frequency) of 1.
In both cases, make sure you increase the total frequency of words.

For your convenience, we have implemented the `toString` method that 
dumps out a list of the words and their reference counts.

The `FreqList` class also has a method with the following definition:

    public String get(double p)

This method takes a `double p` as an input and returns a word from the `HashMap`. The input `p` must be between 0.0 and 1.0, otherwise the method throws an `IllegalArgumentException`. How can we use `p`
to generate a word? In our example above, for the words “you can” would look like: `{<“keep”,1>, <“trust”, 1>, <“wait, 1>}`. The sum of all of the frequencies is 3. Thus, we will return “keep” whenever
0 ≤ *p* &lt; 1/3, “trust” whenever 1/3 ≤ *p* &lt; 2/3 and “wait” whenever 2/3 ≤ p ≤ 1. If the frequency list is
empty, this method returns an empty string.

Fill in the constructor and `add()` method and test it using our provided `main` method (which as always you can expand to include more tests of your choice).

### `StringPair`

You should write the `StringPair` class next. We do not provide any starter code.

`StringPair` should represent a pair of two strings.
Fill in the constructor, and write 
a `toString` method that returns something like "<string_one,string_two>".
Because we will be using `StringPair` objects as keys in `HashMap`s, you will also have to implement
`equals` and `hashCode` methods that do their comparisons and computations
based on *both* of the string values. Consult the Lecture26_27 slides on "recipes" on how to implement the `equals` and `hashCode` methods.

To make sure that you have correctly implemented these methods, you should
create a `main()` method that tests them by:

- creating multiple `StringPair` objects, for the same and different pairs of strings.
- ensuring your `equals` method correctly identifies matching and non-matching pairs.
- creating a <StringPair,String> `HashMap`.
- populating it with mappings for many different `StringPair`s.
- confirming that you can find and update those mappings.

### `TextGenerator`

The `TextGenerator` class also contains a `HashMap` object. In this case, the keys will be sequences of two words (e.g., “you can”) that you will represent through the `StringPair` class you created. The values will be objects
of type `FreqList`. 

We have provided you with the `main` method of class `TextGenerator` that will pop up a
dialog box to allow the user to choose a file containing the input text. We have also provided the headers of two methods for that class that you need to fill.

The first method, `enter(s1, s2, s3)`, will be used to build the table. The three String parameters are used to build the table that will be used later to generate random text. The elements of the table are of the form

    [<Word1, Word2> -> <w1, n1>, <w2, n2>, ..., <wk, nk>]

where this notation represents an association, where the key is the pair <Word1, Word2> and the value is the **frequency list** *<w1,n1>,<w2,n2>,...,<wk,nk>*

The table we are building is named `table`. Suppose the input starts with “This is the time for”. Then we call `table.enter("This", "is", "the")`, which updates the entry for the word pair of
“This” and “is” to record that “the” is a possible word to follow the pair. More carefully, if there is no entry
for that word pair, then it will create one for that pair with an empty frequency list, and then add “the”
occurring once to that frequency list. If the word pair is already there, we update the frequency list to record
the added occurrence of “the”. Having done this, we continue with `table.enter("is","the","time")`, then
`table.enter("the","time","for")`, and continue in the same way if there is more text.

We have included some text files (ending with suffix “txt”) in the text folder that you can use to test
your program. We’ve tried to pick files with sufficient repetition of triples.

After the input has been processed to build the table, we generate new text given two first words. The method `getNextWord(s1,s2)` uses the table
generated from the input to return a randomly chosen word from the frequency list associated with the word
pair of s1 and s2, choosing the word using the probabilities as discussed earlier.

We generate and print a string of at least 400 words so that we can see how our program works. 
Finally, we print the table of frequencies to see if your table is correct on our test input. Here are some lines of output based on the lyrics for Dylan’s “Blowin’
in the wind”:


    {...
    <how,many>=Frequency List: <seas=1> <times=3> <ears=1> <roads=1> <years=2> <deaths=1>, <many,roads>=Frequency List: <must=1>, <must,the>=Frequency List: <cannon=1>, <?,the>=Frequency List: <answer=3>,...}

And 

    Generated data:
    how many years can a man ? how many years can a mountain exist before they ' re forever banned ?
    the answer is blowin ' in the wind the answer is blowin ' in the wind the answer my friend
    is blowin ' in the wind . yes , how many years can a man turn his head pretending he
    just doesn ' t see ? the answer my friend is blowin ' in the wind the answer my friend
    is blowin ' in the wind the answer my friend is blowin ' in the wind the answer is blowin
    ' in the wind the answer my friend is blowin ' in the wind the answer is blowin ' in
    the sand ? yes , how many times must a man walk down before you call him a man look
    up before he can see the sky ? yes , how many times must the cannon balls fly before they
    ' re forever banned ? the answer is blowin ' in the sand ? yes , how many times must
    the cannon balls fly before they ' re forever banned ? the answer my friend is blowin ' in

This output indicates that after the word pair “how many”, the words “roads”, “seas”, “times”, “years”, “ears”, and “deaths” each occurred along with their frequencies. Whereas after “many roads”, only “must” occurred and it only occurred once.

### `WordStream` 

This class is already implemented for you. It processes a text file and breaks up the input into separate words that can be requested using the method `nextToken`. In the `main` method of the `TextGenerator` class, there is an object of type `WordStream` called `ws`. We get successive words from `ws` by calling
`ws.nextToken()` repeatedly. Before getting a new word, we check that there is a word available by evaluating `ws.hasMoreTokens()`, which will return true if there are more words available. If you call `nextToken()` when there are no more words available (i.e., you’ve exhausted the input), then it will throw
an `IndexOutOfBoundsException`.


## Submitting your work

Double-check that your work is indeed pushed in Github! It is your responsibility to ensure that you do so before the deadline. Don't forget to commit and push your changes as you go and to edit the provided `json`. 
