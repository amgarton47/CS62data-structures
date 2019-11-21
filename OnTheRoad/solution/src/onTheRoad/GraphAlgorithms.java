package onTheRoad;

/**
 * Common algorithms for Graphs. 
 * They all assume working with a EdgeWeightedDirected graph.
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;

public class GraphAlgorithms {

	/**
	 * Reverses the edges of a graph
	 * 
	 * @param g
	 *            edge weighted directed graph
	 * @return graph like g except all edges are reversed
	 */
	public static EdgeWeightedDigraph graphEdgeReversal(EdgeWeightedDigraph g) {
		EdgeWeightedDigraph gRev = new EdgeWeightedDigraph(g.V());

		for (DirectedEdge edge : g.edges()) {
			gRev.addEdge(new DirectedEdge(edge.to(), edge.from(), edge.weight()));
		}

		return gRev;
	}

	/**
	 * Performs breadth-first search of g from vertex start.
	 * 
	 * @param g
	 *            directed edge weighted graph
	 * @param start
	 *            index of starting vertex for search
	 */
	public static void breadthFirstSearch(EdgeWeightedDigraph g, int start) {
		g.reset();
		Deque<Integer> q = new ArrayDeque<Integer>();
		q.add(start);
		g.visit(new DirectedEdge(start, start, 0.0), 0.0);
		while (!q.isEmpty()) {
			int v = q.remove();
			for (DirectedEdge edge : g.adj(v)) {
				int w = edge.to();
				if (!g.isVisited(w)) {
					g.visit(edge, g.getDist(v) + 1.0);
					q.add(w);
				}
			}
		}
	}

	/**
	 * Calculates whether the graph is strongly connected
	 * 
	 * @param g
	 *            directed edge weighted graph
	 * @return whether graph g is strongly connected.
	 */
	public static boolean isStronglyConnected(EdgeWeightedDigraph g) {
		// do breadth-first search from start and make sure all vertices
		// have been visited. If not, return false.
		breadthFirstSearch(g, 0);
		for (int i = 0; i < g.V(); i++) {
			if (!g.isVisited(i)) {
				return false;
			}
		}

		// now reverse the graph, do another breadth-first search,
		// and make sure all visited again. If not, return false
		EdgeWeightedDigraph gRev = graphEdgeReversal(g);
		breadthFirstSearch(gRev, 0);
		for (int i = 0; i < g.V(); i++) {
			if (!gRev.isVisited(i)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Runs Dijkstra's algorithm on path to calculate the shortest path from
	 * starting vertex to every other vertex of the graph.
	 * 
	 * @param g
	 *            directed edge weighted graph
	 * @param s
	 *            starting vertex
	 * @return a hashmap where a key-value pair <i, path_i> corresponds to the i-th
	 *         vertex and path_i is an arraylist that contains the edges along the
	 *         shortest path from s to i.
	 */
	public static HashMap<Integer, ArrayList<DirectedEdge>> dijkstra(EdgeWeightedDigraph g, int s) {
		g.reset();
		HashMap<Integer, ArrayList<DirectedEdge>> result = new HashMap<Integer, ArrayList<DirectedEdge>>();

		for (int v = 0; v < g.V(); v++)
			g.setDist(v, Double.POSITIVE_INFINITY);
		g.setDist(s, 0.0);

		// relax vertices in order of distance from s
		IndexMinPQ<Double> pq = new IndexMinPQ<Double>(g.V());
		pq.insert(s, g.getDist(s));
		while (!pq.isEmpty()) {
			int v = pq.delMin();
			for (DirectedEdge e : g.adj(v))
				relax(g, e, pq);
		}

		for (int t = 0; t < g.V(); t++) {
			if (g.getDist(t) < Double.POSITIVE_INFINITY) {
				ArrayList<DirectedEdge> path = new ArrayList<DirectedEdge>();
				for (DirectedEdge e = g.getEdgeTo(t); e != null; e = g.getEdgeTo(e.from())) {
					path.add(e);
				}
				result.put(t, path);
			}
			if (!result.containsKey(t)) {
				result.put(t, null);
			}

		}
		return result;

	}

	/**
	 * Relaxes edge e of graph g and updates min-priority queue pq if changes.
	 * 
	 * @param g
	 *            directed edge weighted graph
	 * @param e
	 *            edge to be relaxed
	 * @param pq
	 *            min-priority queue
	 */
	private static void relax(EdgeWeightedDigraph g, DirectedEdge e, IndexMinPQ<Double> pq) {
		int v = e.from(), w = e.to();

		if (g.getDist(w) > g.getDist(v) + e.weight()) {
			g.setDist(w, g.getDist(v) + e.weight());
			g.setEdgeTo(e);

			if (pq.contains(w))
				pq.decreaseKey(w, g.getDist(w));
			else
				pq.insert(w, g.getDist(w));
		}
	}

	/**
	 * Computes shortest path from start to end using Dijkstra's algorithm.
	 *
	 * @param g
	 *            directed graph
	 * @param start
	 *            starting node in search for shortest path
	 * @param end
	 *            ending node in search for shortest path
	 * @return a list of edges in that shortest path in correct order
	 */
	public static ArrayList<DirectedEdge> getShortestPath(EdgeWeightedDigraph g, int start, int end) {
		// run dijkstra and create a new ArrayList with edges running from start to end.
		HashMap<Integer, ArrayList<DirectedEdge>> map = dijkstra(g, start);
		ArrayList<DirectedEdge> backward = map.get(end);
		ArrayList<DirectedEdge> forward = new ArrayList<DirectedEdge>();
		for (int i = backward.size() - 1; i >= 0; i--) {
			forward.add(backward.get(i));
		}

		return forward;
	}

	/**
	 * Using the output from getShortestPath, print the shortest path
	 * between two nodes
	 * 
	 * @param path shortest path from start to end
	 * @param isDistance prints it based on distance (true) or time (false)
	 */
	public static void printShortestPath(ArrayList<DirectedEdge> path, boolean isDistance, List<String> vertices) {
		System.out.println(" Begin at " + vertices.get(path.get(0).from()));
		double totalWeights = 0.0;
		for (DirectedEdge e : path) {
			if (e != null) {
				if (isDistance)
					System.out.println(" Continue to " + vertices.get(e.to()) + " (" + e.weight() + ")");
				else
					System.out.println(" Continue to " + vertices.get(e.to()) + " (" + hoursToHMS(e.weight()) + ")");
				totalWeights += e.weight();
			}
		}

		if (isDistance)
			System.out.println("Total distance: " + totalWeights + " miles");
		else
			System.out.println("Total time: " + hoursToHMS(totalWeights));
	}

	/**
	 * Converts hours (in decimal) to hours, minutes, and seconds
	 * 
	 * @param rawhours
	 *            time elapsed
	 * @return Equivalent of rawhours in hours, minutes, and seconds (to nearest
	 *         10th of a second)
	 */
	private static String hoursToHMS(double rawhours) {
		// Will hold all output
		String output = "";
		// number of hours in rawhours
		int hours = (int) (rawhours);
		// remember whether started reporting time units yet
		boolean started = false;
		if (hours > 0) {
			output = output + hours + " hours ";
			started = true;
		}
		// Once hours are removed, calculate number of 10th seconds still left
		int tenthSeconds = (int) Math.round(36000 * (rawhours - hours));
		// number of whole minutes still left
		int minutes = tenthSeconds / 600;
		if (minutes > 0 || started) {
			output = output + minutes + " minutes ";
		}
		// number of 10th of second left after minutes removed
		int tenthSecondsLeft = tenthSeconds - minutes * 600;
		return output + tenthSecondsLeft / 10.0 + " seconds";
	}
}
