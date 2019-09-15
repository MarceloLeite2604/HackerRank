import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Result {

	/*
	 * Complete the 'pickingNumbers' function below.
	 *
	 * The function is expected to return an INTEGER. The function accepts
	 * INTEGER_ARRAY a as parameter.
	 */

	public static int pickingNumbers(List<Integer> a) {
		Collections.sort(a);

		Integer result = null;
		for (int index = 0; index < a.size(); index++) {
			List<Integer> multiset = retrieveMultiset(index, a);
			if (Objects.isNull(result) || multiset.size() > result) {
				result = multiset.size();
			}
		}
		
		return result;
	}

	private static List<Integer> retrieveMultiset(int originalIndex, List<Integer> integers) {

		int value = integers.get(originalIndex);
		List<Integer> result = new ArrayList<>();
		result.add(value);
		int index = originalIndex + 1;
		
		while (index < integers.size() && Math.abs(integers.get(index) - value) <= 1) {
			result.add(integers.get(index));
			index++;
		}

		return result;
	}

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> a = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(Collectors.toList());

        int result = Result.pickingNumbers(a);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
