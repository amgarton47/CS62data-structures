package darwin;

import java.util.Random;
import java.io.*;

/**
 * This class controls the simulation. The design is entirely up to you. You
 * should include a main method that takes the array of species file names
 * passed in and populates a world with species of each type. You class should
 * be able to support anywhere between 1 oto 4 creatures
 * <p>
 * Be sure to call the WorldMap.pause() method every time through the main
 * simulation loop or else the simulation will be too fast. For example:
 * 
 * <pre>
 * public void simulate() {
 * 	for (int rounds = 0; rounds &lt; numRounds; rounds++) {
 * 		giveEachCreatureOneTurn();
 * 		WorldMap.pause(100);
 * 	}
 * }
 * </pre>
 */
class Darwin {

	/**
	 * The array passed into main will include the arguments that appeared on
	 * the command line. For example, running "java Darwin Hop.txt Rover.txt"
	 * will call the main method with s being an array of two strings: "Hop.txt"
	 * and "Rover.txt".
	 * 
	 * The autograder will always call the full path to the creature files, for
	 * example
	 * "java Darwin /home/user/Desktop/Assignment02/Creatures/Hop.txt"
	 * So please keep all your creates in the Creatures in the supplied
	 * Darwin/Creatures folder.
	 * 
	 * When running in eclipse, you should be able to access the files using
	 * "./Creatures/Hop.txt" as an argument to the file reader. In Run
	 * Configurations -> Arguments, your arguments should be in the format
	 * "./Creatures/Hop.txt"
	 */
	public static void main(String s[]) {
	}

	public void simulate() {

		// don't forget to call pause somewhere in the simulator's loop...
		// make sure to pause using WorldMap so that TAs can modify pause time
		// when grading
		WorldMap.pause(500);

	}
}
