/**
 * Main class that handles population Queries
 * 
 * @author Your Name Here
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class PopulationQuery {
	// next four constants are relevant to parsing
	private static final int TOKENS_PER_LINE = 7;
	private static final int POPULATION_INDEX = 4; // zero-based indices
	private static final int LATITUDE_INDEX = 5;
	private static final int LONGITUDE_INDEX = 6;

	private static int minRow, maxRow, minCol, maxCol;

	/**
	 * parse the input file into a large array held in a CensusData object
	 * 
	 * @param filename
	 *            - name of file on disk that holds census data
	 * @return CensusData with all data translated using Mercator Proj.
	 */
	public static CensusData parse(String filename) {
		CensusData result = new CensusData();

		try {
			BufferedReader fileIn = new BufferedReader(new FileReader(filename));

			// Skip the first line of the file
			// After that each line has 7 comma-separated numbers (see constants
			// above)
			// We want to skip the first 4, the 5th is the population (an int)
			// and the 6th and 7th are latitude and longitude (floats)
			// If the population is 0, then the line has latitude and longitude
			// of +.,-.
			// which cannot be parsed as floats, so that's a special case
			// (we could fix this, but noisy data is a fact of life, more fun
			// to process the real data as provided by the government)

			String oneLine = fileIn.readLine(); // skip the first line

			// read each subsequent line and add relevant data to a big array
			while ((oneLine = fileIn.readLine()) != null) {
				String[] tokens = oneLine.split(",");
				if (tokens.length != TOKENS_PER_LINE)
					throw new NumberFormatException();
				int population = Integer.parseInt(tokens[POPULATION_INDEX]);
				if (population != 0)
					result.add(population,
							Float.parseFloat(tokens[LATITUDE_INDEX]),
							Float.parseFloat(tokens[LONGITUDE_INDEX]));
			}

			fileIn.close();
		} catch (IOException ioe) {
			System.err
					.println("Error opening/reading/writing input or output file.");
			System.exit(1);
		} catch (NumberFormatException nfe) {
			System.err.println(nfe.toString());
			System.err.println("Error in file format");
			System.exit(1);
		}
		return result;
	}

	public static void process(String s) {
		StringTokenizer st = new StringTokenizer(s);

		minRow = Integer.parseInt(st.nextToken());
		maxRow = Integer.parseInt(st.nextToken());
		minCol = Integer.parseInt(st.nextToken());
		maxCol = Integer.parseInt(st.nextToken());
		System.out.println("process " + s);

	}

	/**
	 * argument 1: file name for input data: pass this to parse
	 * 
	 * argument 2: number of x-dimension buckets argument 3: number of
	 * y-dimension buckets argument 4: -v1, -v2, -v3, -v4. etc.
	 */
	public static void main(String[] args) {
		int rows, cols;
		boolean parallel = false, hard = false;

		CensusData data = parse(args[0]);

		// we'll calculate this sequentially, and in the obvious way to
		// avoid errors.
		int totalPopulation = 0;
		for (int i = 0; i < data.length(); i++) {
			CensusGroup cg = data.get(i);
			totalPopulation += cg.getPopulation();
		}

		cols = Integer.parseInt(args[1]);
		rows = Integer.parseInt(args[2]);

		if (args[3].equals("-v1")) {
			parallel = false;
			hard = false;
		} else if (args[3].equals("-v2")) {
			parallel = true;
			hard = false;
		} else if (args[3].equals("-v3")) {
			parallel = false;
			hard = true;
		} else if (args[3].equals("-v4")) {
			parallel = true;
			hard = true;
		} else {
			System.out.println("unrecognized command line argument:" + args[3]);
			System.exit(0);
		}

		PopulationGrid grid = new PopulationGrid(data, rows, cols, parallel,
				hard);
		System.out.println("Pre-processing time: "
				+ grid.getPreprocessAverageSecs());

		try {

			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			String str = "";
			while (str != null) {
				System.out.print("> Enter min_row max_row min_col max_col: ");
				str = in.readLine();
				process(str);
				int pop = grid.populationQuery(minRow, maxRow, minCol, maxCol);
				double per = Math
						.floor(((double) pop / totalPopulation) * 10000) / 100.0;
				System.out.println("Population: " + pop + " (" + per + "%)");
				System.out.println("Query time: "
						+ grid.getLastQueryAverageSecs() + " secs");
			}
		} catch (Exception e) {

		}

	}
}
