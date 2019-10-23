package coinSort;

import java.awt.geom.Rectangle2D;

/**
 * A Square class, for the Silver Dollar Game
 * <p>
 * A Square is one element of a coin strip. There are methods to add, query, and remove coins from Squares.
 * <p>
 * No graphics are necessary. However, the position of the square in the strip
 * is necessary so that we can inform the coin where to move.
 * 
 */
public class Square extends Rectangle2D.Double {

	protected Coin occupant; // the coin if there is one;
	// null otherwise
	protected int index; // the position of the square in the
	// strip
	protected int size; // the size of one side of the square

	/**
	 * @post creates a Square
	 * @param i
	 *            the index in the strip of squares
	 * @param s
	 *            the size of a side of the square
	 * 
	 */
	public Square(int i, int s) {
		super(i * s, 0, s, s);
		occupant = null;
		index = i;
		size = s;
	}

	/**
	 * @return true if there is a coin in the square
	 */
	public boolean isOccupied() {
		return occupant != null;
	}

	/**
	 * @pre there is no coin in the square
	 * @post the coin is in the square
	 * @param coin
	 *            the coin
	 */
	public void occupy(Coin coin) {
		assert occupant == null : "occupy: square is already occupied";
		occupant = coin;
		coin.moveTo(index * size + size / 2, size / 2);
	}

	/**
	 * @pre there is a coin in the square
	 * @post removes a coin
	 * @return the coin that was removed
	 */
	public Coin release() {
		assert occupant != null : "release: square is not occupied";
		Coin coin = occupant;
		occupant = null;
		return coin;
	}

	/**
	 * @return a reference to the coin in the square; may be null
	 */
	public Coin theCoin() {
		return occupant;
	}
}
