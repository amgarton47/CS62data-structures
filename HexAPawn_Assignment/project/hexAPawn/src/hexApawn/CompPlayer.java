package hexAPawn;

// TODO: Any useful imports to pick random choice

public class CompPlayer implements Player {

	// TODO: Any instance variables


	public CompPlayer(char color) {
		this.color = color;
	}

	public Player play(GameTree node, Player opponent) {

        return null; // TODO: Fix based on instructions below
        
        // TODO: Print current board

        // TODO: if the state is a winning state for the opponent or there aren't any moves:
			// trim tree rooted in node
				// go to node corresponding to parent
				// if parent is not null
					// find grandparent 
					// remove grandparent's child which corresponds to parent
					// print what you removed
				// else
					// print that the parent is null and that was nothing there for you to remove
            // return the opponent
        // else:    
        //    Pick a random GameTree child
        //    return opponent.play on the chosen random child with "this" player as the opponent

	}

	public String toString() {
		return color == HexBoard.BLACK ? "black" : "white";
	}

	public static void main(String s[]) {
		Scanner r = new Scanner(System.in);
		GameTree gt = new GameTree(new HexBoard(), HexBoard.WHITE);
		String ans = "yes";
		while (ans.equals( "yes")) {
			Player h1 = new CompPlayer(HexBoard.BLACK);
			Player h2 = new HumanPlayer(HexBoard.WHITE);
			System.out.println(h2 + " plays first.");
			System.out.println(h2.play(gt, h1) + " wins!");
			System.out.println("The tree now has size " + gt.size());
			System.out.println("Play again?  answer yes or no");
			ans = r.next().toLowerCase();
			System.out.println("Your answer was " + ans);
		}
		System.out.println("Thanks for playing!");
		r.close();
	}
}
