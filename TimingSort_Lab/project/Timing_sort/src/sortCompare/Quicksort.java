package sortCompare;

import java.util.ArrayList;

/**
 * An implementation of the Quicksort algorithm following the Hoare Partition scheme
 * 
 * @param <E> the type of elements to be sorted
 */
public class Quicksort<E extends Comparable<E>> implements Sorter<E>{

	/**
	 * Sorts the data using Quicksort
	 * 
	 * @param data Data to be sorted
	 */
	public void sort(ArrayList<E> data) {
		quicksortHelper(data, 0, data.size()-1);
	}
	
	/**
	 * Helper method for Quicksort.  Sorts data so that data[lo .. j-1] <= data[j] <= data[j+1 .. hi]
	 * 
	 * @param data data to be sorted
	 * @param lo start of the data to be sorted (inclusive)
	 * @param hi end of the data to be sorted (exclusive)
	 */
	private void quicksortHelper(ArrayList<E> data, int lo, int hi){
		if( hi <= lo ){
			return;
		}
		int j = partition(data, lo, hi);
		quicksortHelper(data, lo, j-1);
		quicksortHelper(data, j+1, hi);

	}

	/**
	 * partitions the data based on the element at index end.
	 * 
	 * @param data data to be partitioned
	 * @param lo start of the data to be partitioned
	 * @param hi end of the data to be partitioned
	 * @return returned the index of the pivot element (after being copied 
	 * into the correct location)
	 */
	private int partition(ArrayList<E> data, int lo, int hi){
		E pivot = data.get(lo);
		int i = lo;
		int j = hi + 1;
		
		while(true) {
			//find larger item than pivot to swap
			while (less(data.get(++i), pivot)) {
 				if (i == hi) break;
 			}

			//find smaller item than pivot to swap
			while (less(pivot, data.get(--j))) {
 				if (j == lo) break;
 			}

			if(i >= j){
				break;
			}

			exch(data, i, j);
		}

		//partition pivot at position j
		exch(data, lo, j);

		return j;
	}
	
	/**
	 * Swap two elements in the ArrayList
	 * 
	 * @param data data array
	 * @param i first element to be swapped
	 * @param j second element to be swapped
	 */
	private void exch(ArrayList<E> data, int i, int j) {
		E swap = data.get(i);
		data.set(i, data.get(j));
		data.set(j, swap);
	}

	/**
	* Compares two elements and returns whether v is smaller than w
	*
	* @param v first element to be compared
	* @param w second element to be compared
	* @return true iff v<w based on its comparable interface implementation
	*/
	private boolean less(E v, E w) {
		return v.compareTo(w) < 0;
	}
}
