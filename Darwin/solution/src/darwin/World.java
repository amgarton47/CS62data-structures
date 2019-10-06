
/**
 * This module includes the functions necessary to keep track of the creatures
 * in a two-dimensional world. In order for the design to be general, the
 * interface adopts the following design:
 * <p>1. The contents are represented by type variable E.
 * <p>2. The dimensions of the world array are specified by the client.
 * <p>
 * There are many ways to implement this structure. HINT: look at the
 * structure.Matrix class. You should need to add no more than about ten lines
 * of code to this file.
 */

public class World {
	Matrix<Creature> board;

	/**
	 * This function creates a new world consisting of width columns and height
	 * rows, each of which is numbered beginning at 0. A newly created world
	 * contains no objects.
	 */
	public World(int w, int h) {
		board = new Matrix<Creature>(h, w);
	}

	/**
	 * Returns the height of the world.
	 */
	public int height() {
		return board.numRows();
	}

	/**
	 * Returns the width of the world.
	 */
	public int width() {
		return board.numCols();
	}

	/**
	 * Returns whether pos is in the world or not. 
	 * @pre pos is a non-null position. 
	 * @post returns true if pos is an (x,y) location in the bounds of
	 * the board.
	 */
	boolean inRange(Position pos) {
		return 0 <= pos.getX()
			&& pos.getX() < width()
			&& 0 <= pos.getY()
			&& pos.getY() < height();
	}

	/**
	 * Set a position on the board to contain c. 
	 * @pre pos is a non-null position
	 * on the board.
	 */
	public void set(Position pos, Creature c) {
		board.set(pos.getY(), pos.getX(), c);
	}

	/**
	 * Return the contents of a position on the board. 
	 * @pre pos is a non-null position on the board.
	 */
	public Creature get(Position pos) {
		return board.get(pos.getY(), pos.getX());
	}

}
