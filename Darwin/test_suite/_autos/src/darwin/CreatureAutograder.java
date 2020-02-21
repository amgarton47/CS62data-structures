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
 * @author cs62
 *
 */

public class CreatureAutograder {
	public static final File TESTING_DIR = new File(System.getProperty("testing"));
	public static final File DEP_DIR = new File(TESTING_DIR, "dependencies");

	public static final String FLY_PATH = new File(DEP_DIR, "Flytrap.txt").getPath();
	public static final String ROVER_PATH = new File(DEP_DIR, "Rover.txt").getPath();
	public static final String TURN_PATH = new File(DEP_DIR, "Turner.txt").getPath();

	//FOR RUNNING LOCALLY, comment out above and use full paths
//	public static final String FLY_PATH = "/Users/jackweber/Desktop/solution/Creatures/FlyTrap.txt"; 
//	public static final String ROVER_PATH = "/Users/jackweber/Desktop/solution/Creatures/Rover.txt";
//	public static final String HOP_PATH = "/Users/jackweber/Desktop/solution/Creatures/Hop.txt"; 
//	public static final String TURN_PATH = "/Users/jackweber/Desktop/solution/Creatures/Turner.txt";	
	
	World<Creature> w;
	Species flytrap, rover, turner;
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
		turner = new Species(new BufferedReader(
				new FileReader(TURN_PATH)));
		w = new World<Creature>(10, 10);
		WorldMap.createWorldMap(10,10);
	}
	/**
	 * Tests the "left" command.
	 * The flytrap will turn left in a 360.
	 * Should usually passes if testLeft passes.
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
		
		f.takeOneTurn();
		assertEquals("Flytrap should rotate to face south.",
				Position.SOUTH, f.direction());

		f.takeOneTurn();
		assertEquals("Flytrap should rotate to face east.",
				Position.EAST, f.direction());
		
		f.takeOneTurn();
		assertEquals("Flytrap should rotate to face north.",
				Position.NORTH, f.direction());
		
	}
	
	/**
	 * Tests the "right" command.
	 * The turner will turn right.
	 */
	@Test
	public void testRight() {
		Creature t = new Creature(turner, w, new Position(1, 1),
															Position.NORTH);
		t.takeOneTurn();
		assertEquals("Turner shouldn't move.", 1, t.position().x);
		assertEquals("Turner shouldn't move.", 1, t.position().y);
		assertEquals("Turner should rotate to face east.",
								Position.EAST, t.direction());
	}
	
	
	/**
	 * Tests the "left" command.
	 * The flytrap will turn left.
	 */
	@Test
	public void testLeft() {
		Creature f = new Creature(flytrap, w, new Position(1, 1),
															Position.NORTH);
		f.takeOneTurn();
		assertEquals("Flytrap shouldn't move.", 1, f.position().x);
		assertEquals("Flytrap shouldn't move.", 1, f.position().y);
		assertEquals("Flytrap should rotate to face west.",
								Position.WEST, f.direction());
	}
		
	

	/**
	 * Tests the "hop" command.
	 * Rover "r" should move forward one square.
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
	 * Tests the "ifwall" command.
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
	 * Tests the "ifsame" command,
	 * Rover "r" should detect another rover and turn as a result.
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
	 * Tests the "infect" command.
	 * Flytrap "f" should infect rover "r".
	 */
	@Test
	public void testInfect(){
		Creature f = new Creature(flytrap,w,new Position(0, 0), Position.EAST);
		Creature r = new Creature(rover, w, new Position(1, 0), Position.EAST);
		f.takeOneTurn();
		assertEquals("Rover should become a Flytrap.", flytrap, r.species());
	}
	
	/**
	 * Tests the "ifenemy" command.
	 * Flytrap "f" should infect rover "r." Should not turn left while infecting
	 * Note this is almost same as testInfect().
	 */
	@Test
	public void testIfEnemy(){
		Creature f = new Creature(flytrap,w,new Position(0, 0), Position.EAST);
		Creature r = new Creature(rover, w, new Position(1, 0), Position.EAST);
		f.takeOneTurn();
		assertEquals("Rover should become a Flytrap.", flytrap, r.species());
		assertEquals("Flytrap should not rotate since it infected.",
				Position.EAST, f.direction());
	}
	
	/**
	 * Tests the "ifEmpty" command.
	 * Turner "t" should turn right if the space in front is empty. Otherwise, turns left.
	 * Note this is almost same as testInfect().
	 */
	@Test
	public void testIfEmpty(){
		Creature t1 = new Creature(turner,w,new Position(1, 1), Position.NORTH);
		new Creature(turner, w, new Position(2,1), Position.EAST);
		
		t1.takeOneTurn();
		assertEquals("Turner should rotate right since position directly in front is empty",
				Position.EAST, t1.direction());
		
		t1.takeOneTurn();
		assertEquals("Turner should rotate left since position directly in front is filled with creature",
				Position.NORTH, t1.direction());
		
		
	}
	
	/**
	 * Tests the "ifRandom" command.
	 * Rover "r" should randomly turn left or right when facing a wall.
	 * We assume that if rover turns same direction 10 times in a row then it is not exhibiting true 
	 * randomness
	 */
	@Test
	public void testIfRandom(){
		Boolean hasTurnedLeft = false;
		Boolean hasTurnedRight = false;
		
		Creature r = new Creature(rover,w,new Position(4, 0), Position.NORTH);
		
		for(int i=0; i<10; i++) {
			r.takeOneTurn();
			
			// if rover has not turned left and it is now facing west
			if (!hasTurnedLeft && r.direction() == Position.WEST) {
				hasTurnedLeft = true;
			} 
			
			// if rover has not turned right and it is now facing east
			if(!hasTurnedRight && r.direction() == Position.EAST) {
				hasTurnedRight = true;
			}
			
			// if rover has turned left and right
			if(hasTurnedLeft && hasTurnedRight) {
				break;
			}
			r.dir = Position.NORTH;
		}
		
		assertTrue("Rover should randomly turn left and right. Rover never turned left.", hasTurnedLeft);
		assertTrue(hasTurnedRight);
		
		
		
	}
	
	/**
	 * Tests the "go" command
	 * Hop "h" should hop twice, since the go command should return it to the beginning
	 */
	@Test
	public void testGo(){
		Creature r = new Creature(rover,w,new Position(0, 0), Position.EAST);
		r.takeOneTurn();
		r.takeOneTurn();
		assertEquals("Rover should have hopped east two times so x should be 2", 2, r.position().x);
		assertEquals("Rover should have hopped east two times so y should still be 0", 0, r.position().y);		
		
		
	}
}
