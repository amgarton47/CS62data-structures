package introduction;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Auto-grader unit tests for <strong>Bag/Token Set-up project</strong>.
 *
 * @author cs62 staff
 */
public class Autograder {

	public static final int NUM_TOKENS = 16;
	public static final int MAX_VAL = 10;	// should be public from Token

	public Bag testBag;		// bag created for all testing
	public String colors[] = {
		"Red", "Yellow", "Green", "Cyan", "Green",
		"Blue", "Violet", "Green" };

	public int sum = 0;
	public int num_high = 0;
	public int first_green = -1;

	/**
	 * create a big with known contents
	 */
	@Before
	public void setup() {
		// create a bag with known values and colors
		testBag = new Bag(NUM_TOKENS);
		for(int i = 0; i < NUM_TOKENS; i++) {
			// choose the value and color
			int value = i % MAX_VAL;
			String color = colors[i % colors.length];

			// set them
			testBag.contents[i].setValue(value);
			testBag.contents[i].setColor(color);

			// compute the counts
			sum += value;
			if (value > MAX_VAL/2)
				num_high++;
			if (color.equals("Green") && first_green < 0)
				first_green = i;
		}
	}

	/**
	 * Determine if Token.getValue works
	 */
	@Test
	public void getValue_returns_as_constructed() {
		Token t1 = new Token("None", 1);
		Token t10 = new Token("None", 10);
		assertEquals("Token.getValue returns value set by Token(,1).", 1, t1.getValue());
		assertEquals("Token.getValue returns value set by Token(,10).", 10, t10.getValue());
	}

	/**
	 * Determine if Token.setValue works
	 */
	@Test
	public void setValue_changes_value() {
		Token t1 = new Token("None", 1);
		t1.setValue(6);
		assertEquals("Token.setValue changes cvalue.", 6, t1.getValue());
	}


	/**
	 * Determine if Token.setColor works
	 */
	@Test
	public void setColor_changes_color() {
		Token t1 = new Token("vermilion", 1);
		String newColor = "Chartreuse";
		t1.setColor(newColor);
		assertEquals("Token.setColor changes color.", newColor, t1.getColor());
	}

	/** * Determine if Token.isHighValue works for =half
	 */
	@Test
	public void highValue_for_half_max() {
		Token t5 = new Token("None", 5);
		assertFalse("Token.highValue(5) returns False", t5.isHighValue());
	}

	/**
	 * Determine if Token.isHighValue works for =half+1
	 */
	@Test
	public void highValue_for_half_plus_1() {
		Token t6 = new Token("None", 6);
		assertTrue("Token.highValue(6) returns True", t6.isHighValue());
	}

	/**
	 * Determine if Token.toString procudes the expected output
	 */
	@Test
	public void toString_as_expected() {
		Token t5g = new Token("Green", 5);
		String result = t5g.toString();
	
		// a typo in the starter invited a simple mistake
		String preferred = "Token's color is Green and has value 5";
		String sort_of = "Token's color is Green and has value5";

		if (result.equals(sort_of))
			assertEquals("toString(Token(\"Green\", 5) works awkwardly.", sort_of, result);
		else
			assertEquals("toString(Token(\"Green\", 5) works.", preferred, result);
	}


	/*
	 * testing allTokens and allTokensWhile is a little difficult,
	 * because their output is printed to stdout rather than
	 * returned.
	 */

	/**
	 * Determine if addTokens produces expected sum
	 */
	@Test
	public void addTokens_sums_correctly() {
		assertEquals("addTokens properly finds sum of " + sum + ".",
					sum, testBag.addTokens());
	}

	/**
	 * Determine if highValueTokens finds expected number of Tokens
	 */
	@Test
	public void highValueTokens_counts_correctly() {
		assertEquals("highValueTokens correctly finds " + num_high + "high value Tokens.", 
					num_high, testBag.highValueTokens());
	}

	/**
	 * Determine if firstGreen finds the expected Token
	 */
	@Test
	public void firstGreen_finds_correctly() {
		assertEquals("firstGreen correctly finds first Green Token.", first_green, testBag.firstGreen());
	}
}
