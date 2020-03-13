package coinSort;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CoinKeyListener implements KeyListener {

	protected static final int DEFAULT_SIZE = 16;

	protected char[] queue;
	protected int head;
	protected int tail;
	protected Freezer freezer;

	public CoinKeyListener(Freezer f, int size) {
		queue = new char[size];
		head = 0;
		tail = 0;
		freezer = f;
	}

	public CoinKeyListener(Freezer f) {
		this(f, DEFAULT_SIZE);
	}

	public synchronized char get() {
		while (head == tail) {
			try {
				wait();
			} catch (InterruptedException e) {

			}
		}
		char result = queue[head];
		head = (head + 1) % queue.length;
		return result;
	}

	public synchronized void keyTyped(KeyEvent e) {
		char c = Character.toLowerCase(e.getKeyChar());
		if (c == 'f') // f for freeze or single step
			freezer.toggle(true);
		else if (c == 't') // t for thaw
			freezer.toggle(false);
		else if ((tail + 1) % queue.length != head) {
			queue[tail] = c;
			tail = (tail + 1) % queue.length;
			notify();
		}
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

}
