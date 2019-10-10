/* 
 * Your name, date, and description of the program 
 * 
 */ 

package silverdollar;

import java.util.Random; 
import java.util.Scanner; 
import java.util.ArrayList;

public class TextCoinStrip { 
	/** 
	 * the strip of coins. It is a vector with locations 
	 * indexed starting at 0. A square is occupied by a coin 
	 * if the boolean value at that location is true. 
	 */ 
	private ArrayList<Boolean> theStrip; 
	private int coins;
	
	/** 
	 * A demonstration main method. It simply constructs 
	 * a 12-square strip with 5 coins and then plays 
	 * the Silver Dollar Game. 
	 */ 
	public static void main (String[] args) { 
		TextCoinStrip tcs = new TextCoinStrip (12, 5); 
		tcs.play(); 
	}
	
	
	/** Constructs a representation of the Silver Dollar Game 
	 * 
	 * @param squares the number of squares 
	 * @param coins the number of coins 
	 * @pre 0 < coins < squares 
	 */ 
	public TextCoinStrip (int squares, int coins){ 
		assert 0 < coins && coins < squares; 
		
		this.coins = coins;
		theStrip = new ArrayList<Boolean>(squares); 
	
		
		for (int i = 0; i < squares; i++){ 
			theStrip.add(false); 
		}
		
		Random rand = new Random (); 
		
		while (0 < coins) { 
			int i = rand.nextInt(squares); 
		
			if (!theStrip.get(i)){
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
		StringBuffer buffer = new StringBuffer();
		
		for( boolean entry: theStrip){
			if( entry ){
				buffer.append("o");
			}else{
				buffer.append("_");
			}
		}
		
		return buffer.toString();
	}
	/** 
	 * isLegalMove determines if a move is legal. 
	 * 
	 * @param start the location of the coin to be moved 
	 * @param distance how far the coin is to move 
	 * @return true if the move is legal 
	 */ 
	public boolean isLegalMove(int start, int distance) { 
		if( start < 0 || start >= theStrip.size() ||
				!theStrip.get(start) ){
			return false;
		}else{
			boolean legal = true;
			
			// check to make sure there isn't a coin within distance moves
			for( int i = 1; i <= distance && legal; i++ ){
				if( theStrip.get(start-i) ){
					legal = false;
				}
			}
			
			return legal;
		}		
	}
	
	/** 
	 * makeMove makes a (legal) move. 
	 * 
	 * @param start the location of the coin to be moved 
	 * @param distance how far the coin is to move 
	 * @pre the move must be a legal one 
	 */ 
	public void makeMove(int start, int distance) {
		assert isLegalMove(start, distance);
		
		theStrip.set(start, false);
		theStrip.set(start-distance, true);
	} 
	
	/** 
	 * gameIsOver determines if a game is completed. 
	 * 
	 * @return true if there are no more moves 
	 */ 
	public boolean gameIsOver() { 
		boolean over = true;
		
		for( int i = 0; i < coins && over; i++ ){
			// check if the strip contains any non-coins in the
			// beginning
			if( !theStrip.get(i) ){
				over = false;
			}
		}
		
		return over;
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
	}
}
