package onTheRoad;

/**
 * Class whose main method reads in description of graph and trip requests,
 * and then returns shortest paths (according to distance or time) from one
 * given vertex to another.  The input file is given by a command line argument.
 */
import java.util.ArrayList;
import java.util.List;

public class Optimizer {
	public static void main(String[] args) {
		FileParser fp = new FileParser(args[0]);
		EdgeWeightedDigraph roadNetworkDistance = fp.makeGraph(true);
		EdgeWeightedDigraph roadNetworkTime = fp.makeGraph(false);

		if (GraphAlgorithms.isStronglyConnected(roadNetworkDistance)) {

			List<TripRequest> trips = fp.getTrips();
			for (TripRequest trip : trips) {
				if (trip.isDistance()) {
					ArrayList<DirectedEdge> result = GraphAlgorithms.getShortestPath(roadNetworkDistance,
							trip.getStart(), trip.getEnd());
					System.out.println("Shortest distance from " + fp.getVertices().get(trip.getStart()) + " to "
							+ fp.getVertices().get(trip.getEnd()) + ":");
					GraphAlgorithms.printShortestPath(result, true, fp.getVertices());
				} else {
					ArrayList<DirectedEdge> result = GraphAlgorithms.getShortestPath(roadNetworkTime, trip.getStart(),
							trip.getEnd());
					System.out.println("Shortest driving time from " + fp.getVertices().get(trip.getStart()) + " to "
							+ fp.getVertices().get(trip.getEnd()) + ":");
					GraphAlgorithms.printShortestPath(result, false, fp.getVertices());
				}
			}
		}
		else {
			System.out.println("Disconnected Map");
		}
	}
}