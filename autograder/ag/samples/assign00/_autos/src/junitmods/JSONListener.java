package junitmods;

/**
 * Sample from JUnit on GitHub
 */

import java.io.Writer;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import com.google.gson.Gson;

public class JSONListener extends RunListener {

	private Writer writer;
	private JUnitJSONObject json;
	private boolean currentTestStatus;

	public JSONListener(Writer writer, JUnitJSONObject resultCollector) {
		super();
		this.writer = writer;
		this.json = resultCollector;
	}

	/**
	 * Called when all tests have finished
	 */
	public void testRunFinished(Result result) throws java.lang.Exception {
		json.updateTime(result.getRunTime());
		writer.write(new Gson().toJson(json));
	}

	/**
	 * Called when an atomic test is about to be started.
	 */
	public void testStarted(Description description) throws java.lang.Exception {
		currentTestStatus = true;
	}

	/**
	 * Called when an atomic test has finished, whether the test succeeds or
	 * fails.
	 */
	public void testFinished(Description description) throws java.lang.Exception {
		if (currentTestStatus) {
			json.addPass(description.getDisplayName());
		}
	}

	/**
	 * Called when an atomic test fails.
	 */
	public void testFailure(Failure failure) throws java.lang.Exception {
		currentTestStatus = false;
		json.addFailure(failure);
	}
}
