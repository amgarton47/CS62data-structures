/**
 * Frequency List of word/# of occurrence pairs
 * @author NAME GOES HERE!!
 **/
 
package wordsGeneric;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class FreqList {
    // list of associations holding words and their frequencies
    private HashMap<String, Integer> flist;

	/**
	 * constructor
	 */
    public FreqList() {
		// TODO implement FreqList constructor
    }
    
    /** 
     * Add a reference to a word to our list
     * 
     * @param word ... reference to add
     */
    public void add(String word) {
		// TODO implement FreqList.add()
    }

    /**
     * return a word from the FreqList based on a 0-1 value
     * 
     * @param p ... number between 0 and 1
     * @return a String from the list of recorded words
     * 		the probability of returning a word should be directly
     *		proportional to the number of times it has been referenced.
     */
    public String get(double p) {
		// TODO implement FreqList.get()
    	return "";
    }

    /**
     * String representation of the list
     * @return a string containing keys and counts
     * 			e.g. "the=4, story=2, ..."
     */
    public String toString() {
		// TODO implement FreqList.toString()
    	return "";
    }
    
    /**
     * static method to test this class
     * 
     * Suggested tests:
     * 		instantiate a list
     * 		add references to it
     * 		print the list to confirm correct reference counts
     * 		call p multiple times to confirm distribution of results
     */
    public static void main(String args[]) {
		// TODO: implement FreqList.main tester
    }
}
