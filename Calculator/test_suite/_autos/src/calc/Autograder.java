package calc;

import static org.junit.Assert.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

/**
 *
 * @author Sean Zhu
 *
 */
public class Autograder {
	private JTextField display; // dummy display for testing
	private State st; // sample state object
	private static final double EPSILON = 0.00001;
	private static double addEnterScore = 0;
	private static double opScore = 0;
	private static double errorScore = 0;
	private static double miscScore = 0;
	private static final int BUTTON_LISTENER_NUM = 2;
	// private static final char BUTTON_LISTENER_CHAR = '*';
	private static int digitButtonScore = 0;

	// set up state with 2 on top of stack and 9 below it.
	@Before
	public void setUp() {
		display = new JTextField("0");
		st = new State(display);

	}

	/**
	 * test simple addDigit and enter
	 *
	 * 1 point add/enter
	 */
	@Test
	public void testAddSimpleDigitAndEnter() {

		// check if properly initialized
		assertNotNull("Stack/ArrayDeque shoud be initialized.", st.stack);
		assertEquals("Stack should be empty when initialized.", 0,
				st.stack.size(), 0);
		assertEquals("Display should be 0 when initialized.", 0.,
				Double.parseDouble(display.getText()), EPSILON);

		// add 2 and enter
		st.addDigit(2);
		st.enter();
		assertEquals("Called addDigit(2), enter(). " + "Display should be 2",
				2., Double.parseDouble(display.getText()), EPSILON);
		assertEquals("Called addDigit(2), enter()."
				+ " Top of stack should be 2", 2.,
				Double.valueOf(st.stack.peek()), EPSILON);

		// add 7
		st.addDigit(7);
		assertEquals("Called addDigit(2),enter(), addDigit(7)."
				+ " Display should be 7", 7.,
				Double.parseDouble(display.getText()), EPSILON);
		assertEquals("Called addDigit(2),enter(), addDigit(7). "
				+ "Top of stack shoulbe be 2.", 2.,
				Double.valueOf(st.stack.peek()), EPSILON);

		// add 5 and enter
		st.addDigit(5);
		assertEquals("Called addDigit(2),enter(), addDigit(7), addDigit(5)."
				+ " Display should be 75", 75.,
				Double.parseDouble(display.getText()), EPSILON);

		assertEquals("Called addDigit(2),enter(), addDigit(7), addDigit(5)."
				+ " Top of stack shoulbe be 2.", 2.,
				Double.valueOf(st.stack.peek()), EPSILON);

		st.enter();
		assertEquals("Called addDigit(2),enter(), addDigit(7), addDigit(5),"
				+ " enter(). Display should be 75", 75.,
				Double.parseDouble(display.getText()), EPSILON);

		assertEquals("Called addDigit(2),enter(), addDigit(7), addDigit(5),"
				+ " enter(). Top of stack shoulbe be 75.", 75.,
				Double.valueOf(st.stack.peek()), EPSILON);
		assertEquals("75", display.getText());

		addEnterScore++; // increment score by 1
	}

