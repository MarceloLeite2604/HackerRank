import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

class Result {

	private static final int MAXIMUM_DIFFERENCE = 3;
	private static final int ROUNDING_STEP = 5;
	private static final int LOWEST_ROUNDING_GRADE = 38;

	/*
	 * Complete the 'gradingStudents' function below.
	 *
	 * The function is expected to return an INTEGER_ARRAY. The function accepts
	 * INTEGER_ARRAY grades as parameter.
	 */

	public static List<Integer> gradingStudents(List<Integer> grades) {
		List<Integer> result = new ArrayList<>(grades.size());
		for (Integer grade : grades) {
			result.add(round(grade));
		}
		return result;
	}

	private static int round(int grade) {
		int result = grade;
		if (grade >= LOWEST_ROUNDING_GRADE) {
			int difference = ROUNDING_STEP - (grade % ROUNDING_STEP);
			if (difference < MAXIMUM_DIFFERENCE) {
				result += difference;
			}
		}
		return result;
	}

}

public class Solution {
	public static void main(String[] args) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bufferedWriter = new BufferedWriter(
				new FileWriter(System.getenv("OUTPUT_PATH")));

		int gradesCount = Integer.parseInt(bufferedReader.readLine()
				.trim());

		List<Integer> grades = IntStream.range(0, gradesCount)
				.mapToObj(i -> {
					try {
						return bufferedReader.readLine()
								.replaceAll("\\s+$", "");
					} catch (IOException ex) {
						throw new RuntimeException(ex);
					}
				})
				.map(String::trim)
				.map(Integer::parseInt)
				.collect(toList());

		List<Integer> result = Result.gradingStudents(grades);

		bufferedWriter.write(result.stream()
				.map(Object::toString)
				.collect(joining("\n")) + "\n");

		bufferedReader.close();
		bufferedWriter.close();
	}
}
