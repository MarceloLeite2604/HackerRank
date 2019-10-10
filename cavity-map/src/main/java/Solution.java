import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {

	private static final int ROW = 0;
	private static final int COLUMN = 1;

	// Complete the cavityMap function below.
	static String[] cavityMap(String[] grid) {
		int[][] matrix = createMatrix(grid);

		List<Integer[]> cavities = retrieveCavities(matrix);

		for (Integer[] cavity : cavities) {
			grid[cavity[ROW]] = replaceCharacterAtPositionByX(grid[cavity[ROW]], cavity[COLUMN]);
		}

		return grid;
	}

	private static List<Integer[]> retrieveCavities(int[][] matrix) {
		List<Integer[]> cavities = new ArrayList<>();
		for (int row = 1; row < matrix.length - 1; row++) {
			for (int column = 1; column < matrix.length - 1; column++) {
				int value = matrix[row][column];
				if (isCavity(value, row, column, matrix)) {
					cavities.add(createCavity(row, column));
				}
			}
		}
		return cavities;
	}

	private static Integer[] createCavity(int row, int column) {
		Integer[] cavity = new Integer[2];
		cavity[ROW] = row;
		cavity[COLUMN] = column;
		return cavity;
	}

	private static String replaceCharacterAtPositionByX(String text, int index) {
		return text.substring(0, index) + "X" + text.substring(index + 1);
	}

	private static boolean isCavity(int value, int row, int column, int[][] matrix) {
		return (matrix[row - 1][column] < value && matrix[row + 1][column] < value
				&& matrix[row][column - 1] < value && matrix[row][column + 1] < value);
	}

	private static int[][] createMatrix(String[] grid) {
		int[][] matrix = new int[grid.length][grid.length];

		int row = 0;
		for (String rowValues : grid) {
			matrix[row++] = rowValues.chars()
					.map(Character::getNumericValue)
					.toArray();
		}
		return matrix;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(
				new FileWriter(System.getenv("OUTPUT_PATH")));

		int n = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		String[] grid = new String[n];

		for (int i = 0; i < n; i++) {
			String gridItem = scanner.nextLine();
			grid[i] = gridItem;
		}

		String[] result = cavityMap(grid);

		for (int i = 0; i < result.length; i++) {
			bufferedWriter.write(result[i]);

			if (i != result.length - 1) {
				bufferedWriter.write("\n");
			}
		}

		bufferedWriter.newLine();

		bufferedWriter.close();

		scanner.close();
	}
}
