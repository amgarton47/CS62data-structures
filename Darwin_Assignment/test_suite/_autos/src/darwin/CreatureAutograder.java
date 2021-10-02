package darwin;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;


/**
 * JUnit tests for creatures
 * @author cs62
 *
 */

public class CreatureAutograder {
	public static final File DEP_DIR = new File("Creatures");

	public static final String FLY_PATH = new File(DEP_DIR, "Flytrap.txt").getPath();
	public static final String ROVER_PATH = new File(DEP_DIR, "Rover.txt").getPath();
	public static final String TURN_PATH = new File(DEP_DIR, "Turner.txt").getPath();
	
	World w;
	Species flytrap, rover, turner;
	/**
	 * Loads up the species files
	 * @throws FileNotFoundException
	 */
	@BeforeEach
	public void setUp() throws FileNotFoundException{
		flytrap = new Species(new BufferedReader(
				new FileReader(FLY_PATH)));
		rover = new Species(new BufferedReader(
				new FileReader(ROVER_PATH)));
		turner = new Species(new BufferedReader(
				new FileReader(TURN_PATH)));
		w = new World(10, 10);
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
		assertEquals(1, f.position().getX(), "Flytrap shouldn't move.");
		assertEquals(1, f.position().getY(), "Flytrap shouldn't move.");
		assertEquals(Position.WEST, f.direction(),
					"Flytrap should rotate to face west.");

		
		f.takeOneTurn();
		assertEquals(Position.SOUTH, f.direction(),
					"Flytrap should rotate to face south.");

		f.takeOneTurn();
		assertEquals(Position.EAST, f.direction(),
					"Flytrap should rotate to face east.");
		
		f.takeOneTurn();
		assertEquals(Position.NORTH, f.direction(),
					"Flytrap should rotate to face north.");
		
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
		assertEquals(1, t.position().getX(),
					"Turner shouldn't move.");
		assertEquals(1, t.position().getY(),
					"Turner shouldn't move.");
		assertEquals(Position.EAST, t.direction(),
					"Turner should rotate to face east.");
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
		assertEquals(1, f.position().getX(),
					"Flytrap shouldn't move.");
		assertEquals(1, f.position().getY(),
					"Flytrap shouldn't move.");
		assertEquals(Position.WEST, f.direction(),
					"Flytrap should rotate to face west.");
	}
		
	

	/**
	 * Tests the "hop" command.
	 * Rover "r" should move forward one square.
	 */
	@Test
	public void testHop() {
		Creature r = new Creature(rover, w, new Position(0,1), Position.NORTH);
		r.takeOneTurn();
		assertEquals(0, r.position().getX(),
					"Rover shouldn't move horizontally.");
		assertEquals(0, r.position().getY(),
					"Rover should move up one square.");
		assertEquals( Position.NORTH, r.direction(),
					"Rover shouldn't rotate.");

		assertNull(w.get(new Position(0, 1)),
					"Rover should remove itself from previous world position.");

		assertEquals(r, w.get(new Position(0, 0)),
					"Rover should add itself at new world position.");
	}
	/**
	 * Tests the "ifwall" command.
	 * Rover "r" should detect the wall and rotate.
	 */
	@Test
	public void testIfWall() {
		Creature r = new Creature(rover, w, new Position(0,0), Position.NORTH);
		r.takeOneTurn();
		assertEquals(0, r.position().getX(),
					"Rover shouldn't move if facing a wall.");
		assertEquals(0, r.position().getY(),
					"Rover shouldn't move if facing a wall.");
		assertNotEquals(Position.NORTH, r.direction(),
					"Rover should rotate if facing a wall.");
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
		assertEquals(0, r.position().getX(),
					"Rover shouldn't move if facing a rover.");
		assertEquals(1, r.position().getY(),
					"Rover shouldn't move if facing a rover.");
		assertNotEquals(Position.NORTH, r.direction(),
					"Rover should rotate if facing a rover.");
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
		assertEquals(flytrap, r.species(),
					"Rover should become a Flytrap.");
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
		assertEquals(flytrap, r.species(),
					"Rover should become a Flytrap.");
		assertEquals(Position.EAST, f.direction(),
					"Flytrap should not rotate since it infected.");
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
		assertEquals(Position.EAST, t1.direction(),
					"Turner should rotate right since position directly in front is empty");
		
		t1.takeOneTurn();
		assertEquals(Position.NORTH, t1.direction(),
					"Turner should rotate left since position directly in front is filled with creature");
		
		
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

			r.setDirection((int)Position.NORTH);
		}
		
		assertTrue(hasTurnedLeft,
					"Rover should randomly turn left and right. Rover never turned left.");
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
		assertEquals(2, r.position().getX(),
					"Rover should have hopped east two times so x should be 2");
		assertEquals(0, r.position().getY(),
					"Rover should have hopped east two times so y should still be 0");
	}
}
