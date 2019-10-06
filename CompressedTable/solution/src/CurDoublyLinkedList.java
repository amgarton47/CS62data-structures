/**
 *  Implementation of lists, using doubly linked elements and keeping track of
 *  current reference.
 *  
 *  @author kim
 *  @version 2/2011
 */

import structure5.DoublyLinkedNode;
import structure5.DoublyLinkedList;

public class CurDoublyLinkedList<E> extends DoublyLinkedList<E> {
	private DoublyLinkedNode<E> current; // special designated node, site of actions
	private boolean offRight = false; // Whether current if off right side of

	// list, not left

	/**
	 * post: constructs an empty list
	 */
	public CurDoublyLinkedList() {
		super();
		current = null;
	}

	/**
	 * pre: list is non-empty post: current set to first node of list
	 */
	public void first() {
		current = head;
	}

	/**
	 * pre: list is non-empty post: current set to last node of list
	 */
	public void last() {
		current = tail;
	}

	/**
	 * pre: list is non-empty && current is not off right side of list post: if
	 * is off left then make first elt the current elt, else reset current elt
	 * to be the next element of list.
	 */
	public void next() {
		if (!isOff()) {
			current = current.next();
			if (current == null) {
				offRight = true;
			}
		} else if (!isOffRight()) {
			current = head;
		}
	}

	/**
	 * pre: list is non-empty && not isOffLeft() post: if is off right side of
	 * list then make tail the current elt and no longer off right, if current
	 * is head then set current to null and remember off left side of list, else
	 * set current elt to be previous element of list.
	 */
	public void back() {
		if (!isOff()) {
			current = current.previous();
			if (current == null) {
				offRight = false;
			}
		} else if (isOffRight()) {
			current = tail;
		}
	}

	/**
	 * pre: list is non-empty
	 * 
	 * @return whether current is off right side of list
	 */
	public boolean isOffRight() {
		return current == null && offRight;
	}

	/**
	 * pre: list is non-empty
	 * 
	 * @return whether current is off left side of list
	 */
	public boolean isOffLeft() {
		return current == null && !offRight;
	}

	/**
	 * pre: list is non-empty
	 * 
	 * @return whether current is off right or left side of list
	 */
	public boolean isOff() {
		return current == null;
	}

	/**
	 * pre: List is not empty & current not off list
	 * 
	 * @return value in current node
	 */
	public E currentValue() {
		return current.value();
	}

	/**
	 * pre: value is not null, List non-empty & current is not off list 
	 * post: adds element after current with given value. New elt is set to be
	 * current.
	 * 
	 * @param value
	 *            new value for node inserted after current
	 */
	public void addAfterCurrent(E value) {
		if (current == tail) {
			addLast(value);
		} else {
			// construct a new element, adding it after current
			current = new DoublyLinkedNode<E>(value, current.next(), current);
			count++;
		}
	}

	/**
	 * pre: list is non-empty & !isOff() post: Current element is deleted,
	 * successor is new current elt If deleted tail, then current is null and is
	 * off right
	 */
	public void removeCurrent() {
		if (current == head) {
			removeFirst();
		} else if (current == tail) {
			removeLast();
		} else {
			DoublyLinkedNode<E> prev = current.previous();
			DoublyLinkedNode<E> nextOne = current.next();
			prev.setNext(nextOne);
			nextOne.setPrevious(current.previous());
			current = nextOne;
			count--;
		}
	}

	/**
	 * pre: value is not null post: adds element to head of list with value
	 * newFirst, and make it the current element
	 * 
	 * @param newFirst
	 *            value of new first element of list
	 */
	// 
	public void addFirst(E newFirst) {
		super.addFirst(newFirst);
		current = head;
	}

	/**
	 * pre: list is not empty post: removes first value from list, successor is
	 * current
	 * 
	 * @return value of element formerly first i list
	 */
	public E removeFirst() {
		E oldHeadValue = super.removeFirst();
		if (head == null) {
			offRight = true;
		}
		current = head;
		return oldHeadValue;
	}

	/**
	 * pre: value is not null post: adds new value to tail of list and make it
	 * current
	 * 
	 * @param newLast
	 *            value of new last element of list
	 */
	public void addLast(E newLast) {
		super.addLast(newLast);
		current = tail;
	}

	/**
	 * pre: list is not empty post: removes value from tail of list, and current
	 * is set to null and off right side of list
	 * 
	 * @return value formerly in last element of list
	 */
	public E removeLast() {
		E oldLastValue = super.removeLast();
		current = null;
		offRight = true;
		return oldLastValue;

	}

	/**
	 * pre: list is not empty post: sets current to first element of list
	 * 
	 * @return value of first element in list
	 */
	public E getFirst() {
		current = head;
		return head.value();
	}

	/**
	 * pre: list is not empty post: sets current to last element of list
	 * 
	 * @return value of last element in list
	 */
	public E getLast() {
		current = tail;
		return tail.value();
	}

	/**
	 * post: removes all the elements from the list current set to null
	 */
	public void clear() {
		super.clear();
		current = null;
	}

	/**
	 * @return readable representation of table Shows contents of underlying
	 *         list rather than all elements of the table.
	 */
	public String toString() {
		return super.toString() + "\nCurrent is " + current;
	}
	
	/**
	 * This method does not change current
	 * @return a string representation of the list, with 
	 * each element on a new line.
	 */
	public String otherString(){
		DoublyLinkedNode<E> finger = head;
		StringBuilder ans = new StringBuilder("CurDoublyLinkedList:\n");
		while (finger != null) {
			ans.append(finger+"\n");
			finger = finger.next();
		}
		return ans.toString();
	}

	/**
	 * program to test implementation of CurDoublyLinkedList
	 * 
	 * @param args
	 *            ignored, as not used in main
	 */
	public static void main(String[] args) {
		CurDoublyLinkedList<String> list = new CurDoublyLinkedList<String>();
		list.addFirst("This");
		System.out.println(list);
		list.addFirst("Not");
		System.out.println("Should be <Not,This>: "+list);
		list.removeFirst();
		System.out.println("Should be <This>: "+list);
		list.addFirst("Not");
		list.removeLast();
		System.out.println("Should be <Not>: "+list);
		list.addLast("time");
		System.out.println(">>Should be <Not,Time>: "+list.otherString());
		list.back();
		System.out.println("after back: " + list);
		list.removeCurrent();
		System.out.println("Should be <Time>: " + list);
		list.addAfterCurrent("after");
		System.out.println("Should be <Time,after>: " + list);

	}

}
