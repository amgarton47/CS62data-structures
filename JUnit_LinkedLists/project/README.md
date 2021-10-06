# JUnit

## Learning Goals

* Gain practice writing and running `JUnit` test cases.
* Play a bit with implementing variants of singly linked lists

## Key Terms and Concepts

* `JUnit` - A unit testing framework where tests are written for each method in a class, each with their own assertions about what the result should be in order to test cases (See [documentation](https://junit.org/junit5/) for more)


## JUnit

A "unit" test is a test that tests a very small, portion of code, often some functionality of a single function/method.  `JUnit` is a framework that allows you to write meaningful tests, run them regularly and easily identify which parts of your Java code might be problematic.

To get started, we're going to write some JUnit tests to test the functionality of the `SinglyLinkedList` class.

To create a new set of tests, right click on the project you just cloned and then select `New -> JUnit Test case`.  This should pop up a dialog box.  Make sure that `New JUnit Jupiter test` is selected at the top.  Under the `Name` field, put the name `SinglyLinkedListTest`. Then press `Finish`.  When you do, you may see a window pop-up telling you that JUnit 5 is not on the build path.  If it does, make sure that `Add JUnit 5 library to the build path` is selected and click `OK`.

You should now see a class called `SinglyLinkedListTest` with a *very* simple test.

A unit testing class is like a normal Java class, however, it does not have a constructor and the methods that we write are treated as tests.  Tests are `public void` methods that are annotated above the method with `@Test`.  We then can use special method calls inside these methods that check to see that answers are what we expect them to be.  These are called `assert` statements, because they assert what we think should be true if everything is functioning properly.

Delete the method test that is auto-generated and then copy and paste the following code within your SinglyLinkedListTest class


	SinglyLinkedList<Integer> l; //the singly linked list object we will use to test our code
	
	/**
	* @BeforeEach before the setUp() method indicates that this code will run before each test
	* In this case, we instantiate the object l of type SinglyLinkedList<Integer>
	*/
	@BeforeEach
	public void setUp() {
		l = new SinglyLinkedList<Integer>();
	}

	//mark all tests with @Test
	@Test
	void testConstructor() {
		assertTrue(l.isEmpty());
	}
	
	/**
	 * Note this technically is testing both addFirst
	 * and getFirst at the same time.
	 */
	@Test
	void testAdd() {	
		l.add(1);
		assertEquals(l.get(0), 1);
		
		l.add(2);
		assertEquals(l.get(0), 2);

		l.add(3);
		assertEquals(l.get(0), 3);
	}

		
	//the convention is to name methods as test+name of method to test + any particular subcase
	// e.g., here, test the get method, specifically what happens when requesting an index out of bounds
	@Test
	void testGetRange() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
		    l.get(1);
		  });
	}


There are four common assert statements you will use:

* `assertTrue`: takes a single parameter that is a `boolean` and checks to make sure that it is `true`.  If the argument is not true (i.e. `false`), then the test that contains the assert statement will fail.

* `assertFalse`: just like `assertTrue` except passing is `false` and failing is `true`.

* `assertEquals`: takes two parameters and checks to make sure that they are equal to each other (based on ==).  If they are not, then the test that contains the assert statement will fail.

* `assertThrows`: tests a method which throws an exception. The general syntax is:

	@Test
	void testExpectedException() {
	
		assertThrows(NameOfException.class, () -> {
			//Code under test
		});
	
	}

Should these tests pass?  To run these tests, just click on the the green run arrow.  When you do, you'll see the `JUnit` window open where the package explorer is (generally on the left) and should see a green bar indicating that your tests passed.  If the bar is red, that indicates that one or more of your tests failed.  If you want to see all of the individual tests passing, you can click the arrow to the left of `SinglyLinkedListTest` in the JUnit window and you can see the results of the individual tests.

The power of JUnit is that you can run these tests over and over again as you add new code.  This makes sure that the new things you add didn't break anything that was working already!

## Testing `SinglyLinkedList`

The three JUnit tests are a good start, but a good test suite will test all of the functionality of a class.  Write additional JUnit test cases to test all of the public methods of the `SinglyLinkedList` class.

* You should have at least one test case for each method.  Often, for more complicated methods, you should have more than one.

* Your test cases should try hard to just isolate the one method that you're testing.  This isn't always possible (e.g., in the case of `testAdd`, but you should minimize the number of methods that you call in a test.  The goal of a JUnit test is to try and precisely as possible isolate where the issue is.

* As you write your test cases, think about edge cases, e.g., when the singly linked list is empty.

Once you have a reasonable test suite. Compare your tests with another group in the lab.

* How similar are you tests?

* Were there any cases that you tested that the other group didn't (and vice versa)?

* Pick one of your test cases that you think is particularly interesting (between the two of you) and write it up on the whiteboard somewhere.

## Extending Singly Linked Lists

To give you a bit of practice actually writing linked lists (don't worry, you'll get plenty more :), modify the `SinglyLinkedList` class that we saw in class to support a `last` reference (i.e. a tail) . What operation(s) should this make faster?  Specifically:

* Add another instance variable `private Node last;`

* Go through each of the methods and change them appropriately to make sure that tail is 1) kept up to date and 2) utilized to make the methods as efficient as possible.  Note that not all methods will need changing.

## When you're done

Add JavaDoc to your JUnit test cases and do a final commit of both your new `SinglyLinkedList` class and the JUnit tests.

