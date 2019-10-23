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
	 *
	 * Note: the merged data must be in the same data array that
	 *       was passed as a parameter.
	 */
	public void merge(ArrayList<E> data, int low, int mid, int high){
		//TODO: write the merge method
	}
}
