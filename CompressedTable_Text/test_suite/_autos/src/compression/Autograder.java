package compression;

import static org.junit.Assert.*;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for CurDoublyLinkedList class
 *
 * @author Sean Zhu
 * @author Ross Wollman
 */
public class Autograder {
	private CurDoublyLinkedList<Integer> list;

	/**
	 * Sets up each test from scratch by creating a new empty list.
	 */
	@Before
	public void setUp() throws Exception {
		list = new CurDoublyLinkedList<Integer>();
	}

	@Test
	public void testCurDoublyLinkedList() {
		assertNull("current should be null when list initialized.", list.current);
		assertEquals("initial size of list should be 0.", 0, list.size());
		// TODO: add assertion to make sure current is not off the right
		// or left side of the list

		// should test these too, but there is conflicting definition
		// in their preconditions
		// assertFalse(list.isOffRight());
		// assertFalse(list.isOffLeft());

	}

	/**
	 * When adding new nodes using .add(), first should 
	 */
	@Test
	public void testFirst() {
		// 47
		// one element
		list.add(47);
		list.first();
		assertNotNull("current not null after calling first().", list.current);
		assertEquals("list is: 47, first is 47.", 47, list.current.item, 0);
		// off right

		// 83 <=> 47

		list.add(83);
		list.next();
		list.first();
		assertNotNull("current not null after insert.", list.current);
		assertEquals(
				"list is: 83 <=> 47, called next(), first(), then get current:",
				83, list.current.item, 0);

	}

	@Test
	public void testFirst_off() {
		// 47
		// off right
		list.add(47);
		list.next();
		list.first();
		assertNotNull(
				"current not null after calling first() on non-empty list.",
				list.current);
		assertEquals("list is: 47, called next(), first(), then get current:",
				47, list.current.item, 0);
		assertFalse("list should not be off right after calling first()",
				list.isOffRight());

		// off left
		list.back();
		list.first();
		assertFalse("list should not be off left after calling first()",
				list.isOffLeft());
	}

	@Test(expected = Exception.class)
	public void testFirst_empty() {
		list.first();
	}

	@Test
	public void testLast() {
		// 47
		// one element
		list.add(47);
		list.first();
		assertNotNull("current not null after calling last().", list.current);
		assertEquals("list is: 47, last is 47.", 47, list.current.item, 0);
		// off right

		// 83 <=> 47

		list.add(83);
		list.last();
		assertNotNull("current not null after insert.", list.current);
		assertEquals("list is: 83 <=> 47, called last(), then get current:",
				47, list.current.item, 0);
	}

	@Test
	public void testLast_off() {
		// one element
		// off right
		list.add(47);
		list.next();
		list.last();
		assertNotNull(
				"current not null after calling last() on non-empty list.",
				list.current);
		assertEquals("list is: 47, called next(), lst(), then get current:",
				47, list.current.item, 0);
		assertFalse("list should not be off right after calling lst()",
				list.isOffRight());

		// off left
		list.back();
		list.last();
		assertFalse("list should not be off left after calling last()",
				list.isOffLeft());
	}

	@Test(expected = Exception.class)
	public void testLast_empty() {
		list.last();
	}

	// Calls next() on an empty list. Replace the "NoSuchElementException"
	// with whatever exception you throw when the precondition is violated
	@Test(expected = Exception.class)
	public void testNext_EmptyList() {
		list.next();
	}

	// Calls next() on a non-empty list where current is off the right side
	// Replace with whatever exception you throw when the precondition is
	// violated
	@Test(expected = Exception.class)
	public void testNext_OffRightSide() {
		list.addFirst(47);
		list.next(); // this moves current off right
		list.next(); // this should trigger an exception

	}

	// Calls next() on non-empty list where current is head
	@Test
	public void testNext() {
		// 83 <=> 47
		list.add(47);
		list.add(83);
		list.next();
		assertEquals("list is: 83 <=> 47, called next() after add().", 47,
				list.current.item, 0);
		assertFalse(
				"list is: 83 <=> 47, called next() after add(). list is not off right.",
				list.isOffRight());
		list.next();
		assertTrue(
				"list is: 83 <=> 47, called next() and next() after add(). list should be off right.",
				list.isOffRight());

	}

