import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class Solution {

	// Complete the strangeCounter function below.
	static long strangeCounter(long t) {

		// t is 1-based index, thus the subtraction.
		long position = t - 1;
		long neighbourPosition = retrieveNeighbourPosition(position);
		long neighbourPositionValue = neighbourPosition + 3;
		long positionDifference = position - neighbourPosition;
		return neighbourPositionValue - positionDifference;
	}

	private static long retrieveNeighbourPosition(long position) {
    	int counter = 0;
    	
    	long neighbourPosition = 0;
    	do {
    		neighbourPosition = (long)((Math.pow(2, ++counter) * 3.0) - 3.0);
    	} while(neighbourPosition <= position);
		return (long)((Math.pow(2, --counter) * 3.0) - 3.0);
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(
				new FileWriter(System.getenv("OUTPUT_PATH")));

		long t = scanner.nextLong();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		long result = strangeCounter(t);

		bufferedWriter.write(String.valueOf(result));
		bufferedWriter.newLine();

		bufferedWriter.close();

		scanner.close();
	}
}
