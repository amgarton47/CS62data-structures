package silverdollar;

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

/**
 * <p>
 * Auto-grader unit tests for <strong>Graphics Silver Dollar Game</strong>.
 * </p>
 *
 *
 * @author CS62 team
 */
public class Autograder {

	private static final int TIMEOUT_SECONDS = 10;
	private static final int DEFAULT_SLEEP = 500;
	private static final int SQUARE_SIZE = 120;
	private static final int SQUARE_NUM = 11;
	private static final int COIN_NUM = 2;
	private static final int LEFT_MOST_X = SQUARE_SIZE / 2;
	private static final int RIGHT_MOST_X = SQUARE_SIZE * SQUARE_NUM - LEFT_MOST_X;
	private static final int OFFSET = 5; // arbitrary offset

	private GraphicsCoinStrip game;
	private ArrayList<Coin> coins;
	private Robot robo;

	private Coin leftCoin, rightCoin;
	private int leftCoinX;
	private int rightCoinX;

	private int leftX, leftY, rightX, rightY;
	private boolean dragging;

	// Set global time out
	@Rule
	public Timeout globalTimeout = Timeout.seconds(TIMEOUT_SECONDS);

	@Before
	public void setup() throws AWTException {
				dragging = false;
				leftCoinX = 0;
				rightCoinX = 0;

				initCoinStrip(SQUARE_NUM, COIN_NUM);

				// attempt to reinitialize to get desireable testing situation.
				while (leftCoinX <= LEFT_MOST_X + SQUARE_SIZE || rightCoinX >= RIGHT_MOST_X
						|| (rightCoinX - leftCoinX) <= SQUARE_SIZE * 2) {
					game.dispose();
					coins.clear();
					initCoinStrip(SQUARE_NUM, COIN_NUM);
				}
				
				// init robot
				robo = new Robot();
				game.setAlwaysOnTop(true);

				// check square sizes followed the constant
				if ((int) game.strip.get(0).getWidth() != SQUARE_SIZE){
					new Exception("Square sizes improperly initialized.");

				System.err.println("Fatal test suite incident.");
				System.exit(1);
			}

			updateCenters();
	}

	/**
	 * Determine if a coin can be dragged (which does not include a drop).
	 *
	 * @throws InterruptedException
	 */
	@Test
	public void dragCoin_InitialCoinDrag_ShouldMoveWithMouse() throws InterruptedException {
		int x1 = leftX + OFFSET;
		int y1 = leftY + OFFSET;

		mouseDrag(leftX, leftY, x1, y1);
		assertEquals("The center X of the coin should move with the mouse.", x1, leftCoin.getCenterX(), 0);
		assertEquals("The center Y of the coins should move with the mouse.", y1, leftCoin.getCenterY(), 0);

	}

	/**
	 * Determine if a coin can be dropped in its original centered location
	 * after being dragged and moved back.
	 *
	 * NOTE: This should not test the centered drop functionality.
	 */
	@Test
	public void dropCoin_InitialCoinDrop_ShouldMoveBackToPlace() {
		int x1 = leftX + OFFSET;
		int y1 = leftY + OFFSET;

		mouseDrag(leftX, leftY, x1, y1); // move
		mouseDrag(x1, y1, leftX, leftY); // move back
		mouseRelease();
		// check that coin is in original position
		assertEquals("Coin should be positioned in the same place after dropping back to its starting point.", leftX,
				leftCoin.getCenterX(), 0);
		assertEquals("Coin should be positioned in the same place after dropping back to its starting point.", leftY,
				leftCoin.getCenterY(), 0);

	}

	/**
	 * Determine if a second coin can be dragged (which does not include its
	 * drop).
	 */
	@Test
	public void dragCoin_SubsequentMove_ShouldMoveWithMouse() {
		int x1 = rightX - OFFSET - SQUARE_SIZE;
		int y1 = rightY - OFFSET;

		mouseDrag(rightX, rightY, x1, y1); // move

		assertEquals("The center X of the coin should move with the mouse.", x1, rightCoin.getCenterX(), 0);
		assertEquals("The center Y of the coins should move with the mouse.", y1, rightCoin.getCenterY(), 0);
	}

	/**
	 * Determine if a second coin can be dropped.
	 *
	 * NOTE: This should not test the centered drop functionality.
	 */
	@Test
	public void dropCoin_SubsequentDrop_ShouldMoveBackToPlace() {
		int x1 = rightX - OFFSET - SQUARE_SIZE;
		int y1 = rightY - OFFSET;

		mouseDrag(rightX, rightY, x1, y1); // move
		mouseDrag(x1, y1, rightX, rightY); // move back
		mouseRelease();
		// check that coin is in original position
		assertEquals("Coin should be positioned in the same place after dropping back to its starting point.", rightX,
				rightCoin.getCenterX(), 0);
		assertEquals("Coin should be positioned in the same place after dropping back to its starting point.", rightY,
				rightCoin.getCenterY(), 0);
	}

