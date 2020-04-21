/**
 * Frequency List of word/# of occurrence pairs
 * @author TODO: NAME GOES HERE!!
 **/
 
package wordsGeneric;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class FreqList {
    // list of associations holding words and their frequencies
    private HashMap<String, Integer> flist;
    // this is a double to save time later when dividing
	  protected double totalFreq;


	/**
	 * constructor
	 */
    public FreqList() {
		  // TODO implement FreqList constructor to instantiate variables
    }
    
    /** 
     * Add a reference to a word to our list
     * 
     * @param word ... reference to add
     */
    public void add(String word) {
		  // TODO implement FreqList.add()

    	// Determine if any entry in flist contains the key word and save its value

      // If there is already an entry in flist with key word increase its value by 1
      // else insert the word in the flist with the value being equal to 1

      //increase total frequency by 1.0


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

      //illegal probabilities
      if (p < 0 || p > 1) {
        throw new IllegalArgumentException("p must be between 0 and 1 inclusive");
      }
      //empty dictionary
      if (totalFreq == 0.0) {
        return "";
      } 
      else {
        double wordProb = 0.0;
        Iterator hmIterator = flist.entrySet().iterator();
        //return a word based on the provided probability
        while (hmIterator.hasNext()) {
          Map.Entry mapElement = (Map.Entry) hmIterator.next();
          String key = (String) mapElement.getKey();
          wordProb += (int) mapElement.getValue() / totalFreq;
          if (p <= wordProb) {
            return key;
          }
        }
      }    
    }

    /**
     * String representation of the list
     * @return a string containing keys and counts
     */
    public String toString() {
      StringBuilder rep = new StringBuilder();
      rep.append("Frequency List: ");
      Iterator hmIterator = flist.entrySet().iterator();
      // Iterate through the hashmap
      while (hmIterator.hasNext()) {
        Map.Entry mapElement = (Map.Entry) hmIterator.next();
        rep.append("<" + mapElement.getKey() + "=" + (int) mapElement.getValue()+">");
      }
      return rep.toString();
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
      FreqList list = new FreqList();
      list.add("cow");
      list.add("apple");
      list.add("cow");
      list.add("banana");
      list.add("dog");
      System.out.println(list);
      
      Random rand = new Random();
      for (int i = 0; i < 10; ++i) {
        double randomOne = rand.nextDouble();
        System.out.println(randomOne);
        String word = list.get(randomOne);
        System.out.println(word);
      }
    }
}
