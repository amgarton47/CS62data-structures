package junitmods;


import java.util.ArrayList;

import org.junit.runner.notification.Failure;

public class JUnitJSONObject {

	private int total_count;
	private int passed_count;
	private int failed_count;
	private long time;
	private String serializer = "PomonaRunner";
	private String version = "0.0.2";

	private ArrayList<PCFailure> failures;
	private ArrayList<String> passes;

	public JUnitJSONObject() {
		failures = new ArrayList<>();
		passes = new ArrayList<>();

		total_count = 0;
		passed_count = 0;
		failed_count = 0;
		time = 0;
	}

	public void addPass(String methodName) {
		passed_count++;
		total_count++;
		passes.add(methodName);
	}

	public void addFailure(Failure failure) {
		failed_count++;
		total_count++;
		failures.add(new PCFailure(failure));
	}

	public void updateTime(long time) {
		this.time = time;
	}


	public class PCFailure {
		private String testname;
		private String message = " ";
		private String trace;

		public PCFailure(Failure fail) {
			testname = fail.getTestHeader();
			if (fail.getMessage() != null)
				message = fail.getMessage();
			trace = fail.getTrace();
		}
	}
}
