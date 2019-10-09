import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Solution {

	// Complete the cutTheSticks function below.
	static int[] cutTheSticks(int[] arr) {
		List<Integer> stickLengths = new ArrayList<>(Arrays.stream(arr)
				.boxed()
				.collect(Collectors.toList()));

		List<Integer> remainingSticks = new ArrayList<>();

		while (!stickLengths.isEmpty()) {
			remainingSticks.add(stickLengths.size());
			iterate(stickLengths);
		}

		return remainingSticks.stream()
				.mapToInt(i -> i)
				.toArray();
	}

	private static void iterate(List<Integer> stickLengths) {
		int shortestStickLength = retrieveShortestStickLength(stickLengths);

		removeOrCutSticks(stickLengths, shortestStickLength);
	}

	private static void removeOrCutSticks(List<Integer> stickLengths, int shortestStickLength) {
		Iterator<Integer> stickLengthsIterator = stickLengths.iterator();
		while (stickLengthsIterator.hasNext()) {
			Integer stickLength = stickLengthsIterator.next();
			if (stickLength == shortestStickLength) {
				stickLengthsIterator.remove();
			} else {
				stickLength = stickLength - shortestStickLength;
			}
		}
	}

	private static int retrieveShortestStickLength(List<Integer> stickLengths) {
		Integer shortestStickLength = null;
		for (Integer stickLength : stickLengths) {
			if (Objects.isNull(shortestStickLength) || stickLength < shortestStickLength) {
				shortestStickLength = stickLength;
			}
		}
		return shortestStickLength;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(
				new FileWriter(System.getenv("OUTPUT_PATH")));

		int n = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		int[] arr = new int[n];

		String[] arrItems = scanner.nextLine()
				.split(" ");
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int i = 0; i < n; i++) {
			int arrItem = Integer.parseInt(arrItems[i]);
			arr[i] = arrItem;
		}

		int[] result = cutTheSticks(arr);

		for (int i = 0; i < result.length; i++) {
			bufferedWriter.write(String.valueOf(result[i]));

			if (i != result.length - 1) {
				bufferedWriter.write("\n");
			}
		}

		bufferedWriter.newLine();

		bufferedWriter.close();

		scanner.close();
	}
}
