import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.lang.Math;

public class DiskSchedule {
	// Initiating values
	public int header, header2, header3;
	public int value1, value2, value3;
	public int[] cylinder = new int[5000];
	public int[] generated = new int[1000];
	public int count, count2, temp, calc, ans;

	public static enum Algorithm {
		FCFS, SSTF, SCAN
	};

	// Disk schedule
	public DiskSchedule() {
		cylinders();
		generating();

		Scanner sc = new Scanner(System.in);
		System.out.print("System: Type the initial header: ");
		int initialHeader = sc.nextInt();

		Scanner sc2 = new Scanner(System.in);
		System.out.print("System: Type the case: ");
		Algorithm algorithm = Algorithm.valueOf(sc2.next().toUpperCase());

		System.out.println("System: Header = " + initialHeader);
		switch (algorithm) {
		case FCFS:
			System.out.println("System: FCFS enum triggered.");
			value1 = FCFS(initialHeader);
			System.out.println("System: FCFS head movement = " + value1);
			break;
		case SSTF:
			System.out.println("System: SSTF enum triggered.");
			value2 = SSTF(initialHeader);
			System.out.println("System: SSTF head movement = " + value2);
			break;
		case SCAN:
			System.out.println("System: SCAN enum triggered.");
			value3 = SCAN(initialHeader);
			System.out.println("System: SCAN head movement = " + value3);
			break;
		}
	}

	// This function inserts orderly generated values into arrays.
	public void cylinders() {
		System.out.println("System: cylinder() is triggered.");

		// Assigning numbers to arrays
		for (int i = 0; i < 5000; i++) {
			cylinder[i] = i;
		}

		// System.out.println("Cylinder[]: " + Arrays.toString(cylinder));
		System.out.println("System: cylinder() has been processed.");
		System.out.println("-----------------------------");
	}

	// This function inserts randomly generated values into arrays.
	public void generating() {
		System.out.println("System: generating() is triggered.");

		// Assigning random to arrays
		Random rand = new Random();
		for (int i = 0; i < 1000; i++) {
			// Selects any number from 0-4999
			generated[i] = rand.nextInt(5000);
		}

		// System.out.println("Generated[]: " + Arrays.toString(generated));
		System.out.println("System: generating() has been processed.");
		System.out.println("-----------------------------");
	}

	// First Come First Serve Algorithm
	public int FCFS(int initialHeader) {
		System.out.println("System: FCFS() is triggered.");

		// Select the starting header
		count = 0;
		count2 = 0;
		header = initialHeader;
		header3 = initialHeader;

		System.out.println("System: Header = " + initialHeader + ", Length = " + generated.length);

		int calc2 = Math.abs(initialHeader - cylinder[generated[0]]);
		count2 = count2 + (calc2);
		count++;
		header = generated[0];

		// New attempt
		for (int i = 1; i < generated.length; i++) {
			header = generated[i];
			// head move count system
			int calc = Math.abs(header - cylinder[generated[i - 1]]);
			// System.out.println(header + " - " + cylinder[generated[i-1]] + " = " + calc);
			count2 = count2 + (calc);
			count++;
		}

		System.out.println("System: Header = " + header3 + ", Move count = " + count2);
		// System.out.println("Cylinder[]: " + Arrays.toString(cylinder));
		// System.out.println("Generated[]: " + Arrays.toString(generated));
		System.out.println("System: FCFS() has been processed.");
		System.out.println("-----------------------------");
		return count2;
	}

	// Shortest Seek Time First Algorithm
	public int SSTF(int initialHeader) {
		System.out.println("System: SSTF() is triggered.");

		count = 0;
		count2 = 0;
		header = initialHeader;
		header3 = initialHeader;
		generated[generated.length - 1] = initialHeader;
		System.out.println("System: Header = " + initialHeader + ", Length = " + generated.length);

		// sorting in ascending order
		for (int i = 0; i < generated.length; i++) {
			for (int j = i + 1; j < generated.length; j++) {
				if (generated[i] > generated[j]) {
					temp = generated[i];
					generated[i] = generated[j];
					generated[j] = temp;
				}
			}
		}

		// find the index
		ans = findIndex(generated, header);

		// while ans is in index, it runs
		header = generated[ans];
		while ((0 < ans) && (ans < generated.length)) {
			int before = 0, after = 0;
			int dist1 = 0, dist2 = 0;

			// calculates before distance
			before = generated[ans - 1];
			dist1 = Math.abs(header - cylinder[before]);
			// System.out.println("Before: " + header + " - " + cylinder[before] + " = " +
			// dist1);

			// calculates after distance
			after = generated[ans + 1];
			dist2 = Math.abs(header - cylinder[after]);
			// System.out.println("After: " + header + " - " + cylinder[after] + " = " +
			// dist2);

			// if before is farther than after, it picks future value
			if (dist1 > dist2) {
				ans = ans + 1;
				header = generated[ans];
				count2 = count2 + (dist2);
				// System.out.println("New Header: " + header + ", Ans: " + ans + ", Length: " +
				// generated.length);
			} else {
				ans = ans - 1;
				header = generated[ans];
				count2 = count2 + (dist1);
				// System.out.println("New Header: " + header + ", Ans: " + ans + ", Length: " +
				// generated.length);
			}

			generated = remove(generated, ans);

		}

		// when ans is either 0 or max, it runs
		if (ans == 0) {
			// going up
			for (int q = ans + 1; q < generated.length; q++) {
				header = generated[q];
				int calc = Math.abs(header - cylinder[generated[q - 1]]);
				count2 = count2 + (calc);
				count++;
			}
		} else if (ans == generated.length) {
			// going down
			for (int q = ans - 1; q > 0; q--) {
				header = generated[q];
				int calc = Math.abs(header - cylinder[generated[q - 1]]);
				count2 = count2 + (calc);
				count++;
			}
		}

		System.out.println("System: Header = " + header3 + ", Move count = " + count2);
		// System.out.println("Cylinder[]: " + Arrays.toString(cylinder));
		// System.out.println("Generated[]: " + Arrays.toString(generated));
		System.out.println("System: SSTF() has been processed.");
		System.out.println("-----------------------------");
		return count2;
	}

