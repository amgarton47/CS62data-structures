package card;
import java.awt.Color;
import java.awt.geom.Rectangle2D;


public class GraphicalCard extends Rectangle2D.Double{
	private static final int HEIGHT_MULTIPLIER = 10;
	
	private Card card;
	
	public GraphicalCard(Card card, int x, int y, int width) {
		super(x, y, width, card.getNumber()*HEIGHT_MULTIPLIER);
		this.card = card;
	}
	
	public Color getColor() {
		if( card.getSuit().equals("hearts") ||
			card.getSuit().equals("diamonds") ) {
			return Color.RED;
		}else {
			return Color.BLACK;
		}
	}
	
	public Card getCard() {
		return card;
	}
}