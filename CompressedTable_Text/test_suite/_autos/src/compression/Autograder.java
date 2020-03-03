package compression;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
	@BeforeEach
	public void setUp() throws Exception {
		list = new CurDoublyLinkedList<Integer>();
	}

	@Test
	public void testCurDoublyLinkedList() {
		assertNull(list.current, "current should be null when list initialized.");
		assertEquals(0, list.size(), "initial size of list should be 0.");
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
		assertNotNull(list.current, "current not null after calling first().");
		assertEquals(47, (int) list.current.item, "list is: 47, first is 47.");
		// off right

		// 83 <=> 47

		list.add(83);
		list.next();
		list.first();
		assertNotNull(list.current, "current not null after insert.");
		assertEquals( 83, (int) list.current.item,
				"list is: 83 <=> 47, called next(), first(), then get current:");

	}

	@Test
	public void testFirst_off() {
		// 47
		// off right
		list.add(47);
		list.next();
		list.first();
		assertNotNull( list.current,
				"current not null after calling first() on non-empty list.");
		assertEquals( 47, (int) list.current.item,
				"list is: 47, called next(), first(), then get current:");
		assertFalse(list.isOffRight(),
			"list should not be off right after calling first()");

		// off left
		list.back();
		list.first();
		assertFalse(list.isOffLeft(),
			"list should not be off left after calling first()");
	}

	@Test
	public void testFirst_empty() {
		assertThrows(Exception.class, () -> {list.first();} );
	}

	@Test
	public void testLast() {
		// 47
		// one element
		list.add(47);
		list.first();
		assertNotNull(list.current, "current not null after calling last().");
		assertEquals(47, (int) list.current.item, "list is: 47, last is 47.");
		// off right

		// 83 <=> 47

		list.add(83);
		list.last();
		assertNotNull(list.current, "current not null after insert.");
		assertEquals(47, (int) list.current.item,
				"list is: 83 <=> 47, called last(), then get current:");
	}

	@Test
	public void testLast_off() {
		// one element
		// off right
		list.add(47);
		list.next();
		list.last();
		assertNotNull(list.current,
				"current not null after calling last() on non-empty list.");
		assertEquals(47, (int) list.current.item,
				"list is: 47, called next(), lst(), then get current:");
		assertFalse(list.isOffRight(),
				"list should not be off right after calling lst()");

		// off left
		list.back();
		list.last();
		assertFalse( list.isOffLeft(),
				"list should not be off left after calling last()");
	}

	@Test
	public void testLast_empty() {
		assertThrows(Exception.class, () -> {list.last();} );
	}

	// Calls next() on an empty list. Replace the "NoSuchElementException"
	// with whatever exception you throw when the precondition is violated
	@Test
	public void testNext_EmptyList() {
		assertThrows(Exception.class, () -> {list.next();} );
	}

	// Calls next() on a non-empty list where current is off the right side
	// Replace with whatever exception you throw when the precondition is
	// violated
	@Test
	public void testNext_OffRightSide() {
		list.addFirst(47);
		list.next(); // this moves current off right
		assertThrows(Exception.class, () -> {list.next();} );
	}

	// Calls next() on non-empty list where current is head
	@Test
	public void testNext() {
		// 83 <=> 47
		list.add(47);
		list.add(83);
		list.next();
		assertEquals( 47, (int) list.current.item,
				"list is: 83 <=> 47, called next() after add().");
		assertFalse(list.isOffRight(),
				"list is: 83 <=> 47, called next() after add(). list is not off right.");
		list.next();
		assertTrue(list.isOffRight(),
				"list is: 83 <=> 47, called next() and next() after add(). list should be off right.");

	}

	// Calls back() on an empty list. Replace the "NoSuchElementException"
	// with whatever exception you throw when the precondition is violated
	@Test
	public void testBack_EmptyList() {
		assertThrows(Exception.class, () -> {list.back();} );
	}

	// Calls back() on a non-empty list where current is off the left side
	// Replace with whatever exception you throw when the precondition is
	// violated
	@Test
	public void testBack_OffLeftSide() {
		list.addFirst(47);
		list.back(); // this moves current off left
		assertThrows(Exception.class, () -> {list.back();} );
	}

	@Test
	public void testBack() {
		list.add(47);
		list.back();
		assertTrue( list.isOffLeft(),
				"list is: 47, called back() after add(). list should be off left.");

		list.add(83);
		list.next();
		list.back();
		assertEquals(83, (int) list.current.item,
				"list is: 83 <=> 47, called add(), back(), add(), next(), and back(). current at 83.");
		assertFalse( list.isOffLeft(),
				"list is: 83 <=> 47, called add(), back(), add(), next(), and back(). list is not off left.");
	}

	@Test
	public void testIsOffRight() {
		list.add(47);
		list.next();
		assertTrue( list.isOffRight(),
				"list is: 47, called next() after add(). list should be off right.");
	}

	@Test
	public void testIsOffLeft() {
		list.add(47);
		list.back();
		assertTrue( list.isOffLeft(),
				"list is: 47, called back() after add(). list should be off left.");
	}

	@Test
	public void testIsOff() {
		list.add(47);
		list.back();
		assertTrue(list.isOff(),
				"list is: 47, called back() after add(). list should be off.");

		list.next();
		list.next();
		assertTrue(list.isOff(),
				"list is: 47, called back(), next(), next() after add(). list should be off.");
	}

	// Calls currentValue() on an empty list. Replace the
	// "NoSuchElementException"
	// with whatever exception you throw when the precondition is violated
	@Test
	public void testCurrentValue_EmptyList() {
		assertThrows(Exception.class, () -> {list.currentValue();} );
	}

	// Calls currentValue() on non-empty list where current is off right.
	// Replace with whatever exception you throw when precondition is violated
	@Test
	public void testCurrentValue_isOff() {
		list.addFirst(47);
		list.next(); // this moves current off right
		assertThrows(Exception.class, () -> {list.currentValue();} );
	}

	@Test
	public void testCurrentValue() {
		// 134 <=> 84 <=> 47
		list.add(47);
		list.add(84);
		list.add(134);

		list.first(); // current points at 134
		assertEquals(134, (int) list.currentValue(),
				"list is 134 <=> 84 <=> 47, called first().");

		list.next(); // current points at 84
		assertEquals(84, (int) list.currentValue(),
				"list is 134 <=> 84 <=> 47, called first(), next().");

		list.next(); // current points at 47
		assertEquals(47, (int) list.currentValue(),
				"list is 134 <=> 84 <=> 47, called first(), next(), next().");
	}

	@Test
	public void testAddAfterCurrent() {
		// 47
		list.add(47);
		list.first();
		assertEquals(1, (int) list.size(),
				"list is 47, called add(), first(). Size should be 1.");
		assertEquals(47, (int) list.current.item,
				"list is 47, called add(), first(). current should be 47.");

		// 47 <=> 83
		list.addAfterCurrent(83);

		assertEquals(2, (int) list.size(),
				"list is 47 <=> 83, called add(), first(), addAfterCurrent(). Size should be 2.");
		assertEquals(83, (int) list.current.item,
				"list is 47 <=> 83, called add(), first(), addAfterCurrent(). Current should be 83.");
		// new node
		// 47 <=> 134 <=> 83
		list.back();
		list.addAfterCurrent(134);

		assertEquals(3, (int) list.size(),
				"list is 47 <=> 83, called add(), first(), addAfterCurrent(), back(), addAfterCurrent(). Size should be 3.");
		assertEquals(134, (int) list.current.item,
				"list is 47 <=> 83, called add(), first(), addAfterCurrent(), back(), addAfterCurrent(). Current should be 134.");
		// new node
	}

	@Test
	public void testAddAfterCurrent_null() {
		list.add(47);
		assertThrows(Exception.class, () -> {list.addAfterCurrent(null);} );
	}

	@Test
	public void testAddAfterCurrent_isOff() {
		list.add(47);
		list.next();
		assertThrows(Exception.class, () -> {list.addAfterCurrent(83);} );
	}

	@Test
	public void testRemoveCurrent() {
		list.add(47);
		list.add(83);
		list.last();
		list.removeCurrent();
		// when removing the last value, check that current becomes null
		// and the size of the list is 1 (having started with 2 elts)
		assertEquals((int) list.size(), 1,
				"list is 83, called add(47), add(83), last(), removeCurrent(). Size should be 1.");
		assertEquals(list.current, null,
				"list is 83, called add(47), add(83), last(), removeCurrent(). current should be null.");
		assertTrue(list.isOff(),
				"list is 83, called add(47), add(83), last(), removeCurrent(). current should be off.");

		list.first();
		list.removeCurrent();
		// when removing the the only value, check that the
		// current value becomes the next one
		assertEquals((int) list.size(), 0,
				"list should be empty. called add(47), add(83), last(), removeCurrent(), first(), removeCurrent().");
		assertEquals(list.current, null,
				"current should be null. called add(47), add(83), last(), removeCurrent(), first(), removeCurrent().");
		// assertTrue(list.isOff());

		// check that we can remove a value in the middle of a list
		// 47 <=> 134 <=> 83
		list.add(83);
		list.add(134);
		list.add(47);
		list.first();
		list.next();
		list.removeCurrent();

		assertEquals(83, (int) list.current.item,
				"list was 47 <=> 134 <=> 83 and removed 134. current at 83.");
		list.first();
		assertEquals(47, (int) list.current.item,
				"list was 47 <=> 134 <=> 83 and removed 134 and called first(). current at 47.");
	}

	@Test
	public void testRemoveCurrent_empty() {
		assertThrows(Exception.class, () -> {list.removeCurrent();} );
	}

	@Test
	public void testRemoveCurrent_isOff() {
		list.add(47);
		list.next();
		assertThrows(Exception.class, () -> {list.removeCurrent();} );
	}

	@Test
	public void testAddFirst() {
		list.addFirst(47);
		assertEquals(47, (int) list.currentValue(),
				"current should be 47 after addFirst(47).");

		list.addFirst(83);
		assertEquals(83, (int) list.currentValue(),
				"current should be (83) <=> 47 after addFirst(47), addFirst(83).");
		list.first();
		assertEquals(83, (int) list.currentValue(),
				"current should be (83) <=> 47 after addFirst(47), addFirst(83), first().");
	}

	@Test
	public void testAddFirst_afterOff() {
		list.addFirst(47);
		list.back();
		list.addFirst(83);
		assertFalse(list.isOffLeft(),
				"list should change to not off left after addFirst()");
		list.next();
		list.next();
		list.addFirst(134);
		assertFalse(list.isOffRight(),
				"list should change to not off right after addFirst()");
	}

	@Test
	public void testAddFirst_null() {
		list.add(47);
		assertThrows(Exception.class, () -> {list.addFirst(null);} );
	}

	@Test
	public void testRemoveFirst() {
		list.add(47);
		list.add(83);
		list.removeFirst();
		assertEquals(47, (int) list.current.item,
				"list was 83 <=> 47, and removed first");
	}

	@Test
	public void testRemoveFirst_afterOffLeft() {
		list.add(47);
		list.add(83);
		list.back();
		list.removeFirst();
		assertFalse(list.isOffLeft(),
				"list was 83 <=> 47. list should change to not off left after removeFirst()");
	}

	@Test
	public void testAddLast() {
		list.addLast(47);
		assertEquals(47, (int) list.currentValue(),
				"list after addLast(47) should be 47, current at 47.");

		list.addLast(83);
		assertEquals(83, (int) list.currentValue(),
				"list after addLast(47), addLast(83) should be 47 <=> 83, current at 83.");
	}

	@Test
	public void testAddLast_off() {
		list.addLast(47);
		list.next();
		list.addLast(83);
		assertFalse(list.isOffRight(),
				"list should change to not off right after addLast()");

		list.back();
		list.back();
		list.addLast(134);
		assertFalse(list.isOffLeft(),
				"list should change to not off left after addLast()");
	}

	@Test
	public void testAddLast_null() {
		assertThrows(Exception.class, () -> {list.addLast(null);} );
	}

	@Test
	public void testRemoveLast_afterOffLeft() {
		list.add(47);
		list.add(83);
		list.back();
		list.removeLast();
		assertFalse(list.isOffLeft(),
				"list should change to not off left after removeLast()");
	}

	@Test
	public void testRemoveLast() {
		list.add(47);
		list.add(83);
		list.removeLast();
		assertTrue(list.isOffRight(),
				"list should be off right after removeLast()");
		assertEquals( list.current, null,
				"current should be null after removeLast()");
	}

	@Test
	public void testGetFirst() {
		list.add(47);
		assertEquals(47, (int) list.getFirst(),
				"list is 47, called getFirst().");

		list.add(83);
		assertEquals(83, (int) list.getFirst(),
				"list is 83<=>47, called getFirst().");

		list.next();
		assertEquals((int) list.currentValue(), 47,
				"list is 83<=>47, current at 47, getCurrent().");
		assertEquals(83, (int) list.getFirst(),
				"list is 83<=>47, current at 47, then called getFirst().");
		assertEquals((int) list.currentValue(), 83,
				"list is 83<=>47, current was at 47, then called getCurrent() after getFirst().");
	}

	@Test
	public void testGetFirst_afterOff() {
		list.add(47);
		list.back();
		assertEquals(47, (int) list.getFirst(),
				"list is 47. getFirst should change current to 47 after off left");
		assertFalse( list.isOffLeft(),
				"list should change to not off left after getFirst()");

		list.next();
		assertEquals(47, (int) list.getFirst(),
				"list is 47. getFirst should change current to 47 after off right");
		assertFalse(list.isOffRight(),
				"list should change to not off right after getFirst()");
	}

	@Test
	public void testGetLast() {
		list.add(47);
		assertEquals(47, (int) list.getLast(),
				"list is 47, called getLast().");

		list.add(83);
		assertEquals(47, (int) list.getLast(),
				"list is 83<=>47, current at 83, then called getLast().");
		assertEquals(47, (int) list.current.item,
				"list is 83<=>47, current was at 83, then called getLast(), current should be at 47.");
	}

	@Test
	public void testGetLast_afterOff() {
		list.add(47);
		list.back();
		assertEquals(47, (int) list.getLast(),
				"list is 47. getLast should change current to 47 after off left");
		assertFalse(list.isOffLeft(),
				"list should change to not off left after getLast()");

		list.next();
		assertEquals(47, (int) list.getLast(),
				"list is 47. getLast should change current to 47 after off right");
		assertFalse(list.isOffRight(),
				"list should change to not off right after getLast()");
	}

	@Test
	public void testClear() {
		list.add(47);
		list.add(83);
		list.clear();
		assertNull(list.current, "current should be null after clear()");
		assertEquals(0, list.size(), "size should be 0 after clear()");
		// TODO: add assertion to make sure current is not off the right
		// or left side of the list

		// assertFalse("off right should be false after clear()",
		// list.isOffRight());
		// assertFalse("off left should be false after clear()",
		// list.isOffLeft());

	}
}
