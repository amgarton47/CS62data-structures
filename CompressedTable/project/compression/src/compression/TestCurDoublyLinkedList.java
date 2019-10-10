
package compression;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


/**
 * Unit tests for CurDoublyLinkedList class
 *	@author
 *	@version
 */

public class TestCurDoublyLinkedList {
	CurDoublyLinkedList<Integer> list;

	@Before
	public void setUp() throws Exception {
		list = new CurDoublyLinkedList<Integer>();
	}

	@Test
	public void testCurDoublyLinkedList() {
		assertNull(list.current);
		assertEquals(0, list.size());		
		// TODO: add assertion to make sure current is not off the right
		// or left side of the list
	}

	@Test
	public void testFirst() {
		// 83 <=> 47
		list.add(47);
		list.add(83);	
		list.first();			
		assertNotNull(list.current);
		assertEquals(83, (int) list.current.item, 0);
	}

	@Test
	public void testLast() {
		// 83 <=> 47
		list.add(47);
		list.add(83);	
		list.last();

		assertNotNull(list.current);
		assertEquals(47, (int) list.current.item, 0);
	}


	// Calls next() on an empty list. Replace the "NoSuchElementException"
	// with whatever exception you throw when the precondition is violated
	@Test(expected=NoSuchElementException.class)
	public void testNext_EmptyList() {
		list.next();
	}

	// Calls next() on a non-empty list where current is off the right side
	// Replace with whatever exception you throw when the precondition is violated 
	@Test(expected=NoSuchElementException.class)	
	public void testNext_OffRightSide() {
		list.addFirst(47);
		list.next(); // this moves current off right
		list.next(); //	this should trigger an exception
	}

	// Calls next() on non-empty list where current is head
	@Test
	public void testNext() {
		// 83 <=> 47
		list.add(47);
		list.add(83);
		list.first();

		assertNotNull(list.current);
		assertEquals(83, (int) list.current.item, 0);

		list.next();
		assertEquals(47, (int) list.current.item, 0);
	}


	@Test
	public void testBack() {
		fail("Not yet implemented");
	}

	
	@Test
	public void testIsOffRight() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsOffLeft() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsOff() {
		fail("Not yet implemented");
	}

	// Calls currentValue() on an empty list. Replace the "NoSuchElementException"
	// with whatever exception you throw when the precondition is violated
	@Test(expected=NoSuchElementException.class)
	public void testCurrentValue_EmptyList() {
		list.currentValue();
	}

	// Calls currentValue() on non-empty list where current is off right.
	// Replace with whatever exception you throw when precondition is violated
	@Test(expected=NoSuchElementException.class)	
	public void testCurrentValue_isOff() {
		list.addFirst(47);
		list.next();  // this moves current off right
		list.currentValue(); // this should trigger an exception
	}
	
	@Test
	public void testCurrentValue() {
		// 134 <=> 84 <=> 47
		list.add(47);
		list.add(84);
		list.add(134);
		
		list.first(); // current points at 134
		assertEquals(134, list.currentValue(), 0);
		
		list.next(); // current points at 84
		assertEquals(84, list.currentValue(), 0);
		
		list.next(); // current points at 47
		assertEquals(47, list.currentValue(), 0);		
	}
	
	@Test
	public void testAddAfterCurrent() {
		// 47
		list.add(47);
		list.first();
		assertEquals(1, list.size());
		assertEquals(47, (int) list.current.item, 0);
		
		// 47 <=> 83
		list.addAfterCurrent(83);
		
		assertEquals(2, list.size());
		assertEquals(83, (int) list.current.item, 0); // current should point to new node		
	}

	@Test
	public void testRemoveCurrent() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddFirstE() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveFirst() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddLastE() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveLast() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFirst() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetLast() {
		fail("Not yet implemented");
	}

	@Test
	public void testClear() {
		list.add(47);
		list.add(83);
		list.clear();		
		assertNull(list.current);
		assertEquals(0, list.size());
		// TODO: add assertion to make sure current is not off the right
		// or left side of the list
	}

}
