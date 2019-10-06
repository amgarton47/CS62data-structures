package autocomplete;


import java.util.Comparator;

public class Term implements Comparable<Term> {


	/**
	 * Initializes a term with the given query string and weight.
	 * 
	 * @param query
	 *            word to be stored
	 * @param weight
	 *            associated frequency
	 */
	public Term(String query, long weight) {

	}

	/**
	 * @return comparator ordering elts by descending weight
	 */
	public static Comparator<Term> byReverseWeightOrder() {
		return null;  // fix this!
	}

	/**
	 * @param r
	 *            Number of initial characters to use in comparing words
	 * @return comparator using lexicographic order, but using only the first r
	 *         letters of each word
	 */
	public static Comparator<Term> byPrefixOrder(int r) {
		return null;   // fix this
	}

	/**
	 * @param that
	 *            Term to be compared
	 * @return -1, 0, or 1 depending on whether the word for that is smaller,
	 *         the same or larger than for the receiver
	 */
	public int compareTo(Term that) {
		return 0;       // fix this
	}

	/**
	 * @return a string representation of this term in the following format: the
	 *         weight, followed by 2 tabs, followed by the word.
	 **/
	public String toString() {
		return null;    // fix this
	}

}
