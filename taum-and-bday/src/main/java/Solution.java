import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.IntStream;

class Result {

	/*
	 * Complete the 'taumBday' function below.
	 *
	 * The function is expected to return a LONG_INTEGER. The function accepts
	 * following parameters: 1. INTEGER b 2. INTEGER w 3. INTEGER bc 4. INTEGER wc
	 * 5. INTEGER z
	 */

	public static long taumBday(int b, int w, int bc, int wc, int z) {
		long firstCost = (long) b * (long) bc + (long) w * (long) wc;
		long secondCost = (long) b * (long) bc + (long) w * ((long) bc + (long) z);
		long thirdCost = (long) b * ((long) wc + (long) z) + (long) w * (long) wc;

		return Long.min(firstCost, Long.min(secondCost, thirdCost));
	}

}

public class Solution {
	public static void main(String[] args) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bufferedWriter = new BufferedWriter(
				new FileWriter(System.getenv("OUTPUT_PATH")));

		int t = Integer.parseInt(bufferedReader.readLine()
				.trim());

		IntStream.range(0, t)
				.forEach(tItr -> {
					try {
						String[] firstMultipleInput = bufferedReader.readLine()
								.replaceAll("\\s+$", "")
								.split(" ");

						int b = Integer.parseInt(firstMultipleInput[0]);

						int w = Integer.parseInt(firstMultipleInput[1]);

						String[] secondMultipleInput = bufferedReader.readLine()
								.replaceAll("\\s+$", "")
								.split(" ");

						int bc = Integer.parseInt(secondMultipleInput[0]);

						int wc = Integer.parseInt(secondMultipleInput[1]);

						int z = Integer.parseInt(secondMultipleInput[2]);

						long result = Result.taumBday(b, w, bc, wc, z);

						bufferedWriter.write(String.valueOf(result));
						bufferedWriter.newLine();
					} catch (IOException ex) {
						throw new RuntimeException(ex);
					}
				});

		bufferedReader.close();
		bufferedWriter.close();
	}
}
