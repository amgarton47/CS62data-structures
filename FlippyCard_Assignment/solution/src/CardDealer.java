import java.util.Random;

/**
 * A basic class to handle dealing and shuffling cards from one or more decks of cards
 * 
 * @author dave
 */
public class CardDealer{
	private static final int CARDS_PER_SUIT = 13;  // the number of cards in the deck
	private static final String[] suits = {"clubs", "diamonds", "hearts", "spades"};
	
	private Card[] cards;  // all of the cards to be dealt
	private int position;  // our current position in our stack of cards

	/**
	 * Creates a new card dealer with numDecks decks in the deck
	 * 
	 * @param numDecks the number of card decks to use
	 */
	public CardDealer(int numDecks){
		int numCards = numDecks * CARDS_PER_SUIT * suits.length;
		cards = new Card[numCards];
		
		int cardIndex = 0;
		
		for( int i = 0; i < numDecks; i++ ){
			for( int suitIndex = 0; suitIndex < suits.length; suitIndex++ ){
				for( int numIndex = 1; numIndex <= CARDS_PER_SUIT; numIndex++ ){
					cards[cardIndex] = new Card(numIndex, suits[suitIndex]);
					cardIndex++;
				}
			}
		}
		
		shuffle();
	}
		
	/** 
	 * Returns true if there are cards left in the deck, false otherwise
	 */
	public boolean hasNext(){
		return position < cards.length;
	}
	
	/**
	 * Returns the next card if there are cards left in the deck
	 * 
	 * @pre assumes there are cards left in the deck (i.e. hasNext() is true)
	 */
	public Card next(){
		position++;
		return cards[position-1];
	}
	
	/** 
	 * Randomly shuffles the deck and resets the card to be dealt as the first card
	 */
	public void shuffle(){
		Random rand = new Random();
		
		for( int i = 0; i < cards.length; i++ ){
			int nextPosition = rand.nextInt(cards.length);
			
			// swap this card with the card at next position
			Card temp = cards[i];
			cards[i] = cards[nextPosition];
			cards[nextPosition] = temp;
		}
		
		position = 0;
	}
	
	public static void main(String[] args){
		CardDealer dealer = new CardDealer(2);
		
		for( int i = 0; i < 100; i++ ){
			System.out.println(dealer.next());
		}
	}
}