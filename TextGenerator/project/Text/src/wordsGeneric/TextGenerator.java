/**
 * Class to artificially generate sentences
 * 
 * Fix these comments!!
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

    // add any instance variables needed and a constructor
    
    /** 
     * Add appropriate comments
     */
    public void enter(String first, String second, String third) {

    }

    public String getNextWord(String first, String second) {
        return "";  // replace by real body
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
                JOptionPane.showMessageDialog(null, "Can't load file "
                        + e.getMessage());
            }

            // insert code to print table & generate new text using
            // random number generator
        } else {
            System.out.println("User cancelled file chooser");
        }
    }
}
