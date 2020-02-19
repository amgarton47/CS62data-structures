package linkedlist;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LinkedListTest {
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
	
	@Test
	void testRemoveFirst() {
		LinkedList<Integer> l = new LinkedList<Integer>();
		
		l.addFirst(1);
		l.addFirst(2);
		l.addFirst(3);
		
		assertTrue(l.removeFirst() == 3);
		assertTrue(l.removeFirst() == 2);
		assertTrue(l.removeFirst() == 1);
		assertTrue(l.removeFirst() == null);
	}

}
