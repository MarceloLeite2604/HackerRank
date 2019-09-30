import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

class Result {

	/*
	 * Complete the 'getTotalX' function below.
	 *
	 * The function is expected to return an INTEGER. The function accepts following
	 * parameters: 1. INTEGER_ARRAY a 2. INTEGER_ARRAY b
	 */

	public static int getTotalX(List<Integer> a, List<Integer> b) {

		List<Integer> numbers = applyFirstRule(a, b);

		numbers = applySecondRule(b, numbers);

		return numbers.size();
	}

	private static List<Integer> applyFirstRule(List<Integer> a, List<Integer> b) {
		Collections.sort(b);
		int lowestValueSecondGroup = b.get(0);

		List<Integer> firstRuleNumbers = new ArrayList<>();

		for (int value = 1; value <= lowestValueSecondGroup; value++) {

			int index = 0;
			while (index < a.size() && value % a.get(index) == 0) {
				index++;
			}

			if (index == a.size()) {
				firstRuleNumbers.add(value);
			}
		}
		return firstRuleNumbers;
	}

	private static List<Integer> applySecondRule(List<Integer> b, List<Integer> numbers) {

		List<Integer> result = new ArrayList<>();

		for (Integer number : numbers) {
			int index = 0;
			while (index < b.size() && b.get(index) % number == 0) {
				index++;
			}

			if (index == b.size()) {
				result.add(number);
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

		String[] firstMultipleInput = bufferedReader.readLine()
				.replaceAll("\\s+$", "")
				.split(" ");

		int n = Integer.parseInt(firstMultipleInput[0]);

		int m = Integer.parseInt(firstMultipleInput[1]);

		List<Integer> arr = Stream.of(bufferedReader.readLine()
				.replaceAll("\\s+$", "")
				.split(" "))
				.map(Integer::parseInt)
				.collect(toList());

		List<Integer> brr = Stream.of(bufferedReader.readLine()
				.replaceAll("\\s+$", "")
				.split(" "))
				.map(Integer::parseInt)
				.collect(toList());

		int total = Result.getTotalX(arr, brr);

		bufferedWriter.write(String.valueOf(total));
		bufferedWriter.newLine();

		bufferedReader.close();
		bufferedWriter.close();
	}
}
