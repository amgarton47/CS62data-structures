/**
 * CS062: silverdollar.GraphicsCoinStrip
 *	a simple coin-moving game implemented with ArrayLists
 *
 * @author YOUR-NAME-HERE
 */
package silverdollar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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
	private static final int   SQUARE_SIZE = 120;
	private static final int   COIN_DIAMETER = SQUARE_SIZE / 2;
	private static final Color BACKGROUND_COLOR = Color.LIGHT_GRAY;
	private static final Color BOUNDARY_COLOR = Color.BLACK;
	private static final Color COIN_COLOR = Color.RED;

	private BufferedImage bf = new BufferedImage(DISPLAY_WIDTH, SQUARE_SIZE, 
												BufferedImage.TYPE_INT_RGB);
	private ArrayList<CoinSquare> strip;  // the arraylist of squares for the game
	private Coin movingCoin;          // the coin that is currently being dragged by the mouse
	
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
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(f.strip.size() * SQUARE_SIZE, SQUARE_SIZE);
		f.setVisible(true);
		CoinMouseListener listener = f.new CoinMouseListener(f);
		f.addMouseListener(listener);
		f.addMouseMotionListener(listener);
	}
	
	// TODO: add some comments here
	public GraphicsCoinStrip(int squares, int coins){		
		//TODO: add some code here!
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
			
			// if the cursor is in a square, and
			// there is a coin in that square, and
			// the cursor is actually within the
			// boundaries of the coin
			if (..) {
				movingCoin = strip.get(squareIndex).release();
				movingCoin.moveTo(newX, newY);
				origin = squareIndex;
				
			}else{
				movingCoin = null;
			}
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
			
			// if a coin is being dragged and
			// the cursor is within a square and
			// that square is the destination of 
			// a legal move
			if (...){
				strip.get(squareIndex).setCoin(movingCoin);
			}else{
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
		 * dragging a coin
		 * @post the selected coin, if there is one, is moved
		 */
		public void mouseDragged(MouseEvent event) {
			if (movingCoin != null)
				movingCoin.moveTo(event.getX(), event.getY());
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