	/**
	 * test if all digit buttons work
	 *
	 * 1 point add/enter
	 */
	@Test
	public void testAddSingleDigitAndEnter() {
		// add 0
		st.addDigit(0);
		assertEquals("Called addDigit(0). Display should be 0.", 0.,
				Double.parseDouble(display.getText()), EPSILON);

		st.enter();
		assertEquals("Called addDigit(0), enter(). Display should be 0.", 0.,
				Double.parseDouble(display.getText()), EPSILON);
		assertEquals("Called addDigit(0), enter()."
				+ " Top of stack should be 0.", 0.,
				Double.valueOf(st.stack.peek()), EPSILON);

		// 1
		st.addDigit(1);
		assertEquals("Top of stack was 0. Called addDigit(1). "
				+ "Display should be 1.", 1.,
				Double.parseDouble(display.getText()), EPSILON);

		st.enter();
		assertEquals("Top of stack was 0. Called addDigit(1), enter(). "
				+ "Display should be 1.", 1.,
				Double.parseDouble(display.getText()), EPSILON);
		assertEquals("Top of stack was 0. Called addDigit(1), enter(). "
				+ " Top of stack should be 1.", 1.,
				Double.valueOf(st.stack.peek()), EPSILON);

		// 2
		st.addDigit(2);
		assertEquals("Top of stack was 1. Called addDigit(2). "
				+ "Display should be 2.", 2.,
				Double.parseDouble(display.getText()), EPSILON);

		st.enter();
		assertEquals("Top of stack was 1. Called addDigit(2), enter(). "
				+ "Display should be 2.", 2.,
				Double.parseDouble(display.getText()), EPSILON);
		assertEquals("Top of stack was 1. Called addDigit(2), enter(). "
				+ " Top of stack should be 2.", 2.,
				Double.valueOf(st.stack.peek()), EPSILON);

		// 3
		st.addDigit(3);
		assertEquals("Top of stack was 2. Called addDigit(3). "
				+ "Display should be 3.", 3.,
				Double.parseDouble(display.getText()), EPSILON);

		st.enter();
		assertEquals("Top of stack was 2. Called addDigit(3), enter(). "
				+ "Display should be 3.", 3.,
				Double.parseDouble(display.getText()), EPSILON);
		assertEquals("Top of stack was 2. Called addDigit(3), enter(). "
				+ " Top of stack should be 3.", 3.,
				Double.valueOf(st.stack.peek()), EPSILON);

		// 4
		st.addDigit(4);
		assertEquals("Top of stack was 3. Called addDigit(4). "
				+ "Display should be 4.", 4.,
				Double.parseDouble(display.getText()), EPSILON);

		st.enter();
		assertEquals("Top of stack was 3. Called addDigit(4), enter(). "
				+ "Display should be 4.", 4.,
				Double.parseDouble(display.getText()), EPSILON);
		assertEquals("Top of stack was 3. Called addDigit(4), enter(). "
				+ " Top of stack should be 4", 4.,
				Double.valueOf(st.stack.peek()), EPSILON);

		// 5
		st.addDigit(5);
		assertEquals("Top of stack was 4. Called addDigit(5). "
				+ "Display should be 5.", 5.,
				Double.parseDouble(display.getText()), EPSILON);

		st.enter();
		assertEquals("Top of stack was 4. Called addDigit(4), enter(). "
				+ "Display should be 5", 5.,
				Double.parseDouble(display.getText()), EPSILON);
		assertEquals("Top of stack was 4. Called addDigit(5), enter(). "
				+ " Top of stack should be 5.", 5.,
				Double.valueOf(st.stack.peek()), EPSILON);

		// 6
		st.addDigit(6);
		assertEquals("Top of stack was 5. Called addDigit(6). "
				+ "Display should be 6.", 6.,
				Double.parseDouble(display.getText()), EPSILON);

		st.enter();
		assertEquals("Top of stack was 5. Called addDigit(6), enter(). "
				+ "Display should be 6.", 6.,
				Double.parseDouble(display.getText()), EPSILON);
		assertEquals("Top of stack was 5. Called addDigit(6), enter(). "
				+ " Top of stack should be 6.", 6.,
				Double.valueOf(st.stack.peek()), EPSILON);
		// 7
		st.addDigit(7);
		assertEquals("Top of stack was 6. Called addDigit(7). "
				+ "Display should be 7.", 7.,
				Double.parseDouble(display.getText()), EPSILON);

		st.enter();
		assertEquals("Top of stack was 6. Called addDigit(7), enter(). "
				+ "Display should be 7.", 7.,
				Double.parseDouble(display.getText()), EPSILON);
		assertEquals("Top of stack was 6. Called addDigit(7), enter(). "
				+ " Top of stack should be 7", 7.,
				Double.valueOf(st.stack.peek()), EPSILON);

		// 8
		st.addDigit(8);
		assertEquals("Top of stack was 7. Called addDigit(8). "
				+ "Display should be 8.", 8.,
				Double.parseDouble(display.getText()), EPSILON);

		st.enter();
		assertEquals("Top of stack was 7. Called addDigit(8), enter(). "
				+ "Display should be 8.", 8.,
				Double.parseDouble(display.getText()), EPSILON);
		assertEquals("Top of stack was 7. Called addDigit(8), enter(). "
				+ " Top of stack should be 8.", 8.,
				Double.valueOf(st.stack.peek()), EPSILON);

		// 9
		st.addDigit(9);
		assertEquals("Top of stack was 8. Called addDigit(9). "
				+ "Display should be 9.", 9.,
				Double.parseDouble(display.getText()), EPSILON);

		st.enter();
		assertEquals("Top of stack was 8. Called addDigit(9), enter(). "
				+ "Display should be 9.", 9.,
				Double.parseDouble(display.getText()), EPSILON);
		assertEquals("Top of stack was 8. Called addDigit(9), enter(). "
				+ " Top of stack should be 9.", 9.,
				Double.valueOf(st.stack.peek()), EPSILON);

		addEnterScore++;

	}