	/**
	 * Test that a coin picked up, slightly moved, and dropped in its original
	 * space centers itself on drop.
	 */
	@Test
	public void dropCoin_SameLocationCentered_ShouldBeCentered() {
		int centerX0 = (int) rightCoin.getCenterX();
		int centerY0 = (int) rightCoin.getCenterY();
		int x1 = centerX0 - OFFSET;
		int y1 = centerY0 - OFFSET;

		dragAndRelease(centerX0, centerY0, x1, y1);

		assertEquals("Coin should be centered when dragged and dropped slightly in its own square.", centerX0,
				rightCoin.getCenterX(), 0);
		assertEquals("Coin should be centered when dragged and dropped slightly in its own square.", centerY0,
				rightCoin.getCenterY(), 0);
	}

	/**
	 *
	 */
	private void mouseRelease() {
		robo.mouseRelease(InputEvent.BUTTON1_DOWN_MASK); // release
		robo.delay(DEFAULT_SLEEP);
		dragging = false;
	}

	/**
	 * Test that a coin picked up, moved to a legal space, and dropped is
	 * centered on the square.
	 */
	@Test
	public void dropCoin_LegalNewSpaceCentered_ShouldBeCentered() {
		int centerX0 = (int) rightCoin.getCenterX();
		int centerY0 = (int) rightCoin.getCenterY();
		int x1 = centerX0 - OFFSET - SQUARE_SIZE;
		int y1 = centerY0 - OFFSET;

		dragAndRelease(centerX0, centerY0, x1, y1);

		assertEquals("Coin should be centered when dragged into a new square.", centerX0 - SQUARE_SIZE,
				rightCoin.getCenterX(), 0);
		assertEquals("Coin should be centered when dragged into a new square.", centerY0, rightCoin.getCenterY(), 0);
	}

	/**
	 * @param x0
	 * @param y0
	 * @param x1
	 * @param y1
	 */
	private void dragAndRelease(int x0, int y0, int x1, int y1) {
		mouseDrag(x0, y0, x1, y1); // move
		mouseRelease();
	}

	private void dragAndRelease(Coin coin, int newX) {
		mouseDrag((int) coin.getCenterX(), (int) coin.getCenterY(), newX, (int) coin.getCenterY()); // move
		mouseRelease();
	}

	// // Illegal Tests
	//
	/**
	 * Checks that the user cannot move the left coin one square to the right in
	 * an empty square.
	 */
	@Test
	public void makeMove_IllegalLeftToRightEmpty_ShouldNotMove() {
		int x1 = (int) leftCoin.getCenterX();

		dragAndRelease(leftCoin, (int) x1 + SQUARE_SIZE);

		assertNotEquals("Coin should not move to new space when dragged to the right.", x1, leftCoin.getCenterX());
	}

	/**
	 * Check that the user cannot move the left coin on top of the right coin.
	 */
	@Test
	public void makeMove_IllegalLeftOnTopRight_ShouldNotMove() {
		int x0 = (int) leftCoin.getCenterX();

		dragAndRelease((int) leftCoin.getCenterX(), (int) leftCoin.getCenterY(), (int) rightCoin.getCenterX(),
				(int) rightCoin.getCenterY());

		assertEquals("Left coin should not be on top of right coin.", x0, leftCoin.getCenterX(), 0);
	}

	/**
	 * Check that the left coin cannot move past the right coin.
	 */
	@Test
	public void makeMove_IllegalLeftPastRight_ShouldNotMove() {
		int x0 = (int) leftCoin.getCenterX();

		dragAndRelease(x0, (int) leftCoin.getCenterY(), (int) rightCoin.getCenterX() + SQUARE_SIZE,
				(int) rightCoin.getCenterY());

		assertEquals("Left coin should not be able to be moved past the right coin.", x0, leftCoin.getCenterX(), 0);
	}

	/**
	 * Check that a right coin cannot move to the right one square.
	 */
	@Test
	public void makeMove_IllegalRightToRightEmpty_ShouldNotMove() {
		int x0 = (int) rightCoin.getCenterX();

		dragAndRelease(rightCoin, x0 + SQUARE_SIZE);
		assertEquals("Right coin should not be able to be moved to the right.", x0, rightCoin.getCenterX(), 0);
	}

	/**
	 * Check that the right coin cannot go on top of the left coin.
	 */
	@Test
	public void makeMove_IllegalRightOnTopLeft_ShouldNotMove() {
		int x0 = (int) rightCoin.getCenterX();

		dragAndRelease((int) rightCoin.getCenterX(), (int) rightCoin.getCenterY(), (int) leftCoin.getCenterX(),
				(int) leftCoin.getCenterY());

		assertEquals("Right coin should not be on top of left coin.", x0, rightCoin.getCenterX(), 0);
	}