	// Calls back() on an empty list. Replace the "NoSuchElementException"
	// with whatever exception you throw when the precondition is violated
	@Test(expected = Exception.class)
	public void testBack_EmptyList() {
		list.back();
	}

	// Calls back() on a non-empty list where current is off the left side
	// Replace with whatever exception you throw when the precondition is
	// violated
	@Test(expected = Exception.class)
	public void testBack_OffLeftSide() {
		list.addFirst(47);
		list.back(); // this moves current off left
		list.back(); // this should trigger an exception
	}

	@Test
	public void testBack() {
		list.add(47);
		list.back();
		assertTrue(
				"list is: 47, called back() after add(). list should be off left.",
				list.isOffLeft());

		list.add(83);
		list.next();
		list.back();
		assertEquals(
				"list is: 83 <=> 47, called add(), back(), add(), next(), and back(). current at 83.",
				83, list.current.item, 0);
		assertFalse(
				"list is: 83 <=> 47, called add(), back(), add(), next(), and back(). list is not off left.",
				list.isOffLeft());
	}

	@Test
	public void testIsOffRight() {
		list.add(47);
		list.next();
		assertTrue(
				"list is: 47, called next() after add(). list should be off right.",
				list.isOffRight());
	}

	@Test
	public void testIsOffLeft() {
		list.add(47);
		list.back();
		assertTrue(
				"list is: 47, called back() after add(). list should be off left.",
				list.isOffLeft());
	}

	@Test
	public void testIsOff() {
		list.add(47);
		list.back();
		assertTrue(
				"list is: 47, called back() after add(). list should be off.",
				list.isOff());

		list.next();
		list.next();
		assertTrue(
				"list is: 47, called back(), next(), next() after add(). list should be off.",
				list.isOff());
	}

	// Calls currentValue() on an empty list. Replace the
	// "NoSuchElementException"
	// with whatever exception you throw when the precondition is violated
	@Test(expected = Exception.class)
	public void testCurrentValue_EmptyList() {
		list.currentValue();
	}

	// Calls currentValue() on non-empty list where current is off right.
	// Replace with whatever exception you throw when precondition is violated
	@Test(expected = Exception.class)
	public void testCurrentValue_isOff() {
		list.addFirst(47);
		list.next(); // this moves current off right
		list.currentValue(); // this should trigger an exception
	}

	@Test
	public void testCurrentValue() {
		// 134 <=> 84 <=> 47
		list.add(47);
		list.add(84);
		list.add(134);

		list.first(); // current points at 134
		assertEquals("list is 134 <=> 84 <=> 47, called first().", 134,
				list.currentValue(), 0);

		list.next(); // current points at 84
		assertEquals("list is 134 <=> 84 <=> 47, called first(), next().", 84,
				list.currentValue(), 0);

		list.next(); // current points at 47
		assertEquals(
				"list is 134 <=> 84 <=> 47, called first(), next(), next().",
				47, list.currentValue(), 0);
	}

	@Test
	public void testAddAfterCurrent() {
		// 47
		list.add(47);
		list.first();
		assertEquals("list is 47, called add(), first(). Size should be 1.", 1,
				list.size());
		assertEquals(
				"list is 47, called add(), first(). current should be 47.", 47,
				list.current.item, 0);

		// 47 <=> 83
		list.addAfterCurrent(83);

		assertEquals(
				"list is 47 <=> 83, called add(), first(), addAfterCurrent(). Size should be 2.",
				2, list.size());
		assertEquals(
				"list is 47 <=> 83, called add(), first(), addAfterCurrent(). Current should be 83.",
				83, list.current.item, 0); // current should point to
		// new node
		// 47 <=> 134 <=> 83
		list.back();
		list.addAfterCurrent(134);

		assertEquals(
				"list is 47 <=> 83, called add(), first(), addAfterCurrent(), back(), addAfterCurrent(). Size should be 3.",
				3, list.size());
		assertEquals(
				"list is 47 <=> 83, called add(), first(), addAfterCurrent(), back(), addAfterCurrent(). Current should be 134.",
				134, list.current.item, 0); // current should point to
		// new node
	}

