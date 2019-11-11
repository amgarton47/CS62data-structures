/**
 * Class to artificially generate sentences
 * 
 * Fix these comments!!
 * @author NAME GOES HERE!!
 */
package wordsGeneric;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class TextGenerator {

	// map of letter pairs and their associated frequency lists
	protected HashMap<StringPair, FreqList> trigrams;

	// Random number generator object
	Random rng;

	// Default constructor
	public TextGenerator() {
		trigrams = new HashMap<StringPair, FreqList>();
		rng = new Random();
	}

	public StringPair randomPair() {
		int randomNumber = rng.nextInt(trigrams.size());
		Object[] entries = trigrams.entrySet().toArray();
		Map.Entry randomEntry = (Map.Entry) entries[randomNumber];
		return (StringPair) randomEntry.getKey();
	}

	/**
	 * Records the trigram <first, second, third>
	 */
	public void enter(String first, String second, String third) {
		FreqList freqList = trigrams.get(new StringPair(first, second));
		if (freqList == null) {
			// Add the pair of words (along with a new frequency list containing
			// the third word) to the list of trigrams
			freqList = new FreqList();
			freqList.insert(third);
			trigrams.put(new StringPair(first, second), freqList);
		} else {
			freqList.insert(third);
		}
	}

	/**
	 * Given a pair of words <first, second> returns a randomly selected third word
	 * 
	 * @param first
	 *            first word
	 * @param second
	 *            second word
	 * @return randomly selected word
	 */
	public String getNextWord(String first, String second) {
		StringPair newPair = new StringPair(first, second);
		FreqList freqList = trigrams.get(newPair);
		String returnedWord = "";
		
		if (freqList != null) {
			returnedWord = freqList.get(rng.nextDouble());
		}
		return returnedWord;
	}

	// START OF CODE FOR MAIN PROGRAM -- WRITE & FIX COMMENTS
	public static void main(String args[]) {

		WordStream ws = new WordStream();
		JFileChooser dialog = new JFileChooser(".");

		// Display the dialog box and make sure the user did not cancel.
		if (dialog.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			// Find out which file the user selected.
			File file = dialog.getSelectedFile();
			try {
				// Open the file.
				BufferedReader input = new BufferedReader(new FileReader(file));

				// Fill up the editing area with the contents of the file being
				// read.
				String line = input.readLine();
				while (line != null) {
					ws.addLexItems(line.toLowerCase());
					// System.out.println(line);
					line = input.readLine();
				}
				// System.out.println("Finished reading data");
				// Close the file
				input.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Can't load file " + e.getMessage());
			}

			System.out.println("Finished entering words in wordstream");
			TextGenerator table = new TextGenerator();

			String first = ws.nextToken();
			String second = ws.nextToken();

			String origFirst = first;
			String origSecond = second;
			while (ws.hasMoreTokens()) {
				String third = ws.nextToken();
				table.enter(first, second, third);
				first = second;
				second = third;
			}

			first = origFirst;
			second = origSecond;
			String newText = "" + origFirst + " " + origSecond;
			for (int wordNo = 2; wordNo < 200; wordNo++) {
				String third = table.getNextWord(first, second);
				newText = newText + " " + third;
				if (wordNo % 20 == 0) {
					newText = newText + "\n";
				}
				first = second;
				second = third;
			}
			System.out.println(table.trigrams);
			System.out.println("Generated data:\n" + newText);

		} else {
			System.out.println("User cancelled file chooser");
		}
	}
}
