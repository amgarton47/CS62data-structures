package darwin;

/**
 * This class includes the functions necessary to keep track of the creatures in
 * a two-dimensional world. 
 */


public class World<E> {
	Matrix<E> board;

	/**
	 * This function creates a new world consisting of width columns and height
	 * rows, each of which is numbered beginning at 0. A newly created world
	 * contains no objects.
	 */
	public World(int w, int h) {
		board = new Matrix<E>(h, w);
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
	 * Set a position on the board to contain e. 
	 * @pre pos is in range - throws IllegalArgumentException otherwise
	 */
	public void set(Position pos, E e) {
		if(inRange(pos))	
			board.set(pos.getY(), pos.getX(), e);
		else
			throw new IllegalArgumentException("Illegal pos position");
	}

	/**
	 * Return the contents of a position on the board. 
	 * @pre pos is a in range - throws IllegalArgumentException otherwise
	 */
	public E get(Position pos) {
		if(inRange(pos))
			return board.get(pos.getY(), pos.getX());
		else
			throw new IllegalArgumentException("Illegal pos position");
	}

}
