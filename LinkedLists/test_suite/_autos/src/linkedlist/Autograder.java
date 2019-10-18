package linkedlist;

import java.util.Iterator;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import java.util.concurrent.TimeUnit;

/**
 * Auto-grader unit tests for <strong>Linked List lab</strong>.
 *
 * @author Mark Kampe
 */
public class Autograder {


	/*
	 * this testing class gives us named nodes
	 */
	private class Named_DLL_Node extends DLL_Node {
		public String nodeName;

		public Named_DLL_Node(String name) {
			nodeName = name;
		}

		/*
		 * render the list (starting from this node) as a string
		 *
		 *	we do this ourselves, so we can distinguish between iterator
		 *  bugs and insert/remove bugs.
		 */
		public String toString() {
			// check for non lists
			if (this.prev == null || this.next == null)
				return "[]";


			String contents = "";
			int seen = 0;
			Named_DLL_Node current = this;

			while(current != this || seen == 0) {
				if (!contents.equals(""))
					contents = contents + ",";
				contents = contents + current.nodeName;
				seen++;
				current = (Named_DLL_Node) current.next;
			}

			return '[' + contents + ']';
		}

		/*
		 * render list (starting from this node) as a string
		 *	using the submitted iterator
		 */
		public String walk() {
			String contents = "";
			Iterator<DLL_Node> it = this.iterator();
			while(it.hasNext()) {
				Named_DLL_Node n = (Named_DLL_Node) it.next();
				if (!contents.equals(""))
					contents = contents + ",";
				if (n != null)
					contents = contents + n.nodeName;
				else {
					contents = contents + "!NULL!";
					break;
				}
			}

			return "[" + contents + "]";
		}
	}

	// protect ourselves against loops in the iterator
	@Rule
	public Timeout globalTiemout = new Timeout(1000, TimeUnit.MILLISECONDS);

	/* 
	 * DLL_Node test cases
	 *
	 *	for insert(), remove() and iterator()
	 */
	// [a], b.insert(a) -> [a,b]
	@Test
	public void insert_at_end() {
		Named_DLL_Node a = new Named_DLL_Node("a");
		Named_DLL_Node b = new Named_DLL_Node("b");
		b.insert(a);
		assertEquals("[a], b.insert(a)", "[a,b]", a.toString());
	}

	// [a,c], b.insert(a) -> [a,b,c]
	@Test
	public void insert_in_middle() {
		Named_DLL_Node a = new Named_DLL_Node("a");
		Named_DLL_Node b = new Named_DLL_Node("b");
		Named_DLL_Node c = new Named_DLL_Node("c");
		c.insert(a);
		b.insert(a);
		assertEquals("[a,b] c.insert(a), b.insert(a)", "[a,b,c]", a.toString());
	}

	// [a], a.remove() -> []
	@Test
	public void remove_only() {
		Named_DLL_Node a = new Named_DLL_Node("a");
		a.remove();
		assertEquals("[a], a.remove", "[]", a.toString());
	}

	// [a,b,c], a.remove() -> [b,c]
	@Test
	public void remove_first() {
		Named_DLL_Node a = new Named_DLL_Node("a");
		Named_DLL_Node b = new Named_DLL_Node("b");
		Named_DLL_Node c = new Named_DLL_Node("c");
		b.insert(a);
		c.insert(b);
		a.remove();
		assertEquals("[a,b,c], a.remove", "[b,c]", b.toString());
	}

	// [a,b,c], b.remove() -> [a,c]
	@Test
	public void remove_middle() {
		Named_DLL_Node a = new Named_DLL_Node("a");
		Named_DLL_Node b = new Named_DLL_Node("b");
		Named_DLL_Node c = new Named_DLL_Node("c");
		b.insert(a);
		c.insert(b);
		b.remove();
		assertEquals("[a,b,c], b.remove", "[a,c]", a.toString());
	}

	// [a,b,c], c.remove() -> [a,b]
	@Test
	public void remove_end() {
		Named_DLL_Node a = new Named_DLL_Node("a");
		Named_DLL_Node b = new Named_DLL_Node("b");
		Named_DLL_Node c = new Named_DLL_Node("c");
		b.insert(a);
		c.insert(b);
		c.remove();
		assertEquals("[a,b,c], c.remove", "[a,b]", a.toString());
	}

	@Test
	public void iterate_none() {
		Named_DLL_Node a = new Named_DLL_Node("a");
		a.remove();
		// we did not tell them how to handle this error
		try {
			String result = a.walk();
			assertEquals("[a], a.remove", "[]", a.walk());
		} catch (IllegalArgumentException x) {
			assertTrue("[a], a.remove -> EXCEPTION", true);
		} catch (NullPointerException x) {
			assertTrue("[a], a.remove -> EXCEPTION", true);
		}
	}

	@Test
	public void iterate_one() {
		Named_DLL_Node a = new Named_DLL_Node("a");
		assertEquals("iterate [a]", "[a]", a.walk());
	}

