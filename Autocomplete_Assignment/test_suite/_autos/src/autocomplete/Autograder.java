package autocomplete;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Autograder {
	Term term1, term2, term3, term4, term5, term6, term7;
	@BeforeEach
	public void setUp() throws Exception {
		term1 = new Term("hello", 7);
		term2 = new Term("goodbye", 99);
		term3 = new Term("banana", 27);
		term4 = new Term("goodie", 47);
		term5 = new Term("goofie", 12);
        term6 = new Term("Goodbye", 50);
        term7 = new Term("goal", 36);
	}


	/******************* TERM CLASS TESTING *******************/
	
	
	@Test
	public void testCompareTo() {
		List<Term> lst = new ArrayList<Term>();
		lst.add(term1);		// hello, 7
		lst.add(term2);		// goodbye, 99
		lst.add(term3);		// banana, 27
		lst.add(term4);		// goodie, 47
		lst.add(term5);		// goofie, 12
        lst.add(term6);		// Goodbye, 50

		Collections.sort(lst);

		List<Term> lst2 = new ArrayList<Term>();
        lst2.add(term6);		// Goodbye, 50
		lst2.add(term3);		// banana, 27
		lst2.add(term2);		// goodbye, 99
		lst2.add(term4);		// goodie, 47
		lst2.add(term5);		// goofie, 12
		lst2.add(term1);		// hello, 7

		assertEquals(lst2, lst);
	}
	
	
	@Test
	public void testTermByReverseWeightOrder() {
		List<Term> lst = new ArrayList<Term>();
		lst.add(term1);		// hello, 7
		lst.add(term2);		// goodbye, 99
		lst.add(term3);		// banana, 27
		lst.add(term4);		// goodie, 47
		lst.add(term5);		// goofie, 12
        lst.add(term6);		// Goodbye, 50

		Collections.sort(lst, Term.byReverseWeightOrder());

		List<Term> lst2 = new ArrayList<Term>();
        lst2.add(term2);		// goodbye, 99
        lst2.add(term6);		// Goodbye, 50
		lst2.add(term4);		// goodie, 47
		lst2.add(term3);		// banana, 27
		lst2.add(term5);		// goofie, 12
		lst2.add(term1);		// hello, 7

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
        lst.add(term6);		// Goodbye, 50

		Collections.sort(lst, Term.byPrefixOrder(7));

		List<Term> lst2 = new ArrayList<Term>();
        lst2.add(term6);		// Goodbye, 50
		lst2.add(term3);		// banana, 27
		lst2.add(term2);		// goodbye, 99
		lst2.add(term4);		// goodie, 47
		lst2.add(term5);		// goofie, 12
		lst2.add(term1);		// hello, 7

		assertEquals(lst2, lst);

        lst.add(term7);

        Collections.sort(lst, Term.byPrefixOrder(2));
        assertEquals(lst.get(5), term7);

        Collections.sort(lst, Term.byPrefixOrder(3));
        assertEquals(lst.get(2), term7);
	}



	/******************* BINARYSEARCHFORALL CLASS TESTING *******************/
	@Test
	public void testFirstIndexOf() {
		List<Term> lst = new ArrayList<Term>();
		lst.add(term3);		// banana, 27
        lst.add(term7);     // goal, 36
		lst.add(term2);		// goodbye, 99
		lst.add(term4);		// goodie, 47
		lst.add(term5);		// goofie, 12
		lst.add(term1);		// hello, 7

		int index = BinarySearchForAll.firstIndexOf(lst, term3, Term.byPrefixOrder(3));

		assertEquals(0, index);

        Term go = new Term("go", 0);
        int index2 = BinarySearchForAll.firstIndexOf(lst, go, Term.byPrefixOrder(2));

        assertEquals(1, index2);

        Term goo = new Term("goo", 0);
        int index3 = BinarySearchForAll.firstIndexOf(lst, goo, Term.byPrefixOrder(3));

        assertEquals(2, index3);
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

        List<Term> lst2 = new ArrayList<Term>();
		lst2.add(term3);		// banana, 27
		lst2.add(term2);		// goodbye, 99
		lst2.add(term4);		// goodie, 47
		lst2.add(term5);		// goofie, 12
		lst2.add(term1);		// hello, 7

        Term goo = new Term("goo", 0);
        int index2 = BinarySearchForAll.lastIndexOf(lst2, goo, Term.byPrefixOrder(3));

        assertEquals(3, index2);
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
                lst.add(term6);		// Goodbye, 50
                lst.add(term7);		// goal, 36
                Autocomplete auto = new Autocomplete(lst);
                String key = "good";
                List<Term> matches = auto.allMatches(key);

		List<Term> lst2 = new ArrayList<Term>();
		lst2.add(term2);	// goodbye, 99
		lst2.add(term4);	// goodie, 47

		assertEquals(matches, lst2);

		//------including "go(o)..." and "go(a)..." with correct reverse weight order-------
        String key2 = "go";
        List<Term> matches2 = auto.allMatches(key2);

        List<Term> lst3 = new ArrayList<Term>();
        lst3.add(term2);	// goodbye, 99
		lst3.add(term4);	// goodie, 47
		lst3.add(term7);		// goal, 36
		lst3.add(term5);		// goofie, 12
		
		assertEquals(matches2, lst3);
		
		//-------capitalization-------
		String key3 = "Go";
        List<Term> matches3 = auto.allMatches(key3);

        List<Term> lst4 = new ArrayList<Term>();
		lst4.add(term6);		// Goodbye, 50
		
		assertEquals(matches3, lst4);
		
		//-------filtering out "goal"------
        String key4 = "goo";
        List<Term> matches4 = auto.allMatches(key4);

        List<Term> lst5 = new ArrayList<Term>();
        lst5.add(term2);	// goodbye, 99
		lst5.add(term4);	// goodie, 47
		lst5.add(term5);		// goofie, 12
		
		assertEquals(matches4, lst5);
	}
}