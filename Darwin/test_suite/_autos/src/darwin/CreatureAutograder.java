package darwin;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit tests for creatures
 * @author Peter Cowal
 *
 */
public class CreatureAutograder {
	public static final File TESTING_DIR = new File(System.getProperty("testing"));
	public static final File DEP_DIR = new File(TESTING_DIR, "dependencies");

	public static final String FLY_PATH = new File(DEP_DIR, "Flytrap.txt").getPath();
	public static final String ROVER_PATH = new File(DEP_DIR, "Rover.txt").getPath();

	World<Creature> w;
	Species flytrap, rover;
	/**
	 * Loads up the species files
	 * @throws FileNotFoundException
	 */
	@Before
	public void setUp() throws FileNotFoundException{
		flytrap = new Species(new BufferedReader(
				new FileReader(FLY_PATH)));
		rover = new Species(new BufferedReader(
				new FileReader(ROVER_PATH)));
		w = new World<Creature>(10, 10);
		WorldMap.createWorldMap(10,10);
	}
	/**
	 * Tests the "left" command
	 * The flytrap will turn left.
	 */
	@Test
	public void testRotate() {
		Creature f = new Creature(flytrap, w, new Position(1, 1),
															Position.NORTH);
		f.takeOneTurn();
		assertEquals("Flytrap shouldn't move.", 1, f.position().x);
		assertEquals("Flytrap shouldn't move.", 1, f.position().y);
		assertEquals("Flytrap should rotate to face west.",
								Position.WEST, f.direction());
	}

	/**
	 * Tests the "hop" command
	 * Rover "r" should move forwards one square
	 */
	@Test
	public void testHop() {
		Creature r = new Creature(rover, w, new Position(0,1), Position.NORTH);
		r.takeOneTurn();
		assertEquals("Rover shouldn't move horizontally.", 0, r.position().x);
		assertEquals("Rover should move up one square.", 0, r.position().y);
		assertEquals("Rover shouldn't rotate.", Position.NORTH, r.direction());

		assertNull("Rover should remove itself from previous world position.",
										w.get(new Position(0, 1)));
		assertEquals("Rover should add itself at new world position.", r,
										w.get(new Position(0, 0)));
	}
	/**
	 * Tests the "ifwall" command
	 * Rover "r" should detect the wall and rotate.
	 */
	@Test
	public void testIfWall() {
		Creature r = new Creature(rover, w, new Position(0,0), Position.NORTH);
		r.takeOneTurn();
		assertEquals("Rover shouldn't move if facing a wall.",
												0, r.position().x);
		assertEquals("Rover shouldn't move if facing a wall.",
												0, r.position().y);
		assertNotEquals("Rover should rotate if facing a wall.",
												Position.NORTH, r.direction());
	}

	/**
	 * Tests the "ifsame" command
	 * Rover "r" should detect another rover and turn as a result
	 */
	@Test
	public void testIfSame() {
		Creature r = new Creature(rover, w, new Position(0,1), Position.NORTH);
		new Creature(rover, w, new Position(0,0), Position.NORTH);
		r.takeOneTurn();
		assertEquals("Rover shouldn't move if facing a rover.",
				0, r.position().x);
		assertEquals("Rover shouldn't move if facing a rover.",
				1, r.position().y);
		assertNotEquals("Rover should rotate if facing a rover.",
				Position.NORTH, r.direction());
	}

	/**
	 * Tests the "infect" command
	 * Flytrap "f" should infect rover "r"
	 */
	@Test
	public void testInfect(){
		Creature f = new Creature(flytrap,w,new Position(0, 0), Position.EAST);
		Creature r = new Creature(rover, w, new Position(1, 0), Position.EAST);
		f.takeOneTurn();
		assertEquals("Rover should become a Flytrap.", flytrap, r.species());
	}

}
