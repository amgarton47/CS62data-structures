package darwin;

/**
 * This class includes the functions necessary to keep track of the creatures in
 * a two-dimensional world.
 */

public class World {
	private Matrix<Creature> creatures;

	private int w, h;

	/**
	 * This function creates a new world consisting of width columns and height
	 * rows, each of which is numbered beginning at 0. A newly created world
	 * contains no objects.
	 */
	public World(int w, int h) {
		this.w = w;
		this.h = h;
		creatures = new Matrix<Creature>(h, w);
	}

	/**
	 * Returns the height of the world.
	 */
	public int height() {
		return h;
	}

	/**
	 * Returns the width of the world.
	 */
	public int width() {
		return w;
	}

	/**
	 * Returns whether pos is in the world or not.
	 * 
	 * returns true *if* pos is an (x,y) location within the bounds of the board.
	 */
	public boolean inRange(Position pos) {
		return pos.getX() >= 0 && pos.getX() < w && pos.getY() >= 0 && pos.getY() < h;
	}

	/**
	 * Set a position on the board to contain e.
	 * 
	 * @throws IllegalArgumentException if pos is not in range
	 */
	public void set(Position pos, Creature e) {
		if (inRange(pos)) {
			creatures.set(pos.getY(), pos.getX(), e);
		} else {
			throw new IllegalArgumentException("Trying to set position of creature out of bounds");
		}
	}

	/**
	 * Return the contents of a position on the board.
	 * 
	 * @throws IllegalArgumentException if pos is not in range
	 */
	public Creature get(Position pos) {
		if (!inRange(pos)) {
			throw new IllegalArgumentException("Trying to set position of creature out of bounds");
		}

		System.out.println(pos.getX());
		System.out.println(pos.getY());
		return creatures.get(pos.getY(), pos.getX());
	}

}
