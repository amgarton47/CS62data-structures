package darwin;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;

import org.junit.Before;
import org.junit.Test;
/**
 * JUnit tests for species
 * @author Sean Zhu
 */
public class SpeciesAutograder {
	public static final File TESTING_DIR = new File(System.getProperty("testing"));
	public static final File DEP_DIR = new File(TESTING_DIR, "dependencies");

	public static final String FOOD_PATH = new File(DEP_DIR, "Food.txt").getPath();

	Species foodSpecies;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		foodSpecies = new Species(new BufferedReader(new FileReader(
				FOOD_PATH))); //CHECK THIS FILE PATH pls
	}

	// /**
	// * Test method for {@link
	// darwin.Species#Species(java.io.BufferedReader)}.
	// */
	// @Test
	// public final void testSpecies() {
	// fail("Not yet implemented"); // TODO
	// }

	/**
	 * Test method for {@link darwin.Species#getSpeciesChar()}.
	 *
	 * 0.5 pt
	 */
	@Test
	public final void testGetSpeciesChar() {
		assertEquals("Reading Food.txt, species char should be 'O'.", "O",
				"" + foodSpecies.getSpeciesChar());
	}

	/**
	 * Test method for {@link darwin.Species#getName()}.
	 *
	 * 0.5 pt
	 */
	@Test
	public final void testGetName() {
		assertEquals("Reading Food.txt, name should be 'OodFay'", "OodFay",
				foodSpecies.getName());
	}

	/**
	 * Test method for {@link darwin.Species#getColor()}.
	 *
	 * 0.5 pt
	 */
	@Test
	public final void testGetColor() {
		assertEquals("Reading Food.txt, color should be green", "green",
				foodSpecies.getColor());
	}

	/**
	 * Test method for {@link darwin.Species#programSize()}.
	 *
	 * 0.5 pt
	 */
	@Test
	public final void testProgramSize() {
		assertEquals("Reading Food.txt, program size should be 2", 2,
				foodSpecies.programSize());
	}

	/**
	 * Test method for {@link darwin.Species#programStep(int)}.
	 *
	 * 1 pt
	 */
	@Test
	public final void testProgramStep() {
		assertEquals(
				"Reading Food.txt, 1st program step should have op code 2",
				2, foodSpecies.programStep(1).getOpcode());

		// assertEquals("Reading Food.txt, 1st program step should have address 0",
		// -0.5, foodSpecies.programStep(1).getAddress(),0.6);

		assertEquals(
				"Reading Food.txt, 2nd program step should have op code 10",
				10, foodSpecies.programStep(2).getOpcode());
		assertEquals(
				"Reading Food.txt, 2nd program step should have address 1",
				1, foodSpecies.programStep(2).getAddress());
	}

	/**
	 * Test method for {@link darwin.Species#programToString()}.
	 *
	 * 1 pt
	 */
	@Test
	public final void testProgramToString() {
		String expected = "1: left\n2: go 1\n";
		assertEquals("Reading Food.txt, printing program to string.",
				expected, foodSpecies.programToString());
	}
}
