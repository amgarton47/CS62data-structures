package wordsGeneric;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * This a JUnit test class for the FreqList class
 * @author americachambers
 *
 */
public class FreqListTest {
	private FreqList freqList;
	
	@Before
	public void setUp() throws Exception {
		freqList = new FreqList();
	}

	@Test
	public void testInsert() {
		freqList.insert("hello");		
		
		// Note that we can access the instance variables in the FreqList
		// class because they are declared as "protected"
		
		assertEquals(1, freqList.flist.size(), 0);				// size of ArrayList is 1
		assertEquals(1, freqList.totalFreq, 0);					// total frequency is 1
		assertEquals("hello", freqList.flist.get(0).getKey());	// key is "hello"
		assertEquals(1, freqList.flist.get(0).getValue(), 0);	// value is 1
		
		freqList.insert("cow");
		assertEquals(2, freqList.flist.size(), 0);				// size of ArrayList is 2
		assertEquals(2, freqList.totalFreq, 0);					// total frequency is 2
		assertEquals("cow", freqList.flist.get(1).getKey());	// key is "cow"
		assertEquals(1, freqList.flist.get(1).getValue(), 0);	// value is 1
		
		freqList.insert("cow");
		assertEquals(2, freqList.flist.size(), 0);				// size of ArrayList is still 2
		assertEquals(3, freqList.totalFreq, 0);					// total frequency is 3
		assertEquals("cow", freqList.flist.get(1).getKey());	// key is "cow"
		assertEquals(2, freqList.flist.get(1).getValue(), 0);	// value is now 2		
	}

	@Test
	public void testGet() {
		freqList.insert("hello");
		freqList.insert("cow");
		freqList.insert("apple");
		freqList.insert("banana");
		
		assertEquals("hello", freqList.get(0.1));
		assertEquals("cow", freqList.get(0.251));
		assertEquals("apple", freqList.get(0.51));
		assertEquals("banana", freqList.get(0.751));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetInputLessThanZero(){
		freqList.get(-.000001);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetInputGrtThanOne(){
		freqList.get(1.00001);
	}
}