	// Elevator Algorithm
	public int SCAN(int initialHeader) {
		System.out.println("System: SCAN() is triggered.");

		count = 0;
		count2 = 0;
		header = initialHeader;
		header3 = initialHeader;

		// System.out.println("Generated Length = " + generated.length);
		generated[generated.length - 1] = initialHeader;

		System.out.println("System: Header = " + initialHeader + ", Length = " + generated.length);

		// sorting in ascending order
		for (int i = 0; i < generated.length; i++) {
			for (int j = i + 1; j < generated.length; j++) {
				if (generated[i] > generated[j]) {
					temp = generated[i];
					generated[i] = generated[j];
					generated[j] = temp;
				}
			}
		}

		// find the index
		ans = findIndex(generated, header);

		if (initialHeader < 2500) {

			// while it's less than the q it goes down elevator
			for (int q = ans; q > 0; q--) {
				header = generated[q];
				int calc = Math.abs(header - cylinder[generated[q - 1]]);
				// System.out.println(header + " - " + cylinder[generated[q - 1]] + " = " +
				// calc);
				count2 = count2 + (calc);
				count++;
			}

			// goes down elevator
			// make it go to 0 and then up
			count2 = count2 + Math.abs(header - 0);
			// System.out.println("Header = " + header);
			count2 = count2 + header3;
			header = 0;

			// counting up to the end
			for (int g = ans; g < generated.length; g++) {
				header = generated[g];
				int calc = Math.abs(header - cylinder[generated[g - 1]]);
				// System.out.println(header + " - " + cylinder[generated[g - 1]] + " = " +
				// calc);
				count2 = count2 + (calc);
				count++;
			}

			// If InitialHeader is closer to 5000, then it's different case
		} else if (initialHeader > 2500) {

			// while it's more than the q it goes up elevator
			for (int q = ans; q < generated.length; q++) {
				header = generated[q];
				int calc = Math.abs(header - cylinder[generated[q - 1]]);
				// System.out.println(header + " - " + cylinder[generated[q - 1]] + " = " +
				// calc);
				count2 = count2 + (calc);
				count++;
			}

			// goes up elevator
			// make it go to 5000 and then down
			count2 = count2 + Math.abs(header - 4999);
			count2 = count2 + header3;
			header = 4999;

			// counting down to the end
			for (int g = ans; g > 0; g--) {
				header = generated[g];
				int calc = Math.abs(header - cylinder[generated[g - 1]]);
				// System.out.println(header + " - " + cylinder[generated[g - 1]] + " = " +
				// calc);
				count2 = count2 + (calc);
				count++;
			}
		}
		System.out.println("System: Header = " + header3 + ", Move count = " + count2);
		// System.out.println("Cylinder[]: " + Arrays.toString(cylinder));
		// System.out.println("Generated[]: " + Arrays.toString(generated));
		System.out.println("System: SCAN() has been processed.");
		System.out.println("-----------------------------");
		return count2;
	}

	// removes an index from the array
	public static int[] remove(int[] generated, int index) {

		int[] temp = new int[generated.length - 1];

		for (int i = 0, k = 0; i < generated.length; i++) {
			if (i == index) {

			} else {
				temp[k++] = generated[i];
			}
		}
		return temp;
	}

	// Finding the index in an array
	public static int findIndex(int[] generated, int header) {
		int len = generated.length;
		int k = 0, ans = 0;
		while (k < len) {
			if (generated[k] == header) {
				ans = k;
				System.out.println("Head Index: " + ans);
				break;
			} else {
				k = k + 1;
			}
		}
		return ans;
	}

	// Main method
	public static void main(String[] args) {
		DiskSchedule Start = new DiskSchedule();
	}
}