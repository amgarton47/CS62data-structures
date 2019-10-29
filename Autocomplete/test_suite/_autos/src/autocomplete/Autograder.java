package autocomplete;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class Autograder {
	Term term1, term2, term3, term4, term5;
	@Before
	public void setUp() throws Exception {
		term1 = new Term("hello", 7);
		term2 = new Term("goodbye", 99);
		term3 = new Term("banana", 27);
		term4 = new Term("goodie", 47);
		term5 = new Term("goofie", 12);
	}


	/******************* TERM CLASS TESTING *******************/
	@Test
	public void testTermByReverseWeightOrder() {
		List<Term> lst = new ArrayList<Term>();
		lst.add(term1);		// hello, 7
		lst.add(term2);		// goodbye, 99
		lst.add(term3);		// banana, 27
		lst.add(term4);		// goodie, 47
		lst.add(term5);		// goofie, 12

		Collections.sort(lst, Term.byReverseWeightOrder());

		List<Term> lst2 = new ArrayList<Term>();
		lst2.add(term2);		// goodbye, 99
		lst2.add(term4);		// goodie, 47
		lst2.add(term3);		// banana, 27
		lst2.add(term5);		// goofie, 12
		lst2.add(term1);

		assertEquals(lst2, lst);
	}

	@Test
	public void testTermByPrefixOrder() {
		List<Term> lst = new ArrayList<Term>();
		lst.add(term1);		// hello, 7
		lst.add(term2);		// goodbye, 99
		lst.add(term3);		// banana, 27
		lst.add(term4);		// goodie, 47
		lst.add(term5);		// goofie, 12

		Collections.sort(lst, Term.byPrefixOrder(1));

		List<Term> lst2 = new ArrayList<Term>();
		lst2.add(term3);		// banana, 27
		lst2.add(term2);		// goodbye, 99
		lst2.add(term4);		// goodie, 47
		lst2.add(term5);		// goofie, 12
		lst2.add(term1);		// hello, 7

		assertEquals(lst2, lst);
	}



	/******************* BINARYSEARCHFORALL CLASS TESTING *******************/
	@Test
	public void testFirstIndexOf() {
		List<Term> lst = new ArrayList<Term>();
		lst.add(term3);		// banana, 27
		lst.add(term2);		// goodbye, 99
		lst.add(term4);		// goodie, 47
		lst.add(term5);		// goofie, 12
		lst.add(term1);		// hello, 7

		int index = BinarySearchForAll.firstIndexOf(lst, term3, Term.byPrefixOrder(3));

		assertEquals(0, index);
	}

	@Test
	public void testLastIndexOf() {
		List<Term> lst = new ArrayList<Term>();
		lst.add(term4);		// goodie, 47
		lst.add(term5);		// goofie, 12
		lst.add(term1);		// hello, 7
		lst.add(term1);		// hello, 7
		lst.add(term1);		// hello, 7

		int index = BinarySearchForAll.lastIndexOf(lst, term1, Term.byPrefixOrder(3));

		assertEquals(4, index);
	}


	/******************* AUTOCOMPLETE CLASS TESTING *******************/
	@Test
	public void testAllMatches(){
		List<Term> lst = new ArrayList<Term>();
                lst.add(term1);		// hello, 7
                lst.add(term2);		// goodbye, 99
                lst.add(term3);		// banana, 27
                lst.add(term4);		// goodie, 47
                lst.add(term5);		// goofie, 12
                Autocomplete auto = new Autocomplete(lst);
                String key = "good";
                List<Term> matches = auto.allMatches(key);

		List<Term> lst2 = new ArrayList<Term>();
		lst2.add(term2);	// goodbye, 99
		lst2.add(term4);	// goodie, 47

		assertEquals(matches, lst2);
	}
}
