
import java.util.ArrayList;

class GameTree {

	protected GameTree parent;
	protected HexMove move;
	protected HexBoard board;
	protected ArrayList<GameTree> children;

	/**
	 * Construct a board with starting color
	 * @param board
	 * @param color
	 */
	public GameTree(HexBoard board, char color) {
		this(board, color, null, null);
	}

	public GameTree(HexBoard board, char color, HexMove m, GameTree parent) {
		this.board = board;
		this.move = m;
		this.parent = parent;
		children = new ArrayList<GameTree>();
		if (!board.win(HexBoard.opponent(color))) { 
			ArrayList<HexMove> moves = board.moves(color);
			for (int i = 0; i < moves.size(); i++) {
				HexBoard newBoard = new HexBoard(board,moves.get(i));
				children.add(new GameTree(newBoard,
						HexBoard.opponent(color),
						moves.get(i),
						this));
			}
		}
	}

	public int numChildren() {
		return children.size();
	}


	public GameTree getChild(int i) {
		return children.get(i);
	}

	public void remove(GameTree o) {
		children.remove(o);
	}
	
	public HexMove getMove() {
		return move;
	}

	public GameTree getParent() {
		return parent;
	}

	public HexBoard getBoard() {
		return board;
	}

	public int size() {
		int num = 1;
		for (int i = 0; i < numChildren(); i++) {
			num += getChild(i).size();
		}
		return num;
	}
	
	public String toString() {
		return board.toString();
	}

	public static void main(String s[]) {
		HexBoard b = new HexBoard(3,3);
		GameTree t = new GameTree(b, HexBoard.WHITE);
		System.out.println(t.size());
	}
}
