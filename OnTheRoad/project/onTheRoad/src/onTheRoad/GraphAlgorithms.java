package onTheRoad;

/** Common algorithms for Graphs.  All assume working with a directed graph.
 * Written ????
 * @author ???? (based on algorithms in Bailey, Java Structures)
 */
import java.util.ArrayList;

import structure5.Association;
import structure5.ComparableAssociation;
import structure5.Map;
import structure5.Graph;
import structure5.Edge;

public class GraphAlgorithms {

	/**
	 * @param g
	 *            directed graph
	 * @return graph like g except all edges are reversed
	 */
	public static <V, E> Graph<V, E> graphEdgeReversal(Graph<V, E> g) {
		// FIX THIS!
		return null;
	}

	/**
	 * Perform breadTH-first search of g from vertex start. At end, can ask
	 * vertices in if they were visited
	 * 
	 * @param g
	 *            directed graph
	 * @param start
	 *            starting vertex for search
	 */
	public static <V, E> void breadthFirstSearch(Graph<V, E> g, V start) {
		// FIX THIS!		
	}

	/**
	 * @param g
	 *            directed graph
	 * @return whether graph g is strongly connected.
	 */
	public static <V, E> boolean isStronglyConnected(Graph<V, E> g) {
		// FIX THIS!
		return false;
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
		// FIX THIS!
		return null;
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
		// FIX THIS!
		return null;
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
		// FIX THIS!
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
		// FIX THIS!
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
		// FIX THIS!
		return null;
	}
}
