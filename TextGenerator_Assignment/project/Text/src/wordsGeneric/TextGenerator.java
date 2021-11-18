/**
 * Class to artificially generate sentences
 * 
 * @author Aidan Garton
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

    // add any instance variables needed and a constructor

    /**
     * Add a reference to <first,second>->third to our letPairList
     * 
     * @param first  string in triad
     * @param second string in triad
     */
    public void enter(String first, String second, String third) {
        // TODO implement TextGenerator.enter()
    }

    /**
     * Use the <first,second> FreqList to choose a word to follow them
     * 
     * @param first  String in triad
     * @param second String in triad
     * @return likely third String to follow the first two
     *
     *         Note: it would also be very good to do something graceful if nothing
     *         has followed the <first,second> pair.
     */
    public String getNextWord(String first, String second) {
        // TODO implement TextGenerator.getNextWord()
        return "";
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
                    System.out.println(line);
                    line = input.readLine();
                }
                System.out.println("Finished reading data");
                // Close the file
                input.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Can't load file " + e.getMessage());
            }

            // TODO create extGenerator, populate it from the WordStream

            // TODO (for debug) print resulting <StringPair,FreqList> map

            // TODO (for debug) add a few getNextWord test cases

            // TODO pick two starting words
            // TODO generate 400 words of text, choosing likely random
            // words to follow each preceding pair.
        } else {
            System.out.println("User cancelled file chooser");
        }
    }
}
