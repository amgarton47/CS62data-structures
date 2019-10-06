package autocomplete;

/**
 * Class to represent pairs of value and its associated weight.
 * @author Kim Bruce
 * @version 8/17/2017
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Term implements Comparable<Term> {
	// save word and its associated frequency
	protected String word;
	protected long frequency;

	/**
	 * Initializes a term with the given query string and weight.
	 * 
	 * @param query
	 *            word to be stored
	 * @param weight
	 *            associaged frequency
	 */
	public Term(String query, long weight) {
		word = query;
		frequency = weight;
	}

	/**
	 * @return comparator ordering elts by descending weight
	 */
	public static Comparator<Term> byReverseWeightOrder() {
		return (Term a, Term b) -> {
			if (a.frequency > b.frequency) {
				return -1;
			} else if (a.frequency < b.frequency) {
				return 1;
			} else {
				return 0;
			}
		};
	}

	/**
	 * @param r
	 *            Number of initial characters to use in comparing words
	 * @return comparator using lexicographic order, but using only the first r
	 *         letters of each word
	 */
	public static Comparator<Term> byPrefixOrder(int r) {
		return (Term a, Term b) -> {
				int aLast = Integer.min(r, a.word.length());
				int bLast = Integer.min(r, b.word.length());
				return a.word.substring(0, aLast).compareTo(
						b.word.substring(0, bLast));
		};
	}

	/**
	 * @param that
	 *            Term to be compared
	 * @return -1, 0, or 1 depending on whether the word for that is smaller,
	 *         the same or larger than for the receiver
	 */
	public int compareTo(Term that) {
		return word.compareTo(that.word);
	}

	/**
	 * @return a string representation of this term in the following format: the
	 *         weight, followed by 2 tabs, followed by the word.
	 **/
	public String toString() {
		return "" + frequency + "\t\t" + word;
	}

	/**
	 * Code to test the Term class
	 * 
	 * @param args
	 *            ignored
	 */
	public static void main(String[] args) {
		List<Term> lst = new ArrayList<Term>();
		lst.add(new Term("hello", 7));
		lst.add(new Term("goodrye", 99));
		lst.add(new Term("ice", 47));
		lst.add(new Term("goodie", 12));

		Collections.sort(lst, Term.byReverseWeightOrder());
		System.out.println("\nSorted by weight");
		for (Term elt : lst) {
			System.out.println(elt);
		}

		Collections.sort(lst, Term.byPrefixOrder(1));
		System.out.println("\nSorted by prefix order");
		for (Term elt : lst) {
			System.out.println(elt);
		}

	}

}
