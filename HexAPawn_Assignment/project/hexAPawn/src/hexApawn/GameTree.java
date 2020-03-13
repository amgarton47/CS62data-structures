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
	// TODO create instance variables
	
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
	 * recursive constructor (for moves and positions)
	 * 
	 * Generate a GameTree that includes the transitive closure
	 * of all possible moves originating from this position,
	 * given who's turn it is.
	 * 
	 * @param board	for this position
	 * @param color	of the player who gets next move
	 * @param move HexMove that gets us from our parent to this position
	 * @param parent GameTree for our parent
	 */
	public GameTree(HexBoard board, char color, HexMove m, GameTree parent) {
		// TODO enumerate all children (possible moves) of this position
		// TODO (recursively) generate the GameTrees for those moves/positions
		//		recursion ends when no move is possible (opponent won)
	}

	/**
	 * @return number of possible moves from this position
	 */
	public int numChildren() {
		return 0;	// TODO implement numChildren
	}
	
	/**
	 * @param child index number
	 * @return GameTree for our n'th child
	 */
	public GameTree getChild(int child) {
		return null;	// TODO implement getChild
	}
	
	/**
	 * @return total number of positions in this (sub)tree
	 */
	public int size() {
		return 1;	// TODO implement size
	}
	
	// TODO identify and implement other needed GameTree methods
	
	/**
	 * string representation of the board (for this position)
	 */
	public String toString() {
		return "FIXME";		// TODO implement toString
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