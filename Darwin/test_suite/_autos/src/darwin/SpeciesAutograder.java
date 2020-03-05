package darwin;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;

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
	@BeforeEach
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
		assertEquals("O", "" + foodSpecies.getSpeciesChar(),
				"Reading Food.txt, species char should be 'O'.");
	}

	/**
	 * Test method for {@link darwin.Species#getName()}.
	 *
	 * 0.5 pt
	 */
	@Test
	public final void testGetName() {
		assertEquals("OodFay", foodSpecies.getName(),
				"Reading Food.txt, name should be 'OodFay'");
	}

	/**
	 * Test method for {@link darwin.Species#getColor()}.
	 *
	 * 0.5 pt
	 */
	@Test
	public final void testGetColor() {
		assertEquals("green", foodSpecies.getColor(),
				"Reading Food.txt, color should be green");
	}

	/**
	 * Test method for {@link darwin.Species#programSize()}.
	 *
	 * 0.5 pt
	 */
	@Test
	public final void testProgramSize() {
		assertEquals(2, foodSpecies.programSize(),
				"Reading Food.txt, program size should be 2");
	}

	/**
	 * Test method for {@link darwin.Species#programStep(int)}.
	 *
	 * 1 pt
	 */
	@Test
	public final void testProgramStep() {
		assertEquals(2, foodSpecies.programStep(1).getOpcode(),
				"Reading Food.txt, 1st program step should have op code 2");

		// assertEquals("Reading Food.txt, 1st program step should have address 0",
		// -0.5, foodSpecies.programStep(1).getAddress(),0.6);

		assertEquals(10, foodSpecies.programStep(2).getOpcode(),
				"Reading Food.txt, 2nd program step should have op code 10");
		assertEquals(1, foodSpecies.programStep(2).getAddress(),
				"Reading Food.txt, 2nd program step should have address 1");
	}

	/**
	 * Test method for {@link darwin.Species#programToString()}.
	 *
	 * 1 pt
	 */
	@Test
	public final void testProgramToString() {
		String expected = "1: left\n2: go 1\n";
		assertEquals( expected, foodSpecies.programToString(),
				"Reading Food.txt, printing program to string.");
	}
}
