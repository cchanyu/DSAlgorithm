import java.util.Random;
import java.util.Arrays;
import java.lang.Math; 

public class DiskSchedule {

	// Initiating values
	public int header, header2, header3;
	public int[] cylinder = new int[5000];
	public int[] generated = new int[1000];
	public int count, count2, temp, calc;
	public static enum Algorithm {FCFS, SSTF, SCAN};
	
	// Disk schedule
	public DiskSchedule(){
		cylinders();
		generating();
		FCFS();
		//SSTF();
		SCAN();
	}
	
	// This function inserts orderly generated values into arrays.
	public void cylinders() {
		System.out.println("System: cylinder() is triggered.");
		
		// Assigning numbers to arrays
		for(int i = 0; i < 5000; i++) {
			cylinder[i] = i;
		}
		
		//System.out.println("Cylinder[]: " + Arrays.toString(cylinder));
		System.out.println("System: cylinder() has been processed.");
	}
	
	// This function inserts randomly generated values into arrays.
	public void generating() {
		System.out.println("System: generating() is triggered.");
		
		// Assigning random to arrays
		Random rand = new Random(); 
		for(int i = 0; i < 1000; i++) {
			// Selects any number from 0-4999
			generated[i] = rand.nextInt(5000);
		}
		
		//System.out.println("Generated[]: " + Arrays.toString(generated));
		System.out.println("System: generating() has been processed.");
	}
	
	// First Come First Serve Algorithm
	public void FCFS() {
		System.out.println("System: FCFS() is triggered.");

		// Select the starting header
		count = 0; count2 = 0;
		header = generated[0];
		header3 = generated[0];
		
		System.out.println("System: Header = " + generated[0] + ", generated Length = " + generated.length);
		
		// New attempt
		for(int i = 1; i < generated.length; i++) {
			header = generated[i];
			
			// head move count system
			int calc = Math.abs(header - cylinder[generated[i-1]]);
			//System.out.println(header + " - " + cylinder[generated[i-1]] + " = " + calc);
			count2 = count2 + (calc);
			count++;
		}

		System.out.println("System: Header = " + header3 + ", Move count = " + count2);
		//System.out.println("Cylinder[]: " + Arrays.toString(cylinder));
		//System.out.println("Generated[]: " + Arrays.toString(generated)); 
		
		System.out.println("System: FCFS() has been processed.");
	}
	
	// Shortest Seek Time First Algorithm
	public void SSTF() {
		System.out.println("System: SSTF() is triggered.");
		
		
		
		System.out.println("System: SSTF() has been processed.");
	}
	
	// Elevator Algorithm
	public void SCAN() {
		System.out.println("System: SCAN() is triggered.");
		
		count = 0; count2 = 0;
		header = generated[0];
		header3 = generated[0];
		
		System.out.println("System: Header = " + generated[0] + ", generated Length = " + generated.length);
		
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
                 //System.out.println(ans);
                 break;
            } 
            else { k = k + 1; }
        }
		
        // while it's less than the q it goes down elevator
		for(int q = ans; q > 0; q--) {
			header = generated[q];
			
			int calc = Math.abs(header - cylinder[generated[q-1]]);
			//System.out.println(header + " - " + cylinder[generated[q-1]] + " = " + calc);
			count2 = count2 + (calc);
			count++;
		}
		
		// goes up elevator
		count2 = count2 + header3;
		
		// counting up to the end
		for (int g = ans; g < generated.length; g++) {
			header = generated[g];

			int calc = Math.abs(header - cylinder[generated[g-1]]);
			//System.out.println(header + " - " + cylinder[generated[g-1]] + " = " + calc);
			count2 = count2 + (calc);
			count++;
		}

		System.out.println("System: Header = " + header3 + ", Move count = " + count2);
		//System.out.println("Cylinder[]: " + Arrays.toString(cylinder));
		//System.out.println("Generated[]: " + Arrays.toString(generated)); 
		
		System.out.println("System: SCAN() has been processed.");
	}
	
	// Main method
	public static void main(String[] args) {
		DiskSchedule Start = new DiskSchedule();
	}
	
}
