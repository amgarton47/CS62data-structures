package coinSort;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.List;

public class CoinSorter extends JFrame {

	protected static final int SQUARE_SIZE = 100;
	protected static final int COIN_DIAMETER = SQUARE_SIZE / 2;
	protected static final Color BACKGROUND_COLOR = Color.LIGHT_GRAY;
	protected static final Color BOUNDARY_COLOR = Color.BLACK;
	protected static final Color COIN_COLOR = Color.RED;
	protected static final Color SWAP_COLOR = Color.GREEN;
	protected static final Color COMPARE_COLOR = Color.YELLOW;
	protected static final Color FINAL_COLOR = COIN_COLOR; // Color.BLACK;
	protected static final Color PIVOT_COLOR = COIN_COLOR; // Color.BLUE;
	protected static final int PAUSE_DELAY = 300;
	protected static final int SHORT_PAUSE_DELAY = PAUSE_DELAY / 5;

	protected List<Square> strip; // the squares for the coins
	protected Freezer freezer; // object on which to synchronize

	// keystrokes

	public CoinSorter(int numCoins, Freezer f) {
		strip = new ArrayList<Square>();
		int baseDiameter = COIN_DIAMETER / (numCoins + 2);
		for (int i = 0; i < numCoins; i++) {
			SortableCoin c = new SortableCoin(this, baseDiameter * (i + 2), 0,
					0, COIN_COLOR);
			Square s = new Square(i, SQUARE_SIZE);
			s.occupy(c);
			strip.add(s);
		}

		freezer = f;
		freezer.permit();
	}

	public CoinSorter(Freezer f) {
		this(12, f);
	}

	public void randomize() {
		Random rand = new Random();
		int count = 20;
		for (int i = 0; i < strip.size(); i++)
			coinAt(i).setColor(COIN_COLOR);
		while (0 < count) {
			int i = rand.nextInt(strip.size());
			int j = rand.nextInt(strip.size());
			if (i != j) {
				swap(i, j, SHORT_PAUSE_DELAY);
				count--;
			}
		}
	}

	protected void swap(int i, int j, long pauseLength) {
		assert 0 <= i && i < strip.size() : "swap: index i out of bounds";
		assert 0 <= j && j < strip.size() : "swap: index j out of bounds";
		SortableCoin tempI = coinAt(i);
		SortableCoin tempJ = coinAt(j);
		Color colorI = tempI.getColor();
		Color colorJ = tempJ.getColor();
		tempI.setColor(SWAP_COLOR);
		tempJ.setColor(SWAP_COLOR);
		repaint();
		pause(pauseLength / 2);
		if (i != j) {
			coinRelease(i);
			coinRelease(j);
			coinOccupy(i, tempJ);
			coinOccupy(j, tempI);
			repaint();
		}
		pause(pauseLength / 2);
		freezer.check();
		tempI.setColor(colorI);
		tempJ.setColor(colorJ);
		repaint();
		pause(pauseLength / 2);
	}

	protected int compare(int i, int j, long pauseLength) {
		assert 0 <= i && i < strip.size() : "compare: index i out of bounds";
		assert 0 <= j && j < strip.size() : "compare: index j out of bounds";
		SortableCoin tempI = coinAt(i);
		SortableCoin tempJ = coinAt(j);
		Color colorI = tempI.getColor();
		Color colorJ = tempJ.getColor();
		tempI.setColor(COMPARE_COLOR);
		tempJ.setColor(COMPARE_COLOR);
		repaint();
		pause(pauseLength);
		freezer.check();
		tempI.setColor(colorI);
		tempJ.setColor(colorJ);
		repaint();
		return tempI.compareTo(tempJ);
	}

	protected SortableCoin coinAt(int i) {
		assert 0 <= i && i < strip.size() : "coinAt: index i out of bounds";
		return (SortableCoin) strip.get(i).theCoin();
	}

	protected SortableCoin coinRelease(int i) {
		assert 0 <= i && i < strip.size() : "coinRelease: index i out of bounds";
		return (SortableCoin) strip.get(i).release();
	}

	protected void coinOccupy(int i, SortableCoin coin) {
		assert 0 <= i && i < strip.size() : "coinOccupy: index i out of bounds";
		strip.get(i).occupy(coin);
	}

	protected void pause(long delay) {
		try {
            java.lang.Thread.sleep(delay);
        } catch (InterruptedException e) {
            // do nothing
        }
	}

