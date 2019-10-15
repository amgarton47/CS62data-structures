package compression;

/**
 *  Implementation of lists, using doubly linked elements and keeping track of
 *  current reference.  This enables get/insert/delete operations relative to
 *  the current position.
 *
 *  There is also a notion of having gone "off" the list (e.g. by going next
 *  from the last or back from the first element).
 */

public class CurDoublyLinkedList<E> extends DoublyLinkedList<E> {
	protected DoublyLinkedList<E>.Node current; // special designated node, site of actions
	// add other instance variables

	/**
	 * @post: constructs an empty list, not off
	 */
	public CurDoublyLinkedList() {
		// TODO implement
	}

	/**
	 * set current to first element of list
	 *
	 * @pre: list is non-empty 
	 * @post: current set to first node of list
	 *
	 * throws exeception if list is empty
	 */
	public void first() {
		// TODO implement
	}

	/**
	 * set current to last element of list
	 *
	 * @pre: list is non-empty 
	 * @post: current set to last node of list
	 *
	 * throws exeception if list is empty
	 */
	public void last() {
		// TODO implement
	}

	/**
	 * set current to element after current
	 *
	 * @pre: list is non-empty && current is not off right side of list 
	 * @post: if is off left then make first elt the current elt, 
	 *        if current is last, set current to null and remember off_right
	 *        else reset current elt to be the next element of list.
	 *
	 * throws exception if called on an empty list or if we are already off
	 * the right side.
	 */
	public void next() {
		// TODO implement
	}

	/**
	 * set current to element before current
	 *
	 * @pre: list is non-empty && is not off left side of list
	 * @post: if is off right side of list then make * tail the current elt,
	 *        if current is head then set current to null and remember off left,
	 *        set current elt to be previous element of list.
	 *
	 * throws exception if called on an empty list or we are already
	 * off the left side
	 */
	public void back() {
		// TODO implement
	}

	/**
	 * has current gone off the right side of the list
	 *
	 * @pre: list is non-empty
	 * 
	 * @return whether current is off right side of list
	 */
	public boolean isOffRight() {
		return true;  // FIX THIS
	}

	/**
	 * has current gone off the left side of the list
	 *
	 * @pre: list is non-empty
	 * 
	 * @return whether current is off left side of list
	 */
	public boolean isOffLeft() {
		return true;  // FIX THIS
	}

	/**
	 * has current gone off either side of the list
	 *
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
	 *
	 * throws exception if list is empty or current is off either side
	 */
	public E currentValue() {
		return null;  // FIX THIS
	}

	/**
	 * create a new node immediately after the current node
	 *
	 * @pre: value is not null, List non-empty & current is not off list 
	 * @post: adds element after current with given value. New elt is set to be
	 * current.
	 * 
	 * @param value
	 *            new value for node inserted after current
	 *
	 * throws exception if called on an empty list or we are already
	 *	      off of either side
	 */
	public void addAfterCurrent(E value) {
		// TODO implement
		//	hint: look at the DoublyLinkedList methods
		//		  addLast(), getIndex(), and add(index,item)
	}

	/**
	 * remove the current node from the list
	 *
	 * @pre: list is non-empty & !isOff()
	 * @post: Current element is deleted, successor is new current elt 
	 * If deleted tail, then current is null and is off right
	 *
	 * throws exception if called on an empty list or we are already
	 *	      off of either side
	 */
	public void removeCurrent() {
		// TODO implement
	}

	/**
	 * add a new node to the beginning of the list
	 *
	 * @pre: value is not null 
	 * @post: adds element to head of list with value
	 * newFirst, and make it the current element
	 * 
	 * @param newFirst
	 *            value of new first element of list
	 */
	public void addFirst(E newFirst) {
		super.addFirst(newFirst);
		current = first;
	}

	/**
	 * remove the first node from the list
	 *
	 * @pre: list is not empty 
	 * @post: removes first value from list, successor is current
	 * 
	 * @return value of element formerly first i list
	 *
	 * throws exception if called on an empty list
	 */
	public E removeFirst() {

		return null;  //FIX THIS!
	}

	/**
	 * add a new node to the end of the list
	 *
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
	 * remove the last node from the list
	 *
	 * @pre: list is not empty 
	 * @post: removes value from tail of list, and current is
	 * set to null and off right side of list
	 *
	 * throws exception if called on an empty list
	 * 
	 * @return value formerly in last element of list
	 */
	public E removeLast() {
		return null;   // FIX THIS
	}

	/**
	 * return value of the first node in the list
	 *
	 * @pre: list is not empty
	 * @post: sets current to first element of list
	 * 
	 * @return value of first element in list
	 *
	 * throws exception if called on an empty list
	 */
	public E getFirst() {
		current = first;
		return last.item;
	}

	/**
	 * return value of the last node in the list
	 *
	 * @pre: list is not empty 
	 * @post: sets current to last element of list
	 * 
	 * @return value of last element in list
	 *
	 * throws exception if called on an empty list
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
