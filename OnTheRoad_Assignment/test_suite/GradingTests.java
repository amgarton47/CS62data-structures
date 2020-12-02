package onTheRoad;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GradingTests {
	private static final String TEST_DIR = "/Users/drk04747/classes/cs62/grading/assign10/script/";		
	
	/**
	 * Basic Dijkstra's + trip requests
	 */
	@Test
	void shortTest() {
		System.out.println("---------------------------");
		System.out.println("Short test");
		String[] input = new String[1];		
		input[0] = TEST_DIR + "short-test.txt";
		Optimizer.main(input);
		System.out.println();
	}
	
	/**
	 * Bigger Dijkstra's + trip requests
	 */
	@Test
	void longTest() {
		System.out.println("---------------------------");
		System.out.println("Long test");
		String[] input = new String[1];		
		input[0] = TEST_DIR + "long-test.txt";
		Optimizer.main(input);
		System.out.println();
	}
	
	/**
	 * Test file parser error handling (should not raise an exception, but should terminate
	 * and print out an error.
	 */
	@Test
	void brokenTest() {
		System.out.println("---------------------------");
		System.out.println("Broken test");
		System.out.println("Should print out error about file formatting");
		String[] input = new String[1];		
		input[0] = TEST_DIR + "brokenFile.txt";
		Optimizer.main(input);
		System.out.println();
	}


	/**
	 * Should print out that the graph is not conneccted
	 */
	@Test
	void disjointTest() {
		System.out.println("---------------------------");
		System.out.println("Disjoint test");
		System.out.println("Should print out that the graph is disconnected");
		String[] input = new String[1];		
		input[0] = TEST_DIR + "disjoint-test.txt";
		Optimizer.main(input);
		System.out.println();
	}
	
	@Test
	void weakTest() {
		System.out.println("---------------------------");
		System.out.println("Weak test");
		System.out.println("Should print out that the graph is disconnected");
		String[] input = new String[1];		
		input[0] = TEST_DIR + "weak-test.txt";
		Optimizer.main(input);
		System.out.println();
	}
	
	@Test
	void weakTest2() {
		System.out.println("---------------------------");
		System.out.println("Weak test2");
		System.out.println("Should print out that the graph is disconnected");
		String[] input = new String[1];		
		input[0] = TEST_DIR + "weak-test-2.txt";
		Optimizer.main(input);
		System.out.println();
	}
	
	@Test
	void testBFS() {
		final double epsilon = 0.001;
		FileParser fp = new FileParser(TEST_DIR + "long-test.txt");
		EdgeWeightedDigraph distance = fp.makeGraph(true);
		GraphAlgorithms.breadthFirstSearch(distance, 0);
		
		double[] correct = {0.0, 1.0, 2.0, 1.0, 2.0, 3.0, 2.0, 3.0, 4.0,
				            3.0, 4.0, 1.0, 3.0, 5.0, 1.0, 2.0, 3.0};
		
		int incorrect = 0;
		
		for(int v = 0; v < distance.V(); v++ ) {
			if( Math.abs(distance.getDist(v) - correct[v])  > epsilon ) {
				incorrect++;
			}
		}
		
		if( incorrect > 0 ) {
			fail("BFS: " + incorrect + " incorrect out of " + correct.length);
		}
	}
	
	/**
	 * Note this test assumes that they use the graph to store
	 * distances and previous edges.
	 */
	@Test
	void testDijkstras() {
		final double epsilon = 0.001;
		FileParser fp = new FileParser(TEST_DIR + "long-test.txt");
		EdgeWeightedDigraph distance = fp.makeGraph(true);
		GraphAlgorithms.dijkstra(distance, 0);
		
		double[] distances = {0.0, 1.5, 3.5, 2.3, 2.9, 4.9, 4.8, 5.4, 6.09, 7.199999999999999, 7.4, 0.05, 3.0, 6.16, 0.06, 2.81, 6.01};
		int[] prevs = {0, 0, 1, 0, 15, 4, 3, 4, 16, 6, 7, 0, 4, 8, 0, 14, 15};
		
		int incorrect = 0;
		
		for(int v = 0; v < distance.V(); v++ ) {
			if( Math.abs(distance.getDist(v) - distances[v])  > epsilon ) {
				incorrect++;
			}
		}
		
		if( incorrect > 0 ) {
			fail("Dijstrka distances: " + incorrect + " incorrect out of " + distances.length);
		}
		
		incorrect = 0;
		
		for(int v = 1; v < distance.V(); v++ ) {
			if( distance.getEdgeTo(v).from() != prevs[v] ) {
				incorrect++;
			}
		}
		
		if( incorrect > 0 ) {
			fail("Dijstrka edge to: " + incorrect + " incorrect out of " + distances.length);
		}
	}
}