	@Test(expected = Exception.class)
	public void testAddAfterCurrent_null() {
		list.add(47);
		list.addAfterCurrent(null);
	}

	@Test(expected = Exception.class)
	public void testAddAfterCurrent_isOff() {
		list.add(47);
		list.next();
		list.addAfterCurrent(83);
	}

	@Test
	public void testRemoveCurrent() {
		list.add(47);
		list.add(83);
		list.last();
		list.removeCurrent();
		// when removing the last value, check that current becomes null
		// and the size of the list is 1 (having started with 2 elts)
		assertEquals(
				"list is 83, called add(47), add(83), last(), removeCurrent(). Size should be 1.",
				list.size(), 1, 0);
		assertEquals(
				"list is 83, called add(47), add(83), last(), removeCurrent(). current should be null.",
				list.current, null);
		assertTrue(
				"list is 83, called add(47), add(83), last(), removeCurrent(). current should be off.",
				list.isOff());

		list.first();
		list.removeCurrent();
		// when removing the the only value, check that the
		// current value becomes the next one
		assertEquals(
				"list should be empty. called add(47), add(83), last(), removeCurrent(), first(), removeCurrent().",
				list.size(), 0, 0);
		assertEquals(
				"current should be null. called add(47), add(83), last(), removeCurrent(), first(), removeCurrent().",
				list.current, null);
		// assertTrue(list.isOff());

		// check that we can remove a value in the middle of a list
		// 47 <=> 134 <=> 83
		list.add(83);
		list.add(134);
		list.add(47);
		list.first();
		list.next();
		list.removeCurrent();

		assertEquals(
				"list was 47 <=> 134 <=> 83 and removed 134. current at 83.",
				83, list.current.item, 0);
		list.first();
		assertEquals(
				"list was 47 <=> 134 <=> 83 and removed 134 and called first(). current at 47.",
				47, list.current.item, 0);
	}

	@Test(expected = Exception.class)
	public void testRemoveCurrent_empty() {
		list.removeCurrent();
	}

	@Test(expected = Exception.class)
	public void testRemoveCurrent_isOff() {
		list.add(47);
		list.next();
		list.removeCurrent();
	}

	@Test
	public void testAddFirst() {
		list.addFirst(47);
		assertEquals("current should be 47 after addFirst(47).", 47,
				list.currentValue(), 0);

		list.addFirst(83);
		assertEquals(
				"current should be (83) <=> 47 after addFirst(47), addFirst(83).",
				83, list.currentValue(), 0);
		list.first();
		assertEquals(
				"current should be (83) <=> 47 after addFirst(47), addFirst(83), first().",
				83, list.currentValue(), 0);
	}

	@Test
	public void testAddFirst_afterOff() {
		list.addFirst(47);
		list.back();
		list.addFirst(83);
		assertFalse("list should change to not off left after addFirst()",
				list.isOffLeft());
		list.next();
		list.next();
		list.addFirst(134);
		assertFalse("list should change to not off right after addFirst()",
				list.isOffRight());
	}

	@Test(expected = Exception.class)
	public void testAddFirst_null() {
		list.add(47);
		list.addFirst(null);
	}

	@Test
	public void testRemoveFirst() {
		list.add(47);
		list.add(83);
		list.removeFirst();
		assertEquals("list was 83 <=> 47, and removed first", 47,
				list.current.item, 0);
	}

	@Test
	public void testRemoveFirst_afterOffLeft() {
		list.add(47);
		list.add(83);
		list.back();
		list.removeFirst();
		assertFalse(
				"list was 83 <=> 47. list should change to not off left after removeFirst()",
				list.isOffLeft());
	}

	@Test
	public void testAddLast() {
		list.addLast(47);
		assertEquals("list after addLast(47) should be 47, current at 47.", 47,
				list.currentValue(), 0);

		list.addLast(83);
		assertEquals(
				"list after addLast(47), addLast(83) should be 47 <=> 83, current at 83.",
				83, list.currentValue(), 0);
	}

	@Test
	public void testAddLast_off() {
		list.addLast(47);
		list.next();
		list.addLast(83);
		assertFalse("list should change to not off right after addLast()",
				list.isOffRight());

		list.back();
		list.back();
		list.addLast(134);
		assertFalse("list should change to not off left after addLast()",
				list.isOffLeft());
	}

