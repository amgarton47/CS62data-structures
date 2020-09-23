# JUnit

## Learning Goals

* Play a bit with implementing variants of linked lists
* Gain practice writing and running `JUnit` test cases.

## Key Terms and Concepts

* `JUnit` - A unit testing framework where tests are written for each method in a class, each with their own assertions about what the result should be in order to test cases (See [documentation](https://junit.org/junit5/) for more, and [here](https://www.qualitestgroup.com/resources/knowledge-center/how-to-guide/set-junit-eclipse/) for information about JUnit and Eclipse).

## JUnit

A "unit" test is a test that tests a very small, portion of code, often some functionality of a single function/method.  `JUnit` is a framework that allows you to write meaningful tests, run them regularly and easily identify which parts of your code might be problematic.

To get started, we're going to write some JUnit tests to test the functionality of the `LinkedList` class.

To create a new set of tests, right click on your project and then select `New -> JUnit Test case`.  This should pop up a dialog box.  Make sure that `New JUnit Jupiter test` is selected at the top.  Under the "Name" field, put the name "LinkedListTest". Then press "Finish".  When you do, you may see a window pop-up telling you that JUnit 5 is not on the build path.  If it does, make sure that "Add JUnit 5 library to the build path" is selected and click "OK".

You should now see a class called LinkedListTest with a *very* simple test.

A unit testing class is like a normal Java class, however, it does not have a constructor and the methods that we write are treated as tests.  Tests are `public void` methods that are annotated above the method with "@Test".  We then can use special method calls inside these methods that check to see that answers are what we expect them to be.  These are called `assert` statements, because they assert what we think should be true if everything is functioning properly.

Delete the method test that is auto-generated and then copy and paste these two tests:


	@Test
	void testConstructor() {
		LinkedList<Integer> l = new LinkedList<Integer>();
		assertEquals(l.getFirst(), null);
	}

	/**
	 * Note this technically is testing both addFirst
	 * and getFirst at the same time.
	 */
	@Test
	void testAddFirst() {
		LinkedList<Integer> l = new LinkedList<Integer>();
	
		l.addFirst(1);
		assertTrue(l.getFirst() == 1);
		
		l.addFirst(2);
		assertTrue(l.getFirst() == 2);

		l.addFirst(3);
		assertTrue(l.getFirst() == 3);
	}




There are three common assert statements you will use:

* `assertTrue`: takes a single parameter that is a `boolean` and checks to make sure that it is `true`.  If the argument is not true (i.e. `false`), then the test that contains the assert statement will fail.

* `assertFalse`: just like `assertTrue` except passing is `false` and failing is `true`.

* `assertEquals`: takes two parameters and checks to make sure that they are equal to each other (based on ==).  If they are not, then the test that contains the assert statement will fail.

Should these tests pass?  To run these tests, just click on the the green run arrow.  When you do, you'll see the `JUnit` window open where the package explorer is (generally on the left) and should see a green bar indicating that your tests passed.  If the bar is red, that indicates that one or more of your tests failed.  If you want to see all of the individual tests passing, you can click the arrow to the left of `LinkedListTests` in the JUnit window and you can see the results of the individual tests.

The power of JUnit is that you can run these tests over and over again as you add new code.  This makes sure that the new things you add didn't break anything that was working already!

## Testing `LinkedList`

The two JUnit tests are a good start, but a good test suite will test all of the functionality of a class.  Write additional JUnit test cases to test all of the public methods of the `LinkedList` class.

* You should have at least one test case for each method.  Often, for more complicated methods, you should have more than one.

* Your test cases should try hard to just isolate the one method that you're testing.  This isn't always possible (e.g., in the case of `testAddFirst`, but you should minimize the number of methods that you call in a test.  The goal of a JUnit test is to try and as precisely as possible where the issue is.

* As you write your test cases, think about edge cases, e.g., when the linked list is empty.


Once you have a reasonable test suite. Compare your tests with someone else in the lab.

* How similar are you tests?

* Were there any cases that you tested that the other person didn't (and vice versa)?

* Pick one of your test cases that you think is particularly interesting (between the two of you) and write it up on the whiteboard somewhere.

## Linked Lists

To give you a bit of practice actually writing linked lists (don't worry, you'll get plenty more :), modify the `LinkedList` class that we saw in class to support a `tail` reference (what operation(s) should this make faster?).  Specifically:

* Add another instance variable `private Node tail;`

* Go through each of the methods and change them appropriately to make sure that tail is 1) kept up to date and 2) utilized to make the methods as efficient as possible.  Note that not all methods will need changing.

## When you're done

Add JavaDoc to your JUnit test cases and do a final commit of both your new `LinkedList` class and the JUnit tests.

