package linkedlist;

import java.util.Iterator;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Starting point for a DLL_Node JUnit test suite
 */
public class DLL_Node_test {

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
		 * (testing the DLL_Node iterator in the process)
		 */
		public String toString() {
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

	// non-list iterates as []
	@Test
	public void iterate_none() {
		Named_DLL_Node a = new Named_DLL_Node("a");
		a.remove();
		assertEquals("[a], a.remove", "[]", a.toString());
	}
	
	// [a,b,c] iterates as [a,b,c]
	@Test
	public void iterate_three() {
		Named_DLL_Node a = new Named_DLL_Node("a");
		Named_DLL_Node b = new Named_DLL_Node("b");
		Named_DLL_Node c = new Named_DLL_Node("c");
		b.insert(a);
		c.insert(b);
		assertEquals("iterate [a,b,c]", "[a,b,c]", a.toString());
	}
}