	@Test(expected = Exception.class)
	public void testAddLast_null() {
		list.addLast(null);
	}

	@Test
	public void testRemoveLast_afterOffLeft() {
		list.add(47);
		list.add(83);
		list.back();
		list.removeLast();
		assertFalse("list should change to not off left after removeLast()",
				list.isOffLeft());
	}

	@Test
	public void testRemoveLast() {
		list.add(47);
		list.add(83);
		list.removeLast();
		assertTrue("list should be off right after removeLast()",
				list.isOffRight());
		assertEquals("current should be null after removeLast()", list.current,
				null);
	}

	@Test
	public void testGetFirst() {
		list.add(47);
		assertEquals("list is 47, called getFirst().", 47, list.getFirst(), 0);

		list.add(83);
		assertEquals("list is 83<=>47, called getFirst().", 83,
				list.getFirst(), 0);

		list.next();
		assertEquals("list is 83<=>47, current at 47, getCurrent().",
				list.currentValue(), 47, 0);
		assertEquals("list is 83<=>47, current at 47, then called getFirst().",
				83, list.getFirst(), 0);
		assertEquals(
				"list is 83<=>47, current was at 47, then called getCurrent() after getFirst().",
				list.currentValue(), 83, 0);
	}

	@Test
	public void testGetFirst_afterOff() {
		list.add(47);
		list.back();
		assertEquals(
				"list is 47. getFirst should change current to 47 after off left",
				47, list.getFirst(), 0);
		assertFalse("list should change to not off left after getFirst()",
				list.isOffLeft());

		list.next();
		assertEquals(
				"list is 47. getFirst should change current to 47 after off right",
				47, list.getFirst(), 0);
		assertFalse("list should change to not off right after getFirst()",
				list.isOffRight());
	}

	@Test
	public void testGetLast() {
		list.add(47);
		assertEquals("list is 47, called getLast().", 47, list.getLast(), 0);

		list.add(83);
		assertEquals("list is 83<=>47, current at 83, then called getLast().",
				47, list.getLast(), 0);
		assertEquals(
				"list is 83<=>47, current was at 83, then called getLast(), current should be at 47.",
				47, list.current.item, 0);
	}

	@Test
	public void testGetLast_afterOff() {
		list.add(47);
		list.back();
		assertEquals(
				"list is 47. getLast should change current to 47 after off left",
				47, list.getLast(), 0);
		assertFalse("list should change to not off left after getLast()",
				list.isOffLeft());

		list.next();
		assertEquals(
				"list is 47. getLast should change current to 47 after off right",
				47, list.getLast(), 0);
		assertFalse("list should change to not off right after getLast()",
				list.isOffRight());
	}

	@Test
	public void testClear() {
		list.add(47);
		list.add(83);
		list.clear();
		assertNull("current should be null after clear()", list.current);
		assertEquals("size should be 0 after clear()", 0, list.size());
		// TODO: add assertion to make sure current is not off the right
		// or left side of the list

		// assertFalse("off right should be false after clear()",
		// list.isOffRight());
		// assertFalse("off left should be false after clear()",
		// list.isOffLeft());

	}

	// public static void main(String[] args) {
	// 	Result result = JUnitCore.runClasses(CurDoublyLinkedListTest.class);
	// 	double grade = 0;
	// 	if (result.wasSuccessful()) {
	// 		System.out.println("All CurDoublyLinkedList Tests passed.");
	// 		grade = 4.;
	// 	} else {
	// 		System.out.println("Some CurDoublyLinkedList Tests failed:\n");
	// 		for (Failure failure : result.getFailures()) {
	// 			System.out.println(failure.toString() + "\n");
	// 		}
	// 		grade = ((double) (result.getRunCount() - result.getFailureCount()) / result
	// 				.getRunCount()) * 4.;
	// 	}
	// 	System.out.println("Grading for CurDoublyLinkedList finished.");
	// 	System.out.println("Suggested grade: " + grade + "/4");
	// 	System.out.println("Please check the pdf file and enter "
	// 			+ "a final grade for CurDoublyLinkedList:");
	//
	// }
}
