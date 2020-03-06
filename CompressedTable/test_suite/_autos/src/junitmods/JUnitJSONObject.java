/*
 * This class decodes JUnit TestExecutionResults and creates
 * a record of the passage/failure of each test case.   When
 * the test is over this information will be printed out in
 * JSON as a record of the results.
 */
package junitmods;

import java.util.ArrayList;
import org.junit.platform.engine.TestExecutionResult;

public class JUnitJSONObject {
	
	private int total_count;	// total number of test cases
	private int passed_count;	// number passed
	private int failed_count;	// number failed
	private long time;			// elapsed milliseconds
	private String serializer = "PomonaRunner";
	private String version = "0.0.1";
	
	private ArrayList<PCFailure> failures;
	private ArrayList<String> passes;
	
	/*
	 * instantiate a new results list
	 */
	public JUnitJSONObject() {
		failures = new ArrayList<>();
		passes = new ArrayList<>();
		
		total_count = 0;
		passed_count = 0;
		failed_count = 0;
		time = 0;
	}
	
	/*
	 * record the successful execution of a test case
	 */
	public void addPass(String methodName) {
		passed_count++;
		total_count++;
		passes.add(methodName);
	}
	
	/*
	 * record the failure of a test case
	 */
	public void addFailure(String methodName, TestExecutionResult result) {
		failed_count++;
		total_count++;
		failures.add(new PCFailure(methodName, result));	
	}
	
	/*
	 * record the elapsed time for the entire suite
	 */
	public void updateTime(long time) {
		this.time = time;
	}
	
	
	/*
	 * this class represents the record of a single test case failure
	 */
	public class PCFailure {
		private String testname;	// name of the test case
		private String message;		// description of why it failed
		private String trace;		// stack-trace to point of failure
				
		public PCFailure(String name, TestExecutionResult result) {
			testname = name;

			if (result.getThrowable().isPresent())
				// if there is a message use it, else the exception string
				message = result.getThrowable().get().getMessage();
				if (message == null || message == "")
					message = result.getThrowable().get().toString();

				// if there is a stack trace, capture it
				trace = "";
				StackTraceElement stack[] = result.getThrowable().get().getStackTrace();
				for (int frame = 0; frame < stack.length; frame++)
						trace += stack[frame].toString() + "\n";

			// if we didn't get a message, complain
			if (message == null || message == "")
				message = "!!!NO CAUSE!!!";
		}
	}
}
