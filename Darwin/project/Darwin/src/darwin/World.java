package darwin;

/**
 * This class includes the functions necessary to keep track of the creatures in
 * a two-dimensional world. 
 */

public class World<E> {

	/**
	 * This function creates a new world consisting of height rows and width
	 * columns, each of which is numbered beginning at 0. A newly created world
	 * contains no objects.
	 */
	public World(int h, int w) {
	}

	/**
	 * Returns the height of the world.
	 */
	public int height() {
		return 0; // FIX
	}

	/**
	 * Returns the width of the world.
	 */
	public int width() {
		return 0; // FIX
	}

	/**
	 * Returns whether pos is in the world or not.
	 * 
	 * @post returns true if pos is an (x,y) location within the bounds of the
	 *       board.
	 */
	public boolean inRange(Position pos) {
		return false; // FIX
	}

	/**
	 * Set a position on the board to contain e.
	 * 
	 * @pre pos is in range - throws IllegalArgumentException otherwise
	 */
	public void set(Position pos, E e) {
		// FIX
	}

	/**
	 * Return the contents of a position on the board.
	 * 
	 * @pre pos is a in range - throws IllegalArgumentException otherwise
	 */
	public E get(Position pos) {
		return null; // FIX
	}

}
