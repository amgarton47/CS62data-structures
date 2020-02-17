package darwin;

import java.util.ArrayList;
import java.util.Random;
import java.io.*;

/**
 * This class controls the simulation. The design is entirely up to you. You
 * should include a main method that takes the array of species file names
 * passed in and populates a world with species of each type. You class should
 * be able to support anywhere between 1 to 4 species.
 * 
 * Be sure to call the WorldMap.pause() method every time through the main
 * simulation loop or else the simulation will be too fast. For example:
 * 
 * 
 * public void simulate() { 
 * 	for (int rounds = 0; rounds < numRounds; rounds++) {
 * 		giveEachCreatureOneTurn(); 
 * 		WorldMap.pause(500); 
 * 	} 
 * }
 * 
 */
class Darwin {

	public static final int width = 15;
	public static final int height = 15;
	World<Creature> world;
	Random rand;
	ArrayList<Creature> creatures;

	public Darwin(String[] speciesFilenames) {
		world = new World<Creature>(width, height);
		rand = new Random();
		creatures = new ArrayList<Creature>(speciesFilenames.length);
		WorldMap.createWorldMap(width, height);
		for (int i = 0; i < speciesFilenames.length; i++) {
			Species sp;
			try {
				sp = new Species(new BufferedReader(new FileReader("./Creatures/" + speciesFilenames[i])));
				placeCreatures(sp, 10);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.err.println("File not found, double check your path");
			}
			
		}		
	}

	/**
	 * Helper function to place num creatures of the sp Species on the world
	 */
	private void placeCreatures(Species sp, int num) {
		for (int i = 0; i < num; i++) {
			Position pos = new Position(rand.nextInt(width), rand.nextInt(height));
			while (world.get(pos) != null) {
				pos = new Position(rand.nextInt(width), rand.nextInt(height));
			}
			Creature c = new Creature(sp, world, pos, rand.nextInt(4));
			world.set(pos, c);
			creatures.add(c);
		}
	}

	/**
	 * The array passed into main will include the arguments that appeared on the
	 * command line. For example, running "java Darwin Hop.txt Rover.txt" will call
	 * the main method with s being an array of two strings: "Hop.txt" and
	 * "Rover.txt".
	 * 
	 * The autograder will always call the full path to the creature files, for
	 * example "java Darwin /home/user/Desktop/Assignment02/Creatures/Hop.txt" So
	 * please keep all your creates in the Creatures in the supplied
	 * Darwin/Creatures folder.
	 *
	 * To run your code you can either: supply command line arguments through
	 * Eclipse ("Run Configurations -> Arguments") or by creating a temporary array
	 * with the filenames and passing it to the Darwin constructor. If you choose
	 * the latter options, make sure to change the code back to: Darwin d = new
	 * Darwin(s); before submitting. If you want to use relative filenames for the
	 * creatures they should be of the form "./Creatures/Hop.txt".
	 */
	public static void main(String s[]) {
		Darwin d = new Darwin(s);
		d.simulate();
	}

	public void simulate() {

		// don't forget to call pause somewhere in the simulator's loop...
		// make sure to pause using WorldMap so that TAs can modify pause time
		// when grading
		for (int round = 0; round < 100; round++) {
			for (int index = 0; index < creatures.size(); index++) {
				creatures.get(index).takeOneTurn();
			}
			WorldMap.pause(800);
		}
	}
}