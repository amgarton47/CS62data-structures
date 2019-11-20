package wordsGeneric;

//import static org.junit.Assert.*;
//import java.util.List;
//import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * @author Sean Zhu and Peter Cowal
 * @version: 02/06/16
 * @author Alexandra Papoutsaki
 * @version: 11/19/19 
 */

public class TextGeneratorTest {

	/**
	 * an alternate toString method for frequency lists
	 * uses the get method because it's all I have access to...
	 *
	 * This should hopefully fix some of the false alarms from the autograder
	 */
	protected String freqListString(FreqList flist){
		String str = "";
		for(double p = 0.25; p<1; p += 0.5){
			str += flist.get(p) + " ";
		}
		return str;
	}

	protected String listToString(HashMap<StringPair, FreqList> lst) {
		String str = "";
		str += " List has " + lst.size() + " elements:" + "\n";
		Iterator hmIterator = lst.entrySet().iterator();

		while (hmIterator.hasNext()) {
			Map.Entry mapElement = (Map.Entry) hmIterator.next();
			StringPair key = (StringPair) mapElement.getKey();
			FreqList value = (FreqList) mapElement.getValue();
			str += " Bigram" + key + " has elements:\n";
			str += " " + value + "\n";
		}
		return str;
	}

	@Test
	public void enterOneTrigram() {
		TextGenerator tg = new TextGenerator();
		tg.enter("first", "second", "third");
		String expectedStr = " List has 1 elements:\n Bigram<first,second> has elements:\n Frequency List: <third=1>\n";
		String currentStr = listToString(tg.letPairList);
		assertEquals(expectedStr, currentStr);
	}

	@Test
	public void enterSeveralTrigrams() {
		TextGenerator tg = new TextGenerator();
		tg.enter("first", "second", "third");
		tg.enter("first", "second", "fourth");
		tg.enter("first", "third", "fourth");
		tg.enter("second", "third", "fourth");
		tg.enter("fifth", "sixth", "seventh");
		tg.enter("first", "second", "fourth");

		
		assertTrue(tg.letPairList.containsKey(new StringPair("first","second")));
		assertTrue(tg.letPairList.containsKey(new StringPair("first","third")));
		assertTrue(tg.letPairList.containsKey(new StringPair("second","third")));
		assertTrue(tg.letPairList.containsKey(new StringPair("fifth","sixth")));
		
		assertEquals(tg.letPairList.get(new StringPair("first","second")).toString(), "Frequency List: <third=1><fourth=2>");
		assertEquals(tg.letPairList.get(new StringPair("first","third")).toString(), "Frequency List: <fourth=1>");
		assertEquals(tg.letPairList.get(new StringPair("second","third")).toString(), "Frequency List: <fourth=1>");
		assertEquals(tg.letPairList.get(new StringPair("fifth","sixth")).toString(), "Frequency List: <seventh=1>");
	}

	@Test
	public void getNextWord() {
		TextGenerator tg = new TextGenerator();
		FreqList flist1 = new FreqList();
		FreqList flist2 = new FreqList();
		flist1.add("third");
		flist2.add("third");
		flist2.add("fourth");		
		tg.letPairList.put(new StringPair("first", "second"), flist1);
		tg.letPairList.put(new StringPair("first", "third"), flist2);
		String firstWord = tg.getNextWord("first", "second");
		String secondWord = tg.getNextWord("first", "third");

		if (firstWord.equals("third")
				&& (secondWord.equals("third") || secondWord
						.equals("fourth"))) {
			return; //pass
		} else {
			fail("Expected:\n third; third or forth\nCurrent:\n" + firstWord
					+ "; " + secondWord +"\n");
		}
	}
}

