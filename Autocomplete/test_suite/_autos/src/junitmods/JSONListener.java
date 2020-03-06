/*
 * This is a listener that will be registered to receive call-backs
 * when a test completes (succeeds or fails), and records the results
 * for output as a JSON record.
 *
 * This is boiler-plate code that should be identical for all JUnit
 * based tests.
 */
package junitmods;

import java.io.Writer;
import java.io.IOException;

import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;

import com.google.gson.Gson;

public class JSONListener implements TestExecutionListener {

	private Writer writer;
	private JUnitJSONObject json;
	private long start_time;
	

	public JSONListener(Writer writer, JUnitJSONObject resultCollector) {
		super();
		this.writer = writer;
		this.json = resultCollector;
	}

	/**
	 * Called when test execution begins
	 */
	public void testPlanExecutionStarted(TestPlan testplan) {
		start_time = System.currentTimeMillis();
		// System.out.println("Testplan started at " + start_time);
	}

	/**
	 * Called when all tests have finished
	 *		write out the accumulated results
	 */
	public void testPlanExecutionFinished(TestPlan testplan) {
		long end_time = System.currentTimeMillis();
		json.updateTime(end_time - start_time);
		// System.out.println("Testplan finished at " + end_time);
		try {
			writer.write(new Gson().toJson(json));
		} catch (IOException e) {
			System.err.println("Write error on JSON results output");
		}
	}

	/**
	 * Called when anything has completed (container/test, success/failure)
	 */
	public void executionFinished(TestIdentifier identifier, TestExecutionResult result) {
		// we don't care about the completion of test suites, only tests
		if (identifier.isContainer())
			return;

		// figure out whether or not it worked, and call appropriate handler
		switch(result.getStatus()) {
			case SUCCESSFUL:
				json.addPass(testName(identifier));
				break;

			case FAILED:
				json.addFailure(testName(identifier), result);
				break;

			case ABORTED:
				json.addFailure(testName(identifier), result);
					break;
		}
	}

	/**
	 * extract a method(package.class) name from a TestIdentifier
	 *
	 *	assignment.json files use this form because there might be
	 *	multiple test-suites, and we want to be able to distinguish
	 *	results from each.
	 */
	private String testName(TestIdentifier identifier) {
		String source = identifier.getSource().toString();

		// parse out the method name
		int x_method = source.indexOf("methodName = '");
		if (x_method < 0) {
			System.err.println("ERROR: unable to find methodName in completion for " + source);
			return identifier.getDisplayName();
		}
		String methodName = source.substring(x_method + 14).split("'")[0];

		// parse out the class name
		int x_class = source.indexOf("className = '");
		if (x_class < 0) {
			System.err.println("ERROR: unable to find className in completion for " + source);
			return identifier.getDisplayName();
		}
		String className = source.substring(x_class + 13).split("'")[0];

		// assemble them in the expected format
		return methodName + "(" + className + ")";
	}
}
