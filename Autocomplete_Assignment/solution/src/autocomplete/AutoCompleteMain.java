package autocomplete;

/**
 * Program to autocomplete a prefix typed in by user in a file consisting
 * of entries and associated numbers, where number is provided before
 * the entry.  The program takes a parameter k as a run-time argument
 * and prompts the user to provide a file to be searched.  The user
 * is prompted to enter a prefix in the console.  The program then 
 * prints in the console the k most popular matches according to 
 * the numbers associated with each matching entry.  The program 
 * continues prompting the user for more prefixes until the user types
 * control-D or otherwise halts the program.
 * 
 * @author Kim Bruce
 * @date 8/17/2017
 **/

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class AutoCompleteMain {

	public static void main(String[] args) {
		List<Term> terms = new ArrayList<Term>();

		File file = new File(args[1]);
		if (loadedInput(terms, file)) {
			// Get number of items to display
			int numToDisplay = Integer.parseInt(args[0]);
			Autocomplete autocomplete = new Autocomplete(terms);

			// Start reading prefixes from console window
			// and print most popular matches
			Scanner prefixScanner = new Scanner(System.in);
			System.out.print("Enter a new prefix: ");
			while (prefixScanner.hasNext()) {
				String prefix = prefixScanner.nextLine();
				List<Term> results = autocomplete.allMatches(prefix);
				System.out.println("There are " + results.size()
						+ " matches.");
				if(numToDisplay < results.size()) {
					System.out.println("The " + numToDisplay + " largest are:");
				} else {
					System.out.println("The matching items are:");
				}
				for (int i = 0; i < Math.min(numToDisplay, results.size()); i++) {
					System.out.println(results.get(i));
				}
				System.out.print("\nEnter a new prefix: ");
			}
			prefixScanner.close();
		}
	}

	/**
	 * Load input from file to list of terms terms
	 * 
	 * @param terms
	 *            list of resulting terms
	 * @param file
	 *            source of input data
	 * @return whether load succeeded
	 */
	private static boolean loadedInput(List<Term> terms, File file) {
		try {
			Scanner input = new Scanner(file);

			int fileSize = input.nextInt();
			for (int i = 0; i < fileSize; i++) {
				long weight = input.nextLong();
				String nextItem = input.nextLine().trim();
				terms.add(new Term(nextItem, weight));
			}
			input.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"Can't open selected file " + e.getMessage());
			return false;
		}
		return true;
	}

}
