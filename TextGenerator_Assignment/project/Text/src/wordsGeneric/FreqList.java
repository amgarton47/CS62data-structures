/**
 * Frequency List of word/# of occurrence pairs
 * @author NAME GOES HERE!!
 **/

package wordsGeneric;

import java.security.Key;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class FreqList {
  // list of associations holding words and their frequencies
  private HashMap<String, Integer> flist;
  private double totalFreq;

  /**
   * constructor
   */
  public FreqList() {
    flist = new HashMap<String, Integer>();
    totalFreq = 0.0;
  }

  /**
   * Add a reference to a word to our list
   * 
   * @param word ... reference to add
   */
  public void add(String word) {
    Integer prevVal = flist.putIfAbsent(word, 1);

    if (prevVal != null) {
      flist.replace(word, prevVal + 1);
    }
    totalFreq++;
  }

  /**
   * return a word from the FreqList based on a 0-1 value
   * 
   * @param p ... number between 0 and 1
   * @return a String from the list of recorded words the probability of returning
   *         a word should be directly proportional to the number of times it has
   *         been referenced.
   */
  public String get(double p) {
    double tempProb = 0.0;
    for (HashMap.Entry<String, Integer> entry : flist.entrySet()) {
      String key = entry.getKey();
      int val = entry.getValue();

      tempProb += val / totalFreq;

      if (p <= tempProb) {
        return key;
      }
    }
    throw new AssertionError("Iterated through entire hashmap but temp probability < 1");
  }

  /**
   * String representation of the list
   * 
   * @return a string containing keys and counts e.g. "the=4, story=2, ..."
   */
  public String toString() {
    String str = "<";
    for (HashMap.Entry<String, Integer> entry : flist.entrySet()) {
      str += "(" + entry.getKey() + ", " + entry.getValue() + "), ";
    }

    return str.substring(0, str.length() - 2) + ">";
  }

  /**
   * static method to test this class
   * 
   * Suggested tests: instantiate a list add references to it print the list to
   * confirm correct reference counts call p multiple times to confirm
   * distribution of results
   */
  public static void main(String args[]) {
    System.out.println("FreqList Tester");

    FreqList fl = new FreqList();

    fl.add("hello");
    fl.add("hello");
    fl.add("hey");
    fl.add("bye");
    fl.add("bye");

    System.out.println(fl);

    Random r = new Random();
    System.out.println(fl.get(r.nextDouble()));
  }
}
