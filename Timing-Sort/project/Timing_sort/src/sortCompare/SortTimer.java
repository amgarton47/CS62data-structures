package sortCompare;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class times different sorting methods on random data for varying data
 * sizes
 */
public class SortTimer {
	private Stopwatch timer;
	private Random rand = new Random();

	/**
	 * Setup the sorters and run the test
	 * 
	 * @param args
	 *            does not use args
	 */
	public static void main(String[] args) {
		ArrayList<Sorter<Integer>> sorters = new ArrayList<Sorter<Integer>>();
		sorters.add(new Quicksort<Integer>());
		sorters.add(new MergeSort<Integer>());

		ArrayList<Integer> sizes = new ArrayList<Integer>();

		int dataSize = 1000;

		for (int i = 0; i < 7; i++) {
			sizes.add(dataSize);
			dataSize *= 2;
		}

		SortTimer timer = new SortTimer();
		// Why are we running this for loop 10 times?
		for (int count = 0; count < 10; count++){
			timer.printTimes(sorters, sizes);
		}
	}

	/**
	 * Print out the times to the console for the different sorters for random
	 * arrays of different sizes, specified by sizes
	 * 
	 * @param sorters
	 *            the different sorting methods to run
	 * @param sizes
	 *            the different data sizes to run
	 */
	public void printTimes(ArrayList<Sorter<Integer>> sorters,
			ArrayList<Integer> sizes) {
		System.out.println("\nSorting time versus size\n");
		System.out.printf("%8s", "size");

		// note the use of "getClass().toString()" to print out the class name
		// associated with Sorter s.
		for (Sorter<Integer> s : sorters) {
			String className = s.getClass().toString();
			int lastDot = className.lastIndexOf(".");
			if (lastDot > 0)
				className = className.substring(lastDot+1);
			System.out.printf(" | %15s", className);
		}

		System.out.println();
		System.out.println("--------------------------------------------");

		for (Integer size : sizes) {
			System.out.printf("%8d", size);

			for (Sorter<Integer> s : sorters) {
				ArrayList<Integer> data = fillArrayWRandom(size);
				int cksum = checkSum(data);
				System.out.printf(" | %15f", time(s, data)*1000);

				// make sure the output is correctly sorted
				if (!isSorted(data))
					System.err.println("Data wasn't sorted correctly by: "
							+ s.getClass().toString());
				// make sure the output is the same numbers as the input
				if (checkSum(data) != cksum)
					System.err.println("Data values were changed by: "
							+ s.getClass().toString());
			}

			System.out.println();
		}
	}

	/**
	 * Get a random ArrayList of integers with size elements
	 * 
	 * @param size
	 *            number of random integers to generate
	 * @return a random ArrayList of size integers
	 */
	private ArrayList<Integer> fillArrayWRandom(int size) {
		ArrayList<Integer> returnMe = new ArrayList<Integer>(size);

		for (int i = 0; i < size; i++) {
			returnMe.add(rand.nextInt());
		}

		return returnMe;
	}

	/**
	 * Checks if the ArrayList is sorted
	 * 
	 * @param data
	 *            the data to check
	 * @return true is the data is sorted, false otherwise
	 */
	private boolean isSorted(ArrayList<Integer> data) {
		for (int i = 1; i < data.size(); i++) {
			if (data.get(i - 1) > data.get(i)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * compute a checksum for an ArrayList
	 *		to verify that the contents are unchanged
	 *
	 * @param data
	 *				the data to ve checked
	 * @return integer checksum
	 */
	private int checkSum(ArrayList<Integer> data) {
		int sum = 0;
		for (int i = 0; i < data.size(); i++)
			sum += data.get(i);
		return(sum);
	}

	/**
	 * Times the sorting algorithm for sorting data
	 * 
	 * @param s
	 *            the sorting algorithm
	 * @param nums
	 *            the numbers to sort
	 * @return the time taken to sort nums using sorting algorithm s
	 */
	private double time(Sorter<Integer> s, ArrayList<Integer> nums) {
		System.gc();
		timer = new Stopwatch();
		s.sort(nums);
		return timer.elapsedTime();
	}
}
