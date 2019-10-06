package wordsGeneric;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Sean Zhu and Peter Cowal
 * @version: 02/06/16
 *
 */
public class FreqListTest {
	
	@Test
	public void addOneElement(){
		FreqList list = new FreqList();
		list.add("cow");
		String currentStr = list.get(0.5);
		String expectedStr = "cow";
		assertEquals("Adding one element", expectedStr, currentStr);
	}
	
	@Test
	public void addTwoElements(){
		FreqList list = new FreqList();
		list.add("cow");
		list.add("apple");
		String currentStr = "";
		for (double i = 0.3; i < 1.0; i += 0.5) {
			currentStr += list.get(i) + " ";
		}
		String expectedStr = "cow apple ";
		assertEquals("Adding two elements", expectedStr, currentStr);
	}
	
	@Test
	public void addDuplicatedElements(){
		FreqList list = new FreqList();
		list.add("cow");
		list.add("apple");
		list.add("cow");
		String currentStr = "";
		for (double i = 0.3; i < 1.0; i += 0.3) {
			currentStr += list.get(i) + " ";
		}
		String expectedStr = "cow cow apple ";
		assertEquals("Adding duplicated elements", expectedStr, currentStr);
	}
	
	@Test
	public void addManyElements(){
		FreqList list = new FreqList();
		list.add("cow");
		list.add("apple");
		list.add("banana");
		list.add("milk");
		list.add("egg");
		list.add("milk");
		list.add("egg");
		list.add("cow");
		list.add("cow");
		list.add("cow");
		String currentStr = "";
		for (double i = 0.05; i < 1.0; i += 0.1) {
			currentStr += list.get(i) + " ";
		}
		String expectedStr = "cow cow cow cow apple banana milk milk egg egg ";
		assertEquals("Adding many elements", expectedStr, currentStr);
	}
	
	@Test
	public void getNoElement(){
		FreqList list = new FreqList();
		String currentStr = list.get(0.5);
		String expectedStr = "";
		assertEquals("Getting an element from an empty FreqList", expectedStr, currentStr);
	}
	
	@Test
	public void checkBounds(){
		FreqList list = new FreqList();
		try {
			list.get(-1);
		} catch (IllegalArgumentException e) {
			return;
		}
		fail("Entering an illegal parameter failed to throw " +
								"the proper exception");
	}
	
}