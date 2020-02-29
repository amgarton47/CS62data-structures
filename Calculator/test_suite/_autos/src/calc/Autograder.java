package calc;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JTextField;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

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
	@BeforeEach
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
		assertNotNull(st.stack, "Stack/ArrayDeque shoud be initialized.");
		assertEquals(0, st.stack.size(), EPSILON, 
					"Stack should be empty when initialized.");
		assertEquals(0. , Double.parseDouble(display.getText()), EPSILON,
					"Display should be 0 when initialized.");

		// add 2 and enter
		st.addDigit(2);
		st.enter();
		assertEquals( 2., Double.parseDouble(display.getText()), EPSILON,
				"Called addDigit(2), enter(). " + "Display should be 2");
		assertEquals(2., Double.valueOf(st.stack.peek()), EPSILON,
				"Called addDigit(2), enter()." + " Top of stack should be 2");

		// add 7
		st.addDigit(7);
		assertEquals(7., Double.parseDouble(display.getText()), EPSILON,
				"Called addDigit(2),enter(), addDigit(7)." + " Display should be 7");
		assertEquals( 2., Double.valueOf(st.stack.peek()), EPSILON,
				"Called addDigit(2),enter(), addDigit(7). " + "Top of stack shoulbe be 2.");

		// add 5 and enter
		st.addDigit(5);
		assertEquals( 75., Double.parseDouble(display.getText()), EPSILON,
				"Called addDigit(2),enter(), addDigit(7), addDigit(5)." + 
				" Display should be 75");

		assertEquals( 2., Double.valueOf(st.stack.peek()), EPSILON,
				"Called addDigit(2),enter(), addDigit(7), addDigit(5)."
				+ " Top of stack shoulbe be 2.");

		st.enter();
		assertEquals( 75., Double.parseDouble(display.getText()), EPSILON,
				"Called addDigit(2),enter(), addDigit(7), addDigit(5),"
				+ " enter(). Display should be 75");

		assertEquals( 75., Double.valueOf(st.stack.peek()), EPSILON,
				"Called addDigit(2),enter(), addDigit(7), addDigit(5),"
				+ " enter(). Top of stack shoulbe be 75.");
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
		assertEquals( 0., Double.parseDouble(display.getText()), EPSILON,
				"Called addDigit(0). Display should be 0.");

		st.enter();
		assertEquals( 0., Double.parseDouble(display.getText()), EPSILON,
				"Called addDigit(0), enter(). Display should be 0.");
		assertEquals( 0., Double.valueOf(st.stack.peek()), EPSILON,
				"Called addDigit(0), enter()."
				+ " Top of stack should be 0.");

		// 1
		st.addDigit(1);
		assertEquals( 1., Double.parseDouble(display.getText()), EPSILON,
				"Top of stack was 0. Called addDigit(1). "
				+ "Display should be 1.");

		st.enter();
		assertEquals( 1., Double.parseDouble(display.getText()), EPSILON,
				"Top of stack was 0. Called addDigit(1), enter(). "
				+ "Display should be 1.");
		assertEquals( 1., Double.valueOf(st.stack.peek()), EPSILON,
				"Top of stack was 0. Called addDigit(1), enter(). "
				+ " Top of stack should be 1.");

		// 2
		st.addDigit(2);
		assertEquals( 2., Double.parseDouble(display.getText()), EPSILON,
				"Top of stack was 1. Called addDigit(2). "
				+ "Display should be 2.");

		st.enter();
		assertEquals( 2., Double.parseDouble(display.getText()), EPSILON,
				"Top of stack was 1. Called addDigit(2), enter(). "
				+ "Display should be 2.");
		assertEquals( 2., Double.valueOf(st.stack.peek()), EPSILON,
				"Top of stack was 1. Called addDigit(2), enter(). "
				+ " Top of stack should be 2.");

		// 3
		st.addDigit(3);
		assertEquals( 3., Double.parseDouble(display.getText()), EPSILON,
				"Top of stack was 2. Called addDigit(3). "
				+ "Display should be 3.");

		st.enter();
		assertEquals( 3., Double.parseDouble(display.getText()), EPSILON,
				"Top of stack was 2. Called addDigit(3), enter(). "
				+ "Display should be 3.");
		assertEquals( 3., Double.valueOf(st.stack.peek()), EPSILON,
				"Top of stack was 2. Called addDigit(3), enter(). "
				+ " Top of stack should be 3.");

		// 4
		st.addDigit(4);
		assertEquals( 4., Double.parseDouble(display.getText()), EPSILON,
				"Top of stack was 3. Called addDigit(4). "
				+ "Display should be 4.");

		st.enter();
		assertEquals( 4., Double.parseDouble(display.getText()), EPSILON,
				"Top of stack was 3. Called addDigit(4), enter(). "
				+ "Display should be 4.");
		assertEquals( 4., Double.valueOf(st.stack.peek()), EPSILON,
				"Top of stack was 3. Called addDigit(4), enter(). "
				+ " Top of stack should be 4");

		// 5
		st.addDigit(5);
		assertEquals( 5., Double.parseDouble(display.getText()), EPSILON,
				"Top of stack was 4. Called addDigit(5). "
				+ "Display should be 5.");

		st.enter();
		assertEquals( 5., Double.parseDouble(display.getText()), EPSILON,
				"Top of stack was 4. Called addDigit(4), enter(). "
				+ "Display should be 5");
		assertEquals( 5., Double.valueOf(st.stack.peek()), EPSILON,
				"Top of stack was 4. Called addDigit(5), enter(). "
				+ " Top of stack should be 5.");

		// 6
		st.addDigit(6);
		assertEquals( 6., Double.parseDouble(display.getText()), EPSILON,
				"Top of stack was 5. Called addDigit(6). "
				+ "Display should be 6.");

		st.enter();
		assertEquals( 6., Double.parseDouble(display.getText()), EPSILON,
				"Top of stack was 5. Called addDigit(6), enter(). "
				+ "Display should be 6.");
		assertEquals( 6., Double.valueOf(st.stack.peek()), EPSILON,
				"Top of stack was 5. Called addDigit(6), enter(). "
				+ " Top of stack should be 6.");
		// 7
		st.addDigit(7);
		assertEquals( 7., Double.parseDouble(display.getText()), EPSILON,
				"Top of stack was 6. Called addDigit(7). "
				+ "Display should be 7.");

		st.enter();
		assertEquals( 7., Double.parseDouble(display.getText()), EPSILON,
				"Top of stack was 6. Called addDigit(7), enter(). "
				+ "Display should be 7.");
		assertEquals( 7., Double.valueOf(st.stack.peek()), EPSILON,
				"Top of stack was 6. Called addDigit(7), enter(). "
				+ " Top of stack should be 7");

		// 8
		st.addDigit(8);
		assertEquals( 8., Double.parseDouble(display.getText()), EPSILON,
				"Top of stack was 7. Called addDigit(8). "
				+ "Display should be 8.");

		st.enter();
		assertEquals( 8., Double.parseDouble(display.getText()), EPSILON,
				"Top of stack was 7. Called addDigit(8), enter(). "
				+ "Display should be 8.");
		assertEquals( 8., Double.valueOf(st.stack.peek()), EPSILON,
				"Top of stack was 7. Called addDigit(8), enter(). "
				+ " Top of stack should be 8.");

		// 9
		st.addDigit(9);
		assertEquals( 9., Double.parseDouble(display.getText()), EPSILON,
				"Top of stack was 8. Called addDigit(9). "
				+ "Display should be 9.");

		st.enter();
		assertEquals( 9., Double.parseDouble(display.getText()), EPSILON,
				"Top of stack was 8. Called addDigit(9), enter(). "
				+ "Display should be 9.");
		assertEquals( 9., Double.valueOf(st.stack.peek()), EPSILON,
				"Top of stack was 8. Called addDigit(9), enter(). "
				+ " Top of stack should be 9.");

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

		assertEquals( 876543210., Double.parseDouble(display.getText()), EPSILON,
				"Called addDigit(8) to addDigit(0). "
				+ "Display should be 876543210.");

		st.enter();
		assertEquals( 876543210., Double.parseDouble(display.getText()), EPSILON,
				"Called addDigit(8) to addDigit(0),"
				+ " enter(). Display should be 876543210.");
		assertEquals( 876543210., Double.valueOf(st.stack.peek()), EPSILON,
				"Called addDigit(8) to addDigit(0),"
				+ " enter(). Top of stack should be 876543210.");

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

		assertEquals( 2147483647., Double.parseDouble(display.getText()), EPSILON,
				"Called addDigit()s to add 2147483647. "
				+ "Display should be 2147483647.");

		st.enter();
		assertEquals( 2147483647., Double.parseDouble(display.getText()), EPSILON,
				"Called addDigit()s to add 2147483647, enter() "
				+ "Display should be 2147483647.");
		assertEquals( 2147483647., Double.valueOf(st.stack.peek()), EPSILON,
				"Called addDigit() to add 2147483647. enter"
				+ "Top of stack should be 2147483647.");

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
		assertEquals( 1, st.stack.size(),
				"AddDigit(1), enter(), enter(). "
				+ "Should only have one element on the stack.");

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
		assertEquals( 11., Double.parseDouble(display.getText()), EPSILON,
				"Adding 4 and 7. Display should be 11.");
		assertEquals( 11., Double.valueOf(st.stack.peek()), EPSILON,
				"Adding 4 and 7");
		st.doOp('+');
		assertEquals( 58., Double.parseDouble(display.getText()), EPSILON,
				"Adding 47 and 11. Display should be 58.");
		assertEquals( 58., Double.valueOf(st.stack.peek()), EPSILON,
				"Adding 47 and 11. Top of stack should be 58.");

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
		assertEquals( 876543268., Double.parseDouble(display.getText()), EPSILON,
				"Adding 58 and 876543210. Display should be 876543268.");
		assertEquals( 876543268., Double.valueOf(st.stack.peek()), EPSILON,
				"Adding 58 and 876543210. Top of stack should be " + "876543268");

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
		assertEquals( 51., Double.parseDouble(display.getText()), EPSILON,
				"Adding 47 and 4, without entering 4."
				+ " Display should be 51.");
		assertEquals( 51., Double.valueOf(st.stack.peek()), EPSILON,
				"Adding 47 and 4, without entering 4."
				+ " Top of stack should be 51");

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
		assertEquals( "Error", display.getText(),
				"Calling doOp('+') after initialization."
				+ " Display should be 'Error'.");
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
		assertEquals( "Error", display.getText(),
				"Add 47 and 4, then doOp('+') twice."
				+ " Display should be 'Error'.");
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
		assertEquals( 28., Double.parseDouble(display.getText()), EPSILON,
				"Multiplying 4 and 7." + " Display should be 28.");
		assertEquals( 28., Double.valueOf(st.stack.peek()), EPSILON,
				"Mulitplying 4 and 7" + " Top of stack should be 28.");
		st.doOp('*');
		assertEquals( 1316., Double.parseDouble(display.getText()), EPSILON,
				"Multiplying 47 and 28. Display should be 1316.");
		assertEquals( 1316., Double.valueOf(st.stack.peek()), EPSILON,
				"Multiplying 47 and 28. Top of stack should be 1316.0");

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
		assertEquals( 188., Double.parseDouble(display.getText()), EPSILON,
				"Mulitplying 47 and 4, without entering 4."
				+ " Display should be 58.");
		assertEquals( 188., Double.valueOf(st.stack.peek()), EPSILON,
				"Mulitplying 47 and 4, without entering 4."
				+ " Top of stack should be 188.");

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
		assertEquals( "Error", display.getText(),
				"Calling doOp('*') after initializaiton."
				+ " Display should be 'Error'.");
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
		assertEquals( "Error", display.getText(),
				"Enter 47 and 4, then doOp('*') twice."
				+ " Display should be 'Error'.");
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
		assertEquals( -3., Double.parseDouble(display.getText()), EPSILON,
				"Subtract 7 from 4." + " Display should be -3.");
		assertEquals( -3., Double.valueOf(st.stack.peek()), EPSILON,
				"Subtract 7 from 4." + " Top of stack be 'Error'.");
		st.doOp('-');
		assertEquals( 50., Double.parseDouble(display.getText()), EPSILON,
				"Subtract -3 from 47." + " Display should be 50.");
		assertEquals( 50., Double.valueOf(st.stack.peek()), EPSILON,
				"Subtract -3 from 47." + " Top of stack should be '50.");

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
		assertEquals( -876543160., Double.parseDouble(display.getText()), EPSILON,
				"Subtract 876543210 from 50. "
				+ "Display should be -876543160");
		assertEquals( -876543160., Double.valueOf(st.stack.peek()), EPSILON,
				"Subtract 876543210 from 50."
				+ " Top of stack should be -876543160");

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
		assertEquals( 43., Double.parseDouble(display.getText()), EPSILON,
				"Subtracting 4 from 47, without entering 4. Display should be 43");
		assertEquals( 43, Double.valueOf(st.stack.peek()), EPSILON,
				"Subtracting 4 from 47, without entering 4. "
				+ " Top of stack should be 43");
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
		assertEquals( "Error", display.getText(),
				"Calling doOp('-') after initialization."
				+ " Display should be 'Error'.");
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
		assertEquals( "Error", display.getText(),
				"Enter 47 and 4, then doOp('-') twice."
				+ " Display should be 'Error'.");
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
		assertEquals( 5., Double.parseDouble(display.getText()), EPSILON,
				"Dividing 10 by 2." + " Display should be 5.");
		assertEquals( 5., Double.valueOf(st.stack.peek()), EPSILON,
				"Diving 10 by 2" + " Top of stack should be 5.");
		st.doOp('/');
		assertEquals( 20., Double.parseDouble(display.getText()), EPSILON,
				"Dividing 100 by 5. Display should be 20.");
		assertEquals( 20., Double.valueOf(st.stack.peek()), EPSILON,
				"Dividing 100 by 5. Top of stack should be 20.");
		st.addDigit(1);
		st.addDigit(9);
		st.enter();
		st.doOp('/');
		assertEquals( 1., (int) Double.parseDouble(display.getText()), EPSILON,
				"Dividing 20 by 19. Display should be 1.");

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
		assertEquals( 1., Double.parseDouble(display.getText()), EPSILON,
				"Dividing 4 by 4, without entering 4."
				+ " Display should be 1.");
		assertEquals( 1., Double.valueOf(st.stack.peek()), EPSILON,
				"Dividing 4 by 4, without entering 4."
				+ " Top of stack should be 1.");
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
		assertEquals( "Error", display.getText(),
				"Calling doOp('/') after initializaiton."
				+ " Display should be 'Error'.");
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
		assertEquals( "Error", display.getText(),
				"Enter 47 and 4, then doOp('/') twice."
				+ " Display should be 'Error'.");

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
		assertEquals( "Error", display.getText(),
				"Divide 4 by 0." + " Display should be 'Error'.");

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
		assertEquals( 0, st.stack.size(),
				"Called clear(), size of stack should zero.");
		assertEquals( 0., Double.parseDouble(display.getText()), EPSILON,
				"Called clear(). Display should 0.");
		st.addDigit(4);
		assertEquals( 4., Double.parseDouble(display.getText()), EPSILON,
				"Called addDigit(3), clear(), and addDigit(4). Display should be 4");

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
		assertEquals(
				9., Double.parseDouble(display.getText()), EPSILON,
				"Added 9 and 2. Called pop(). Display should be 9.");
		st.pop();
		assertEquals(
				0., Double.parseDouble(display.getText()), EPSILON,
				"Added 9 and 2. Called pop() twice. Display should be 0.");
		st.pop();
		assertEquals( 0., Double.parseDouble(display.getText()), EPSILON,
				"Poping from an empty stack. Display should be 0.");

		st.addDigit(1);
		st.enter();
		st.addDigit(3);
		st.pop();

		assertEquals(
				1., Double.parseDouble(display.getText()), EPSILON,
				"Entered 1 and building a number. Called pop(). "
				+ "Display should be 1.");

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
		assertEquals(
				9., Double.parseDouble(display.getText()), EPSILON,
				"Added 9 and 2. Called exchange(). Display should be 9.");
		st.pop();
		assertEquals(
				2., Double.parseDouble(display.getText()), EPSILON,
				"Added 9 and 2. Called exchange(), pop(). "
				+ "Display should be 2.");

		miscScore++;
	}

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