	/**
	 * test if adding a large number and the maximum int work
	 *
	 * 0.5 point add/enter
	 */
	@Test
	public void testAddVLargeDigitAndEnter() {

		// add a large number 876543210
		st.addDigit(8);
		st.addDigit(7);
		st.addDigit(6);
		st.addDigit(5);
		st.addDigit(4);
		st.addDigit(3);
		st.addDigit(2);
		st.addDigit(1);
		st.addDigit(0);

		assertEquals("Called addDigit(8) to addDigit(0). "
				+ "Display should be 876543210.", 876543210.,
				Double.parseDouble(display.getText()), EPSILON);

		st.enter();
		assertEquals("Called addDigit(8) to addDigit(0),"
				+ " enter(). Display should be 876543210.", 876543210.,
				Double.parseDouble(display.getText()), EPSILON);
		assertEquals("Called addDigit(8) to addDigit(0),"
				+ " enter(). Top of stack should be 876543210.", 876543210.,
				Double.valueOf(st.stack.peek()), EPSILON);

		// add max int value 2147483647
		st.addDigit(2);
		st.addDigit(1);
		st.addDigit(4);
		st.addDigit(7);
		st.addDigit(4);
		st.addDigit(8);
		st.addDigit(3);
		st.addDigit(6);
		st.addDigit(4);
		st.addDigit(7);

		assertEquals("Called addDigit()s to add 2147483647. "
				+ "Display should be 2147483647.", 2147483647.,
				Double.parseDouble(display.getText()), EPSILON);

		st.enter();
		assertEquals("Called addDigit()s to add 2147483647, enter() "
				+ "Display should be 2147483647.", 2147483647.,
				Double.parseDouble(display.getText()), EPSILON);
		assertEquals("Called addDigit() to add 2147483647. enter"
				+ "Top of stack should be 2147483647.", 2147483647.,
				Double.valueOf(st.stack.peek()), EPSILON);

		addEnterScore += 0.5;
	}

	/**
	 * test if enter() twice does not enter value twice
	 *
	 * 0.5 point add/enter
	 */
	@Test
	public void testEnterTwice() {
		st.addDigit(1);
		st.enter();
		st.enter();
		assertEquals("AddDigit(1), enter(), enter(). "
				+ "Should only have one element on the stack.", 1,
				st.stack.size());

		addEnterScore += 0.5;
	}

