import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class HashCodeProblem {

	public static final float ALPHA = 0.5f;

	private HashCodeProblem() {
		super();
	}

	public static void main(String[] args) throws IOException {
		resolve("problem/data/a_example.in");
		resolve("problem/data/b_should_be_easy.in");
		resolve("problem/data/c_no_hurry.in");
		resolve("problem/data/d_metropolis.in");
		resolve("problem/data/e_high_bonus.in");
	}

	public static void resolve(String fileName) throws IOException {
		List<String> lines = Files.readAllLines(new File(fileName).toPath());

		System.out.println("*************************" + fileName + "*************************");

		String[] inputData = getRow(lines);
		int rHorizStreets = Integer.parseInt(inputData[0]);
		int cVertStreets = Integer.parseInt(inputData[1]);
		int fVehicles = Integer.parseInt(inputData[2]);
		System.out.println(fVehicles);
		int nRides = Integer.parseInt(inputData[3]);
		System.out.println(nRides);
		int bBonus = Integer.parseInt(inputData[4]);
		long tSteps = Long.parseLong(inputData[5]);
		System.out.println(tSteps);

		List<Ride> rides = new ArrayList<>();
		for (int i = 0; i < nRides; i++) {
			inputData = getRow(lines);
			Ride ride = new Ride();
			ride.start[0] = Integer.parseInt(inputData[0]);
			ride.start[1] = Integer.parseInt(inputData[1]);

			ride.end[0] = Integer.parseInt(inputData[2]);
			ride.end[1] = Integer.parseInt(inputData[3]);

			ride.earliestStart = Integer.parseInt(inputData[4]);
			ride.latestFinish = Integer.parseInt(inputData[5]);

			ride.id = i;
			rides.add(ride);
		}

		// sort
		rides.stream()
				.sorted((r1, r2) -> Integer.compare(
						((r2.latestFinish - r2.earliestStart) - r2.getDistance()) - r2.earliestStart,
						((r1.latestFinish - r1.earliestStart) - r1.getDistance()) - r1.earliestStart));

		// TODO add sort also for the distance

		List<Vehicle> notRunVehicles = new ArrayList<>();
		for (int i = 0; i < fVehicles; i++) {
			notRunVehicles.add(new Vehicle());
		}

		List<Vehicle> runVehicles = new ArrayList<>();
		for (long i = 0; i < tSteps; i++) {

			if (i % 5000 == 0) {
				System.out.println(i);
			}

			// assign rides
			List<Integer> rToRun = new ArrayList<>();
			for (int j = 0; j < rides.size(); j++) {
				Ride minRide = rides.get(j);
				//

				/*final long cont = i;
				Vehicle vehicle = notRunVehicles.stream()//
						.filter(v -> (cont + v.getDistanceToStartRide(minRide)) == minRide.earliestStart).findFirst()
						.orElse(null);*/

				final long cont = i;
				Vehicle vehicle = notRunVehicles.stream()//
						.filter(v -> (cont + v.getDistanceToStartRide(minRide)
								+ minRide.getDistance()) < minRide.latestFinish)
						.min((v1, v2) -> Integer.compare(v1.getDistanceToStartRide(minRide),
								v2.getDistanceToStartRide(minRide)))
						.orElse(null);

				if (vehicle != null) {
					rToRun.add(j);

					notRunVehicles.remove(vehicle);
					runVehicles.add(vehicle);

					vehicle.start(minRide);
				}
				/*for (int k = 0; k < notRunVehicles.size(); k++) {

					Vehicle vehicle = notRunVehicles.get(k);

					if ((i + vehicle.getDistanceToStartRide(minRide) >= minRide.earliestStart) && (i
							+ vehicle.getDistanceToStartRide(minRide) + minRide.getDistance()) < minRide.latestFinish) {
						rToRun.add(j);

						notRunVehicles.remove(vehicle);
						runVehicles.add(vehicle);

						vehicle.start(minRide);
						break;
					}
				}*/
			}

			int count = 0;
			for (Integer rideToremove : rToRun) {
				rides.remove(rideToremove.intValue() - count);
				count++;
			}

			// Execute steps
			List<Integer> removeV = new ArrayList<>();
			for (int j = 0; j < runVehicles.size(); j++) {
				Vehicle vehicle = runVehicles.get(j);
				vehicle.nextStep();
				if (!vehicle.isRunning) {
					notRunVehicles.add(vehicle);
					removeV.add(j);
				}
			}

			count = 0;
			for (Integer j : removeV) {
				runVehicles.remove(j.intValue() - count);
				count++;
			}

		}

		PrintWriter out = new PrintWriter(fileName + ".out");

		// Print the result
		for (Vehicle vehicle : notRunVehicles) {
			out.print(vehicle.countRides);
			// if (vehicle.rideIds.size() > )
			for (Integer id : vehicle.rideIds) {
				out.print(" " + id);
			}
			out.println();

		}

		for (Vehicle vehicle : runVehicles) {
			out.print(vehicle.countRides);
			for (Integer id : vehicle.rideIds) {
				out.print(" " + id);
			}
			out.println();

		}
		out.close();

	}

	private static String[] getRow(List<String> lines) {
		String[] input = lines.get(0).split(" ");
		lines.remove(0);
		return input;
	}

}
