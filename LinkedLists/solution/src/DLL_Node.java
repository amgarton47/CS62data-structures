import java.util.Iterator;

/**
 * The DLL_Node is a super-class for nodes in a circular Doubly Linked List,
 * and implements only the most basic operations.  It is expected
 * that it will be extended to support application-specific DLLs.
 * 
 * Since all operations are implemented entirely on the DLL_Node,
 * there is no class for Lists of DLL_Nodes.
 *
 * well-formed list invariants:
 *	 1. if n1.prev == n2 then n2.next == n1
 *   2. if n1.next == n2 then n2.prev == n1
 *	 3. circularity:
 *      if there are N elements in the list 
 *      following n.next N times leads back to n
 *      following n.prev N times leads back to n
 * 
 * @author Mark Kampe
 */
public class DLL_Node implements Iterable<DLL_Node> {
	private DLL_Node next;	// forward pointer ... must remain private
	private DLL_Node prev;	// backwards pointer ... must remain private

	/**
	 * New DLL_Node, not in any list
	 */
	public DLL_Node() {
		// a newly allocated DLL_Node is not on any list
		next = this;
		prev = this;
	}

	/**
	 * Add this node to a list AFTER the indicated insertion point
	 *
	 * @param beforeMe: node after which this is to be inserted
	 *
	 * @precondition: after is node in a well-formed list
	 * @postcondition: after.next = this, in a well-formed list
	 */
	public void insert(DLL_Node beforeMe) {
		DLL_Node afterMe = beforeMe.next;
		// set my next and prev pointer
		this.next = afterMe;
		this.prev = beforeMe;
		
		// set prev and next nodes to point at me
		beforeMe.next = this;
		afterMe.prev = this;
	}

	/**
	 * Remove this node from whatever list it is in
	 * 
	 * @precondition: this is an element of a well-formed list
	 * @postcondition: that list no longer contains this
	 */
	public void remove() {
		// my prev and next now point past me
		if (this.next != this) {
			this.prev.next = this.next;
			this.next.prev = this.prev;
		}
		// I am no longer on any list
		this.next = null;
		this.prev = null;
	}

	/**
	 * return an iterator for the list containing this node
	 *
	 * @precondition: this node in a well-formed list
	 */
	public Iterator<DLL_Node>  iterator() {
		return new DLL_Node_Iterator(this);
	}

	/**
	 * a sub-class that defines the iterator for this class
	 */
	 public class DLL_Node_Iterator implements Iterator<DLL_Node> {
	
		private DLL_Node head;		// start/end of this iteration
		private DLL_Node current;	// current position in the iteration

		/**
		 * instantiate an Iterator with the specified starting point
		 *
		 * @param start: starting point for the iteration
		 *
		 * @precondition: start is node in a well-formed list
		 * @postcondition: successive calls to next will visit entire list
	     */
		public DLL_Node_Iterator(DLL_Node start) {
			this.head = start;		// end for wrap-around
			this.current = null;	// haven't seen any nodes yet
		}

		/**
		 * returns whether or not there is another Node in the iteration
		 *
		 * @return boolean: are there more nodes to be returned
		 * 
		 * DO NOT ASSUME THAT head IS WELL-FORMED (non null pointers)
		 */
		public boolean hasNext() {
			// make sure head is well formed
			if (head.next == null || head.prev == null)
				return false;
			
			// we haven't started, or we haven't wrapped around
			return current == null || current.next != head;
		}
		
		/**
		 * return next node in list, and advance the current pointer
		 * 
		 * @precondition before first call, current == null
		 *               after first call, current = last node returned
		 *               
		 * DO NOT ASSSUME that head is WELL-FORMED (non null pointers)
		 */
		 public DLL_Node next() {
				// make sure head is well formed
				if (head.next == null || head.prev == null)
					return null;
			 
			 if (current == null)
				 current = head;			// first node in list
			 else if (current.next != head)
				 current = current.next;	// next node in list
			 else
				 return null;				// we have wrapped around
				 
			return current;
		 }
	 }
	 
	 private static String[] names = {"a", "b", "c", "d"};
	 private static DLL_Node[] nodes;	// associated nodes
	 
	 /**
	  * TESTING return reference to a named node
	  * 
	  * @param name (string) of desired node
	  * @return reference to the DLL_Node (or null)
	  */
	 private static DLL_Node node(String name) {
		 
		 for(int i = 0; i < names.length; i++)
			 if (name.equals(names[i]))
				 return nodes[i];
		 return null;
	 }
	 /**
	  * TESTING print contents of list as enumerated from specified point
	  * 
	  * @param start	(string) name of starting node to list
	  * @return description of the list
	  */
	 private static String diag_list(String start) {
		 String list = start + ": ";	// output report
		 
		 // figure out which node to start with
		 DLL_Node start_node = null;
		 for(int i = 0; i < names.length; i++)
			 if (start.equals(names[i]))
				 start_node = nodes[i];
		 if (start_node == null)
			 return list + "NO SUCH NODE";
		 
		 // get the iterator and generate the list
		 Iterator<DLL_Node> it = start_node.iterator();
		 boolean first = true;
		 while(it.hasNext()) {
			 // get and figure out name of next node
			 DLL_Node node = it.next();
			 String name = "?";
			 for(int i = 0; i < nodes.length; i++)
				 if (nodes[i] == node)
					 name = names[i];
			 
			 // append it to our list
			 if (first)
				 first = false;
			 else
				 list += ", ";
			 list += name;
		 }
		 return (first) ? list + "0" : list;
	 }
	 
	 /**
	  * Description of suggested test cases:
	  * 
	  * 	operations				expected iteration
	  * 	----------				------------------
	  * 	a=new,					a: a				
	  * 	b=new, b.add(a)			a: a, b
	  * 	c=new, c.add(a)			a: a, c, b
	  * 	d=new, d.add(b)			a: a, c, b, d
	  * 	a.remove				c: c, b, d
	  * 	b.remove				c: c, d
	  * 	d.remove				c: c
	  * 	c.remove				c: 0
	  */
	 public static void main(String[] args) {
		 // allocate the nodes
		 nodes = new DLL_Node[4];
		 for(int i = 0; i < nodes.length; i++)
			 nodes[i] = new DLL_Node();
		 
		 // a = new		expect a: a
		 System.out.println("a=new() -> " + diag_list("a"));
		 
		 // b.add(a)	expect a: a, b
		 node("b").insert(node("a"));
		 System.out.println("b=new(), b.insert(a) -> " + diag_list("a"));
		 
		 // c.add("a")	expect a: a, c, b
		 node("c").insert(node("a"));
		 System.out.println("c = new(), c.insert(a) -> " + diag_list("a"));
		 
		 // d.add("b")  expect a: a, c, b, d
		 node("d").insert(node("b"));
		 System.out.println("d = new(), c.insert(b) -> " + diag_list("a"));
		 
		 // a.remove()  expect c: c, b, d
		 node("a").remove();
		 System.out.println("a.remove() -> " + diag_list("c"));
		 
		 // b.remove()  expect c: c, d
		 node("b").remove();
		 System.out.println("b.remove() -> " + diag_list("c"));
		 
		 // d.remove()  expect c: c
		 node("d").remove();
		 System.out.println("d.remove() -> " + diag_list("c"));
		 
		 // c.remove()  expect c: 0
		 node("c").remove();
		 System.out.println("c.remove() -> " + diag_list("c"));
	 }
}
