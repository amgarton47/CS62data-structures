package hexAPawn;

// TODO: Any useful imports to read input from user

public class HumanPlayer implements Player {

	// TODO: Any instance variables


	public HumanPlayer(char color) {
		this.color = color;
	}

	public Player play(GameTree node, Player opponent) {

        return null; // TODO: Fix based on instructions below

        // TODO: Print current board

        // TODO: if the state is a winning state for the opponent or there aren't any moves:
            //    return the opponent
        // else:    
        //    Print all available moves in the form of "1: Move from [1,1] to [2,1]." etc.
        //    Ask the user for which move they have chosen
        //    Find the GameTree child that corresponds to the chosen move (be careful if you have printed the moves starting at 1) 
        //    return opponent.play on the chosen child with "this" player as the opponent

	}

	public String toString() {
		return color == HexBoard.BLACK ? "black" : "white";
	}

	public static void main(String s[]) {
		HumanPlayer h1 = new HumanPlayer(HexBoard.WHITE);
		HumanPlayer h2 = new HumanPlayer(HexBoard.BLACK);
		System.out.println(
			h1.play(new GameTree(new HexBoard(), HexBoard.WHITE), h2)+" wins");
	}
}
