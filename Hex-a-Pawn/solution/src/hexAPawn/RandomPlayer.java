import java.util.*;

public class RandomPlayer implements Player {
	char color;
	Random r = new Random();

	public String toString() {
		return color == HexBoard.BLACK ? "black" : "white";
	}

	public RandomPlayer(char color) {
		this.color = color;
	}

	public Player play(GameTree node, Player opponent) {
		if (node.getBoard().win(HexBoard.opponent(color))) {
			return opponent;
		}

		if (node.numChildren() == 0) {
			return opponent;
		} else {
			int num = r.nextInt(node.numChildren());
			GameTree newNode = node.getChild(num);
			return opponent.play(newNode, this);
		}
	}

	public static void main(String s[]) {
		RandomPlayer h1 = new RandomPlayer(HexBoard.WHITE);
		HumanPlayer h2 = new HumanPlayer(HexBoard.BLACK);
		System.out.println(
			h1.play(new GameTree(new HexBoard(), HexBoard.WHITE), h2));
	}
}
