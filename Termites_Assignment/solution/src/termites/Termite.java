import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Termite extends Thread {

	private int row, col;
	private Random motionGen;
	private Cell world[][];
	private TermiteWorld drawing;
	private boolean hasAChip = false;
	private Rectangle2D.Double myChip = null;
	
	public Termite(int row, int col, Cell[][] world, TermiteWorld tw) {
		this.world = world;
		this.row = row;
		this.col = col;
		motionGen = new Random();
		drawing = tw;
	}

	// move one cell over from current position and pause
	private void move() {
		int movement;

		movement = motionGen.nextInt(3) - 1;
		row = (row + movement + TermiteWorld.WORLDSIZE) % TermiteWorld.WORLDSIZE;
		movement = motionGen.nextInt(3) - 1;
		col = (col + movement + TermiteWorld.WORLDSIZE) % TermiteWorld.WORLDSIZE;
		drawing.repaint();
		try{
			sleep (30);
		}catch (InterruptedException exc) {
			System.out.println("sleep interrupted!");
		}
	}

	// keep moving until find a position with a chip
	private void findAChip() {
		while (world[row][col].isEmpty())
			move();
	}

	// keep moving until find a position with no chip
	private void findOpenSpace() {
		while (!world[row][col].isEmpty())
			move();
	}

	//If find a chip, pick it up and drop it next to another chip
	public void run() {

		while (true) {
			findAChip();
			hasAChip = false;
			try{
				sleep (30);
			}catch (InterruptedException exc) {
				System.out.println("sleep interrupted!");
			}

			synchronized(world[row][col]) {
				if (!world[row][col].isEmpty()){
					myChip = world[row][col].takeChip();
					hasAChip = true;
					drawing.repaint();
				}
			}

			while(hasAChip) {
				findAChip();
				findOpenSpace();
				synchronized(world[row][col]) {
					if(world[row][col].isEmpty()) {
						world[row][col].putChip(myChip);
						hasAChip = false;
						drawing.repaint();
					}
				}
			}
			findOpenSpace();

		}
	}
	
	public boolean gotOne() {
		return hasAChip;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}

}