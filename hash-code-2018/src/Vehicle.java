import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Vehicle {

	public int[] pos = new int[2];
	public boolean isRunning = false;
	public Ride ride;
	public int countRides = 0;

	public List<Integer> rideIds = new ArrayList<>();

	public void start(Ride ride) {
		this.ride = ride;
		isRunning = true;
	}

	public void nextStep() {
		if (isRunning) {
			if (pos[0] < ride.end[0]) {
				pos[0] += 1;
			} else if (pos[0] > ride.end[0]) {
				pos[0] -= 1;
			} else if (pos[1] < ride.end[1]) {
				pos[1] += 1;
			} else if (pos[1] > ride.end[1]) {
				pos[1] -= 1;
			} else {
				isRunning = false;
				countRides++;
				rideIds.add(ride.id);
			}

		}
	}

	public int getDistanceToStartRide(Ride ride) {
		return Math.abs(ride.start[0] - pos[0]) + Math.abs(ride.start[1] - pos[1]);
	}

	@Override
	public String toString() {
		return "Vehicle [pos=" + Arrays.toString(pos) + ", isRunning=" + isRunning + ", ride=" + ride + ", countRides="
				+ countRides + ", rideIds=" + rideIds + "]";
	}

}
