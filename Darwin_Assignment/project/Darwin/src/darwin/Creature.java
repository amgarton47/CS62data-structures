package darwin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/**
 * This class represents one creature on the board. Each creature must remember
 * its species, position, direction, and the world in which it is living. In
 * addition, the Creature must remember the next instruction out of its program
 * to execute. The creature is also responsible for making itself appear in the
 * WorldMap. In fact, you should only update the WorldMap from inside the
 * Creature class.
 */
public class Creature {
	Species species;
	World world;
	Position pos;
	int dir;
	int programStep;

	/**
	 * Create a creature of the given species, with the indicated position and
	 * direction. Note that we also pass in the world-- remember this world, so that
	 * you can check what is in front of the creature and update the board when the
	 * creature moves.
	 */
	public Creature(Species species, World world, Position pos, int dir) {
		this.species = species;
		this.world = world;
		this.pos = pos;
		this.dir = dir;

		programStep = 1;

		WorldMap.displaySquare(pos, species.getSpeciesChar(), dir, species.getColor());
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
	 * Sets the current direction of the creature to the given value
	 */
	public void setDirection(int dir) {
		this.dir = dir;
	}

	/**
	 * Return the position of the creature.
	 */
	public Position position() {
		return pos;
	}

	/**
	 * Execute steps from the Creature's program starting at step #1 continue until
	 * a hop, left, right, or infect instruction is executed.
	 */
	public void takeOneTurn() {
		Instruction i = species.programStep(programStep);
		System.out.println("here");

		while (i.getOpcode() != Instruction.HOP && i.getOpcode() != Instruction.LEFT
				&& i.getOpcode() != Instruction.RIGHT && i.getOpcode() != Instruction.INFECT) {

			if (i.getOpcode() == Instruction.IFEMPTY) {
				Position nextPos = pos.getAdjacent(dir);
				if (world.inRange(nextPos) && world.get(nextPos) == null) {
					programStep = i.getAddress();
				}
			} else if (i.getOpcode() == Instruction.IFWALL) {
				Position nextPos = pos.getAdjacent(dir);
				if (!world.inRange(nextPos)) {
					programStep = i.getAddress();
				}
			} else if (i.getOpcode() == Instruction.IFSAME) {
				Position nextPos = pos.getAdjacent(dir);
				if (world.inRange(nextPos) && world.get(nextPos) != null
						&& world.get(nextPos).species.getName().equals(species.getName())) {
					programStep = i.getAddress();
				}
			} else if (i.getOpcode() == Instruction.IFENEMY) {
				Position nextPos = pos.getAdjacent(dir);

				if (world.inRange(nextPos) && world.get(nextPos) != null
						&& !world.get(nextPos).species.getName().equals(species.getName())) {

					programStep = i.getAddress();

				}
			} else if (i.getOpcode() == Instruction.IFRANDOM) {
				Random r = new Random();
				if (r.nextDouble() > 0.5) {
					programStep = i.getAddress();
				}
			} else if (i.getOpcode() == Instruction.GO) {
				programStep = i.getAddress();
			}

			programStep++;
			if (programStep >= species.programSize()) {
				programStep = 1;
			}

			i = species.programStep(programStep);

			System.out.println("here1");
			System.out.println(i.getOpcode());
			System.out.println(programStep);
		}

		if (i.getOpcode() == Instruction.HOP) {
			Position newPos = pos.getAdjacent(dir);
			if (world.inRange(newPos) && world.get(newPos) == null) {
				WorldMap.displaySquare(pos, ' ', dir, "blue");
				WorldMap.displaySquare(newPos, species.getSpeciesChar(), dir, species.getColor());
				world.set(pos, null);
				world.set(newPos, this);
				pos = newPos;
			}
		} else if (i.getOpcode() == Instruction.RIGHT) {
			dir = rightFrom(dir);
			WorldMap.displaySquare(pos, species.getSpeciesChar(), dir, species.getColor());
		} else if (i.getOpcode() == Instruction.LEFT) {
			dir = leftFrom(dir);
			WorldMap.displaySquare(pos, species.getSpeciesChar(), dir, species.getColor());
		} else if (i.getOpcode() == Instruction.INFECT) {
			Position nextPos = pos.getAdjacent(dir);
			if (world.inRange(nextPos) && world.get(nextPos) != null && !world.get(nextPos).species.equals(species)) {
				world.get(nextPos).species = species;
				world.get(nextPos).programStep = programStep;

				WorldMap.displaySquare(nextPos, species.getSpeciesChar(), dir, species.getColor());
			}
		}

		programStep++;
		if (programStep >= species.programSize()) {
			programStep = 1;
		}
		System.out.println("here2");
	}

	/**
	 * Return the compass direction that is 90 degrees left of the one passed in.
	 */
	public static int leftFrom(int direction) {
		return (4 + direction - 1) % 4;
	}

	/**
	 * Return the compass direction that is 90 degrees right of the one passed in.
	 */
	public static int rightFrom(int direction) {
		return (direction + 1) % 4;
	}

	public static void main(String[] args) {
		try {
			BufferedReader fr = new BufferedReader(new FileReader("../Creatures/Rover.txt"));
			Species s1 = new Species(fr);

			WorldMap.createWorldMap(10, 10);
			World w = new World(10, 10);

			ArrayList<Creature> creatures = new ArrayList<Creature>();

			for (int i = 0; i < 10; i++) {
				Creature c1 = new Creature(s1, w, new Position(i, i), i % 4);
				creatures.add(c1);
			}

			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < creatures.size(); j++) {
					WorldMap.pause(200);
					creatures.get(j).takeOneTurn();
				}

			}
		} catch (Exception e) {
			System.out.println("exception in main try/catch");
			e.printStackTrace();
		}
	}
}
