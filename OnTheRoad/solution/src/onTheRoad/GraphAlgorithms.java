package onTheRoad;

/** Common algorithms for Graphs.  All assume working with a directed graph.
 * Written 11/21/2017
 * @author Kim Bruce (based on algorithms in Bailey, Java Structures)
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import structure5.Association;
import structure5.ComparableAssociation;
import structure5.Map;
import structure5.Graph;
import structure5.GraphListDirected;
import structure5.Edge;

import java.util.PriorityQueue;

import structure5.QueueList;
import structure5.Queue;
import structure5.Table;

public class GraphAlgorithms {

	/**
	 * @param g
	 *            directed graph
	 * @return graph like g except all edges are reversed
	 */
	public static <V, E> Graph<V, E> graphEdgeReversal(Graph<V, E> g) {
		Graph<V, E> gRev = new GraphListDirected<V, E>();
		for (V vertex : g) {
			gRev.add(vertex);
		}
		Edge<V, E> edge;
		for (Iterator<Edge<V, E>> edges = g.edges(); edges.hasNext();) {
			edge = edges.next();
			gRev.addEdge(edge.there(), edge.here(), edge.label());
		}

		return gRev;
	}

	/**
	 * Perform bread-first search of g from vertex start. At end, can ask
	 * vertices in if they were visited
	 * 
	 * @param g
	 *            directed graph
	 * @param start
	 *            starting vertex for search
	 */
	public static <V, E> void breadthFirstSearch(Graph<V, E> g, V start) {
		g.reset();
		Queue<V> q = new QueueList<V>();
		g.visit(start);
		q.add(start);
		while (q.size() != 0) {
			// Dequeue a vertex from queue and print it
			V s = q.remove();

			// Get all adjacent vertices of the dequeued vertex s
			// If an adjacent has not been visited, then mark it
			// visited and enqueue it
			Iterator<V> i = g.neighbors(s);
			while (i.hasNext()) {
				V n = i.next();
				if (!g.isVisited(n)) {
					g.visit(n);
					q.add(n);
				}
			}
		}
	}

	/**
	 * @param g
	 *            directed graph
	 * @return whether graph g is strongly connected.
	 */
	public static <V, E> boolean isStronglyConnected(Graph<V, E> g) {
		Iterator<V> it = g.iterator();
		V start = it.next();
		// do breadth-first search from start and make sure all vertices
		// have been visited. If not, return false
		breadthFirstSearch(g, start);
		for (V vertex : g) {
			if (!g.isVisited(vertex)) {
				return false;
			}
		}

		// now reverse the graph, do another breadth-first search,
		// and make sure all visited again. If not, return false
		Graph<V, E> gRev = graphEdgeReversal(g);
		breadthFirstSearch(gRev, start);
		for (V vertex : gRev) {
			if (!gRev.isVisited(vertex)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Perform Dijkstra's algorithm on graph g from vertex start.
	 * 
	 * @param g
	 * @param start
	 * @return map taking each vertex to cost to get there from start and the
	 *         last edge in a shortest path to the vertex
	 */
	public static Map<String, ComparableAssociation<Double, Edge<String, Double>>> dijkstra(
			Graph<String, Double> g, String start) {
		// priority queue to keep track of best distances so far & last edge
		// to get there
		PriorityQueue<ComparableAssociation<Double, Edge<String, Double>>> pq = new PriorityQueue<ComparableAssociation<Double, Edge<String, Double>>>();

		// result tells for each vertex how much it costs to get there from
		// start and what last edge taken to get there
		Map<String, ComparableAssociation<Double, Edge<String, Double>>> result = new Table<String, ComparableAssociation<Double, Edge<String, Double>>>();

		// We can get to start from start with 0 cost & no previous edge
		String v = start;
		ComparableAssociation<Double, Edge<String, Double>> possible = new ComparableAssociation<Double, Edge<String, Double>>(
				0.0, null);

		// Still nodes to explore from v
		while (v != null) {
			if (!result.containsKey(v)) {// haven't recorded shortest path info
											// yet

				// Enter shortest path info on v
				result.put(v, possible);

				// total distance to get to v from start
				double vDist = possible.getKey();

				// Update costs to neighbors
				Iterator<String> nearIt = g.neighbors(v);
				while (nearIt.hasNext()) {
					Edge<String, Double> e = g.getEdge(v, nearIt.next());
					// add cost of edge e to update cost from start to e
					possible = new ComparableAssociation<Double, Edge<String, Double>>(
							vDist + e.label(), e);
					pq.add(possible);
				}
			}

			// get edge on pq w/shortest path from start
			if (!pq.isEmpty()) {
				possible = pq.remove(); // lowest cost vertex
				// destination of new edge
				v = possible.getValue().there();
			} else { // no new vertex, so stop loop
				v = null;
			}
		}
		return result;
	}

	/**
	 * Compute shortest path from start to end using Dijkstra's algorithm
	 * 
	 * @param g
	 *            directed graph
	 * @param start
	 *            starting node in search for shortest path
	 * @param end
	 *            ending node in search for shortest path
	 * @return pair of the total cost from start to end in shortest path as well
	 *         as a list of edges in that shortest path
	 */
	public static Association<Double, ArrayList<Edge<String, Double>>> getShortestPath(
			Graph<String, Double> g, String start, String end) {
		Map<String, ComparableAssociation<Double, Edge<String, Double>>> map = dijkstra(
				g, start);

		ArrayList<Edge<String, Double>> backward = new ArrayList<Edge<String, Double>>();
		ComparableAssociation<Double, Edge<String, Double>> assoc = map
				.get(end);
		double distance = assoc.getKey();
		// put together path in reverse direction, from end to start
		while (assoc.getValue() != null) {
			backward.add(assoc.getValue());
			assoc = map.get(assoc.getValue().here());
		}
		// fix it to go forward from start to end
		ArrayList<Edge<String, Double>> forward = new ArrayList<Edge<String, Double>>();
		for (int count = backward.size() - 1; count >= 0; count--) {
			forward.add(backward.get(count));
		}
		return new Association<Double, ArrayList<Edge<String, Double>>>(
				distance, forward);
	}

	/**
	 * Using the output from Dijkstra, print the shortest path (according to
	 * distance) between two nodes
	 * 
	 * @param pathInfo
	 *            Cost and path information from run of Djikstra
	 */
	public static void printShortestPathDistance(
			Association<Double, ArrayList<Edge<String, Double>>> pathInfo) {
		ArrayList<Edge<String, Double>> path = pathInfo.getValue();
		System.out.println("   Begin at " + path.get(0).here());
		for (Edge<String, Double> e : path) {
			System.out.println("   Continue to " + e.there() + " (" + e.label()
					+ " miles)");
		}
		System.out.println("Total distance: " + pathInfo.getKey() + " miles.");
	}

	/**
	 * Using the output from Dijkstra, print the shortest path (according to
	 * time) between two nodes
	 * 
	 * @param pathInfo
	 *            Pair consisting of total time and shortest times to each node
	 */
	public static void printShortestPathTime(
			Association<Double, ArrayList<Edge<String, Double>>> pathInfo) {
		ArrayList<Edge<String, Double>> path = pathInfo.getValue();
		System.out.println("   Begin at " + path.get(0).here());
		for (Edge<String, Double> e : path) {
			System.out.println("   Continue to " + e.there() + " ("
					+ hoursToHMS(e.label()) + ")");
		}
		System.out.println("Total time: " + hoursToHMS(pathInfo.getKey()));
	}

	/**
	 * Convert hours (in decimal) to
	 * 
	 * @param rawhours
	 *            time elapsed
	 * @return Equivalent of rawhours in hours, minutes, and seconds (to
	 *         nearest 10th of a second)
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
