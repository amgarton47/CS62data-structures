package compression;

/**
 *  Implementation of lists, using doubly linked elements and keeping track of
 *  current reference.
 */

public class CurDoublyLinkedList<E> extends DoublyLinkedList<E> {
	protected DoublyLinkedList.Node current; // special designated node, site of actions
	// add other instance variables

	/**
	 * @post: constructs an empty list
	 */
	public CurDoublyLinkedList() {

	}

	/**
	 * @pre: list is non-empty 
	 * @post: current set to first node of list
	 */
	public void first() {

	}

	/**
	 * @pre: list is non-empty 
	 * @post: current set to last node of list
	 */
	public void last() {

	}

	/**
	 * @pre: list is non-empty && current is not off right side of list 
	 * @post: if is off left then make first elt the current elt, else reset current elt
	 * to be the next element of list.
	 */
	public void next() {

	}

	/**
	 * @pre: list is non-empty && is not off left side of list
	 * @post: if is off right side of list then make 
	 * tail the current elt and no longer off right, if current
	 * is head then set current to null and remember off left side of list, else
	 * set current elt to be previous element of list.
	 */
	public void back() {
	}

	/**
	 * @pre: list is non-empty
	 * 
	 * @return whether current is off right side of list
	 */
	public boolean isOffRight() {
		return true;  // FIX THIS
	}

	/**
	 * @pre: list is non-empty
	 * 
	 * @return whether current is off left side of list
	 */
	public boolean isOffLeft() {
		return true;  // Fix this
	}

	/**
	 * @pre: list is non-empty
	 * 
	 * @return whether current is off right or left side of list
	 */
	public boolean isOff() {
		return true;  // FIX THIS
	}

	/**
	 * @pre: List is not empty & current not off list
	 * 
	 * @return value in current node
	 */
	public E currentValue() {
		return null;  // FIX THIS
	}

	/**
	 * @pre: value is not null, List non-empty & current is not off list 
	 * @post: adds element after current with given value. New elt is set to be
	 * current.
	 * 
	 * @param value
	 *            new value for node inserted after current
	 */
	public void addAfterCurrent(E value) {

	}

	/**
	 * @pre: list is non-empty & !isOff()
	 * @post: Current element is deleted, successor is new current elt 
	 * If deleted tail, then current is null and is off right
	 */
	public void removeCurrent() {

	}

	/**
	 * @pre: value is not null 
	 * @post: adds element to head of list with value
	 * newFirst, and make it the current element
	 * 
	 * @param newFirst
	 *            value of new first element of list
	 */
	// 
	public void addFirst(E newFirst) {
		super.addFirst(newFirst);
		current = first;
	}

	/**
	 * @pre: list is not empty 
	 * @post: removes first value from list, successor is current
	 * 
	 * @return value of element formerly first i list
	 */
	public E removeFirst() {

		return null;  //FIX THIS!
	}

	/**
	 * @pre: value is not null 
	 * @post: adds new value to tail of list and make it current
	 * 
	 * @param newLast
	 *            value of new last element of list
	 */
	public void addLast(E newLast) {
		super.addLast(newLast);
		current = last;
	}

	/**
	 * @pre: list is not empty 
	 * @post: removes value from tail of list, and current is
	 * set to null and off right side of list
	 * 
	 * @return value formerly in last element of list
	 */
	public E removeLast() {
		return null;   // FIX THIS
	}

	/**
	 * @pre: list is not empty
	 * @post: sets current to first element of list
	 * 
	 * @return value of first element in list
	 */
	public E getFirst() {
		current = first;
		return last.item;
	}

	/**
	 * @pre: list is not empty 
	 * @post: sets current to last element of list
	 * 
	 * @return value of last element in list
	 */
	public E getLast() {
		current = last;
		return last.item;
	}

	/**
	 * @post: removes all the elements from the list current set to null
	 */
	public void clear() {
		super.clear();
		current = null;
	}

	/**
	 * @return readable representation of table Shows contents of underlying
	 *         list rather than all elements of the table.
	 */
	public String toString() { // do not change
		return super.toString() + "\nCurrent is " + current;
	}

	/**
	 * This method does not change current
	 * @return a string representation of the list, with 
	 * each element on a new line.
	 */
	public String otherString(){ // do not change
	    DoublyLinkedList<E>.Node finger = first;
	    StringBuilder ans = new StringBuilder("CurDoublyLinkedList:\n");
	    while (finger != null) {
		ans.append(finger+"\n");
		finger = finger.next;
	    }
	    return ans.toString();
	}

}

