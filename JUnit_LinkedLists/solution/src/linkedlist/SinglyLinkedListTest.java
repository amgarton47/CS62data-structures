package linkedlist;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SinglyLinkedListTest {
	
	SinglyLinkedList<Integer> l;
	
	/**
	* @BeforeEach before the setUp() method indicates that this code will run before each test
	* In this case, we instantiate the object l of type SinglyLinkedList<Integer>
	*/
	@BeforeEach
	public void setUp() {
		l = new SinglyLinkedList<Integer>();
	}

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

	@Test
	void testgetRange() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
		    l.get(0);
		  });
	}
	
	void testRemove() {
		l.add(1);
		l.add(2);
		l.add(3);
		
		assertEquals(l.remove(),3);
		assertEquals(l.remove(),2);
		assertEquals(l.remove(),1);
		assertTrue(l.isEmpty());
	}

}
