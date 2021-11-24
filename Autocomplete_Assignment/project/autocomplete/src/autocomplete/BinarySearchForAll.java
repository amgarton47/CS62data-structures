package autocomplete;

import java.security.Key;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BinarySearchForAll {
	// flag indicating whether a key occurs at all in the list
	public static final int NOT_FOUND = -1;

	/**
	 * Returns the index of the first element in aList that equals key
	 *
	 * @param aList      Ordered (via comparator) list of items to be searched
	 * @param key        item searching for
	 * @param comparator Object with compare method corresponding to order on aList
	 * @return Index of first item in aList matching key or -1 if not in aList
	 **/
	public static <Key> int firstIndexOf(List<Key> aList, Key key, Comparator<Key> comparator) {
		int lo = 0, hi = aList.size() - 1;
		int found = -1;

		while (lo <= hi) {
			int mid = lo + (hi - lo) / 2;

			if (comparator.compare(key, aList.get(mid)) < 0) {
				hi = mid - 1;
			} else if (comparator.compare(key, aList.get(mid)) > 0) {
				lo = mid + 1;
			} else {
				found = mid;
				hi = mid - 1;
			}
		}

		return found;
	}

	/**
	 * Returns the index of the last element in aList that equals key
	 * 
	 * @param aList      Ordered (via comparator) list of items to be searched
	 * @param key        item searching for
	 * @param comparator Object with compare method corresponding to order on aList
	 * @return Location of last item of aList matching key or -1 if no such key.
	 **/
	public static <Key> int lastIndexOf(List<Key> aList, Key key, Comparator<Key> comparator) {
		int lo = 0, hi = aList.size() - 1;
		int found = -1;

		while (lo <= hi) {
			int mid = lo + (hi - lo) / 2;

			if (comparator.compare(key, aList.get(mid)) < 0) {
				hi = mid - 1;
			} else if (comparator.compare(key, aList.get(mid)) > 0) {
				lo = mid + 1;
			} else {
				found = mid;
				lo = mid + 1;
			}
		}

		return found;
	}

	public static void main(String[] args) {
		Term t1 = new Term("a", 1);
		Term t2 = new Term("c", 2);
		Term t3 = new Term("c", 2);
		Term t4 = new Term("g", 0);

		List<Term> list = new ArrayList<Term>();

		Collections.sort(list, Term.byReverseWeightOrder());

		for (Term t : list) {
			System.out.println(t);
		}

		list.add(t1);
		list.add(t2);
		list.add(t3);
		list.add(t4);

		int fst = firstIndexOf(list, t2, Term.byReverseWeightOrder());
		int snd = lastIndexOf(list, t2, Term.byReverseWeightOrder());

		System.out.println(fst);
		System.out.println(snd);
	}

}
