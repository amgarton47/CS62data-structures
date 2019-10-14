/**
 * An OrderedDLL is a DLL that is guaranteed to store elements
 * in order (based on an ordinal instance variable).
 *
 * This means that an iteration can be abandonned once we encounter
 * an element with a higher ordinal than the one we are seeking.

 * additional well-formed list invariants:
 *	 1. if n1.next == n2, then n2.value >= n1.value
 *   2. if n1.prev == n2, then n2.value <= n1.value
 *
 * @author: YOUR NAME HERE
 */
public class Ordered_DLL extends DLL_Node {
	public int ordinal;		// basis for list sequencing

	/**
	 * New Ordered_DLL Node, not in any list
	 *
	 * @param: sequencing value
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
	 */
	public void insert(DLL_Node head) {
		// TODO: find insertion point, and insert correctly
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
		return null;	// search list and return desired node
	}
}
