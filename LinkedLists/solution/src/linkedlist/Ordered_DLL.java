package linkedlist;
import java.util.Iterator;

/**
 * An OrderedDLL is a DLL that is guaranteed to store elements
 * in order (based on an ordinal instance variable).
 *
 * This means that an iteration can be abandonned once we encounter
 * an element with a higher ordinal than the one we are seeking.

 * additional well-formed list invariants:
 *   1. if n1.next == n2, then n2.value >= n1.value
 *   2. if n1.prev == n2, then n2.value <= n1.value
 *   3. an ordered list begins with (value 0) a header node 
 *
 * @author: Mark Kampe
 */
public class Ordered_DLL extends DLL_Node {
	public int ordinal;		// positive integer, basis for ordering

	/**
	 * New Ordered_DLL Node, not in any list
	 *
	 * @param: (positive integer) sequencing value
	 */
	public Ordered_DLL(int value) {
		// a newly allocated DLL_Node is not on any list
		ordinal = value;
	}

	/**
	 * insert a node into the correct position of an ordered list
	 *
	 * @param head: 0-value node in the desired list
	 *
	 * @precondition: head is (0 value) node in a well-formed list
	 * @postcondition: this is properly inserted into that list
	 *				   (which might be between the last element and head)
	 */
	public void insert(Ordered_DLL head) {
		// find the last node smaller than me
		Iterator<DLL_Node> it = head.iterator();
		Ordered_DLL prev = head;
		while(it.hasNext()) {
			Ordered_DLL next = (Ordered_DLL) it.next();
			if (this.ordinal < next.ordinal)
				break;
			else
				prev = next;
		}

		// insert me after that one
		super.insert(prev);
	}

	/**
	 * locate the first node with a specified value
	 *
	 * @param: desired sequencing value
	 * @return: node with ordinal==value, or null
	 *
	 * @precondition: this a (0 value) node in a well-formed list
	 */
	public Ordered_DLL find(int value) {
		// search until we find desired value or greater
		Iterator<DLL_Node> it = this.iterator();
		while(it.hasNext()) {
			Ordered_DLL next = (Ordered_DLL) it.next();
			if (next.ordinal == value)
				return next;
			if (next.ordinal > value)
				break;
		}

		return null;	// not in the list
	}

	/**
	  * TESTING return a string representation of the list contents
	  * 
	  * @param start	Ordered_DLL list head
	  * @return description of the list
	  */
	 public String listToString() {
		String list = "";	// output report
		 
		Iterator<DLL_Node> it = this.iterator();
		boolean first = true;
		while(it.hasNext()) {
			 if (first)
				 first = false;
			 else
				 list += ",";

			Ordered_DLL n = (Ordered_DLL) it.next();
			if (n == null) {
				list += "!NULL!";
				break;
			 } else
				 list += Integer.toString(n.ordinal);
		 }
		 return "[" + list + "]";
	 }
	 
	/**
	 * Description of suggested test cases:
	 * 	operations				expected iteration
	 * 	----------				------------------
	 */
	public static void main(String[] args) {
		Ordered_DLL head = new Ordered_DLL(0);
		
		// new(0)			expect 0
		System.out.println("new(0) -> " + head.listToString());
		
		// insert(5)		expect 0, 5
		new Ordered_DLL(5).insert(head);
		System.out.println("new(5) -> " + head.listToString());
		
		// insert(3)		expect 0, 3, 5
		new Ordered_DLL(3).insert(head);
		System.out.println("new(3) -> " + head.listToString());
		
		// insert(4)		expect 0, 3, 4, 5
		new Ordered_DLL(4).insert(head);
		System.out.println("new(4) -> " + head.listToString());
		
		// insert(7)		expect 0, 3, 4, 5, 7
		new Ordered_DLL(7).insert(head);
		System.out.println("new(7) -> " + head.listToString());
		
		// make sure we can find everything
		for(int i = 0; i < 10; i++) {
			Ordered_DLL node = head.find(i);
			if (node != null)
				System.out.println("find(" + i + ") => " + node.ordinal);
			else
				System.out.println("find(" + i + ") => " + "!NULL!");	
		}
		
		// remove(3)		expect 0, 4, 5, 7
		head.find(3).remove();
		System.out.println("remove(3) -> " + head.listToString());
		
		// remove(5)		expect 0, 4, 7
		head.find(5).remove();
		System.out.println("remove(5) -> " + head.listToString());
		
		// remove(7)		expect 0, 4
		head.find(7).remove();
		System.out.println("remove(7) -> " + head.listToString());
		
		// remove(4)		expect 0
		head.find(4).remove();
		System.out.println("remove(4) -> " + head.listToString());
	}
}
