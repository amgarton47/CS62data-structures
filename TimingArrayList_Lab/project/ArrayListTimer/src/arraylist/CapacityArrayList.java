package arraylist;

/**
 * The {@code BasicArrayList} class represents a list that is indexable
 * and can dynamically grow.
 */
public class CapacityArrayList<E>{
	private static final int START_SIZE = 10;
	
	private E[] data; // underlying array of items
	private int size; // number of items in arraylist
	private int capacityIncrement = 0;
	
	public CapacityArrayList() {
		data = (E[]) new Object[START_SIZE];
		size = 0;
	}
	
	/**
	 * Constructs an ArrayList with the specified capacity and default to doubleing when resizing.
	 */
	public CapacityArrayList(int capacity) {
		data = (E[]) new Object[capacity];
		size = 0;
	}
	
	/**
	 * Constructs an ArrayList with the specified capacity and extends capacity by capacity increments or doubles if argument is 0.
	 */
	public CapacityArrayList(int capacity, int capacityIncrement) {
		data = (E[]) new Object[capacity];
		size = 0;
		this.capacityIncrement = capacityIncrement;
	}
	
	public E get(int index) {
		rangeCheck(index);

		return data[index];
	}

	public void set(int index, E item) {
		rangeCheck(index);
		data[index] = item;
	}
	
	private void rangeCheck(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index " + index + " out of bounds");
		}
	}
	
	public void add(E item) {
		if (size == data.length){
			if(capacityIncrement == 0){
			    resize(2 * data.length);
            }
            else{
                resize(data.length+capacityIncrement);
            }
		}
		data[size++] = item;
	}

	public int size() {
		return size;
	}	
	
	private void resize(int capacity) {
		E[] temp = (E[]) new Object[capacity];
		
		for (int i = 0; i < size; i++) {
			temp[i] = data[i];
		}

		data = temp;
	}

	public String toString() {
		String s = "ArrayList: [";
		
		for( int i = 0; i < size; i++ ) {
			s += data[i] + ", ";
		}
		
		return s.substring(0, s.length()-2) + "]";
	}
}
