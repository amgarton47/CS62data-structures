/**
 * Frequency List of word/# of occurrence pairs
 */
package wordsGeneric;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class FreqList {
	// symbol table associations holding words and their frequencies
	protected HashMap<String, Integer> flist;

	// This is a double to save time later when dividing
	protected double totalFreq;

	/**
	 * Constructs a new FreqList
	 */
	public FreqList() {
		flist = new HashMap<String, Integer>();
		totalFreq = 0.0;
	}

	/**
	 * Add word to list or, if word already occurs, increment its frequency
	 * 
	 * @param word
	 */
	public void insert(String word) {

		// Determine if any entry in flist contains the key word
		Integer value = flist.get(word);

		// There is already an entry in flist with key word
		if (value != null) {
			flist.put(word, value + 1);
		}

		// There is no entry in flist with key word
		else {
			flist.put(word, 1);
		}
		totalFreq += 1.0;
	}

	/**
	 * Return a word from the freqList
	 * 
	 * @param p
	 *            probability
	 * @pre 0 <= p <= 1
	 * @returns randomly chosen word from the list
	 * @throws IllegalArgumentException
	 *             if p < 0 or p > 1
	 * @throws AssertionError
	 *             if no word from list is selected
	 */
	public String get(double p) {
		if (p < 0 || p > 1) {
			throw new IllegalArgumentException("p must be between 0 and 1 inclusive");
		}
		if (totalFreq == 0.0) {
			return "";
		} else {
			double wordProb = 0.0;
			Iterator hmIterator = flist.entrySet().iterator();

			while (hmIterator.hasNext()) {
				Map.Entry mapElement = (Map.Entry) hmIterator.next();
				String key = (String) mapElement.getKey();
				wordProb += (int) mapElement.getValue() / totalFreq;
				if (p <= wordProb) {
					return key;
				}
			}

			// Should never reach here
			throw new AssertionError("Reached end of list and prob still less than 1");
		}
	}
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

	// static method to test the class
	public static void main(String args[]) {
		FreqList list = new FreqList();
		list.insert("cow");
		list.insert("apple");
		list.insert("cow");
		list.insert("banana");
		list.insert("dog");
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
