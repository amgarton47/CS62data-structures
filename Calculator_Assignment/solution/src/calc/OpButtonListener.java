package calc;
/*
	OpButtonListener.java:  Operation button class - 1/97 Kim B. Bruce
*/

import java.awt.event.*;

/**
	Class representing buttons with operators on them.
**/
public class OpButtonListener implements ActionListener {
	protected State calcState;	// Does all the computation - buttons inform it of events.
	protected char opCode;		// code for kind of operation
	
	/**
	 *	Store the operation corresponding to the button and the state 
	 * so can communicate with it.
	 **/
	public OpButtonListener(String op, State state)	{
		calcState = state;	// Remember the state so can communicate with it.
		opCode = op.charAt(0);
	}
	
	/**
		pre:  Button was pushed.
		post:  Inform state that operation corresponding to button should be performed.
	**/
	public void actionPerformed(ActionEvent evt) 	{
		calcState.doOp(opCode); 
	}
}