package calc;

import java.awt.event.*;

/**
 *	Class notified when operator buttons are pressed.
 *
 */
public class OpButtonListener implements ActionListener
{
	protected State calcState;	// Does all the computation - buttons inform it of events.
	protected char opCode;		// label of button
	/**
	 *	Button knows its operation and the state so can communicate with it.
	 */
	public OpButtonListener(char op, State state)
	{
		calcState = state;	// Remember the state so can communicate with it.
		opCode = op;
	}
	
	/**
	 *	@pre:  Button was pushed.
	 *	@post:  Inform state that operation corresponding to button should be performed.
	 **/
	public void actionPerformed(ActionEvent evt)
	{
		calcState.doOp(opCode); 
	}
}