	public void bubbleSort() {
		int numSorted = 0;
		int index;
		while (numSorted < strip.size()) {
			for (index = 1; index < strip.size() - numSorted; index++)
				if (compare(index - 1, index, PAUSE_DELAY) > 0)
					swap(index - 1, index, PAUSE_DELAY);
			numSorted++;
		}
	}

	public void selectionSort() {
		int numUnsorted = strip.size();
		int index;
		int max;
		while (0 < numUnsorted) {
			max = 0;
			for (index = 1; index < numUnsorted; index++)
				if (compare(max, index, PAUSE_DELAY) < 0)
					max = index;
			swap(max, numUnsorted - 1, PAUSE_DELAY);
			numUnsorted--;
			coinAt(numUnsorted).setColor(FINAL_COLOR);
		}
		coinAt(numUnsorted).setColor(FINAL_COLOR);
	}

	public void insertionSort() {
		int numSorted = 1;
		int index;
		while (numSorted < strip.size()) {
			// find the place where it goes
			index = 0;
			while (index < numSorted
					&& compare(index, numSorted, PAUSE_DELAY) <= 0)
				index++;
			while (index < numSorted) {
				swap(index, numSorted, PAUSE_DELAY);
				index++;
			}
			numSorted++;
		}
	}

	protected int partition(int left, int right) {
		while (true) {
			while (left < right && compare(left, right, PAUSE_DELAY) < 0)
				right--;
			if (left < right) {
				swap(left, right, PAUSE_DELAY);
				left++;
			} else
				return left;
			while (left < right && compare(left, right, PAUSE_DELAY) < 0)
				left++;
			if (left < right) {
				swap(left, right, PAUSE_DELAY);
				right--;
			} else
				return right;
		}
	}

	protected void qSort(int left, int right) {
		if (left < right) {
			int pivot = partition(left, right);
			coinAt(pivot).setColor(PIVOT_COLOR);
			qSort(left, pivot - 1);
			qSort(pivot + 1, right);
		}
	}

	public void quickSort() {
		qSort(0, strip.size() - 1);
	}

	protected void heapsort() {
		for (int j = strip.size() / 2 - 1; 0 <= j; j--)
			restoreHeap(j, strip.size());
		for (int j = strip.size() - 1; 0 < j; j--) {
			swap(0, j, PAUSE_DELAY);
			restoreHeap(0, j);
		}
	}

	void restoreHeap(int low, int high) {
		if (2 * low + 1 < high) {
			int j = 2 * low + 1;
			if (j + 1 < high && compare(j, j + 1, PAUSE_DELAY) < 0)
				j = j + 1;
			if (compare(low, j, PAUSE_DELAY) < 0) {
				swap(low, j, PAUSE_DELAY);
				restoreHeap(j, high);
			}
		}
	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(BACKGROUND_COLOR);
		for (int i = 0; i < strip.size(); i++)
			g2.fill(strip.get(i));

		g2.setPaint(BOUNDARY_COLOR);
		for (int i = 0; i < strip.size(); i++)
			g2.draw(strip.get(i));

		for (int i = 0; i < strip.size(); i++) {
			SortableCoin c = coinAt(i);
			if (c != null) {
				g2.setPaint(c.getColor());
				g2.fill(c);
			}
		}
	}

	public static void main(String[] args) {
		Freezer freezeObject = new Freezer();
		CoinSorter cs = new CoinSorter(12, freezeObject);
		CoinKeyListener getter = new CoinKeyListener(freezeObject);
		Random randomSelector = new Random();

		cs.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cs.setSize(cs.strip.size() * SQUARE_SIZE, SQUARE_SIZE);
		cs.setVisible(true);
		cs.addKeyListener(getter);

		while (true) {
			char coinKey = getter.get();
			if (coinKey == 'c')
				coinKey = "bhiqs".charAt(randomSelector.nextInt(5));
			switch (coinKey) {
			case 'b':
				cs.bubbleSort();
				break;
			case 'h':
				cs.heapsort();
				break;
			case 'i':
				cs.insertionSort();
				break;
			case 'q':
				cs.quickSort();
				break;
			case 'r':
				cs.randomize();
				break;
			case 's':
				cs.selectionSort();
				break;
			case 'x':
				System.exit(0);
			default:
				break;
			}
		}
	}

}
