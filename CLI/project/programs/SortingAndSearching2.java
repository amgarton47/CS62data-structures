package chapter7;

import java.util.Arrays;
import java.util.Scanner;

public class SortingAndSearching2 {

	private static void print(int x[], int columnsize, int numPerLine) {
		for (int i = 0; i < x.length; i++) {
			System.out.printf("%" + columnsize + "d", x[i]);
			if ((i + 1) % numPerLine == 0 || (i + 1) == x.length)
				System.out.println();
		}
	}

	private static int[]generateJunk(int count) {
		int values[] = new int[count];
		for (int i = 0; i < values.length; i++) {
			values[i] = (int) (Math.random() * 100) + 1;
		}	
		return values;
	}
	

	public static void main(String[] args) {

		int values[] = generateJunk(100);
		System.out.println("Random Junk!");
		print(values, 5, 10);
		System.out.println();


		// linear search the junk
		Scanner input = new Scanner(System.in);
		int key;
		do {
			System.out.print("Enter key: ");
			key = input.nextInt();
			int index = LinearSearch.linearSearch(values, key);
			if (index < 0) {
				System.out.println("Not in Array");
			} else {
				System.out.println(key + " found in position " + index);
			}
		} while (key >= 0);
		
		// Duplicate the junk
		int duplicate[] = Arrays.copyOf(values, values.length);


		// Sort the Duplicate Junk
		Arrays.sort(duplicate);
		System.out.println("Sorted Junk!");
		System.out.println(Arrays.toString(duplicate));
		System.out.println();

		// Now Binary Search the dups array
		do {
			System.out.print("Enter key: ");
			key = input.nextInt();
			int index = Arrays.binarySearch(duplicate, key);
			System.out.println(index);
			if (index < 0) {
				System.out.println("Not in Array, but key could be placed in position " + (-index - 1));
			} else {
				System.out.println(key + " found in position " + index);
			}
		} while (key >= 0);
		
		input.close();
	}

}
