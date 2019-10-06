package autocomplete;

/** Class to create objects that can find all items matching a prefix
 * @author: Kim Bruce
 * @version:  8/12/2017
 **/
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Autocomplete implements AutocompleteInterface {
	// list of terms (value and weight) that can be searched
	private List<Term> terms;

	/**
	 * Initializes the data structure from the given array of terms.
	 * 
	 * @param terms
	 *            Collection of terms that can be searched
	 */
	public Autocomplete(List<Term> terms) {
		this.terms = terms;
		Collections.sort(this.terms);
	}

	/**
	 * @param prefix
	 *            String prefix to be matched
	 * @return list of terms that start with the given prefix, in descending
	 *         order of weight
	 */
	public List<Term> allMatches(String prefix) {
		List<Term> matching = new ArrayList<Term>();
		Term termKey = new Term(prefix, 1);
		int firstMatch = BinarySearchForAll.firstIndexOf(terms, termKey,
				Term.byPrefixOrder(prefix.length()));
		int lastMatch = BinarySearchForAll.lastIndexOf(terms, termKey,
				Term.byPrefixOrder(prefix.length()));
		if (firstMatch >= 0) {
			// If any matches found, copy terms matching prefix
			for (int i = firstMatch; i <= lastMatch; i++) {
				matching.add(terms.get(i));
			}
			Collections.sort(matching, Term.byReverseWeightOrder());
		}
		return matching;
	}

	/**
	 * Test to make sure Autocomplete works correctly
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
		lst.add(new Term("goodrye", 45));
		lst.add(new Term("goodrye", 45));
		Autocomplete auto = new Autocomplete(lst);
		String key = "good";
		List<Term> matches = auto.allMatches(key);
		System.out.println("Matching " + key);
		for (Term elt : matches) {
			System.out.println(elt);
		}

	}
}
