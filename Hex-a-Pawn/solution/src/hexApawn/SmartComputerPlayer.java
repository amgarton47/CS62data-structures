import java.util.*;

public class SmartComputerPlayer implements Player {
	char color;
	Random r = new Random();

	public String toString() {
		return color == HexBoard.BLACK ? "black" : "white";
	}

	public SmartComputerPlayer(char color) {
		this.color = color;
	}

	public Player play(GameTree node, Player opponent) {
		if (node.getBoard().win(HexBoard.opponent(color))
				|| node.numChildren() == 0) {
			trimTree(node);
			return opponent;
		} else {
			int num = r.nextInt(node.numChildren());
			GameTree newNode = node.getChild(num);
			return opponent.play(newNode, this);
		}
	}

	// remove the move that led to the loss of the game
	// current node reflects opponents last move.
	private void trimTree(GameTree node) {
		GameTree myLastMoveResult = node.getParent();
		if(myLastMoveResult == null){
			System.out.println("Parent is null: nothing to remove!!");
		} else {
			GameTree gameBeforeMyLastMove = myLastMoveResult.getParent();
			gameBeforeMyLastMove.remove(myLastMoveResult);
			System.out.println("Removed "+myLastMoveResult);
		}
	}

	public static void main(String s[]) {
		Scanner r = new Scanner(System.in);
		GameTree gt = new GameTree(new HexBoard(), HexBoard.WHITE);
		String ans = "yes";
		while (ans.equals( "yes")) {
			Player h1 = new SmartComputerPlayer(HexBoard.BLACK);
			Player h2 = new HumanPlayer(HexBoard.WHITE);
			System.out.println(h2+" plays first.");
			System.out.println(h2.play(gt, h1)+" wins!");
			System.out.println("The tree now has size "+gt.size());
			System.out.println("Play again?  answer yes or no");
			ans = r.next().toLowerCase();
			System.out.println("Your answer was "+ans);
		}
		System.out.println("Thanks for playing!");
		r.close();
	}
}
