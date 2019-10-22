package lab04;

import java.util.Vector;

public class ArrayListTimer {

	private static final int INITIAL_CAPACITY = 2;
	private static final int MIN_SIZE = 1000;
	private static final int MAX_SIZE = 128001;
	
	/**
	 * time the addition of maxSize strings to a new ArrayList
	 * 
	 * @param maxSize ... maximum number of strings to add
	 * @param capacityIncrement ... size increment when growing ArrayList
	 * @return stop-watch time (in seconds) to perform operation
	 */
	public static double run(int maxSize, int capacityIncrement) {
		ArrayList<String> testlist = new ArrayList<String>(INITIAL_CAPACITY, capacityIncrement);
		
		// garbage collect before test to prevent GC during test
		System.gc();
		
		// time filling the ArrayList to the specified size
		Stopwatch timer = new Stopwatch();
		for(int i = 0; i < maxSize; i++)
			testlist.add("new string");
		
		return timer.elapsedTime();
	}
	
	/**
	 * run a test, up to a specified maximum size, for a set of increments
	 * 
	 * @param maxSize ... max size to add to
	 * @param capacityIncrements ... list of increments
	 * @return list of times (in seconds)
	 */
	public static Vector<Double> trial(int maxSize, ArrayList<Integer> capacityIncrements) {
		// run a test for each specified capacity increment
		Vector<Double> results = new Vector<Double>();
		for(int i = 0; i < capacityIncrements.size(); i++) {
			results.add(run(maxSize, capacityIncrements.get(i)));
		}
		
		return results;
	}
	
	/**
	 * Run and time tests for range of sizes and increments
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		// create a list of increments to test
		ArrayList<Integer> increments = new ArrayList<Integer>();
		increments.add(1);
		increments.add(10);
		increments.add(0);
		
		// print out a table header
		System.out.format(" %8s | %12s | %12s | %12s\n", "size", "linear (1)", "linear (10)", "double");
		System.out.println(" ------------------------------------------------------");
		
		// test a range of sizes for each increment
		for (int size = MIN_SIZE; size < MAX_SIZE; size *= 2) {
			Vector<Double> results = trial(size, increments);
			System.out.format(" %8d | %12f | %12f | %12f\n", size, results.get(0), results.get(1), results.get(2));
		}
	}
}
