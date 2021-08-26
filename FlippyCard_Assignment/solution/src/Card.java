
public class Card {
	private static final int MIN_NUMBER = 1;
	private static final int MAX_NUMBER = 13;

	private int number;
	private String suit;
	private boolean faceUp = false;

	public Card(int tempNumber, String tempSuit){
		number = tempNumber;
		suit = tempSuit.toLowerCase();

		if( !isValidSuit(suit) ){
			System.out.println(tempSuit + "Is not a valid suit!");
			System.out.println("Must be one of: clubs, diamonds, hearts or spades");
			System.out.println("Defaulting to hearts");
			suit = "hearts";
		}

		if( !isValidNumber(number) ){
			System.out.println(tempNumber + "Is not a valid number!");
			System.out.println("Must be between " + MIN_NUMBER + " and " + MAX_NUMBER);
			System.out.println("Defaulting to 1");
			number = 1;
		}		
	}

	public String getSuit(){
		return suit;
	}

	public String getNumber(){
		if( number == 1 ){
			return "Ace";
		}else if( number >= 2 && number <= 10 ){
			return Integer.toString(number);
		}else if( number == 11 ){
			return "Jack";
		}else if( number == 12 ){
			return "Queen";
		}else{
			return "King";
		}
	}
	
	public int getCardValue(){
		if( number == 1){
			return 11;
		}else if( number >= 2 && number <= 10 ){
			return number;
		}else{
			return 10;
		}
	}
	
	public boolean isRedCard(){
		return suit.equals("hearts") || suit.equals("diamonds");
	}
	
	public boolean isFaceUp(){
		return faceUp;
	}
	
	public void flip(){
		faceUp = !faceUp;
	}
	
	public String toString(){
		return getNumber() + " of " + getSuit();
	}
	
	public void cheat(){
		number = 1; // ACE!
	}

	// check to make sure the card number is valid
	private boolean isValidNumber(int num){
		return num >= MIN_NUMBER && num <= MAX_NUMBER;
	}

	// check to make sure the suit is valid
	private boolean isValidSuit(String s){
		return s.equals("spades") ||
				s.equals("hearts") ||
				s.equals("clubs") ||
				s.equals("diamonds");
	}
}