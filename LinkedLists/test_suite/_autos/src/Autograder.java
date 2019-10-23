import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Auto-grader unit tests for <strong>Linked List lab</strong>.
 *
 * @author Mark Kampe
 */
public class Autograder {


	/*
	 * DLL_Node insert/remove test cases
	 */
	@Test
	public void new_node_is_list() {
		assertEquals("new a-> [a]", "[a]", "[a]");
	}

	@Test
	public void insert_at_end() {
		assertEquals("[a], b.insert(a)", "[a, b]", "[a, b]");
	}

	@Test
	public void insert_in_middle() {
		assertEquals("[a,b] c.insert(a), b.insert(a)", "[a, b, c]", "[a, b, c]")
	}

	@Test
	public void remove_first() {
		assertEquals("[a, b, c], a.remove", "[b,c]", "[b,c]");
		assertEquals("removed node is empty list", "[]", "[]");
	}

	@Test
	public void remove_middle() {
		assertEquals("[a, b, c], b.remove", "[a,c]", "[a,c]");
	}

	@Test
	public void remove_end() {
		assertEquals("[a, b, c], c.remove", "[a,b]", "[a,b]");
	}

	/*
	 * Ordered_DLL insert/find test cases
	 */
}
