package calc;
/*
 * State.java - 1/97 Kim B. Bruce
 */

import java.util.ArrayDeque;
import java.util.Deque;
import javax.swing.*;
/**
 * Class representing the internal state of the calculator. It is responsible
 * for keeping track of numbers entered and performing operations when buttons
 * are clicked.
 */
public class State {
	// Number typed in so far
	protected int partialNum; 		

	// Was last button clicked a digit?
	protected boolean numberInProgress; 
	
	// Stack of numbers in computation
	protected Deque<Integer> numStack; 
	
	// Display on which results are written
	protected JLabel calcDisplay; 

	/**
	 * Constructor tells State which label to write results to and initializes
	 * other fields.
	 */
	public State(JLabel display) {
		calcDisplay = display;
		numberInProgress = false;
		numStack = new ArrayDeque<Integer>();
	}

	/**
	 * pre: User clicked on a digit button post: Digit has been added to partial
	 * number constructed so far (if any), system remembers that there is a
	 * number in progress.
	 */
	public void addDigit(int value) {
		if (!numberInProgress) { // Start a new number
			partialNum = value;
			numberInProgress = true;
		} else { 	// Continue constructing number typed in so far
			partialNum = 10 * partialNum + value;
		}
		calcDisplay.setText("" + partialNum);
	}

	/**
	 * pre: User has clicked on operator button 
	 * post: If there is a number in progress, push it onto the stack. 
	 * Perform the given operation if there are enough operands
	 * on the run-time stack. Otherwise display error
	 * message. In either case, no longer have a number in progress.
	 */
	public void doOp(char op) {
		boolean error = false;
		if (numberInProgress) { // push partial number before doing the operation
			enter();
		}
		if (numStack.size() >= 2) { // Get two numbers from stack if possible
			int sndArg = numStack.pop();
			int fstArg = numStack.pop();
			int result = 0;
			switch (op) { // Execute appropriate operation on numbers.
				case '+' :
					result = fstArg + sndArg;
					break;
				case '-' :
					result = fstArg - sndArg;
					break;
				case '*' :
					result = fstArg * sndArg;
					break;
				case '/' :
					if (sndArg != 0)
						result = fstArg / sndArg;
					else // Divide by zero causes an error.
						error = true;
					break;
				default :
					break;
			}
			partialNum = result;
			numStack.push(result);
			// Push answer onto stack and display it
			calcDisplay.setText(Integer.toString(result));
		} else // Not enough numbers on stack
			error = true;

		if (error) { // If something went wrong, display error and clear.
			calcDisplay.setText("Error");
			numStack.clear();
			numberInProgress = false;
		}
	}
	
	/**
	 * pre: User clicked on enter button 
	 * post: Push number on display onto stack
	 */
	public void enter() {
		numStack.push(partialNum);
		numberInProgress = false;
	}

	/**
	 * pre: User clicked on clear key 
	 * post: Clear state of computation and reset display to zero.
	 */
	public void clear() {
		numStack.clear();
		numberInProgress = false;
		partialNum = 0;
		calcDisplay.setText("0");
	}

	/**
	 * pre: User clicked on pop key 
	 * post: If number in progress, throw it away, otherwise pop and 
	 * throw away top element on computation stack. Display
	 * new top element of stack or zero if stack now empty.
	 */
	public void pop() {
		String displayNum;

		if (!numberInProgress && !numStack.isEmpty()) {
			numStack.pop();
		}
		// Display new top of stack (or zero if stack empty)
		if (numStack.isEmpty()) {
			displayNum = "0";
			partialNum = 0;
		} else {
			displayNum = numStack.peek().toString();
			partialNum = numStack.peek();
		}
		calcDisplay.setText(displayNum);
		numberInProgress = false;
	}
	
	/**
	 * User clicked on exchange key
	 * post: If number in progress, push it onto the stack.
	 * If there are now at least two elements on the stack,
	 * pop the top two elements of the stack and push them on
	 * in the reverse order.  Display
	 * new top element of stack
     * If there were less than two elements on the stack, do nothing.
	 */
	public void exchange() {
		if (numberInProgress) { // push partial number before doing the operation
			enter();
		}
		if (numStack.size() >= 2) { // Get two numbers from stack if possible
			int topMost = numStack.pop();
			int second = numStack.pop();
			numStack.push(topMost);
			numStack.push(second);
			numberInProgress = false;
			calcDisplay.setText(second + "");
		}
	}
}