	/**
	 * test if simple plus work
	 *
	 * 0.5 point op
	 */
	@Test
	public void testPlus() {

		// 47 4 7 + +
		st.addDigit(4);
		st.addDigit(7);
		st.enter();
		st.addDigit(4);
		st.enter();
		st.addDigit(7);
		st.enter();
		st.doOp('+');
		assertEquals("Adding 4 and 7. Display should be 11.", 11.,
				Double.parseDouble(display.getText()), EPSILON);
		assertEquals("Adding 4 and 7", 11., Double.valueOf(st.stack.peek()),
				EPSILON);
		st.doOp('+');
		assertEquals("Adding 47 and 11. Display should be 58.", 58.,
				Double.parseDouble(display.getText()), EPSILON);
		assertEquals("Adding 47 and 11. Top of stack should be 58.", 58.,
				Double.valueOf(st.stack.peek()), EPSILON);

		// 58 876543210 +
		st.addDigit(8);
		st.addDigit(7);
		st.addDigit(6);
		st.addDigit(5);
		st.addDigit(4);
		st.addDigit(3);
		st.addDigit(2);
		st.addDigit(1);
		st.addDigit(0);
		st.enter();
		st.doOp('+');
		assertEquals("Adding 58 and 876543210. Display should be 876543268.",
				876543268., Double.parseDouble(display.getText()), EPSILON);
		assertEquals("Adding 58 and 876543210. Top of stack should be "
				+ "876543268", 876543268., Double.valueOf(st.stack.peek()),
				EPSILON);

		opScore += 0.5;
	}

	/**
	 * test if plus work without clicking enter()
	 *
	 * 0.25 points op
	 */
	@Test
	public void testPlus_noEnter() {
		st.addDigit(4);
		st.addDigit(7);
		st.enter();
		st.addDigit(4);
		st.doOp('+');
		assertEquals("Adding 47 and 4, without entering 4."
				+ " Display should be 51.", 51.,
				Double.parseDouble(display.getText()), EPSILON);
		assertEquals("Adding 47 and 4, without entering 4."
				+ " Top of stack should be 51", 51.,
				Double.valueOf(st.stack.peek()), EPSILON);

		opScore += 0.25;
	}

	/**
	 * test if error message thrown if adding with empty stack
	 *
	 * 0.2 pts error
	 */
	@Test
	public void testPlus_noElement() {
		st.doOp('+');
		assertEquals("Calling doOp('+') after initialization."
				+ " Display should be 'Error'.", "Error", display.getText());
		errorScore += 0.2;
	}

	/**
	 * test if error message thrown if adding with 1 element on stack
	 *
	 * 0.2 pts error
	 */
	@Test
	public void testPlus_notEnoughElement() {
		st.addDigit(4);
		st.addDigit(7);
		st.enter();
		st.addDigit(4);
		st.enter();
		st.doOp('+');
		st.doOp('+');
		assertEquals("Add 47 and 4, then doOp('+') twice."
				+ " Display should be 'Error'.", "Error", display.getText());
		errorScore += 0.2;
	}

	/**
	 * test if multiplication works
	 *
	 * 0.5 pts op
	 */
	@Test
	public void testMult() {
		st.addDigit(4);
		st.addDigit(7);
		st.enter();
		st.addDigit(4);
		st.enter();
		st.addDigit(7);
		st.enter();
		st.doOp('*');
		assertEquals("Multiplying 4 and 7." + " Display should be 28.", 28.,
				Double.parseDouble(display.getText()), EPSILON);
		assertEquals("Mulitplying 4 and 7" + " Top of stack should be 28.",
				28., Double.valueOf(st.stack.peek()), EPSILON);
		st.doOp('*');
		assertEquals("Multiplying 47 and 28. Display should be 1316.", 1316.,
				Double.parseDouble(display.getText()), EPSILON);
		assertEquals("Multiplying 47 and 28. Top of stack should be 1316.",
				1316., Double.valueOf(st.stack.peek()), EPSILON);

		opScore += 0.5;
	}

	/**
	 * test if mult works without enter()
	 *
	 * 0.25 pt op
	 */
	@Test
	public void testMult_noEnter() {
		st.addDigit(4);
		st.addDigit(7);
		st.enter();
		st.addDigit(4);
		st.doOp('*');
		assertEquals("Mulitplying 47 and 4, without entering 4."
				+ " Display should be 58.", 188.,
				Double.parseDouble(display.getText()), EPSILON);
		assertEquals("Mulitplying 47 and 4, without entering 4."
				+ " Top of stack should be 188.", 188.,
				Double.valueOf(st.stack.peek()), EPSILON);

		opScore += 0.25;
	}

