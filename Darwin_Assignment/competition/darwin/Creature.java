import structure5.*;
import java.util.*;

/**
 * This class represents one creature on the board. Each creature must remember
 * its species, position, direction, and the world in which it is living.
 * <p>
 * In addition, the Creature must remember the next instruction out of its
 * program to execute.
 * <p>
 * The creature is also repsonsible for making itself appear in the WorldMap.
 * In fact, you should only update the WorldMap from inside the Creature class.
 */

public class Creature {
	Species species;
	World world;
	Position pos;
	int dir;
	int pc;

  public static final int MAX_INSTRUCTIONS_PER_TURN = 10000;

	Random rand = new Random();

	/**
	 * Create a creature of the given species, with the indicated position and
	 * direction. Note that we also pass in the world-- remember this world, so
	 * that you can check what is in front of the creature and to update the
	 * board when the creature moves.
	 */
	public Creature(Species species, World world, Position pos, int dir) {
		this.species = species;
		this.world = world;
		this.pos = pos;
		this.dir = dir;
		pc = 1;
		updateBoard();
	}

	/**
	 * Return the species of the creature.
	 */
	public Species species() {
		return species;
	}

	/**
	 * Return the current direction of the creature.
	 */
	public int direction() {
		return dir;
	}

	/**
	 * Return the position of the creature.
	 */
	public Position position() {
		return pos;
	}

	protected void updateBoard() {
		char c = species().getName().charAt(0);
		WorldMap.displaySquare(pos, c, dir, species.getColor());
	}

	/**
	 * Execute steps from the Creature's program until a hop, left, right, or
	 * infect instruction is executed.
	 */
	public void takeOneTurn() {
    int instcount = 0;
		while (instcount < Creature.MAX_INSTRUCTIONS_PER_TURN) {
			Instruction instr = species.programStep(pc);
			Position next = pos.getAdjacent(dir);
			pc++;
			instcount++;
			switch (instr.getOpcode()) {
				case Instruction.HOP :
					{
						if (world.inRange(next) && world.get(next) == null) {
							world.set(pos, null);
							WorldMap.displaySquare(pos, ' ', 0, "");
							world.set(next, this);
							pos = next;
							updateBoard();
						}
						return;
					}
				case Instruction.LEFT :
					dir = leftFrom(dir);
					updateBoard();
					return;
				case Instruction.RIGHT :
					dir = rightFrom(dir);
					updateBoard();
					return;
				case Instruction.INFECT :
					{
						if (world.inRange(next) && world.get(next) != null) {
							Creature c = (Creature) world.get(next);
							if (c.species != species) {
								c.species = species;
								c.pc = instr.getAddress();
								WorldMap.displaySquare(
									c.pos,
									species().getName().charAt(0),
									c.dir,
									species().getColor());
							}
						}
						return;
					}
				case Instruction.IFEMPTY :
					{
						if (world.inRange(next) && world.get(next) == null) {
							pc = instr.getAddress();
						}
						break;
					}
				case Instruction.IFWALL :
					{
						if (!world.inRange(next)) {
							pc = instr.getAddress();
						}
						break;
					}
				case Instruction.IFSAME :
					{
						if (world.inRange(next) && world.get(next) != null) {
							if (((Creature) world.get(next)).species()
								== species) {
								pc = instr.getAddress();
							}
						}
						break;
					}
				case Instruction.IFENEMY :
					{
						if (world.inRange(next) && world.get(next) != null) {
							if (((Creature) world.get(next)).species()
								!= species) {
								pc = instr.getAddress();
							}
						}
						break;
					}
				case Instruction.IFRANDOM :
					{
						if (rand.nextInt(2) == 1) {
							pc = instr.getAddress();
						}
						break;
					}
				case Instruction.GO :
					{
						pc = instr.getAddress();
					}
			}
		}
	}

	/**
	 * Return the compass direction the is 90 degrees left of the one passed in.
	 */
	public static int leftFrom(int direction) {
		Assert.pre(0 <= direction && direction < 4, "Bad direction");
		return (4 + direction - 1) % 4;
	}

	/**
	 * Return the compass direction the is 90 degrees right of the one passed
	 * in.
	 */
	public static int rightFrom(int direction) {
		Assert.pre(0 <= direction && direction < 4, "Bad direction");
		return (direction + 1) % 4;
	}

	/**
	 * This main program reads in one species, creates one creature, and gives
	 * it twenty turns.
	 * 
	 * @pre Atleast one file is given on the command line.
	 */
	public static void main(String st[]) {
		Assert.pre(
			st.length > 0,
			"Must give one species file name on the command line.");

		// create the world map before anything else
		WorldMap.createWorldMap(15, 15);

		// create the world- must be same size a world map
		World w = new World(15, 15);

		// use a command line argument for the species name
		Species s = new Species(st[0]);
		Species s2 = new Species(st[1]);
		
		// put one creature on the world and run it for twenty steps.
		Creature c = new Creature(s, w, new Position(3, 3), Position.NORTH);
		Creature c2 = new Creature(s, w, new Position(5, 5), Position.NORTH);
		Creature c3 = new Creature(s2, w, new Position(7, 7), Position.NORTH);
		Creature c4 = new Creature(s, w, new Position(6, 5), Position.NORTH);
		Creature c5 = new Creature(s, w, new Position(8, 5), Position.NORTH);
		
		for (int i = 0; i < 150; i++) {
			c.takeOneTurn();
			c2.takeOneTurn();
			c3.takeOneTurn();
			c4.takeOneTurn();
			c5.takeOneTurn();
			WorldMap.pause(500);
		}
	}
}
