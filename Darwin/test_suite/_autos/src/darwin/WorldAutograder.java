package darwin;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
/**
 * JUnit tests for the world
 * @author Sean Zhu
 */
public class WorldAutograder {
	World<String> world;

	private static final int H = 3;
	private static final int W = 2;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// a 3 row, 2 column world
		world = new World<String>(W, H);
	}

	/**
	 * Test method for {@link darwin.World#height()}.
	 */
	@Test
	public final void testHeight() {
		assertEquals(
				"Initiated a 3 row, 2 column world. Height should be 3.",
				H, world.height());
	}

	/**
	 * Test method for {@link darwin.World#width()}.
	 */
	@Test
	public final void testWidth() {
		assertEquals(
				"Initiated a 3 row, 2 column world. Width should be 2.", W,
				world.width());

	}

	/**
	 * Test method for {@link darwin.World#inRange(darwin.Position)}.
	 */
	@Test
	public final void testInRange() {

		// row 0, colum 0
		Position p0 = new Position(0, 0);
		assertTrue("h=3, w=2, Pos(0,0) should be in range.",
				world.inRange(p0));

		// row 1, colum 1
		Position p1 = new Position(1, 1);
		assertTrue("h=3, w=2, Pos(1,1) should be in range.",
				world.inRange(p1));

		// row 2, colum 1
		Position p2 = new Position(2, 1);
		assertFalse("h=3, w=2, Pos(2,1) should not be in range.",
				world.inRange(p2));

		// row 2, colum 0
		Position p3 = new Position(2, 2);
		assertFalse("h=3, w=2, Pos(2,2) should not be in range.",
				world.inRange(p3));

		// row 3, colum 1
		Position p4 = new Position(1, 2);
		assertTrue("h=3, w=2, Pos(1,2) should be in range.",
				world.inRange(p4));

	}

	/**
	 * Test method for {@link darwin.World#inRange(darwin.Position)}.
	 */
	@Test
	public final void testInRange_negative() {

		// row -1, colum -1
		Position p5 = new Position(-1, -1);
		assertFalse("h=3, w=2, Pos(-1,-1) should not be in range.",
				world.inRange(p5));

	}

	/**
	 * Test method for
	 * {@link darwin.World#set(darwin.Position, java.lang.Object)}.
	 * {@link darwin.World#get(darwin.Position, java.lang.Object)}.
	 */
	@Test
	public final void testSetAndGet() {
		Position p = new Position(0, 0);
		world.set(p, "cow");
		assertEquals("Set position(0,0) to be 'cow'.", "cow", world.get(p));
	}

	/**
	 * Test method for
	 * {@link darwin.World#set(darwin.Position, java.lang.Object)}.
	 * {@link darwin.World#get(darwin.Position, java.lang.Object)}.
	 */
	@Test
	public final void testResetAndGet() {
		Position p = new Position(0, 0);
		world.set(p, "cow");
		world.set(p, "sheep");
		assertEquals(
				"Reset position(0,0) to be 'sheep' after setting it to 'cow'.",
				"sheep", world.get(p));
	}

	/**
	 * Test method for
	 * {@link darwin.World#set(darwin.Position, java.lang.Object)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testSet_notInRange() {
		Position p = new Position(10, 10);
		world.set(p, "cow");
	}

	/**
	 * Test method for
	 * {@link darwin.World#get(darwin.Position, java.lang.Object)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testGet_notInRange() {
		Position p = new Position(10, 10);
		world.get(p);
	}

}
