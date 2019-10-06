import java.util.ArrayList;
import java.util.Random;

/**
 * This class controls the simulation. The design is entirely up to you. You
 * should include a main method that takes the array of species file names
 * passed in and populates a world with species of each type.
 * <p>
 * This class includes a pause method. Be sure to call this every time through
 * the main simulation loop or else the simulation will be too fast and
 * keyboard / mouse input will be slow. For example:
 * 
 * <pre>
 *  public void simulate() { for (int rounds = 0; rounds < numRounds; rounds++) {giveEachCreatureOneTurn(); pause(100); } }
 * </pre>
 */
class DarwinTest {

	public static void main(String s[]) {
		World world;
		Random rand;
		ArrayList<Creature> creatures;

		System.out.println("*** DarwinTest Simulator ***");

		world = new World(15, 15);
		rand = new Random();
		creatures = new ArrayList<Creature>();
		WorldMap.createWorldMap(15, 15);

		for (int i = 0; i < s.length; i++) {
			Species sp = new Species(s[i]);
			for (int j = 0; j < 10; j++) {
				Position pos;
				do {
					pos = new Position(rand.nextInt(15), rand.nextInt(15));
				} while (world.get(pos) != null);

				Creature c = new Creature(sp, world, pos, rand.nextInt(4));
				world.set(pos, c);
				creatures.add(c);
			}
		}

		for (int j = 0; j < 100; j++) {
			WorldMap.pause(500);
			for (int i = 0; i < creatures.size(); i++) {
				Creature c = creatures.get(i);
				c.takeOneTurn();
			}
		}
	}
}
