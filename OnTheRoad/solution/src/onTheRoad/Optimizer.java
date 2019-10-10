package onTheRoad;
/**
 * Class whose main method reads in description of graph and trip requests,
 * and then returns shortest paths (according to distance or time) from one
 * given vertex to another.  The input file is given by a command line argument.
 */
import java.util.ArrayList;
import java.util.List;

import structure5.Association;
import structure5.Edge;
import structure5.Graph;

public class Optimizer {
	public static void main(String[] args) {
		FileParser fp = new FileParser(args[0]);
		Graph<String, Double> roadNetworkDistance = fp.makeGraph(true);
		Graph<String, Double> roadNetworkTime = fp.makeGraph(false);
		List<TripRequest> trips = fp.getTrips();
		for (TripRequest trip : trips) {
			if (trip.isDistance()) {
				Association<Double, ArrayList<Edge<String, Double>>> result = GraphAlgorithms.getShortestPath(
						roadNetworkDistance, trip.getStart(), trip.getEnd());

				System.out.println("Shortest distance from " + trip.getStart()
						+ " to " + trip.getEnd() + ":");
				// System.out.println(result.getValue());
				GraphAlgorithms.printShortestPathDistance(result);
			} else {
				Association<Double, ArrayList<Edge<String, Double>>> result = GraphAlgorithms.getShortestPath(
						roadNetworkTime, trip.getStart(), trip.getEnd());

				System.out.println("Shortest time from " + trip.getStart()
						+ " to " + trip.getEnd() + ":");
				// System.out.println(result.getValue());
				GraphAlgorithms.printShortestPathTime(result);
			}
		}
	}
}
