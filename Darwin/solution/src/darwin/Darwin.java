import java.util.ArrayList;
import java.util.Random;

/**
 * This class controls the simulation. The design is entirely up to you. You
 * should include a main method that takes the array of species file names
 * passed in and populates a world with species of each type.
 * <p>
 * Be sure to call the WorldMap.pause() method every time through the main
 * simulation loop or else the simulation will be too fast. For example:
 * 
 * <pre>
 *  public void simulate() { for (int rounds = 0; rounds < numRounds; rounds++) {giveEachCreatureOneTurn(); WorldMap.pause(100); } }
 * </pre>
 */
class Darwin {

	public static final int width = 15;
	public static final int height = 15;

	World world;
	Random rand;
	ArrayList<Creature> creatures;

	public Darwin(int w, int h) {
		world = new World(w, h);
		rand = new Random();
		creatures = new ArrayList<Creature>();
		WorldMap.createWorldMap(w, h);
	}

	Position randomPosition() {
		return new Position(rand.nextInt(width), rand.nextInt(height));
	}

	public void placeCreatures(Species sp, int num) {
		for (int i = 0; i < num; i++) {
			Position pos = randomPosition();
			while (world.get(pos) != null) {
				pos = randomPosition();
			}
			Creature c = new Creature(sp, world, pos, rand.nextInt(4));
			world.set(pos, c);
			creatures.add(c);
		}
	}

	public void run() {
		while (true) {
			WorldMap.pause(500);
			for (int i = 0; i < creatures.size(); i++) {
				Creature c = creatures.get(i);
				c.takeOneTurn();
			}
		}
	}

	/**
	 * The array passed into main will include the arguments that appeared on
	 * the command line. For example, running "java Darwin Hop.txt Rover.txt"
	 * will call the main method with s being an array of two strings: "Hop.txt"
	 * and "Rover.txt".
	 */
	public static void main(String s[]) {
		if (s.length == 0) {
			System.out.println("You must supply atleast one Species file");
			System.out.println("Example: java Darwin Hop.txt Rover.txt");
			return;
		}
		Darwin d = new Darwin(width, height);
		for (int i = 0; i < s.length; i++) {
			Species sp = new Species(s[i]);
			d.placeCreatures(sp, 10);
		}
		d.run();
	}
}
