import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Result {

	/*
	 * Complete the 'nonDivisibleSubset' function below.
	 *
	 * The function is expected to return an INTEGER. The function accepts following
	 * parameters: 1. INTEGER k 2. INTEGER_ARRAY s
	 */

	public static int nonDivisibleSubset(int k, List<Integer> s) {

		Map<Integer, Integer> remainderCountersMap = new HashMap<>();

		for (Integer number : s) {
			int remainder = number % k;
			int counter = remainderCountersMap.getOrDefault(remainder, 0);
			remainderCountersMap.put(remainder, ++counter);
		}

		int result = 0;
		for (int counter = 0; counter <= k / 2; counter++) {
			if ((counter == 0 || k - counter == counter) && remainderCountersMap.getOrDefault(counter, 0) > 0) {
				result++;
			} else {
				int firstRemainder = remainderCountersMap.getOrDefault(counter, 0);
				int secondRemainder = remainderCountersMap.getOrDefault(k - counter, 0);
				result += Integer.max(firstRemainder, secondRemainder);
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

		int k = Integer.parseInt(firstMultipleInput[1]);

		List<Integer> s = Stream.of(bufferedReader.readLine()
				.replaceAll("\\s+$", "")
				.split(" "))
				.map(Integer::parseInt)
				.collect(Collectors.toList());

		int result = Result.nonDivisibleSubset(k, s);

		bufferedWriter.write(String.valueOf(result));
		bufferedWriter.newLine();

		bufferedReader.close();
		bufferedWriter.close();
	}
}
