import java.util.Scanner;


public class FlippyCardGame {
	private static final int NUM_CARDS = 5;
	
	public static void playGame(int numRounds){
		FlippyCards flippyCards = new FlippyCards(NUM_CARDS);

		Scanner in = new Scanner(System.in);
		int flipsLeft = numRounds;
		
		while( flipsLeft > 0 ){
			System.out.println(flippyCards);
			System.out.println("Face up total: " + flippyCards.faceUpTotal());
			System.out.println("Face down total: " + flippyCards.faceDownTotal());
			System.out.println("Number of flips left: " + flipsLeft);
			System.out.print("Pick a card to flip between 1 and " + NUM_CARDS + " (-1 to end game): ");
			
			int num = in.nextInt();
			
			System.out.println();
			
			if( num == -1 ){
				flipsLeft = 0;
			}else if( num < 1 || num > NUM_CARDS ){
				System.out.println(num + " is not a valid card");
			}else{
				flippyCards.flipCard(num-1);
				flipsLeft--;
			}
		}
		
		in.close();
		
		System.out.println();
		System.out.println("----------------------");
		System.out.println("Your score: " + flippyCards.faceUpTotal());
		System.out.println("Best possible score: " + flippyCards.calculateOptimal());
	}
	
	
	public static void main(String[] args){
		playGame(5);
	}
}
