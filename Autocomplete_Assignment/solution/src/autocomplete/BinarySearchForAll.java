package autocomplete;

/**
 * Class of methods to find first and last match of a key in a list sorted by
 * a given comparator.
 * @author Kim Bruce
 * @version 8/17/2017
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BinarySearchForAll {
	// flag indicating whether key occurs at all in the list
	public static final int NOT_FOUND = -1;

	/**
	 * Returns the index of the first element in aList that equals key
	 *
	 * @param aList
	 *            Ordered (via comparator) list of items to be searched
	 * @param key
	 *            item searching for
	 * @param comparator
	 *            Object with compare method corresponding to order on aList
	 * @return Index of first item in aList matching key or -1 if not in aList
	 **/
	public static <Key> int firstIndexOf(List<Key> aList, Key key,
			Comparator<Key> comparator) {
		return firstSearch(aList, 0, aList.size() - 1, key, comparator);
	}

	/**
	 * Helper function to find first key in aList that is between start and end
	 * inclusive.
	 * 
	 * @param aList
	 *            Ordered list of keys to be searched
	 * @param start
	 *            First place to start looking in aList
	 * @param end
	 *            last place to look in aList
	 * @param key
	 *            element to be searched for in aList
	 * @param comparator
	 *            Object with compare method corresponding to order on aList
	 * @return Index of first item in aList matching key or -1 if not in aList
	 */
	private static <Key> int firstSearch(List<Key> aList, int start,
			int end, Key key, Comparator<Key> comparator) {
		if (start == end + 1) {
			return NOT_FOUND;
		}
		int middle = (start + end) / 2;
		Key midElt = aList.get(middle);
		if (comparator.compare(midElt, key) < 0) {
			// System.out.println(midElt + "is too small");
			return firstSearch(aList, middle + 1, end, key, comparator);
		} else if (comparator.compare(midElt, key) > 0) {
			// System.out.println(midElt + "is too big");
			return firstSearch(aList, start, middle - 1, key, comparator);
		} else {
			if (middle == 0
					|| comparator.compare(aList.get(middle - 1), key) != 0) {
				return middle;
			} else {
				return firstSearch(aList, start, middle - 1, key, comparator);
			}
		}
	}

	/**
	 * Returns the index of the last element in aList that equals key
	 * 
	 * @param aList
	 *            Ordered (via comparator) list of items to be searched
	 * @param key
	 *            item searching for
	 * @param comparator
	 *            Object with compare method corresponding to order on aList
	 * @return Location of last item of aList matching key or -1 if no such key.
	 **/
	public static <Key> int lastIndexOf(List<Key> a, Key key,
			Comparator<Key> comparator) {
		return lastSearch(a, 0, a.size() - 1, key, comparator);
	}

	/**
	 * Helper function to find last key in aList that is between start and end
	 * inclusive.
	 * 
	 * @param aList
	 *            Ordered list of keys to be searched
	 * @param start
	 *            First place to start looking in aList
	 * @param end
	 *            last place to look in aList
	 * @param key
	 *            element to be searched for in aList
	 * @param comparator
	 *            Object with compare method corresponding to order on aList
	 * @return Index of last item in aList matching key or -1 if not in aList
	 */
	private static <Key> int lastSearch(List<Key> a, int start, int end,
			Key key, Comparator<Key> comparator) {
		if (start == end + 1) {
			return NOT_FOUND;
		}
		int middle = (start + end) / 2;
		Key midElt = a.get(middle);
		if (comparator.compare(midElt, key) < 0) {
			return lastSearch(a, middle + 1, end, key, comparator);
		} else if (comparator.compare(midElt, key) > 0) {
			return lastSearch(a, start, middle - 1, key, comparator);
		} else {
			if (middle == a.size() - 1
					|| comparator.compare(a.get(middle + 1), key) != 0) {
				return middle;
			} else {
				return lastSearch(a, middle + 1, end, key, comparator);
			}
		}
	}

	/**
	 * Method to test binary search operations.
	 * @param args
	 * 		ignored
	 */
	public static void main(String[] args) {
		List<Term> lst = new ArrayList<Term>();
		lst.add(new Term("hello", 7));
		lst.add(new Term("goodrye", 99));
		lst.add(new Term("ice", 47));
		lst.add(new Term("goodie", 12));
		lst.add(new Term("goodrye", 45));
		lst.add(new Term("goodrye", 45));

		Collections.sort(lst, Term.byPrefixOrder(8));
		System.out.println("\nSorted by weight");
		for (Term elt : lst) {
			System.out.println(elt);
		}

		for (Term key : lst) {
			System.out.println(BinarySearchForAll.lastIndexOf(lst, key,
					Term.byPrefixOrder(8)));
		}

	}

}
