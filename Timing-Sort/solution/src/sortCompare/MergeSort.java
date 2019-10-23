package sortCompare;

import java.util.ArrayList;

/**
 * Implementation of the MergeSort algorithm
 * 
 *
 * @param <E> type of data to be sorted
 */
public class MergeSort<E extends Comparable<E>> implements Sorter<E>{
	
	/**
	 * Sort the ArrayList of data using MergeSort
	 * 
	 * @param list data to be sorted
	 */
	public void sort(ArrayList<E> data){
		sortHelper(data, 0, data.size());
	}

	/**
	 * MergeSort helper method.  Sorts data >= start and < end
	 * 
	 * @param list data to be sorted
	 * @param low start of the data to be sorted
	 * @param high end of the data to be sorted (exclusive)
	 */
	private void sortHelper(ArrayList<E> data, int low, int high){
		if( high-low > 1 ){
			int mid = low + (high-low)/2;
			
			sortHelper(data, low, mid);
			sortHelper(data, mid, high);
			merge(data, low, mid, high);
		}
	}

	/**
	 * Merge data >= low and < high into sorted data.  Data >= low and < mid are in sorted order.
	 * Data >= mid and < high are also in sorted order
	 * 
	 * @param data the partially sorted data
	 * @param low bottom index of the data to be merged
	 * @param mid midpoint of the data to be merged
	 * @param high end of the data to be merged (exclusive)
	 */
	public void merge(ArrayList<E> data, int low, int mid, int high){
		// 1. merge the two sub-groups into a new (temp) ArrayList
		ArrayList<E> merged = new ArrayList<E>(high - low);
		int x1 = low;
		int x2 = mid;
		while(x1 < mid || x2 < high) {
			// figure out which value is the smaller
			int next;
			if (x1 == mid)
				next = 2;
			else if (x2 == high)
				next = 1;
			else
				next = (data.get(x1).compareTo(data.get(x2)) <= 0) ? 1 : 2;
			
			// add the smaller value to the temp list
			if (next == 1)
				merged.add(data.get(x1++));
			else
				merged.add(data.get(x2++));	
		}
		
		// 2. copy the merged data back into the original
		for(int i = 0; i < merged.size(); i++)
			data.set(low+i, merged.get(i));
	}
}