	/**
	 * test if error message thrown if multiplying with empty stack
	 *
	 * 0.2 pts error
	 */
	@Test
	public void testMult_noElement() {
		st.doOp('*');
		assertEquals("Calling doOp('*') after initializaiton."
				+ " Display should be 'Error'.", "Error", display.getText());
		errorScore += 0.2;
	}

	/**
	 * test if error message thrown if multiplying with one element stack
	 *
	 * 0.2 pts error
	 */
	@Test
	public void testMult_notEnoughElement() {
		st.addDigit(4);
		st.addDigit(7);
		st.enter();
		st.addDigit(4);
		st.enter();
		st.doOp('*');
		st.doOp('*');
		assertEquals("Enter 47 and 4, then doOp('*') twice."
				+ " Display should be 'Error'.", "Error", display.getText());
		errorScore += 0.2;
	}

	/**
	 * test if minus works
	 *
	 * 0.5 pts op
	 */
	@Test
	public void testMinus() {
		st.addDigit(4);
		st.addDigit(7);
		st.enter();
		st.addDigit(4);
		st.enter();
		st.addDigit(7);
		st.enter();
		st.doOp('-');
		assertEquals("Subtract 7 from 4." + " Display should be -3.", -3.,
				Double.parseDouble(display.getText()), EPSILON);
		assertEquals("Subtract 7 from 4." + " Top of stack be 'Error'.", -3.,
				Double.valueOf(st.stack.peek()), EPSILON);
		st.doOp('-');
		assertEquals("Subtract -3 from 47." + " Display should be 50.", 50.,
				Double.parseDouble(display.getText()), EPSILON);
		assertEquals("Subtract -3 from 47." + " Top of stack should be '50.",
				50., Double.valueOf(st.stack.peek()), EPSILON);

		st.addDigit(8);
		st.addDigit(7);
		st.addDigit(6);
		st.addDigit(5);
		st.addDigit(4);
		st.addDigit(3);
		st.addDigit(2);
		st.addDigit(1);
		st.addDigit(0);
		st.enter();
		st.doOp('-');
		assertEquals("Subtract 876543210 from 50. "
				+ "Display should be -876543160", -876543160.,
				Double.parseDouble(display.getText()), EPSILON);
		assertEquals("Subtract 876543210 from 50."
				+ " Top of stack should be -876543160", -876543160.,
				Double.valueOf(st.stack.peek()), EPSILON);

		opScore += 0.5;
	}

	/**
	 * test if minus works without enter()
	 *
	 * 0.25 pts op
	 */
	@Test
	public void testMinus_noEnter() {
		st.addDigit(4);
		st.addDigit(7);
		st.enter();
		st.addDigit(4);
		st.doOp('-');
		assertEquals(
				"Subtracting 4 from 47, without entering 4. Display should be 43",
				43., Double.parseDouble(display.getText()), EPSILON);
		assertEquals("Subtracting 4 from 47, without entering 4. "
				+ " Top of stack should be 43", 43.,
				Double.valueOf(st.stack.peek()), EPSILON);
		opScore += 0.25;
	}

	/**
	 * test if error message thrown if subtracting with empty stack
	 *
	 * 0.2 pts error
	 */
	@Test
	public void testMinus_noElement() {
		st.doOp('-');
		assertEquals("Calling doOp('-') after initialization."
				+ " Display should be 'Error'.", "Error", display.getText());
		errorScore += 0.2;
	}

	/**
	 * test if error message thrown if subtracting with one element stack
	 *
	 * 0.2 pts error
	 */
	@Test
	public void testMinus_notEnoughElement() {
		st.addDigit(4);
		st.addDigit(7);
		st.enter();
		st.addDigit(4);
		st.enter();
		st.doOp('-');
		st.doOp('-');
		assertEquals("Enter 47 and 4, then doOp('-') twice."
				+ " Display should be 'Error'.", "Error", display.getText());
		errorScore += 0.2;
	}

