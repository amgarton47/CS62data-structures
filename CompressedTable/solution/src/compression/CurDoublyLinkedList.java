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
	private boolean off_right;	// current would be after the last
	private boolean off_left;	// current would be before the first

	/**
	 * @post: constructs an empty list, not off
	 */
	public CurDoublyLinkedList() {
		super();
		current = null;
		off_left = false;
		off_right = false;
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
		if (n == 0)
			throw(new IndexOutOfBoundsException("list is empty"));
		
		current = first;
		off_left = false;
		off_right = false;
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
		if (n == 0)
			throw(new IndexOutOfBoundsException("list is empty"));
		
		current = last;
		off_left = false;
		off_right = false;
	}

	/**
	 * set current to element after current
	 *
	 * @pre: list is non-empty && current is not off right side of list 
	 * @post: if is off left then make first elt the current elt, else reset current elt
	 * to be the next element of list.
	 *
	 * throws exception if called on an empty list or if we are already off
	 * the right side.
	 */
	public void next() {
		if (n == 0)
			throw(new IndexOutOfBoundsException("list is empty"));
		if (off_right)
			throw(new IndexOutOfBoundsException("current is offRight"));
		
		if (off_left) {
			current = first;
			off_left = false;
		} else if (current.next == null) {
			current = null;
			off_right = true;
		} else
			current = current.next;
	}

	/**
	 * set current to element before current
	 *
	 * @pre: list is non-empty && is not off left side of list
	 * @post: if is off right side of list then make 
	 * tail the current elt and no longer off right, if current
	 * is head then set current to null and remember off left side of list, else
	 * set current elt to be previous element of list.
	 *
	 * throws exception if called on an empty list or we are already
	 * off the left side
	 */
	public void back() {
		if (n == 0)
			throw(new IndexOutOfBoundsException("list is empty"));
		if (off_left)
			throw(new IndexOutOfBoundsException("current is offLeft"));
		
		if (off_right) {
			current = last;
			off_right = false;
		} else if (current.prev == null) {
			current = null;
			off_left = true;
		} else
			current = current.prev;
	}

	/**
	 * has current gone off the right side of the list
	 *
	 * @pre: list is non-empty
	 * 
	 * @return whether current is off right side of list
	 */
	public boolean isOffRight() {
		return off_right;
	}

	/**
	 * has current gone off the left side of the list
	 *
	 * @pre: list is non-empty
	 * 
	 * @return whether current is off left side of list
	 */
	public boolean isOffLeft() {
		return off_left;
	}

	/**
	 * has current gone off either side of the list
	 *
	 * @pre: list is non-empty
	 * 
	 * @return whether current is off right or left side of list
	 */
	public boolean isOff() {
		return off_left || off_right;
	}

	/**
	 * @pre: List is not empty & current not off list
	 * 
	 * @return value in current node
	 *
	 * throws exception if list is empty or current is off either side
	 */
	public E currentValue() {
		if (n == 0)
			throw(new IndexOutOfBoundsException("list is empty"));
		if (off_left)
			throw(new IndexOutOfBoundsException("current is offLeft"));
		if (off_right)
			throw(new IndexOutOfBoundsException("current is offRight"));
		return current.item;
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
		if (value == null)
			throw(new IllegalArgumentException("null value"));
		if (n == 0)
			throw(new IndexOutOfBoundsException("list is empty"));
		if (off_left)
			throw(new IndexOutOfBoundsException("current is offLeft"));
		if (off_right)
			throw(new IndexOutOfBoundsException("current is offRight"));
		
		if (current == last) {
			addLast(value);
			current = last;
		} else {
			int index = getIndex(current.item);
			add(index+1, value);
			current = current.next;
		}
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
		if (n == 0)
			throw(new IndexOutOfBoundsException("list is empty"));
		if (off_left)
			throw(new IndexOutOfBoundsException("current is offLeft"));
		if (off_right)
			throw(new IndexOutOfBoundsException("current is offRight"));
		
		E victim = current.item;
		if (current == last) {
			current = null;
			off_right = true;
		} else
			current = current.next;
		
		remove(victim);
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
		if (newFirst == null)
			throw(new IllegalArgumentException("null value"));
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
		if (n == 0)
			throw(new IndexOutOfBoundsException("list is empty"));
		
		E item = super.removeFirst();
		if (n == 0) {
			current = null;
			off_left = true;
		} else {
			current = first;
			off_left = false;
			off_right = false;
		}
		return item;
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
		if (newLast == null)
			throw(new IllegalArgumentException("null value"));
		super.addLast(newLast);
		current = last;
		off_left = false;
		off_right = false;
	}

	/**
	 * remove the last node from the list
	 *
	 * @pre: list is not empty 
	 * @post: removes value from tail of list, and current is
	 * set to null and off right side of list
	 * 
	 * @return value formerly in last element of list
	 * 
	 * throws exception if called on an empty list
	 */
	public E removeLast() {
		if (n == 0)
			throw(new IndexOutOfBoundsException("list is empty"));
		
		E victim = super.removeLast();
		current = null;
		off_right = true;
		off_left = false;
		return victim;
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
		if (n == 0)
			throw(new IndexOutOfBoundsException("list is empty"));
		
		current = first;
		off_left = false;
		off_right = false;
		return first.item;
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
		if (n == 0)
			throw(new IndexOutOfBoundsException("list is empty"));
		
		current = last;
		off_left = false;
		off_right = false;
		return last.item;
	}

	/**
	 * @post: removes all the elements from the list current set to null
	 */
	public void clear() {
		super.clear();
		current = null;
		off_left = false;
		off_right = false;
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
