import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Solution {

	// Complete the stones function below.
	static int[] stones(int n, int a, int b) {

		Set<Integer> stoneValues = new HashSet<>();

		for (int counter = 0; counter < n; counter++) {
			int stoneValue = (n - counter - 1) * a + (counter * b);
			stoneValues.add(stoneValue);
		}

		List<Integer> stoneValuesList = createSortedList(stoneValues);

		return stoneValuesList.stream()
				.mapToInt(Number::intValue)
				.toArray();

	}

	private static List<Integer> createSortedList(Set<Integer> finalNumbers) {
		List<Integer> finalNumbersList = finalNumbers.stream()
				.collect(Collectors.toList());
		Collections.sort(finalNumbersList);
		return finalNumbersList;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(
				new FileWriter(System.getenv("OUTPUT_PATH")));

		int T = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int TItr = 0; TItr < T; TItr++) {
			int n = scanner.nextInt();
			scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

			int a = scanner.nextInt();
			scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

			int b = scanner.nextInt();
			scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

			int[] result = stones(n, a, b);

			for (int i = 0; i < result.length; i++) {
				bufferedWriter.write(String.valueOf(result[i]));

				if (i != result.length - 1) {
					bufferedWriter.write(" ");
				}
			}

			bufferedWriter.newLine();
		}

		bufferedWriter.close();

		scanner.close();
	}
}
