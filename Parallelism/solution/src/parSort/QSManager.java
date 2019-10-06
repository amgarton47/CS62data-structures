package parSort;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
/**
 * Demo of quicksort set up to make it easy to parallelize.
 * 
 * @author kim
 * @version 2/2011
 * @param <T>
 *            type of elements to be sorted
 */

public class QSManager<T extends Comparable<T>> extends RecursiveAction {
	// point where go sequential for efficiency
	private static final int CUTOFF = 100;

	// the table of values
	private T[] table;
	private int left;  // the leftmost elt to be sorted
	private int right; // the rightmost element to be sorted

	/**
	 * @param table
	 *            array of elements to be sorted.
	 * @param left
	 *            leftmost element to be sorted
	 * @param right
	 *            rightmost element to be sorted PRE -- left <= right are legal
	 *            indices of table. POST --table[left..right] sorted in
	 *            non-decreasing order
	 */
	public QSManager(int left, int right, T[] table) {
		this.table = table;
		this.left = left;
		this.right = right;
	}

	/**
	 *  Sort the array with quicksort, using the builtin sort if the
	 *  array has size 100 or less. 
	 */
	public void compute() {
		if (right > left + CUTOFF) { // More than 1 elt in table
			int pivotIndex = partition();
			// table[Left..pivotIndex] <= table[pivotIndex+1..right]

			// Quicksort small elts
			QSManager<T> leftSorter = new QSManager<T>(left, pivotIndex - 1,
					table);
			// Quicksort large elts
			QSManager<T> rightSorter = new QSManager<T>(pivotIndex + 1, right,
					table);
			leftSorter.fork();
			rightSorter.compute();
			leftSorter.join();
		} else if (right > left){
			Arrays.sort(table, left, right);
		}
	}

	/**
	 * @param left
	 *            leftmost element included in part to be sorted
	 * @param right
	 *            rightmost element included in part to be sorted
	 * @param table
	 *            array to be sorted post: table[left..pivotIndex-1] <=
	 *            table[pivot] <= table[pivotIndex+1..right]
	 **/
	private int partition() {
		// index of current posn in left (small elt) partition
		int smallIndex = left;
		// index of current posn in right (big elt) partition
		int bigIndex = right;

		// put sentinel at table[bigIndex] so don't
		// walk off right edge of table in loop below
		if (table[bigIndex].compareTo(table[smallIndex]) < 0) {
			exchange(bigIndex, smallIndex);
		}

		T pivot = table[left]; // pivot is fst elt
		// Now table[smallIndex] = pivot <= table[bigIndex]
		do {
			do
				// scan right from smallIndex
				smallIndex++;
			while (table[smallIndex].compareTo(pivot) < 0);

			do
				// scan left from bigIndex
				bigIndex--;
			while (pivot.compareTo(table[bigIndex]) < 0);

			// Now table[smallIndex] >= pivot >= table[bigIndex]
			// if big elt to left of small element, swap them
			if (smallIndex < bigIndex) {
				exchange(smallIndex, bigIndex);
			} // if
		} while (smallIndex < bigIndex);
		// Move pivot into correct pos'n bet'n small & big elts
		// pivot goes where bigIndex got stuck
		int pivotIndex = bigIndex;
		// swap pivot elt w/small elt at pivotIndex
		exchange(pivotIndex, left);
		return pivotIndex;
	}

	/**
	 * Post: exchange values in table[i] and table[j]
	 * 
	 * @param i
	 * @param j
	 */
	private void exchange(int i, int j) {
		T temp = table[i];
		table[i] = table[j];
		table[j] = temp;
	}

	/**
	 * Test Quicksort algorithm
	 * 
	 * @param args
	 *            ignored
	 */
	public static void main(String[] args) {
		final int NUM_REPS = 10;
		final int NUM_ELTS = 10000;
		Double[] elts = new Double[NUM_ELTS];
		// warm up the sort routine
		for (int reps = 0; reps < NUM_REPS; reps++) {
			fillWithRandom(elts);
			sort(elts);
		}
		StopWatch sw = new StopWatch();
		long min = Long.MAX_VALUE;
		for (int count = 0; count < NUM_REPS; count++) {
			fillWithRandom(elts);
			sw.reset();
			System.gc();
			sw.start();
			sort(elts);
			sw.stop();
			long elapsed = sw.getTime()/1000;
			System.out.println("Sorting " + NUM_ELTS + " doubles takes "
					+ elapsed);
			min = Math.min(min, elapsed);
		}
		System.out.println("The shortest time was "+min);
		printElts(elts, 10);
	}

	private static void printElts(Double[] elts, int n) {
		System.out.println("The first 10 elements of the sorted array are:");
		for (int counter = 0; counter < n; counter++) {
			System.out.println(elts[counter]);
		}
	}

	private static void sort(Double[] elts) {
		QSManager<Double> sorter = new QSManager<Double>(0, elts.length - 1,
				elts);
		sorter.compute();
	}

	private static void fillWithRandom(Double[] elts) {
		for (int counter = 0; counter < elts.length; counter++) {
			elts[counter] = Math.random();
		}
	}

}
