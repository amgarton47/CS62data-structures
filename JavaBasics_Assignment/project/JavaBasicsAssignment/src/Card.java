/**
 * A class representing a standard four suit, thirteen numbered 
 * playing card
 * 
 * @author drk04747
 *
 */
public class Card {
	// Card values min and max (inclusive)
	private static final int MIN_NUMBER = 1;
	private static final int MAX_NUMBER = 13;
	
	// the number on the card and the suit
	private int number;
	private String suit;
	
	/**
	 * Create a new card
	 * 
	 * @param tempNumber
	 * @param tempSuit
	 */
	public Card(int tempNumber, String tempSuit){		
		if( isValidNumber(tempNumber) ) {
			number = tempNumber;
		}else {
			System.out.println("Invalid card number: " + tempNumber);
			System.out.println("Defaulting to 1");
			number = 1;
		}

		if( isValidSuit(tempSuit) ) {
			suit = tempSuit;
		} else {
			System.out.println("Invalid suit: " + tempSuit);
			System.out.println("Defaulting to hearts");
			suit = "hearts";
		}		
	}
	
	/**
	 * Get the suit of this card
	 * 
	 * @return the suit
	 */
	public String getSuit(){
		return suit;
	}
	
	/**
	 * Get the number associated with this card
	 * 
	 * @return the number
	 */
	public int getNumber(){
		return number;
	}
	
	/**
	 * Cheat and change this card to an ace!
	 */
	public void cheat(){
		number = 1; // ACE!
	}
	
	/**
	 * Two cards are equal if their number and suit are the same
	 */
	public boolean equals(Card other){
		return number == other.number && suit.equals(other.suit);
		// could also write as:
		// number == other.getNumber() && suit.equals(other.getSuit());	
	}
	
	public String toString(){
		return number + " of " + suit;
	}
	
	/**
	 * Check if the number is a valid number for the card
	 * 
	 * @param number
	 * @return whether or not the number is valid
	 */
	private boolean isValidNumber(int number){
		return number >= MIN_NUMBER && number <= MAX_NUMBER;
	}
	
	/**
	 * Check if the suit is a valid suit
	 * 
	 * @param suit
	 * @return whether or not the suit is valid
	 */
	private boolean isValidSuit(String suit){
		return suit.equals("spades") ||
			   suit.equals("hearts") ||
			   suit.equals("clubs") ||
			   suit.equals("diamonds");
	}
	
	public static void main(String[] args){
	    /*Card card = new Card(10, "hearts");
	    Card jack = new Card(11, "spades");
	    Card ace = new Card(1, "clubs");
			
	    System.out.println(card.toString());
	    System.out.println(jack.toString());
	    System.out.println(ace.toString());
					
	    System.out.println("--------CHEATING------");
	    card.cheat();
	    System.out.println(card.toString());
	    System.out.println(jack.toString());
	    System.out.println(ace.toString());
			
	    System.out.println("--------BEFORE FLIPPING-------");
	    System.out.println(card.toString() + ": " + card.isFaceUp());
	    System.out.println("--------AFTER FLIPPING-------");
	    card.flip();
	    System.out.println(card.toString() + ": " + card.isFaceUp());
	    System.out.println("--------AFTER FLIPPING-------");
	    card.flip();
	    System.out.println(card.toString() + ": " + card.isFaceUp());*/
	}
}