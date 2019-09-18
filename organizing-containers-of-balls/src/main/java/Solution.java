import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Paste in this file the problem class skeleton.
 */
public class Solution {

	private static String organizingContainers(int[][] container) {
		List<Long> totalBallsInContainers = new ArrayList<>(container.length);
		List<Long> totalBallsOfType = new ArrayList<>(container.length);

		for (int index = 0; index < container.length; index++) {
			totalBallsInContainers.add(countBallsInContainer(container, index));
			totalBallsOfType.add(countBallsOfType(container, index));
		}
		
		while(!totalBallsInContainers.isEmpty() && totalBallsOfType.contains(totalBallsInContainers.get(0))) {
			totalBallsOfType.remove(totalBallsInContainers.get(0));
			totalBallsInContainers.remove(0);
		}

		return (totalBallsInContainers.isEmpty() ? "Possible" : "Impossible");
	}

	private static long countBallsInContainer(int[][] matrix, int container) {
		int[] containerArray = matrix[container];
		long result = 0L;
		for (int ballTypeQuantity : containerArray) {
			result += (long) ballTypeQuantity;
		}

		return result;
	}

	private static long countBallsOfType(int[][] matrix, int type) {
		long result = 0L;
		for (int index = 0; index < matrix.length; index++) {
			result += (long) matrix[index][type];
		}

		return result;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(new
		FileWriter(System.getenv("OUTPUT_PATH")));

		int q = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int qItr = 0; qItr < q; qItr++) {
			int n = scanner.nextInt();
			scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

			int[][] container = new int[n][n];

			for (int i = 0; i < n; i++) {
				String[] containerRowItems = scanner.nextLine()
						.split(" ");
				scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

				for (int j = 0; j < n; j++) {
					int containerItem = Integer.parseInt(containerRowItems[j]);
					container[i][j] = containerItem;
				}
			}

			String result = organizingContainers(container);

			bufferedWriter.write(result);
			bufferedWriter.newLine();
		}

		bufferedWriter.close();

		scanner.close();
	}
}
