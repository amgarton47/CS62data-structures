/**
 * CS062: silverdollar.GraphicsCoinStrip
 *	a simple coin-moving game implemented with ArrayLists
 *
 * @author Dave (+ others)
 */
package silverdollar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import java.util.Random;
import java.util.ArrayList;

/**
 * A class for the graphical version of the Silver
 * Dollar Game
 * <p>
 * See Chapter 3 of Java Structures, by Duane Bailey.
 * We adapt (but do not inherit from) the methods of
 * the text-based version of the game. The class
 * is a specialization of JFrame.
 */
public class GraphicsCoinStrip extends JFrame {
	protected static final int   SQUARE_SIZE = 120;
	protected static final int   COIN_DIAMETER = SQUARE_SIZE / 2;
	protected static final Color BACKGROUND_COLOR = Color.LIGHT_GRAY;
	protected static final Color BOUNDARY_COLOR = Color.BLACK;
	protected static final Color COIN_COLOR = Color.RED;
	protected static final int DISPLAY_WIDTH = SQUARE_SIZE * 12;

	protected BufferedImage bf = new BufferedImage(DISPLAY_WIDTH, SQUARE_SIZE, 
			BufferedImage.TYPE_INT_RGB);
	protected ArrayList<CoinSquare> strip;  // the arraylist of squares for the game
	protected Coin movingCoin;          // the coin that is currently being dragged by the mouse
	protected int coins; // number of coins

	/**
	 * The main method simply creates GraphicsCoinStrip,
	 * initializes it, adds the mouse listeners, and 
	 * plays the game.
	 * 
	 * See the CS 62 handout on the graphics for an explanation of the methods used.
	 * 
	 * @param args command-line arguments (none
	 *        in this case)
	 */
	public static void main(String[] args) {
		GraphicsCoinStrip f = new GraphicsCoinStrip (12, 5);
	}

	/**
	 * Create a new coin strip with randomly placed coins
	 * 
	 * @param squares the number of squares for the strip
	 * @param coins the number of coins to place
	 */
	public GraphicsCoinStrip(int squares, int coins) {		
		if (0 > coins || coins >= squares) { // Check precondition
			System.out.println("Game must be played with number of\n"+
							   "coins less than number of squares");
			throw new IllegalArgumentException("# coins: "+coins+" must be positive "
											   +"and less than # squares: "+squares);
		}
		
		strip = new ArrayList<CoinSquare>(squares); 
		//populate all squares with false values
		for (int i = 0; i < squares; i++){ 
			strip.add(new CoinSquare(i, SQUARE_SIZE)); 
		} 
		
		this.coins = coins;

		//place #coins randomly on the strip
		Random rand = new Random ();
		
		while (0 < coins) { 
			int i = rand.nextInt (squares); 
			if (!strip.get(i).isOccupied()) { 
				strip.get(i).setCoin(new Coin(this, COIN_DIAMETER)); 
				coins--; 
			} 
		}
		// --- only add code above here in this method ---
		CoinMouseListener listener = new CoinMouseListener();
		addMouseListener(listener);
		addMouseMotionListener(listener);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(strip.size() * SQUARE_SIZE, SQUARE_SIZE);
		setVisible(true);  // this should always be at the end of the constructor
	}
	
