package coinSort;

import java.awt.Color;
import javax.swing.JFrame;


public class SortableCoin extends Coin
                          implements Comparable<SortableCoin> {
    
    protected Color theColor;
    
    public SortableCoin(JFrame f, 
                        int diam,
                        int xCoord, int yCoord,
                        Color color) {
        super(f, diam, xCoord, yCoord);
        theColor = color;        
    }
    
    public Color getColor() {
        return theColor;
    }
    
    public void setColor(Color color) {
        theColor = color;
    }

    public int compareTo(SortableCoin other) {
        return diameter - other.diameter;
    }
    
}
