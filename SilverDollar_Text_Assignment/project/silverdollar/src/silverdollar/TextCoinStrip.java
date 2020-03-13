/**
 * CS062: silverdollar 
 *	a simple coin-moving game implemented with ArrayLists
 *
 * @author YOUR-NAME-HERE
 */

package silverdollar; 

import java.util.Random; 
import java.util.Scanner; 
import java.util.ArrayList;

public class TextCoinStrip { 

	/** 
	 * the strip of coins. It is an arraylist with locations 
	 * indexed starting at 0. A square is occupied by a coin 
	 * if the boolean value at that location is true. 
	 */ 
	protected ArrayList<Boolean> theStrip; 

	/** 
	 * Constructs a representation of the Silver Dollar Game 
	 * 
	 * @param squares the number of squares 
	 * @param coins the number of coins 
	 * pre: 0 < coins < squares 
	 */ 
	public TextCoinStrip (int squares, int coins) { 
		if (0 > coins || coins >= squares) { // Check precondition
			System.out.println("Game must be played with number of\n"+
							   "coins less than number of squares");
			throw new IllegalArgumentException("# coins: "+coins+" must be positive "
											   +"and less than # squares: "+squares);
		}
		
		theStrip = new ArrayList<Boolean>(); 
		//populate all squares with false values
		for (int i = 0; i < squares; i++){ 
			theStrip.add(false); 
		} 

		//place #coins randomly on the strip
		Random rand = new Random (); 
		while (0 < coins) { 
			int i = rand.nextInt (squares); 
			if (!theStrip.get(i)) { 
				theStrip.set(i, true); 
				coins--; 
			} 
		} 
	} 

	/** 
	 * toString returns the string representation of a strip. 
	 * 
	 * @return the string representation 
	 */ 
	public String toString() { 
		//TODO: fill this
	} 

	/** 
	 * isLegalMove determines if a move is legal. 
	 * 
	 * @param start the location of the coin to be moved 
	 * @param distance how far the coin is to move 
	 * @return true if the move is legal 
	 */ 
	public boolean isLegalMove(int start, int distance) {
		return false;	// TODO: replace this with real code
	} 

	/** 
	 * makeMove makes a (legal) move. 
	 * 
	 * @param start the location of the coin to be moved 
	 * @param distance how far the coin is to move 
	 * pre: the move must be a legal one 
	 */ 
	public void makeMove(int start, int distance) { 
		//TODO: implement this method

	} 

	/** 
	 * gameIsOver determines if a game is completed. 
	 * 
	 * @return true if there are no more moves 
	 */ 
	public boolean gameIsOver() { 
		return false;	// TODO: replace this with real code
	} 

	/** 
	 * play is a method to play the Silver Dollar Game 
	 * until it is finished. 
	 */ 
	public void play() { 
		Scanner scanner = new Scanner(System.in); 
		int start = 0; 
		int distance = 0; 
		while (!gameIsOver()) { 
			System.out.print(toString() + " Next move? "); 
			start = scanner.nextInt(); 
			distance = scanner.nextInt(); 
			if (isLegalMove(start, distance)){ 
				makeMove(start, distance); 
			}else{ 
				System.out.println("Illegal move!"); 
			} 
		} 
		System.out.println(toString() + "You win!!"); 
		scanner.close();
	} 

	/** 
	 * A demonstration main method. It simply constructs 
	 * a 12-square strip with 5 coins and then plays 
	 * the Silver Dollar Game. 
	 */ 
	public static void main (String[] args) { 
		TextCoinStrip tcs = new TextCoinStrip (12, 5); 
		tcs.play(); 
	} 
}
