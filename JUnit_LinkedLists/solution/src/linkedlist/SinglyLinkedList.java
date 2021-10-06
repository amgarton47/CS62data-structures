package linkedlist;

import java.util.Iterator;

/**
 * The {@code SinglyLinkedList} class represents a singly linked list. It has
 * been implemented based on Sedgewick and Wayne's Algorithms textbook (4th
 * edition).
 * 
 * @author Aden Siebel
 * @author Alexandra Papoutsaki
 *
 */
public class SinglyLinkedList<Item> implements Iterable<Item> {
	private Node first; // head of the singly linked list
	private int n; // number of nodes in the singly linked list

	/**
	 * This nested class defines the nodes in the singly linked list with a value
	 * and pointer to the next node they are connected.
	 */
	private class Node {
		Item item;
		Node next;
	}

	/**
	 * Returns true if the singly linked list does not contain any item.
	 * 
	 * @return true if the singly linked list does not contain any item
	 */
	public boolean isEmpty() {
		return first == null; // size() == 0
	}

	/**
	 * Returns the number of items in the singly linked list.
	 * 
	 * @return the number of items in the singly linked list
	 */
	public int size() {
		return n;
	}

	/**
	 * Returns item at the specified index.
	 * 
	 * @param index
	 *            the index of the item to be returned
	 * @return the item at specified index
	 */
	public Item get(int index) {
		rangeCheck(index);

		Node finger = first;
		// search for index-th element or end of list
		while (index > 0) {
			finger = finger.next;
			index--;
		}
		return finger.item;
	}

	/**
	 * Inserts the specified item at the head of the singly linked list.
	 * 
	 * @param item
	 *            the item to be inserted
	 */
	public void add(Item item) {
		// Save the old node
		Node oldfirst = first;

		// Make a new node and assign it to head. Fix pointers.
		first = new Node();
		first.item = item;
		first.next = oldfirst;

		n++; // increase number of nodes in singly linked list.
	}

	/**
	 * Inserts the specified item at the specified index.
	 * 
	 * @param index
	 *            the index to insert the item
	 * @param item
	 *            the item to insert
	 */
	public void add(int index, Item item) {
		rangeCheck(index);

		if (index == 0) {
			add(item);
		} else {

			Node previous = null;
			Node finger = first;
			// search for index-th position
			while (index > 0) {
				previous = finger;
				finger = finger.next;
				index--;
			}
			// create new value to insert in correct position.
			Node current = new Node();
			current.next = finger;
			current.item = item;
			// make previous value point to new value.
			previous.next = current;

			n++;
		}
	}

	/**
	 * Retrieves and removes the head of the singly linked list.
	 * 
	 * @return the head of the singly linked list.
	 */
	public Item remove() {
		Node temp = first;
		// Fix pointers.
		first = first.next;

		n--;

		return temp.item;
	}

	/**
	 * Retrieves and removes the item at the specified index.
	 * 
	 * @param index
	 *            the index of the item to be removed
	 * @return the item previously at the specified index
	 */
	public Item remove(int index) {
		rangeCheck(index);

		if (index == 0) {
			return remove();
		} else {
			Node previous = null;
			Node finger = first;
			// search for value indexed, keep track of previous
			while (index > 0) {
				previous = finger;
				finger = finger.next;
				index--;
			}
			previous.next = finger.next;

			n--;
			// finger's value is old value, return it
			return finger.item;
		}

	}

	/**
	 * A helper method to check if the specified index is in range.
	 * 
	 * @param index
	 *            the index to check
	 */
	private void rangeCheck(int index) {
		if (index > n || index < 0)
			throw new IndexOutOfBoundsException("Index " + index + " out of bounds");
	}

	/**
	 * Converts the singly linked list to a String.
	 */
	public String toString() {
		if (isEmpty()) {
			return "Singly Linked List: []";
		}

		String ret = "Singly Linked List: [<- ";
		Iterator<Item> i = this.iterator();
		while (i.hasNext()) {
			ret += i.next();
			ret += " <-> ";
		}
		ret = ret.substring(0, ret.length() - 5);

		ret += " ->] First: ";
		ret += first.item;
		return ret;
	}

	/**
	 * Constructs an iterator for the singly linked list.
	 */
	public Iterator<Item> iterator() {
		return new SinglyLinkedListIterator();
	}

	/**
	 * A subclass that defines the iterator for the singly linked list.
	 */
	private class SinglyLinkedListIterator implements Iterator<Item> {
		private Node current = first;

		public boolean hasNext() {
			return current != null;
		}

		public void remove() {
		}

		public Item next() {
			Item item = current.item;
			current = current.next;
			return item;
		}
	}
}