package card;
import java.awt.Color;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;


public class MoveableCard extends GraphicalCard{
	private JFrame frame;
	
	public MoveableCard(JFrame frame, Card card, int x, int y, int width) {
		super(card, x, y, width);
		this.frame = frame;
	}
	
	public void moveCard(int x, int y) {
		setFrame(x, y, this.width, this.height); // inherited method
		frame.repaint();
	}
}