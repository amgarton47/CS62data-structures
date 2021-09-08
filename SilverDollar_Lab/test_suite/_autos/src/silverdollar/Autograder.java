package silverdollar;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * <p>
 * Auto-grader unit tests for <strong>Text Silver Dollar Game</strong>.
 * </p>
 *
 *
 * @author Mark Kampe
 */
public class Autograder {

	private static final int TIMEOUT_SECONDS = 10;
	private static final int DEFAULT_SLEEP = 500;


	// FIX ... timeout needs to be upgraded to JU5
	// Set global time out
	//@Rule
	// public Timeout globalTimeout = Timeout.seconds(TIMEOUT_SECONDS);

	public TextCoinStrip game;

	private static final String TEST_STRIP = "o_oo__o_____o";
	private String testStrip;
	private int coins = 0;
	private int squares = 0;

	@BeforeEach
	public void setup() {
		// allocate a strip of the desired size
		for(int i = 0; i < TEST_STRIP.length(); i++) {
			squares++;
			if (TEST_STRIP.charAt(i) == 'o')
				coins++;
		}
		game = new TextCoinStrip(squares, coins);
	}

	/**
	 * Determine toString returns the initialized value
	 */
	@Test
	public void toString_correct_after_init() {
		initialize(TEST_STRIP);
		assertTrue(testStrip.equals(game.toString()),
			"toString returns expected value: " + testStrip);
	}

	/**
	 * Determine if a legal dist=1 move is recognized
	 */
	@Test
	public void legalMove_1() {
		initialize(TEST_STRIP);
		assertTrue(game.isLegalMove(2,1),
				testStrip + ".legalMove(2,1) -> True");
	}

	/**
	 * Determine if a legal dist=5 move is recognized
	 */
	@Test
	public void legalMove_5() {
		initialize(TEST_STRIP);
		assertTrue(game.isLegalMove(12,5),
				testStrip + ".legalMove(12,5) -> True");
	}

	/**
	 * Determine if a blocked dist=1 move is recognized
	 */
	@Test
	public void legalMove_1_blocked() {
		initialize(TEST_STRIP);
		assertFalse(game.isLegalMove(3,1),
					testStrip + ".legalMove(3,1) -> False");
	}

	/**
	 * Determine if a blocked dist=6 move is recognized
	 */
	@Test
	public void legalMove_6_blocked() {
		initialize(TEST_STRIP);
		assertFalse(game.isLegalMove(12,6),
					testStrip + ".legalMove(12,6) -> False");
	}

	/**
	 * Determine if a move from a place w/no coin is recognized
	 */
	@Test
	public void legalMove_no_coin() {
		initialize(TEST_STRIP);
		assertFalse(game.isLegalMove(4,1),
					testStrip + ".legalMove(4,1) -> False");
	}

	/**
	 * Determine if a move off the edge is recognized
	 */
	@Test
	public void legalMove_too_far() {
		initialize(TEST_STRIP);
		assertFalse(game.isLegalMove(0,1),
					testStrip + ".legalMove(0,1) -> False");
	}

	/**
	 * Determine if move starting off the board is recognized
	 */
	@Test
	public void legalMove_off_right() {
		initialize(TEST_STRIP);
		assertFalse(game.isLegalMove(13,1),
					testStrip + ".legalMove(13,1) -> False");
	}

	/**
	 * initialize testStrip to a known value
	 */
	private void initialize(String value) {
		testStrip = "";
		for(int i = 0; i < value.length(); i++) {
			game.theStrip.set(i, value.charAt(i) == 'o');
			testStrip = testStrip + value.charAt(i);
		}
	}

	/**
	 * update testStrip for a proposed removal
	 */
	private void removeCoin(int position) {
		String newstr = "";
		for(int i = 0; i < testStrip.length(); i++)
			newstr += (i == position) ? '_' : testStrip.charAt(i);
		testStrip = newstr;
	}

	/**
	 * update testStrip for a proposed addition
	 */
	private void addCoin(int position) {
		String newstr = "";
		for(int i = 0; i < testStrip.length(); i++)
			newstr += (i == position) ? 'o' : testStrip.charAt(i);
		testStrip = newstr;
	}

	/**
	 * play an entire game, testing makeMove and gameIsOver
	 *
	 * This one is tricky, because it is one call will spin off
	 * ONE of many possible assertions.  The general model is
	 *		try to win the game
	 *		after each move
	 *			see if toString after makeMove looks right
	 *			see if we agree with gameIsOver
	 *	if we ever find an error, spin off the appropriate assert
	 */
	@Test
	public void move_and_check() {
		
		initialize(TEST_STRIP);

		while(true) {
			// figure out if we think we have won, and if game agrees
			boolean won = true;
			int firstSpace = -1;
			int nextCoin = -1;
			for(int i = 0; i < testStrip.length(); i++) {
				char c = testStrip.charAt(i);
				if (c == '_' && firstSpace < 0)
					firstSpace = i;
				else if (c == 'o' && firstSpace >= 0) {
					nextCoin = i;
					break;
				}
			}
			if (nextCoin >= 0) {	// game is not over
				if (game.gameIsOver())
					assertFalse(true, testStrip + ".gameIsOver() -> False");
			} else {				// game is over
				if (!game.gameIsOver())	
					assertTrue(false, testStrip + ".gameIsOver() -> True");
				break;
			}

			// figure out what our next move should be, and make the move
			int dist = nextCoin - firstSpace;
			game.makeMove(nextCoin, dist);

			// see of the game looks like we expect
			String previous = testStrip;
			removeCoin(nextCoin);
			addCoin(firstSpace);
			if (!testStrip.equals(game.toString()))
				assertTrue(false, previous + "(" + nextCoin + "," + dist + ") -> " + testStrip);

			// and move on to the next round
		}

		// if we made it to here, all of the makeMove, gameIsOver and toString calls worked
		assertTrue(true, "all makeMove and gameIsOver calls work during a complete game.");
	}
}
