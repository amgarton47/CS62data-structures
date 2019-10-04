package calc;

import java.util.Deque;
import javax.swing.*;

/**
 * Class representing the internal state of the calculator. It is responsible
 * for keeping track of numbers entered and performing operations when buttons
 * are clicked. It tells the display what number to show.
 * 
 * TODO: Add missing Docs
 * 
 */
public class State {
	// Display on which results are written
	protected JTextField calcDisplay;
	
	// The stack to on which to store numbers.
	//
	// Note 1: If you implement the extra credit of adding floating point operations,
	// then you will need to change the type parameter here, otherwise this
	// field should not be changed.
	//
	// Note 2: Deque<Integer> is an interface two reasonable implementing classes to use
	// would be ArrayDeque and LinkedList.
	protected Deque<Integer> stack;

	// TODO: add any other instance variables you need below

	/**
	 * TODO: Add Docs
	 */
	public State(JTextField display) {
		// TODO: add code
	}

	/**
	 * User clicked on a digit button ...
	 * TODO: Complete Docs
	 */
	public void addDigit(int value) {
		// TODO: add your code
	}

	/**
	 * User has clicked on operator button ...
	 * TODO: Complete Docs
	 */
	public void doOp(char op) {
		// TODO: add your code
	}

	/**
	 * User clicked on enter button ...
	 * TODO: Complete Docs
	 */
	public void enter() {
		// TODO: add your code
	}

	/**
	 * User clicked on clear key ...
	 * TODO: Complete Docs
	 */
	public void clear() {
		// TODO: add your code
	}

	/**
	 * User clicked on pop key ...
	 * TODO: Complete Docs
	 */
	public void pop() {
		// TODO: add your code
	}

	/**
	 * User clicked on pop key ...
	 * TODO: Complete Docs
	 */
	public void exchange() {
		// TODO: add your code
	}
	
	// TODO: Remember to add your extra operation!
}
