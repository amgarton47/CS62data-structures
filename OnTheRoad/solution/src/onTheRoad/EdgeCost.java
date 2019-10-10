package onTheRoad;

public class EdgeCost {
	private double speed;
	private double distance;
	
	public EdgeCost(double distance, double speed) {
		this.speed = speed;
		this.distance = distance;
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public double getDistance() {
		return distance;
	}
}