	/** 
	 * gameIsOver determines if a game is completed.
	 * 
	 * @return true if there are no more moves 
	 */ 
	public boolean gameIsOver() {
		//check if all the coins are in the first few squares
		for (int i = 0; i < coins; i++) {
			if (!this.strip.get(i).isOccupied()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * The paint method is inherited from JFrame.
	 * 
	 * It is called automatically or in response to
	 * an invocation of repaint(). It simply refreshes
	 * the screen by first laying down the background
	 * color, then outlining the squares, and finally
	 * drawing the coins.
	 * 
	 * @param g a Graphics object that is supplied by
	 *        the (unknown to us) caller
	 */
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) bf.getGraphics();;

		g2.setPaint(BACKGROUND_COLOR);

		for (int i = 0; i < strip.size(); i++) {
			g2.fill(strip.get(i));
		}

		g2.setPaint(BOUNDARY_COLOR);

		for (int i = 0; i < strip.size(); i++) {
			g2.draw(strip.get(i));
		}

		if (gameIsOver()) {
			//winning: coin color change
			g2.setPaint(Color.GREEN);
			System.out.println("Game over");
		}else {
			g2.setPaint(COIN_COLOR);
		}
		
		for (int i = 0; i < strip.size(); i++) {
			if (strip.get(i).isOccupied()) {
				g2.fill(strip.get(i).getCoin());
			}
		}

		if (movingCoin != null) {
			g2.fill(movingCoin);
		}	

		g.drawImage(bf,0,0,null);	
	}

	/**
	 * Given an x, y location, return the index of the CoinSquare within "strip"
	 * that the location occurs.  Return -1 if the x, y location does not occur
	 * in any CoinSquare within "strip"
	 *
	 * @param x
	 * @param y
	 * @return the index within the strip
	 */
	private int getCoinSquareIndex(int x, int y) {

		for( int i = 0; i < strip.size(); i++ ){
			if( strip.get(i).contains(x, y) ){
				return i;
			}
		}
		return -1;
	}

	/**
	 * An inner class to respond to mouse events.
	 */
	public class CoinMouseListener implements MouseListener, MouseMotionListener {
		private int origin;	// the index of the square when a coin is picked up

		/**
		 * Method mousePressed is called when the mouse is
		 * clicked.
		 * 
		 * For this application, pressing the mouse button
		 * picks up a coin if the cursor is on a coin, and
		 * that coin is dragged until the mouse is released.
		 */
		public void mousePressed(MouseEvent event) {
			int newX = event.getX();
			int newY = event.getY();

			if (clickedOnCoin(newX, newY)) {
				int squareIndex = getCoinSquareIndex(newX, newY);
				movingCoin = strip.get(squareIndex).release();
				movingCoin.moveTo(newX, newY);
				origin = squareIndex;

			} else {
				movingCoin = null;
			}
		}

		/**
		 * Determines if an x, y location contains a coin.
		 * 
		 * @param x
		 * @param y
		 * @return true if a coin is at location x, y, false otherwise
		 */
		private boolean clickedOnCoin(int x, int y) {
			int squareIndex = getCoinSquareIndex(x, y);

			return squareIndex != -1 &&
					strip.get(squareIndex).isOccupied() &&
					strip.get(squareIndex).getCoin().contains(x, y); 
		}

		/**
		 * Method mouseReleased is called when the mouse
		 * button is released.
		 * 
		 * If a coin is released in a square, and that square
		 * is not already occupied and the move satisfies
		 * the rules of the game, then the coin is placed
		 * into the square. Otherwise, the coin is returned
		 * to its original square. 
		 */
		public void mouseReleased(MouseEvent event) {
			int newX = event.getX();
			int newY = event.getY();

			if (isValidCoinDrop(newX, newY)) {
				int squareIndex = getCoinSquareIndex(newX, newY);
				strip.get(squareIndex).setCoin(movingCoin);
			} else {
				if( movingCoin != null ){
					strip.get(origin).setCoin(movingCoin);
				}
			}

			// Give up the coin, if any, that was
			// dragged; it has now been returned to
			// some square.
			movingCoin = null;
		}

		/**
		 * Returns true if we are currently moving a coin and
		 * the x, y location represents a valid location on the coin
		 * strip to drop that coin based on the contents of the strip
		 * and the rules of the game.
		 * 
		 * @param x
		 * @param y
		 * @return true if we are moving a coin and this is a valid location to drop it
		 */
		private boolean isValidCoinDrop(int x, int y) {
			int squareIndex = getCoinSquareIndex(x, y);

			return movingCoin != null && squareIndex != -1 && isLegalMove(origin, squareIndex);
		}

		/**
		 * Return whether it is valid to move a coin from start to end
		 * 
		 * @param start the starting index of the coin
		 * @param end the ending index of the coin
		 * @return
		 */
		private boolean isLegalMove(int start, int end) {	
			if( start <= end ){
				return false;
			}else{
				boolean legal = true;

				// check to make sure there isn't a coin within distance moves
				for( int i = end; i < start && legal; i++ ){
					if( strip.get(i).isOccupied() ){
						legal = false;
					}
				}

				return legal;
			}
		}
		/**
		 * dragging a coin
		 * @post the selected coin, if there is one, is moved
		 */
		public void mouseDragged(MouseEvent event) {
			if (movingCoin != null){
				movingCoin.moveTo(event.getX(), event.getY());
			}

			repaint();
		}

		/*
		 * These methods are required by the interfaces.
		 * For this program, they do nothing.
		 */
		public void mouseClicked(MouseEvent event) {}
		public void mouseEntered(MouseEvent event) {}
		public void mouseExited(MouseEvent event) {}
		public void mouseMoved(MouseEvent event) {}
	}
}
