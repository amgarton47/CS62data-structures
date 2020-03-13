import java.util.Scanner;


public class HumanPlayer implements Player {
	// make sure your constructor accepts a char (HexBoard.WHITE or
	// HexBoard.BLACK) to play with. This should be remembered.

	char color;
	Scanner r = new Scanner(System.in);

	public String toString() {
		return color == HexBoard.BLACK ? "black" : "white";
	}

	public HumanPlayer(char color) {
		this.color = color;
	}

	public Player play(GameTree node, Player opponent) {
		System.out.println(node.getBoard());
		if (node.getBoard().win(HexBoard.opponent(color))) {
			return opponent;
		}

		if (node.numChildren() == 0) {
			System.out.println("no moves...");
			return opponent;
		} else {
			for (int i = 0; i < node.numChildren(); i++) {
				System.out.println((i + 1) + ": " + node.getChild(i).getMove());
			}
			System.out.print("> ");

			int num = r.nextInt();
			GameTree newNode = node.getChild(num - 1);

			return opponent.play(newNode, this);

		}
	}

	public static void main(String s[]) {
		HumanPlayer h1 = new HumanPlayer(HexBoard.WHITE);
		HumanPlayer h2 = new HumanPlayer(HexBoard.BLACK);
		System.out.println(
			h1.play(new GameTree(new HexBoard(), HexBoard.WHITE), h2)+" wins");
	}
}
