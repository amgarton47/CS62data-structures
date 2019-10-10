package termites;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Termite extends Thread {
	// current row and column of termite
	private int row, col;
	
	// Random number generatore to determine how termite wanders
	private Random motionGen;
	
	// World containing all of the cells to be displayed
	private Cell world[][];
	
	// Class responsible for writing paint method for repainting the screen
	private TermiteWorld drawing;
	
	// True if termite is carrying around a wood chip.
	private boolean hasAChip = false;
	
	// The wood chip carried by the termite if it has one, null otherwise
	private Rectangle2D.Double myChip = null;
	
	/**
	 * Construct a termite 
	 * @param row
	 * 		row where termite starts
	 * @param col
	 * 		column where termite starts
	 * @param world
	 * 		world of cells where chips are
	 * @param tw
	 * 		object responsible for repainting canvas
	 */
	public Termite(int row, int col, Cell[][] world, TermiteWorld tw) {
		this.world = world;
		this.row = row;
		this.col = col;
		motionGen = new Random();
		drawing = tw;
	}

	/**
	 * move one cell over in a random direction from current position and pause
	 * Can move up, down, left, right, in diagonal directions or stay in the
	 * same spot.
	 */
	private void move() {
		// INSERT CODE TO MAKE TERMITE MOVE ONE STEP
		
		drawing.repaint();
		try{
			sleep (30);
		}catch (InterruptedException exc) {
			System.out.println("sleep interrupted!");
		}
	}

	public void run() {


	}
		
	/**
	 * @return true iff termite is carrying a wood chip
	 */
	public boolean gotOne() {
		return hasAChip;
	}

	/**
	 * @return row where termite is currently
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * @return column where termite is currently
	 */
	public int getCol() {
		return col;
	}

}