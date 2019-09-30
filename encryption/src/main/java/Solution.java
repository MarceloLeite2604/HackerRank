import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Solution {

	// Complete the encryption function below.
	static String encryption(String s) {
		
		String text = s.replace(" ", "");

		int[] gridDimensions = retrieveGridDimensions(text);

		char[][] grid = elaborateCharactersGrid(text, gridDimensions);

		return retrieveEncryptedText(grid);
	}

	private static String retrieveEncryptedText(char[][] grid) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int column = 0; column < grid[0].length; column++) {
			for (int row = 0; row < grid.length; row++) {
				char character = grid[row][column];
				if (character != 0) {
					stringBuilder.append(grid[row][column]);
				}
			}
			stringBuilder.append(" ");
		}
		
		return stringBuilder.toString();
	}

	private static char[][] elaborateCharactersGrid(String text, int[] gridDimensions) {
		int rows = gridDimensions[0];
		int columns = gridDimensions[1];

		char[][] result = new char[rows][columns];

		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				int index = (row * columns) + column;
				result[row][column] = (index < text.length() ? text.charAt(index) : 0);
			}
		}

		return result;
	}

	private static int[] retrieveGridDimensions(String text) {
		double squareRoot = Math.sqrt(text.length());
		int[] dimensions = new int[2];

		dimensions[0] = (int) Math.floor(squareRoot);
		dimensions[1] = (int) Math.ceil(squareRoot);

		if (dimensions[0] * dimensions[0] < text.length()) {
			dimensions[0]++;
		}

		return dimensions;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(
				new FileWriter(System.getenv("OUTPUT_PATH")));

		String s = scanner.nextLine();

		String result = encryption(s);

		bufferedWriter.write(result);
		bufferedWriter.newLine();

		bufferedWriter.close();

		scanner.close();
	}
}
