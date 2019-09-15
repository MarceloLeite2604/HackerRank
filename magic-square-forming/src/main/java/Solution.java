import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Solution {

	private static final int SQUARE_MATRIX_SIZE = 3;

	private static final List<Integer[][]> KNOWN_MAGIC_SQUARES = new ArrayList<>();

	private static final Integer[][] MAGIC_SQUARE_1 = { { 8, 1, 6 }, { 3, 5, 7 }, { 4, 9, 2 } };
	private static final Integer[][] MAGIC_SQUARE_2 = { { 6, 1, 8 }, { 7, 5, 3 }, { 2, 9, 4 } };
	private static final Integer[][] MAGIC_SQUARE_3 = { { 4, 9, 2 }, { 3, 5, 7 }, { 8, 1, 6 } };
	private static final Integer[][] MAGIC_SQUARE_4 = { { 2, 9, 4 }, { 7, 5, 3 }, { 6, 1, 8 } };
	private static final Integer[][] MAGIC_SQUARE_5 = { { 8, 3, 4 }, { 1, 5, 9 }, { 6, 7, 2 } };
	private static final Integer[][] MAGIC_SQUARE_6 = { { 4, 3, 8 }, { 9, 5, 1 }, { 2, 7, 6 } };
	private static final Integer[][] MAGIC_SQUARE_7 = { { 6, 7, 2 }, { 1, 5, 9 }, { 8, 3, 4 } };
	private static final Integer[][] MAGIC_SQUARE_8 = { { 2, 7, 6 }, { 9, 5, 1 }, { 4, 3, 8 } };

	static {
		KNOWN_MAGIC_SQUARES.add(MAGIC_SQUARE_1);
		KNOWN_MAGIC_SQUARES.add(MAGIC_SQUARE_2);
		KNOWN_MAGIC_SQUARES.add(MAGIC_SQUARE_3);
		KNOWN_MAGIC_SQUARES.add(MAGIC_SQUARE_4);
		KNOWN_MAGIC_SQUARES.add(MAGIC_SQUARE_5);
		KNOWN_MAGIC_SQUARES.add(MAGIC_SQUARE_6);
		KNOWN_MAGIC_SQUARES.add(MAGIC_SQUARE_7);
		KNOWN_MAGIC_SQUARES.add(MAGIC_SQUARE_8);
	}

	// Complete the formingMagicSquare function below.
	static int formingMagicSquare(int[][] s) {

		Integer result = null;
		int effort;

		for (Integer[][] magicSquare : KNOWN_MAGIC_SQUARES) {
			effort = calculateEffort(s, magicSquare);
			if (Objects.isNull(result) || result > effort) {
				result = effort;
			}
		}

		return result;
	}

	private static int calculateEffort(int[][] s, Integer[][] magicSquare) {
		int result = 0;
		for (int row = 0; row < SQUARE_MATRIX_SIZE; row++) {
			for (int column = 0; column < SQUARE_MATRIX_SIZE; column++) {
				result += Math.abs(s[row][column] - magicSquare[row][column]);
			}
		}
		return result;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int[][] s = new int[3][3];

        for (int i = 0; i < 3; i++) {
            String[] sRowItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int j = 0; j < 3; j++) {
                int sItem = Integer.parseInt(sRowItems[j]);
                s[i][j] = sItem;
            }
        }

        int result = formingMagicSquare(s);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