	/**
	 * test if division works
	 *
	 * 0.5 pts op
	 */
	@Test
	public void testDiv() {
		st.addDigit(1);
		st.addDigit(0);
		st.addDigit(0);
		st.enter();
		st.addDigit(10);
		st.enter();
		st.addDigit(2);
		st.enter();
		st.doOp('/');
		assertEquals("Dividing 10 by 2." + " Display should be 5.", 5.,
				Double.parseDouble(display.getText()), EPSILON);
		assertEquals("Diving 10 by 2" + " Top of stack should be 5.", 5.,
				Double.valueOf(st.stack.peek()), EPSILON);
		st.doOp('/');
		assertEquals("Dividing 100 by 5. Display should be 20.", 20.,
				Double.parseDouble(display.getText()), EPSILON);
		assertEquals("Dividing 100 by 5. Top of stack should be 20.", 20.,
				Double.valueOf(st.stack.peek()), EPSILON);
		st.addDigit(1);
		st.addDigit(9);
		st.enter();
		st.doOp('/');
		assertEquals("Dividing 20 by 19. Display should be 1.", 1.,
				(int) Double.parseDouble(display.getText()), EPSILON);

		opScore += 0.5;
	}

	/**
	 * test if division works without entering
	 *
	 * 0.25 pts op
	 */
	@Test
	public void testDiv_noEnter() {
		st.addDigit(4);
		st.enter();
		st.addDigit(4);
		st.doOp('/');
		assertEquals("Dividing 4 by 4, without entering 4."
				+ " Display should be 1.", 1.,
				Double.parseDouble(display.getText()), EPSILON);
		assertEquals("Dividing 4 by 4, without entering 4."
				+ " Top of stack should be 1.", 1.,
				Double.valueOf(st.stack.peek()), EPSILON);
		opScore += 0.25;
	}

	/**
	 * test if error message thrown if dividing with empty stack
	 *
	 * 0.2 pts error
	 */
	@Test
	public void testDiv_noElement() {
		st.doOp('/');
		assertEquals("Calling doOp('/') after initializaiton."
				+ " Display should be 'Error'.", "Error", display.getText());
		errorScore += 0.2;
	}

	/**
	 * test if error message thrown if dividing with one element stack
	 *
	 * 0.2 pts error
	 */
	@Test
	public void testDiv_notEnoughElement() {
		st.addDigit(4);
		st.addDigit(7);
		st.enter();
		st.addDigit(4);
		st.enter();
		st.doOp('/');
		st.doOp('/');
		assertEquals("Enter 47 and 4, then doOp('/') twice."
				+ " Display should be 'Error'.", "Error", display.getText());

		errorScore += 0.2;
	}

	/**
	 * test if error message thrown if dividing by zero
	 *
	 * 0.4 pts error
	 */
	@Test
	public void testDiv_divByZero() {
		st.addDigit(4);
		st.enter();
		st.addDigit(0);
		st.enter();
		st.doOp('/');
		assertEquals("Divide 4 by 0." + " Display should be 'Error'.", "Error",
				display.getText());

		errorScore += 0.4;
	}

	/**
	 * test clear
	 *
	 * 1 pt misc
	 */
	@Test
	public void testClear() {
		st.addDigit(1);
		st.addDigit(0);
		st.enter();
		st.addDigit(2);
		st.enter();
		st.doOp('+');
		st.addDigit(3);
		st.clear();
		assertEquals("Called clear(), size of stack should zero.", 0,
				st.stack.size());
		assertEquals("Called clear(). Display should 0.", 0.,
				Double.parseDouble(display.getText()), EPSILON);
		st.addDigit(4);
		assertEquals(
				"Called addDigit(3), clear(), and addDigit(4). Display should be 4",
				4., Double.parseDouble(display.getText()), EPSILON);

		miscScore++;
	}

	/**
	 * test pop
	 *
	 * 1 pt misc
	 */
	@Test
	public void testPop() {
		st.addDigit(9);
		st.enter();
		st.addDigit(2);
		st.enter();
		st.pop();
		assertEquals("Added 9 and 2. Called pop(). Display should be 9.", 9.,
				Double.parseDouble(display.getText()), EPSILON);
		st.pop();
		assertEquals("Added 9 and 2. Called pop() twice. Display should be 0.",
				0., Double.parseDouble(display.getText()), EPSILON);
		st.pop();
		assertEquals("Poping from an empty stack. Display should be 0.", 0.,
				Double.parseDouble(display.getText()), EPSILON);

		st.addDigit(1);
		st.enter();
		st.addDigit(3);
		st.pop();

		assertEquals("Entered 1 and building a number. Called pop(). "
				+ "Display should be 1.", 1.,
				Double.parseDouble(display.getText()), EPSILON);

		miscScore++;
	}

