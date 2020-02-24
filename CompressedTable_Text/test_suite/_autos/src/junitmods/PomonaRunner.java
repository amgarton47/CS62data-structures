package junitmods;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.junit.runner.JUnitCore;

public class PomonaRunner {
	public static void main(String[] args) {
		if (args.length <= 0) {
			System.err.println("No output file specified!");
			System.exit(1);
		}
		try {
			JUnitCore runner = new JUnitCore();
			Writer writer = new FileWriter(args[0]);
			runner.addListener(new JSONListener(writer, new JUnitJSONObject()));
			runner.run(compression.Autograder.class);
			writer.close();
			System.exit(0);
		} catch (IOException e) {
			System.err.println("Error opening JSON file to write to:\n" + e);
			System.exit(1);
		} catch (Exception e) {
			System.err.println("Something went wrong with JUnit!\n" + e + "\nPlease examine or handgrade.");
			System.exit(1);
		}
	}

}
