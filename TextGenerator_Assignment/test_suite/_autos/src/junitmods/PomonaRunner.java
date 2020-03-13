/*
 * This is a Java application that runs a JUnit test suite, accumulates
 * the results, and then writes them out to a JSON test report.
 *
 * This is boiler-plate code that should be nearly identical for every
 * JUnit based test ... except for the package and class.
 */
package junitmods;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;
import static org.junit.platform.launcher.EngineFilter.*;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

public class PomonaRunner {
	private static final String PACKAGE = "wordsGeneric";	// CHANGE for each project
	private static final String TEST1 = "FreqListTest";
	private static final String TEST2 = "TextGeneratorTest";

	public static void main(String[] args) {
		if (args.length <= 0) {
			System.err.println("No output file specified!");
			System.exit(1);
		}
		try {
			// create the output writer to record the results
			Writer writer = new FileWriter(args[0]);

			// find find the test cases
			LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request().
				// this one has multiple testers
				selectors(selectPackage(PACKAGE), 
					selectClass(Class.forName(PACKAGE+"."+TEST1)),
					selectClass(Class.forName(PACKAGE+"."+TEST2))
				).
				// this generates warnings if there are no legacy test cases
				filters(excludeEngines("junit-vintage")).
				build();

			// create a launcher and run the tests
			Launcher launcher = LauncherFactory.create();

			// create a listener and execute the test suite
			TestExecutionListener listener = new JSONListener(writer, new JUnitJSONObject());
			//launcher.registerTestExecutionListeners(listener);
			launcher.execute(request, listener);

			// close the file and exit gracefully
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
