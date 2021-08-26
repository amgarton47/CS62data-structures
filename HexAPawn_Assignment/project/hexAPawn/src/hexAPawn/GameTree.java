package hexAPawn;

/**
 * A GameTree is a tree of HexBoard positions.
 * 
 * The children of a position are all of the positions that could
 * result from possible moves from that position.  Knowing what
 * positions are possible can enable us to choose the best.
 * 
 * @author YOUR NAME HERE
 */
public class GameTree {
	// TODO create instance variables corresponding to GameTree parent, a list of GameTree children, and useful instance variable to store data passed by the constructors.
	
	/**
	 * primary constructor (for new GameTree)
	 * 
	 * @param board with which this tree starts
	 * @param color of the player who gets next move
	 */
	public GameTree(HexBoard board, char color) {
		this(board, color, null, null);
	}
	
	/**
	 * alternative constructor (for moves and positions)
	 * 
	 * Generate a GameTree that generates all possible moves originating from this position,
	 * given whose turn it is.
	 * 
	 * @param board	for this position
	 * @param color	of the player who gets next move
	 * @param move HexMove that gets us from our parent to this position
	 * @param parent GameTree for our parent
	 */
	public GameTree(HexBoard board, char color, HexMove m, GameTree parent) {
		// TODO instantiate appropriate instance and local variables

		// TODO If the opponent has not won
			// TODO Iterate over the possible moves that can occur given the current board
				// TODO Create a new board for each of the available moves
				// TODO Add to the list of children GameTrees a new GameTree with the newly-created board, the opponent's color, the move that brought you here, and this GameTree as a parent 
	}

	/**
	 * @return number of possible moves from this position
	 */
	public int numChildren() {
		return 0;	// TODO implement numChildren by reusing the size method 
	}
	
	/**
	 * @param i index number
	 * @return GameTree for our i'th child
	 */
	public GameTree getChild(int i) {
		return null;	// TODO implement getChild so that it returns the i-th child from the 0-index list
	}
	
	/**
	 * @return total number of positions in this (sub) GameTree
	 */
	public int size() {
		return 1;	// TODO implement size so that it cumulatively adds up the current GameTree and the nodes of all its children
	}
	
	// TODO identify and implement other needed GameTree methods
	
	/**
	 * string representation of the board (for this position)
	 */
	public String toString() {
		return "FIXME";		// TODO implement toString so that it returns the current's board representation
	}
	
	/**
	 * Simple test program for GameTree class.
	 */
	public static void main(String args[]) {
		// 1. Instantiate a new (initial positions) board
		HexBoard b = new HexBoard(3,3);
		
		// 2. generate GameTree of all possible games
		GameTree t = new GameTree(b, HexBoard.WHITE);
		
		// 3. count the number of possible moves/positions
		int nodes = t.size();
		
		// print out the result
		System.out.println("Initial position:");
		System.out.println(t);
		System.out.println("Size of Tree: " + nodes + 
					((nodes == 252) ? " (Correct)" : " (incorrect)"));
	}
}