	@Test
	public void iterate_two() {
		Named_DLL_Node a = new Named_DLL_Node("a");
		Named_DLL_Node b = new Named_DLL_Node("b");
		b.insert(a);
		assertEquals("iterate [a,b]", "[a,b]", a.walk());
	}

	@Test
	public void iterate_three() {
		Named_DLL_Node a = new Named_DLL_Node("a");
		Named_DLL_Node b = new Named_DLL_Node("b");
		Named_DLL_Node c = new Named_DLL_Node("c");
		b.insert(a);
		c.insert(b);
		assertEquals("iterate [a,b,c]", "[a,b,c]", a.walk());
	}


	/**
	 * helper method to render an ordered list as a string
	 *
	 * @param: head
	 *
	 *	we do this ourselves so we can distinguish insert/remove
	 *	bugs from iterator bugs
	 */
	public String toString(Ordered_DLL head) {
		// check for non lists
		if (head.prev == null || head.next == null)
			return "[]";


		String contents = "";
		int seen = 0;
		Ordered_DLL current = head;

		while(current != head || seen == 0) {
			if (!contents.equals(""))
				contents = contents + ",";
			contents = contents + current.ordinal;
			seen++;
			current = (Ordered_DLL) current.next;
		}

		return '[' + contents + ']';
	}

	// [0] + 1 = [0,1]
	@Test
	public void add_one() {
		Ordered_DLL head = new Ordered_DLL(0);
		Ordered_DLL one = new Ordered_DLL(1);
		one.insert(head);
		assertEquals("[0]+1 = [0,1]", "[0,1]", toString(head));
	}

	// [0,1] + 5 = [0,1,5]
	@Test
	public void add_one_five() {
		Ordered_DLL head = new Ordered_DLL(0);
		Ordered_DLL one = new Ordered_DLL(1);
		Ordered_DLL five = new Ordered_DLL(5);
		one.insert(head);
		five.insert(head);
		assertEquals("[0,1]+5 = [0,1,5]", "[0,1,5]", toString(head));
	}

	// [0,1,5] + 3 = [0,1,3,5]
	@Test
	public void add_one_five_three() {
		Ordered_DLL head = new Ordered_DLL(0);
		Ordered_DLL one = new Ordered_DLL(1);
		Ordered_DLL three = new Ordered_DLL(3);
		Ordered_DLL five = new Ordered_DLL(5);
		one.insert(head);
		five.insert(head);
		three.insert(head);
		assertEquals("[0,1,5]+3 = [0,1,3,5]", "[0,1,3,5]", toString(head));
	}

	// find 1 in [0,1,3,5] -> 1
	@Test
	public void find_first() {
		Ordered_DLL head = new Ordered_DLL(0);
		Ordered_DLL one = new Ordered_DLL(1);
		Ordered_DLL three = new Ordered_DLL(3);
		Ordered_DLL five = new Ordered_DLL(5);
		one.insert(head);
		three.insert(head);
		five.insert(head);

		Ordered_DLL found = head.find(1);
		assertNotNull("find 1 in [0,1,3,5] = 1", found);
		assertEquals("find 1 in [0,1,3,5] = 1", 1, found.ordinal);
	}

	// find 5 in [0,1,3,5] -> 5
	@Test
	public void find_last() {
		Ordered_DLL head = new Ordered_DLL(0);
		Ordered_DLL one = new Ordered_DLL(1);
		Ordered_DLL three = new Ordered_DLL(3);
		Ordered_DLL five = new Ordered_DLL(5);
		one.insert(head);
		three.insert(head);
		five.insert(head);

		Ordered_DLL found = head.find(5);
		assertNotNull("find 1 in [0,1,3,5] = 1", found);
		assertEquals("find 5 in [0,1,3,5] = 5", 5, found.ordinal);
	}

	// find 2 in [0,1,3,5] -> 0
	@Test
	public void find_none_middle() {
		Ordered_DLL head = new Ordered_DLL(0);
		Ordered_DLL one = new Ordered_DLL(1);
		Ordered_DLL three = new Ordered_DLL(3);
		Ordered_DLL five = new Ordered_DLL(5);
		one.insert(head);
		three.insert(head);
		five.insert(head);

		Ordered_DLL found = head.find(2);
		assertNull("find 2 in [0,1,3,5] = 0", found);
	}

	// find 6 in [0,1,3,5] -> 0
	@Test
	public void find_none_end() {
		Ordered_DLL head = new Ordered_DLL(0);
		Ordered_DLL one = new Ordered_DLL(1);
		Ordered_DLL three = new Ordered_DLL(3);
		Ordered_DLL five = new Ordered_DLL(5);
		one.insert(head);
		three.insert(head);
		five.insert(head);

		Ordered_DLL found = head.find(6);
		assertNull("find 6 in [0,1,3,5] = 0", found);
	}
}
