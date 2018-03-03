import java.util.Arrays;

public class Ride {

	public int[] start = new int[2];
	public int[] end = new int[2];
	public int earliestStart;
	public int latestFinish;
	public int id;

	private Integer distance;

	public int getDistance() {
		if (distance == null) {
			distance = Math.abs(start[0] - end[0]) + Math.abs(start[1] - end[1]);
		}
		return distance;
	}

	@Override
	public String toString() {
		return "Ride [start=" + Arrays.toString(start) + ", end=" + Arrays.toString(end) + ", earliestStart="
				+ earliestStart + ", latestFinish=" + latestFinish + ", id=" + id + ", distance=" + distance + "]";
	}

}
