package card;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JFrame;

public class MoveableCardDisplay extends JFrame{
	private static final int X = 10;
	private static final int Y = 50;
	private static final int WIDTH = 40;
	private static final int SPACING = 10;
	private static final int NUM_CARDS = 9;
	
	private static final Color BACKGROUND_COLOR = Color.WHITE;

	private Rectangle2D.Double background;
	private ArrayList<MoveableCard> cards = new ArrayList<MoveableCard>();

	public MoveableCardDisplay() {
		super("Moving cards");
		
		CardDealer dealer = new CardDealer(1);
		
		for( int i = 0; i < NUM_CARDS; i++ ) {
			Card nextCard = dealer.next();
			MoveableCard card = new MoveableCard(this, nextCard, X + (WIDTH + SPACING) * i , Y, WIDTH );
			cards.add(card);
		}
		
		background = new Rectangle2D.Double(0, 0, 500, 300);
						
		setSize(500, 300);
		setLocation(100, 100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		CardListener listener = new CardListener();
		addMouseListener(listener);
		addMouseMotionListener(listener);
		
		setVisible(true); // this should happen at the end of the constructor
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setPaint(BACKGROUND_COLOR);;
		g2.fill(background);
		
		for( MoveableCard card: cards ) {
			g2.setPaint(card.getColor());
			g2.fill(card);
		}
	}
	
	private class CardListener implements MouseListener, MouseMotionListener{
		private MoveableCard grabbed;
		private int originalx;
		private int originaly;
		
		private MoveableCard getCard(int x, int y) {
			MoveableCard found = null;  // returns null if we don't find one
			
			for( int i = 0; i < cards.size(); i++ ) {
				MoveableCard card = cards.get(i);
				
				if( card.contains(x, y) ) {
					found = card;
				}
			}
			
			return found;
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			// check if we've clicked on any of the cards
			int x = e.getX();
			int y = e.getY();
			
			grabbed = getCard(x, y);
			
			if( grabbed != null ) {
				// remember where we started
				originalx = (int)grabbed.getX();
				originaly = (int)grabbed.getY();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {			
			if( grabbed != null ) {			
				// see if we dropped it over another card
				int x = e.getX();
				int y = e.getY();
				
				MoveableCard dropped = getCard(x, y);
				
				if( dropped == null ) {
					// we didn't drop it on another card, so reset
					grabbed.moveCard(originalx, originaly);
				}else {
					// swap the cards
					int newx = (int)dropped.getX();
					int newy = (int)dropped.getY();
					
					dropped.moveCard(originalx, originaly);
					grabbed.moveCard(newx, newy);
				}
				
				grabbed = null;
			}
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			
			if( grabbed != null ) {
				grabbed.moveCard(x, y);
			}
			
			repaint();
		}

		@Override
		public void mouseMoved(MouseEvent e) {			
		}

		@Override
		public void mouseClicked(MouseEvent e) {			
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {			
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}
	
	public static void main(String[] args) {
		new MoveableCardDisplay();
	}
}