	/**
	 * Check that the right coin should not be able to move past left coin.
	 */
	@Test
	public void makeMove_IllegalRightPastLeft_ShouldNotMove() {
		int x0 = (int) rightCoin.getCenterX();

		dragAndRelease(x0, (int) rightCoin.getCenterY(), (int) leftCoin.getCenterX() - SQUARE_SIZE,
				(int) leftCoin.getCenterY());

		assertEquals("Right coin should not have moved past the left coin.", x0, rightCoin.getCenterX(), 0);
	}

	/**
	 * Left coin should be able to be moved multiple squares to the left.
	 */
	@Test
	public void dragCoin_LeftToLeftMultipleSquares_ShouldMove() {
		dragAndRelease(leftCoin, LEFT_MOST_X);

		assertEquals("Left coin should be moved multiple spaces to the left.", LEFT_MOST_X, leftCoin.getCenterX(), 0);
	}

	/**
	 * Right coin should be move-able to the left multiple squares.
	 */
	@Test
	public void dragCoin_RightToLeftMultipleSquares_ShouldMove() {
		dragAndRelease(leftCoin, LEFT_MOST_X);

		// move right coin
		dragAndRelease(rightCoin, LEFT_MOST_X + SQUARE_SIZE);

		assertEquals("Right coin should be moved multiple squares to the left.", LEFT_MOST_X + SQUARE_SIZE,
				rightCoin.getCenterX(), 0);
	}

	/**
	 * Game over should be indicated.
	 */
	@Test
	public void game_GameOverIndicated_ShouldPass() {
		dragAndRelease(leftCoin, LEFT_MOST_X);
		dragAndRelease(rightCoin, LEFT_MOST_X + SQUARE_SIZE);

		int n = JOptionPane.showConfirmDialog(game, "Has GAME OVER been indicated in some way?", "TA Input Required",
				JOptionPane.YES_NO_OPTION);

		assertEquals("Game over should be indicated.", JOptionPane.YES_OPTION, n);
	}

	/**
	 * Initialize graphics and set instance variables for test cases.
	 *
	 * @param squareNum
	 *            number of squares on board
	 * @param coinNum
	 *            number of coins on board
	 */
	private void initCoinStrip(int squareNum, int coinNum) {
		initializeGraphics(squareNum, coinNum);
		initializeCoins();
		findCoinPosn();
	}

	/**
	 * Initialize the graphics.
	 *
	 * @param squareNum
	 *            number of squares on board
	 * @param coinNum
	 *            number of coins on board
	 */
	private void initializeGraphics(int squareNum, int coinNum) {
		game = new GraphicsCoinStrip(squareNum, coinNum);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setSize(game.strip.size() * SQUARE_SIZE, SQUARE_SIZE);
		game.setVisible(true);
	}

	// TODO Examine this method -- it's not always finding all the coins.
	private void initializeCoins() {
		coins = new ArrayList<Coin>();
		for (int i = 0; i < game.strip.size(); i++) {
			CoinSquare currSquare = game.strip.get(i);
			if (currSquare.isOccupied()) {
				Coin currCoin = currSquare.getCoin();
				if (currSquare.getCenterX() != currCoin.getCenterX()
						|| currSquare.getCenterY() != currCoin.getCenterY()) {
					new Exception("Error in initiizing coins.");
				} else {
					coins.add(game.strip.get(i).getCoin());
				}
			}
		}

		// System.out.println("Number coins: " + coins.size());
		if (coins.size() != COIN_NUM || game.strip.size() != SQUARE_NUM) {
			// System.out.println("calling exception on number of coins.");
			new Exception("Error in initializing program; number of coins inappropriate.");
		}
	}

	/**
	 * Find the pixel locations of the coins on screen.
	 */
	private void findCoinPosn() {
		// System.out.println(coins.size());
		leftCoin = coins.get(0);
		rightCoin = coins.get(1);

		leftCoinX = (int) leftCoin.getCenterX() - game.getX();
		rightCoinX = (int) rightCoin.getCenterX() - game.getX();
	}

	private void updateCenters() {
		leftX = (int) leftCoin.getCenterX();
		leftY = (int) leftCoin.getCenterY();
		rightX = (int) rightCoin.getCenterX();
		rightY = (int) rightCoin.getCenterY();
	}

	private void mouseDrag(int startX, int startY, int endX, int endY) {
		robo.mouseMove(startX + game.getX(), startY + game.getY());
		robo.delay(DEFAULT_SLEEP);
		if (!dragging) {
			robo.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			dragging = true;
		}
		robo.delay(DEFAULT_SLEEP);
		robo.mouseMove(endX + game.getX(), endY + game.getY());
		robo.delay(DEFAULT_SLEEP);
	}
}
