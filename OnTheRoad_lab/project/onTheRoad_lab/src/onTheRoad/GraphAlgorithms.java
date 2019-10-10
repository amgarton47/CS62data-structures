package onTheRoad;

import java.util.Iterator;

import structure5.Graph;
import structure5.GraphListDirected;
import structure5.Edge;
import structure5.QueueList;
import structure5.Queue;

public class GraphAlgorithms{
	
	/**
	 * Takes an input graph and produces a new graph with all edge directions reversed.
	 *
	 * @param  g input graph
	 * @return   new graph with reversed edges.
	 */
	public static <V, E> Graph<V, E> graphEdgeReversal(Graph<V, E> g) {		
		//TODO create a new directed graph that is stored as an adjacency list
		
		//TODO copy the vertices from the original graph g
		
		//TODO iterate through the edges of g and add a new edge of reversed direction in the new graph
		
		//TODO return the new graph
	}

	/**
	 * Perform bread-first search of g from vertex start. At end, can ask
	 * vertices in if they were visited
	 * 
	 *
	 * @param graph      the directed graph to search
	 * @param start  vertex to start the search with
	 */
	public static <V, E> void breadthFirstSearch(Graph<V, E> g, V start) {
		
		//TODO reset graph
		
		//TODO follow the BFS algorithm described in slides using a queue
	}

	
	public static void main(String args[]) {
		//TODO: Add test code!		
	}

}
