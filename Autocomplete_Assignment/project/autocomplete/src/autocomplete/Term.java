package autocomplete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Term implements Comparable<Term> {
	private String query;
	private long weight;

	/**
	 * Initializes a term with the given query string and weight.
	 * 
	 * @param query  word to be stored
	 * @param weight associated frequency
	 */
	public Term(String query, long weight) {
		this.query = query;
		this.weight = weight;
	}

	/**
	 * @return comparator ordering elts by descending weight
	 */
	public static Comparator<Term> byReverseWeightOrder() {
		return (t1, t2) -> {
			return Long.compare(t2.weight, t1.weight);
		};
	}

	/**
	 * @param r Number of initial characters to use in comparing words
	 * @return comparator using lexicographic order, but using only the first r
	 *         letters of each word
	 */
	public static Comparator<Term> byPrefixOrder(int r) {
		return (t1, t2) -> {
			int r1 = Math.min(r, t1.query.length());
			int r2 = Math.min(r, t2.query.length());

			return t1.query.substring(0, r1).compareTo(t2.query.substring(0, r2));
		};
	}

	/**
	 * @param that Term to be compared
	 * @return -1, 0, or 1 depending on whether the word for THIS is
	 *         lexicographically smaller, same or larger than THAT
	 */
	public int compareTo(Term that) {
		return this.query.compareTo(that.query);
	}

	/**
	 * @return a string representation of this term in the following format: the
	 *         weight, followed by 2 tabs, followed by the word.
	 **/
	public String toString() {
		return weight + "\t\t" + query;
	}

	public static void main(String[] args) {

		List<Term> terms = new ArrayList<>();

		terms.add(new Term("band", 300));
		terms.add(new Term("apple", 800));
		terms.add(new Term("christmas", 300));
		terms.add(new Term("banana", 300));
		terms.add(new Term("band", 100));
		terms.add(new Term("carrot", 100));
		terms.add(new Term("bandana", 300));

		for (Term t : terms) {
			System.out.println(t);
		}

		// Collections.sort(terms, Term.byReverseWeightOrder());
		Collections.sort(terms, Term.byPrefixOrder(4));

		System.out.println();

		for (Term t : terms) {
			System.out.println(t);
		}
	}

}
