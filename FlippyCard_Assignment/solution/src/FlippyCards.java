import java.util.Scanner;

public class FlippyCards {
	private Card[] cards;
	
	public FlippyCards(int numCards){
		cards = new Card[numCards];
		
		CardDealer dealer = new CardDealer(1);
		
		for( int i = 0; i < cards.length; i++ ){
			cards[i] = dealer.next();
		}
	}
	
	public void flipCard(int cardIndex) {
		cards[cardIndex].flip();
	}
	
	public int calculateOptimal(){
		int sum = 0;
		
		for( int i = 0; i < cards.length; i++ ){
			if( cards[i].isRedCard() ){
				sum += cards[i].getCardValue();
			}
		}
		
		return sum;
	}
	
	public int faceUpTotal(){
		return faceTotalHelper(true);
	}
	
	public int faceDownTotal(){
		return faceTotalHelper(false);
	}
	
	private int faceTotalHelper(boolean faceUp){
		int sum = 0;
		
		for( int i = 0; i < cards.length; i++ ){
			if( cards[i].isFaceUp() == faceUp ){
				if( cards[i].isRedCard() ){
					sum += cards[i].getCardValue();
				}else{
					sum -= cards[i].getCardValue();
				}
			}
		}
		
		return sum;
	}
	
	public String toString(){
		String s = "";
		
		for( int i = 0; i < cards.length; i++ ){
			if( cards[i].isFaceUp() ){
				s += cards[i] + " | ";
			}else{
				s += "FACE-DOWN | ";
			}
		}
		
		return s.substring(0, s.length()-2);
	}
}
