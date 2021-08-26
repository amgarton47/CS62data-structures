package hexAPawn;

// TODO: Any useful imports to pick random choice

public class RandPlayer implements Player {

	// TODO: Any instance variables


	public RandPlayer(char color) {
		this.color = color;
	}

	public Player play(GameTree node, Player opponent) {

        return null; // TODO: Fix based on instructions below
        
        // TODO: Print current board

        // TODO: if the state is a winning state for the opponent or there aren't any moves:
            //    return the opponent
        // else:    
        //    Pick a random GameTree child
        //    return opponent.play on the chosen random child with "this" player as the opponent

	}

	public String toString() {
		return color == HexBoard.BLACK ? "black" : "white";
	}

	public static void main(String s[]) {
		RandPlayer h1 = new RandPlayer(HexBoard.WHITE);
		HumanPlayer h2 = new HumanPlayer(HexBoard.BLACK);
		System.out.println(
			h1.play(new GameTree(new HexBoard(), HexBoard.WHITE), h2)+" wins");
	}
}
