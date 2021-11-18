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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.w3c.dom.Text;

public class TextGenerator {

    // symbol table of string pairs and frequency lists
    protected HashMap<WordPair, FreqList> hm;

    public TextGenerator() {
        hm = new HashMap<WordPair, FreqList>();
    }

    /**
     * Add a reference to <first,second>->third to our hash map
     * 
     * @param first  string in triad
     * @param second string in triad
     */
    public void enter(String first, String second, String third) {
        WordPair wp = new WordPair(first, second);
        FreqList fl = new FreqList();
        fl.add(third);

        FreqList val = hm.putIfAbsent(wp, fl);

        if (val != null) {
            val.add(third);
            hm.replace(wp, val);
        }
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
        Random r = new Random();

        WordPair wp = new WordPair(first, second);
        FreqList fl = hm.get(wp);

        if (fl == null) {
            return "";
        } else
            return fl.get(r.nextDouble());
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

            TextGenerator textGenerator = new TextGenerator();
            ArrayList<String> tokens = new ArrayList<String>();

            while (ws.hasMoreTokens()) {
                tokens.add(ws.nextToken());
            }

            for (int i = 0; i < tokens.size() - 3; i++) {
                String first, second, third;
                first = tokens.get(i);
                second = tokens.get(i + 1);
                third = tokens.get(i + 2);

                textGenerator.enter(first, second, third);
            }

            // pick two starting words from a randomly selected StringPair in input
            ArrayList<WordPair> keysAsArray = new ArrayList<WordPair>(textGenerator.hm.keySet());
            WordPair sp = keysAsArray.get(new Random().nextInt(keysAsArray.size()));
            String s1 = sp.getFirst(), s2 = sp.getSecond();

            // print out first two words
            System.out.println("\nGenerated text based on input above:");
            System.out.println("------------------------------------");
            System.out.print(s1 + " " + s2 + " ");

            final int NUM_GENERATED_WORDS = 400;
            final int NUM_WORDS_PER_LINE = 20;

            // generate 400 words of text, choosing likely random words to follow each
            // preceding pair.
            for (int i = 0; i < NUM_GENERATED_WORDS; i++) {
                String nextWord = textGenerator.getNextWord(s1, s2);
                System.out.print(nextWord + " ");

                if (i != 0 && i % NUM_WORDS_PER_LINE == 0)
                    System.out.println();

                s1 = s2;
                s2 = nextWord;

                if (s1 == "" && s2 == "")
                    break;
            }
        } else {
            System.out.println("User cancelled file chooser");
        }
    }
}