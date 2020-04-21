package wordsGeneric;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;


/**
 * @author Sean Zhu and Peter Cowal
 * @version: 02/06/16
 * @author Alexandra Papoutsaki
 * @version: 11/19/19 
 *
 */
public class FreqListTest {
	
	@Test
	public void addOneElement(){
		FreqList list = new FreqList();
		list.add("cow");
		String currentStr = list.get(0.5);
		String expectedStr = "cow";
		assertEquals(expectedStr, currentStr);
		assertTrue(list.flist.get("cow")==1);
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
		List<String> validStrings = Arrays.asList("apple cow ", "cow apple ");
		assertTrue(validStrings.contains(currentStr));
		assertTrue(list.flist.get("cow")==1);
		assertTrue(list.flist.get("apple")==1);

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
		List<String> validStrings = Arrays.asList("apple cow cow ", "cow cow apple ");
		assertTrue(validStrings.contains(currentStr));
		assertTrue(list.flist.get("cow")==2);
		assertTrue(list.flist.get("apple")==1);

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
		List<String> validStrings = Arrays.asList("cow cow cow cow apple banana milk milk egg egg", "banana apple egg egg milk milk cow cow cow cow ");
		assertTrue(validStrings.contains(currentStr));
		assertTrue(list.flist.get("cow")==4);
		assertTrue(list.flist.get("apple")==1);
		assertTrue(list.flist.get("banana")==1);
		assertTrue(list.flist.get("milk")==2);
		assertTrue(list.flist.get("egg")==2);


	}
	
	@Test
	public void getNoElement(){
		FreqList list = new FreqList();
		String currentStr = list.get(0.5);
		String expectedStr = "";
		assertEquals(expectedStr, currentStr);
		assertTrue(list.flist.size()==0);
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