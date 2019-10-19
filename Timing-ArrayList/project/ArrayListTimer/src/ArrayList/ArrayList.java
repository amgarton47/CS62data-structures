package ArrayList;

import java.util.NoSuchElementException;
import java.lang.IndexOutOfBoundsException;
import java.util.Iterator;

/**
 * The {@code ArrayList} class represents a resizing list. It has been
 * implemented based on Sedgewick and Wayne's Algorithms textbook (4th edition).
 * <p>
 * It doubles the underlying array when it is full and halves the underlying
 * array when it is one-quarter full.
 * 
 * @author Aden Siebel
 * @author Alexandra Papoutsaki
 *
 */
public class ArrayList<Item> implements Iterable<Item> {
	private Item[] a; // underlying array of items
	private int n; // number of items in arraylist
    private int capacityIncrement; //rate of growth for arraylist

	/**
	 * Constructs an ArrayList with an initial capacity of 2.
	 */
	@SuppressWarnings("unchecked")
	public ArrayList() {
		a = (Item[]) new Object[2];
		n = 0;
        capacityIncrement = 0;
	}

	/**
	 * Constructs an ArrayList with the specified capacity.
	 */
	@SuppressWarnings("unchecked")
	public ArrayList(int capacity) {
		a = (Item[]) new Object[capacity];
		n = 0;
        capacityIncrement = 0;
	}

	/**
	 * Constructs an ArrayList with the specified capacity and extends capacity by capacity increments or doubles if argument is 0.
	 */
	@SuppressWarnings("unchecked")
	public ArrayList(int capacity, int capacityIncrement) {
		a = (Item[]) new Object[capacity];
		n = 0;
        this.capacityIncrement = capacityIncrement;
	}

	/**
	 * Returns true if the ArrayList contains no items.
	 * 
	 * @return true if the ArrayList does not contain any item
	 */
	public boolean isEmpty() {
		return n == 0;
	}

	/**
	 * Returns the number of items in the ArrayList.
	 * 
	 * @return the number of items in the ArrayList
	 */
	public int size() {
		return n;
	}

	/**
	 * Resizes the ArrayList's capacity to the specified capacity.
	 */
	@SuppressWarnings("unchecked")
	private void resize(int capacity) {
		assert capacity >= n;
		// textbook implementation.
		Item[] temp = (Item[]) new Object[capacity];
		for (int i = 0; i < n; i++)
			temp[i] = a[i];

		a = temp;

		// alternative implementation
		// a = java.util.Arrays.copyOf(a, capacity);
	}

	/**
	 * Appends the item to the end of the ArrayList. Doubles its capacity if
	 * necessary.
	 * 
	 * @param item
	 *            the item to be inserted
	 */
	public void add(Item item) {
		if (n == a.length){
            if(capacityIncrement == 0){
			    resize(2 * a.length);
            }
            else{
                resize(a.length+capacityIncrement);
            }
		}
		a[n++] = item;
	}

	/**
	 * Inserts the item at the specified index. Shifts existing elements to the
	 * right and doubles its capacity if necessary.
	 * 
	 * @param index
	 *            the index to insert the item
	 * @param item
	 *            the item to be inserted
	 */
	public void add(int index, Item item) {
		rangeCheck(index);

        if(capacityIncrement == 0){
			resize(2 * a.length);
        }
        else{
            resize(a.length+capacityIncrement);
        }

		// shift elements to the right
		for (int i = n++; i > index; i--)
			a[i] = a[i - 1];

		a[index] = item;
	}

	/**
	 * Replaces the item at the specified index with the specified item.
	 * 
	 * @param index
	 *            the index of the item to replace
	 * @param item
	 *            item to be stored at specified index
	 * @return the old item that was changed.
	 */
	public Item set(int index, Item item) {
		rangeCheck(index);

		Item old = a[index];
		a[index] = item;

		return old;
	}

	/**
	 * Returns the item at the specified index.
	 * 
	 * @param index
	 *            the index of the item to return
	 * @return the item at the specified index
	 */
	public Item get(int index) {
		rangeCheck(index);

		return a[index];
	}

	/**
	 * Retrieves and removes the item from the end of the ArrayList.
	 * 
	 * @return the removed item
	 * @pre n>0
	 */
	public Item remove() {
		if (isEmpty())
			throw new NoSuchElementException("The list is empty");

		Item item = a[--n];
		a[n] = null; // Avoid loitering (see text).

		// Shrink to save space if possible
		if (n > 0 && n == a.length / 4){
            resize(a.length/2);
        }
		return item;
	}

	/**
	 * Retrieves and removes the item at the specified index.
	 * 
	 * @param index
	 *            the index of the item to be removed
	 * @return the removed item
	 */
	public Item remove(int index) {
		rangeCheck(index);

		Item item = a[index];
		n--;

		for (int i = index; i < n; i++)
			a[i] = a[i + 1];

		a[n] = null; // Avoid loitering (see text).

		// shrink to save space if necessary
		if (n > 0 && n == a.length / 4){
			resize(a.length/2);
        }
		return item;
	}

	/**
	 * Checks if the ArrayList contains the specified item.
	 * 
	 * @param item
	 *            the item to check if it is included in the ArrayList
	 * @return true if the item is in the list
	 */
	public boolean contains(Item item) {
		return indexOf(item) >= 0;
	}

	/**
	 * Check for the first index of an item in the ArrayList.
	 * 
	 * @param item
	 *            the item to check for
	 * @return the index of first occurrence of the specified item
	 */
	public int indexOf(Item item) {
		if (item == null) { // Special check for null elements
			for (int i = 0; i < n; i++)
				if (a[i] == null)
					return i;
		} else { // Regular check
			for (int i = 0; i < n; i++)
				if (item.equals(a[i]))
					return i;
		}

		return -1;
	}

	/**
	 * Clears the ArrayList of all items.
	 */
	public void clear() {

		// Help garbage collector.
		for (int i = 0; i < n; i++)
			a[i] = null;

		n = 0;
	}

	/**
	 * A helper method to check if an index is in range 0<=index<n
	 * 
	 * @param index
	 *            the index to check if it is within range
	 */
	private void rangeCheck(int index) {
		if (index > n || index < 0)
			throw new IndexOutOfBoundsException("Index " + index + " out of bounds");
	}

	/**
	 * Converts the ArrayList to a String.
	 */
	public String toString() {
		if (isEmpty()) {
			return "ArrayList: []";
		}

		String ret = "ArrayList of " + a.length + " capacity: [";

		Iterator<Item> i = this.iterator();
		while (i.hasNext()) {
			ret += i.next();
			ret += "; ";
		}

		ret += "]";
		return ret;
	}

	/**
	 * Constructs an iterator for the ArrayList
	 */
	public Iterator<Item> iterator() {
		return new ArrayListIterator();
	}

	/**
	 * A subclass that defines the iterator for the ArrayList.
	 */
	private class ArrayListIterator implements Iterator<Item> {
		private int i = 0;

		public boolean hasNext() {
			return i < n;
		}

		public Item next() {
			return a[i++];
		}

		public void remove() {
		}
	}
}
