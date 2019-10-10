package wordsGeneric;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import structure5.Association;

/**
 * @author Sean Zhu and Peter Cowal
 * @version: 02/06/16
 *
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

	protected String listToString(
			List<Association<StringPair, FreqList>> lst) {
		String str = "";
		str += " List has " + lst.size() + " elements:" + "\n";
		for (Association<StringPair, FreqList> assoc : lst) {
			str += " Bigram" + assoc.getKey().toString() + " has elements:\n";
			str += " " + freqListString(assoc.getValue()) + "\n";
		}
		return str;
	}

	@Test
	public void enterOneTrigram() {
		TextGenerator tg = new TextGenerator();
		tg.enter("first", "second", "third");
		String expectedStr = " List has 1 elements:\n Bigram<first,second> has elements:\n third third \n";
		String currentStr = listToString(tg.letPairList);
		assertEquals("Enter one trigram", expectedStr, currentStr);
	}

	@Test
	public void enterSeveralTrigrams() {
		TextGenerator tg = new TextGenerator();
		tg.enter("first", "second", "third");
		tg.enter("first", "second", "fourth");
		tg.enter("first", "third", "fourth");
		tg.enter("second", "third", "fourth");
		tg.enter("fifth", "sixth", "seventh");
		String expectedStr = " List has 4 elements:\n"
				+ " Bigram<first,second> has elements:\n"
				+ " third fourth \n"
				+ " Bigram<first,third> has elements:\n"
				+ " fourth fourth \n"
				+ " Bigram<second,third> has elements:\n"
				+ " fourth fourth \n"
				+ " Bigram<fifth,sixth> has elements:\n"
				+ " seventh seventh \n";
		String currentStr = listToString(tg.letPairList);
		assertEquals("Enter several trigrams", expectedStr, currentStr);
	}

	@Test
	public void getNextWord() {
		TextGenerator tg = new TextGenerator();
		FreqList flist1 = new FreqList();
		FreqList flist2 = new FreqList();
		flist1.add("third");
		flist2.add("third");
		flist2.add("fourth");
		Association<StringPair, FreqList> assoc1 = new Association<StringPair, FreqList>(
				new StringPair("first", "second"), flist1);
		Association<StringPair, FreqList> assoc2 = new Association<StringPair, FreqList>(
				new StringPair("first", "third"), flist2);
		tg.letPairList.add(assoc1);
		tg.letPairList.add(assoc2);
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

