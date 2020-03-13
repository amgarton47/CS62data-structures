import structure5.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.io.*;
import java.awt.*;
import objectdraw.*;

/**
 * Runs random samples from a round robin tournament.
 * 
 * This requires the special version of WorldMap that has the DISPLAY boolean
 * variable for whether or not to actually show the simulation in the window.
 * Set display to false to turn off the window (it is way faster that way).
 */
public class DarwinShowOff extends WindowController {

	private static final int PAUSE_TIME = 60;
	public static final int width = 15;
	public static final int height = 15;

	private World<Creature> world;
	private static Random rand = new Random(20);
	private ArrayList<Creature> creatures;

	public DarwinShowOff(int w, int h) {
		world = new World<Creature>(w, h);
		creatures = new ArrayList<Creature>();
	}

	public DarwinShowOff() {
		this(15, 15);
		// WorldMap.DISPLAY = false;
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

	public void runSim() {
		for (int r = 0; r < 200; r++) {
      Collections.shuffle(creatures);
			for (int i = 0; i < creatures.size(); i++) {
				Creature c = creatures.get(i);
				c.takeOneTurn();
			}
			if (WorldMap.DISPLAY)
				WorldMap.pause(PAUSE_TIME);
		}
	}

	public int countSpecies(Species sp) {
		int c = 0;
		for (int i = 0; i < world.width(); i++) {
			for (int j = 0; j < world.height(); j++) {
				Creature cr = world.get(new Position(i, j));
				if (cr != null && cr.species() == sp)
					c++;
			}
		}
		return c;
	}

	private String s[]; // file names
	private Species sp[]; // species
	private int totals[]; // running score
	private int chances[]; // running chances
	private FilledRect bars[]; // bars for each species
	private Text text[]; // text to store the totals for species
	private Text names[]; // text for the names of species

  public static void main(String[] args) {
    DarwinShowOff drr = new DarwinShowOff();
    drr.begin();
  }

	public void begin() {

		ArrayList<String> contestants = new ArrayList<String>();
		try {
			ReadStream in =
				new ReadStream(new FileInputStream("contestants.txt"));
			while (!in.eof()) {
				contestants.add(in.readString());
				in.readLine();
			}
			in.close();
		} catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}

		WorldMap.createWorldMap(15, 15);

		s = new String[contestants.size()];
		for (int i = 0; i < contestants.size(); i++)
			s[i] = contestants.get(i);

		if (s.length == 0) {
			System.out.println("You must supply at least one Species file");
			System.out.println("Example: java Darwin Hop.txt Rover.txt");
			return;
		}

		totals = new int[s.length];
    chances = new int[s.length];
		sp = new Species[s.length];
		text = new Text[s.length];
		names = new Text[s.length];

		bars = new FilledRect[s.length];
		for (int i = 0; i < bars.length; i++) {
			sp[i] = new Species(s[i]);
			names[i] = new Text(sp[i].getName(), 50, 40 + i * 50 - 15, canvas);
			text[i] = new Text("0", 10, 40 + i * 50 - 15, canvas);
			bars[i] = new FilledRect(50, 40 + i * 50, 10, 10, canvas);
			bars[i].setColor(WorldMap.stringToColor(sp[i].getColor()));
		}

		(new Runner()).start();

	}

	private class Runner extends ActiveObject {

		public void run() {

			int trounds = 0;

			for (int rounds = 0; rounds < 5; rounds++) {
				if (rounds == 1)
					WorldMap.DISPLAY = false;
				System.out.println("Round " + (rounds + 1));
        while (true) {
          // Pick random contestants:
          int i, j, k, l;
          i = rand.nextInt(s.length);
          j = rand.nextInt(s.length);
          while (j == i) {
            j = rand.nextInt(s.length);
          }
          k = rand.nextInt(s.length);
          while (k == i || k == j) {
            k = rand.nextInt(s.length);
          }
          l = rand.nextInt(s.length);
          while (l == i || l == j || l == k) {
            l = rand.nextInt(s.length);
          }

          // Compete:
					System.out.println("Current players: "+s[i]+" "+s[j]+" "+s[k]+" "+s[l]);
					names[i].setColor(Color.blue);
					names[j].setColor(Color.blue);
					names[k].setColor(Color.blue);
					names[l].setColor(Color.blue);

					WorldMap.reset();
					DarwinShowOff d = new DarwinShowOff(width, height);
					d.placeCreatures(sp[i], 10);
					d.placeCreatures(sp[j], 10);
					d.placeCreatures(sp[k], 10);
					d.placeCreatures(sp[l], 10);

					d.runSim();

          // Accumulate totals:
					int t;
					t = d.countSpecies(sp[i]);
					totals[i] += t;
					t = d.countSpecies(sp[j]);
					totals[j] += t;
					t = d.countSpecies(sp[k]);
					totals[k] += t;
					t = d.countSpecies(sp[l]);
					totals[l] += t;

          // Accumulate chances:
          chances[i] += 40;
          chances[j] += 40;
          chances[k] += 40;
          chances[l] += 40;

          // Enable this code for a console "leaderboard"
          for (int zz = 0; zz < s.length; ++zz) {
            if (chances[zz] > 0) {
              System.out.format(
                  "% 4d / % 4d  =  %5.1f%%   %s\n",
                  totals[zz],
                  chances[zz],
                  100*(totals[zz]/(double)chances[zz]),
                  s[zz]
              );
            } else {
              System.out.format(
                  "% 4d / % 4d  =    ?? %%   %s\n",
                  totals[zz],
                  chances[zz],
                  s[zz]
              );
            }
          }

					trounds++;

					//				if (trounds == 20) WorldMap.DISPLAY = false;

					for (int m = 0; m < s.length; m++) {
						bars[m].setWidth(
							((double) totals[m])
								/ (trounds * 40)
								* 500);
						text[m].setText("" + totals[m]);
						text[m].setColor(Color.black);
						names[m].setColor(Color.black);
					}

					WorldMap.updateAll();
					pause(100);
				}
			}

			System.out.println("*******************");
			for (int i = 0; i < s.length; i++) {
				System.out.println(s[i] + "    " + totals[i]);
			}
		}
	}
}