	/**
	 * test exchange
	 *
	 * 1 pt misc
	 */
	@Test
	public void testExchange() {
		st.addDigit(9);
		st.enter();
		st.addDigit(2);
		st.enter();
		st.exchange();
		assertEquals("Added 9 and 2. Called exchange(). Display should be 9.",
				9., Double.parseDouble(display.getText()), EPSILON);
		st.pop();
		assertEquals("Added 9 and 2. Called exchange(), pop(). "
				+ "Display should be 2.", 2.,
				Double.parseDouble(display.getText()), EPSILON);

		miscScore++;
	}

	// TO-DO: rewrite the write-up. exchange should also exchange the building
	// or current number
	// @Test
	// public void testExchange_building() {
	// st.addDigit(0);
	// st.enter();
	// st.addDigit(2);
	// st.exchange();
	// // display should be 0
	// // next should be 2
	// }

	// @AfterClass
	// public static void printScores() {
	// 	System.out.println("Grading"); // identifier for bash script to detect
	// 	System.out.println("DigitButtonListener: " + (double) digitButtonScore);
	// 	System.out.println();
	// 	System.out.println("Add and Enter Properly: " + addEnterScore);
	// 	System.out.println();
	// 	System.out.println("Operations: " + opScore);
	// 	System.out.println();
	// 	System.out.println("Handle error properly: " + errorScore);
	// 	System.out.println();
	// 	System.out.println("Miscellaneous buttons: " + miscScore);
	// 	double total = digitButtonScore + addEnterScore + opScore + errorScore
	// 			+ miscScore;
	// 	System.out.println("Total:" + total);
	// }

	/**
	 * helper method to create the error message. did not use eventually
	 */
	// private String message(String action, char c, String expectedNum) {
	// switch (c) {
	// case 'd':
	// return action + " Display should be " + expectedNum;
	// case 't':
	// return action + " Top of stack should be " + expectedNum;
	// default:
	// return "";
	// }
	// }

	/**
	 * test method for digit button listener
	 */
	public void testDigitButtonListener() {

		JTextField field = new JTextField("0");
		MyState state = new MyState(field);
		JButton button = new JButton("" + BUTTON_LISTENER_NUM);
		DigitButtonListener listener = new DigitButtonListener(
				BUTTON_LISTENER_NUM, state);
		button.addActionListener(listener);
		button.doClick();
	}

	// public static void main(String args[]) {
	//
	// 	// test digit button listener
	// 	Grader t = new Grader();
	//
	// 	t.testDigitButtonListener();
	//
	// 	JUnitCore core = new JUnitCore();
	// 	RunListener rl = t.new MyRunListener();
	// 	core.addListener(rl);
	//
	// 	// StateTest test;
	// 	Result run = core.run(Grader.class);
	// 	// for (Failure fail : run.getFailures()) {
	// 	// System.out.println(fail);
	// 	// }
	//
	// }

	protected class MyRunListener extends RunListener {
		private boolean firstRun = true;

		public void testFailure(Failure failure) throws Exception {
			if (firstRun) {
				System.out.println("Some tests failed:");
				firstRun = false;
			}
			System.out.println(failure);
		}
	}

	/**
	 * Test class's special State class for testing purposes
	 *
	 * @author Sean Zhu
	 *
	 */
	protected class MyState extends State {

		public MyState(JTextField display) {
			super(display);
			// TODO Auto-generated constructor stub
		}

		public void addDigit(int value) {
			if (value == BUTTON_LISTENER_NUM) {
				digitButtonScore += 2;
			}
		}

		public void doOp(char c) {
			// System.out.println(c);
		}
	}

}
