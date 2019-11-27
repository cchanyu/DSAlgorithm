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
	public int count, count2, temp, calc;

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
		
		//System.out.println("Generated Length = " + generated.length);
		generated[generated.length-1] = initialHeader;
		
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
		int len = generated.length;
		int k = 0;
		int ans = 0;
		while (k < len) {
			if (generated[k] == header) {
				ans = k;
				// System.out.println(ans);
				break;
			} else {
				k = k + 1;
			}
		}
		
		// add code
		header = generated[ans];
		// if next value is bigger than last value, then it goes down now.
			for (int g = ans; g < generated.length; g++) {
					int calc1 = Math.abs(header - cylinder[generated[g - 1]]);
					System.out.println("Before: " + header + " - " + cylinder[generated[g - 1]] + " = " + calc1);
					int calc2 = Math.abs(header - cylinder[generated[g + 1]]);
					System.out.println("After: " + header + " - " + cylinder[generated[g + 1]] + " = " + calc2);
				
					if(calc1 > calc2) {
						header = generated[g+1];
					} else {
						header = generated[g-1];
					}
				
					remove(generated, g);
					System.out.println("New Header: " + header);
					//System.out.println("Generated[]: " + Arrays.toString(generated));
					count2 = count2 + (calc1) + (calc2);
					count++;
			}	

		
		System.out.println("System: Header = " + header3 + ", Move count = " + count2);
		//System.out.println("Cylinder[]: " + Arrays.toString(cylinder));
		//System.out.println("Generated[]: " + Arrays.toString(generated));
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
		
		//System.out.println("Generated Length = " + generated.length);
		generated[generated.length-1] = initialHeader;
		
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
		int len = generated.length;
		int k = 0;
		int ans = 0;
		while (k < len) {
			if (generated[k] == header) {
				ans = k;
				//System.out.println("Ans = " + ans);
				break;
			} else {
				k++;
			}
		}
		
		if (initialHeader < 2500) {

			// while it's less than the q it goes down elevator
			for (int q = ans; q > 0; q--) {
				header = generated[q];
				int calc = Math.abs(header - cylinder[generated[q - 1]]);
				//System.out.println(header + " - " + cylinder[generated[q - 1]] + " = " + calc);
				count2 = count2 + (calc);
				count++;
			}

			// goes up elevator
			// make it go to 0 and then up
			count2 = count2 + Math.abs(header - 0);
			//System.out.println("Header = " + header);
			count2 = count2 + header3;
			header = 0;

			// counting up to the end
			for (int g = ans; g < generated.length; g++) {
				header = generated[g];
				int calc = Math.abs(header - cylinder[generated[g - 1]]);
				//System.out.println(header + " - " + cylinder[generated[g - 1]] + " = " + calc);
				count2 = count2 + (calc);
				count++;
			}

			// If InitialHeader is closer to 5000, then it's different case
		} else if (initialHeader > 2500) {

			// while it's more than the q it goes up elevator
			for (int q = ans; q < generated.length; q++) {
				header = generated[q];
				int calc = Math.abs(header - cylinder[generated[q - 1]]);
				System.out.println(header + " - " + cylinder[generated[q-1]] + " = " + calc);
				count2 = count2 + (calc);
				count++;
			}

			// goes up elevator
			// make it go to 5000 and then down
			count2 = count2 + Math.abs(header - 4999);
			count2 = count2 + header3;
			header = 4999;

			// counting up to the end
			for (int g = ans; g > 0; g--) {
				header = generated[g];
				int calc = Math.abs(header - cylinder[generated[g - 1]]);
				System.out.println(header + " - " + cylinder[generated[g-1]] + " = " + calc);
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

	// Add remove function
	public static int[] remove(int[] arr, int index) {

		if (arr == null || index < 0 || index >= arr.length) { return arr; }

		int[] arr2 = new int[arr.length - 1];

		for (int i = 0, k = 0; i < arr.length; i++) {
			if (i == index) { continue; }
			arr2[k++] = arr[i];
		}
		return arr2;
	}

	// Main method
	public static void main(String[] args) {
		DiskSchedule Start = new DiskSchedule();
	}
}