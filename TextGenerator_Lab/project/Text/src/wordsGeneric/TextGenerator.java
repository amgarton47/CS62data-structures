/**
 * Class to artificially generate sentences
 * 
 * @author NAME GOES HERE!!
 **/
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

    // symbol table of string pairs and frequency lists
    protected HashMap<StringPair, FreqList> letPairList;

	// Random number generator object
	Random rng;

    // TODO: Fill in constructor
	public TextGenerator() {
	}

    
    /** 
     * Add a reference to <first,second>->third to our letPairList
     * @param first string in triad
     * @param second string in triad
     */
    public void enter(String first, String second, String third) {
		// TODO implement TextGenerator.enter()

        //get a reference freqList to a FreqList object by passing the StringPair (first, second) to letPairList

        //if freqList is null (no (first,second) key in letPairList) 
            // 	instantiate freqList as a new FreqList object
            //  add to it the third word
            //  insert this trigram to letPairList
        // else
            // add to freqList the third word
    }

    /**
     * Use the <first,second> FreqList to choose a word to follow them
     * @param first String in triad
     * @param second String in triad
     * @return likely third String to follow the first two
	 *
	 * Note: it would also be very good to do something graceful
	 *       if nothing has followed the <first,second> pair.
     */
    public String getNextWord(String first, String second) {
		// TODO implement TextGenerator.getNextWord()

        // create a new StringPair with the given first and second Strings
        // pass the StringPair to letPairList to get a FreqList
        // if the FreqList object is null
            //return the empty String
        //else pass a random double to the get method of freqList and return the returned word
        return ""; 
    }

    //Make sure how the main method works
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
                    System.out.println(line);
                    line = input.readLine();
                }
                System.out.println("Finished reading data");
                // Close the file
                input.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Can't load file "
                        + e.getMessage());
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
			for (int wordNo = 2; wordNo < 400; wordNo++) {
				String third = table.getNextWord(first, second);
				newText = newText + " " + third;
				if (wordNo % 20 == 0) {
					newText = newText + "\n";
				}
				first = second;
				second = third;
			}
			System.out.println(table.letPairList);
			System.out.println("Generated data:\n" + newText);
        } else {
            System.out.println("User cancelled file chooser");
        }
    }
}
