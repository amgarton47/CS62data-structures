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
 *
 * @author RettBull
 * @date January 30, 2008
 * 
 * @author David Kauchak
 * @date January 12, 2010
 */
public class GraphicsCoinStrip extends JFrame {
	private static final int   SQUARE_SIZE = 120;  // size of squares on screen
	private static final int   COIN_DIAMETER = SQUARE_SIZE / 2; // diameter of coin
	private static final Color BACKGROUND_COLOR = Color.LIGHT_GRAY;  // colors used
	private static final Color BOUNDARY_COLOR = Color.BLACK;
	private static final Color COIN_COLOR = Color.RED;

	private ArrayList<CoinSquare> strip; // the vector of squares for the game
	private Coin movingCoin;             // the coin that is currently being
										// dragged by the mouse
	private int coins;                   // number of coins in the game
	/**
	 * The main method simply creates GraphicsCoinStrip,
	 * initializes it, adds the mouse listeners, and 
	 * plays the game.
	 * 
	 * See the CS 62 handout from January 25 for a brief
	 * explanation of the methods used.
	 * 
	 * @param args command-line arguments (none
	 *        in this case)
	 */
	public static void main(String[] args) {
		GraphicsCoinStrip f = new GraphicsCoinStrip (12, 5);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(f.strip.size() * SQUARE_SIZE, SQUARE_SIZE);
		f.setVisible(true);
		CoinMouseListener listener = f.new CoinMouseListener();
		f.addMouseListener(listener);
		f.addMouseMotionListener(listener);
	}
	
	public GraphicsCoinStrip(int squares, int coins){		
		assert 0 < coins && coins < squares; 
		
		this.coins = coins;
		
		strip = new ArrayList<CoinSquare>(squares); 
		
		for (int i = 0; i < squares; i++){ 
			strip.add(new CoinSquare(i, SQUARE_SIZE)); 
		}
		
		Random rand = new Random (); 
		
		while (0 < coins) { 
			int i = rand.nextInt(squares);  
			
			if (!strip.get(i).isOccupied()){
				Coin coin = new Coin(this, COIN_DIAMETER);
				strip.get(i).setCoin(coin);
				coins--;
			}
		}
	}
	
	BufferedImage bf = new BufferedImage( 12 * SQUARE_SIZE, SQUARE_SIZE, 
			BufferedImage.TYPE_INT_RGB);
	
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
		Graphics g1 = bf.getGraphics();
		Graphics2D g2 = (Graphics2D) g1;

		g2.setPaint(BACKGROUND_COLOR);
		
		for (int i = 0; i < strip.size(); i++){
			g2.fill(strip.get(i));
		}

		g2.setPaint(BOUNDARY_COLOR);
		
		for (int i = 0; i < strip.size(); i++){
			g2.draw(strip.get(i));
		}

		g2.setPaint(COIN_COLOR);
		for (int i = 0; i < strip.size(); i++){
			if (strip.get(i).isOccupied()){
				g2.fill(strip.get(i).getCoin());
			}
		}

		if (movingCoin != null){
			g2.fill(movingCoin);
		}	
		
		g.drawImage(bf,0,0,null);
	}
		
	/**
	 * An inner class to respond to mouse events.
	 */
	private class CoinMouseListener implements MouseListener,
								MouseMotionListener {
		private int origin;  // the index of the square from
		// which the wanderer coin came
//		private GraphicsCoinStrip s;
		
//		public CoinMouseListener(GraphicsCoinStrip s){
//			this.s = s;
//		}
		
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
			
			// see if we clicked in a square
			int foundIndex = getIndex(newX, newY);
			
			if (strip.get(foundIndex).isOccupied() &&
				strip.get(foundIndex).getCoin().contains(newX, newY)) {
				// if the cursor is in a square, and
				// there is a coin in that square, and
				// the cursor is actually within the
				// boundaries of the coin
				movingCoin = strip.get(foundIndex).release();
				movingCoin.moveTo(newX, newY);
				origin = foundIndex;
			}else{
				movingCoin = null;
			}
		}

		/**
		 * Returns the index of the CoinSquare in which the x and y coordinates reside or -1
		 * the coordinates are not within any CoinSquare
		 * 
		 * @param x x location of the coordinate
		 * @param y y location of the coordinate
		 * @return see description
		 */
		private int getIndex(int x, int y){
			int foundIndex = -1;
			
			for( int i = 0; i < strip.size() && foundIndex == -1; i++ ){
				if( strip.get(i).contains(x, y) ){
					foundIndex = i;
				}
			}
			
			return foundIndex;
		}
		
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
		 * gameIsOver determines if a game is completed. 
		 * 
		 * @return true if there are no more moves 
		 */ 
		private boolean gameIsOver() { 
			boolean over = true;
			
			for( int i = 0; i < coins && over; i++ ){
				// check if the strip contains any non-coins in the
				// beginning
				if( !strip.get(i).isOccupied() ){
					over = false;
				}
			}
			
			return over;
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
			
			int square = getIndex(newX, newY);
			
			if ( movingCoin != null &&
				 square != -1 &&
				 isLegalMove(origin, square) ){
				// if a coin is being dragged and
				// the cursor is within a square and
				// that square is the destination of 
				// a legal move
				strip.get(square).setCoin(movingCoin);
			}else{
				if( movingCoin != null ){
					strip.get(origin).setCoin(movingCoin);
				}
			}

			// Give up the coin, if any, that was
			// dragged; it has now been returned to
			// some square.
			movingCoin = null;
			
			if( gameIsOver() ){
				System.out.println("Game over");
			}
		}


		/**
		 * dragging a coin
		 * @post the selected coin, if there is one, is moved
		 */
		public void mouseDragged(MouseEvent event) {
			if (movingCoin != null) {
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
