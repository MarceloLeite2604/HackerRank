import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Solution {

	// Complete the countSort function below.
	static String countSort(List<List<String>> arr) {

		int position = 0;
		for (List<String> input : arr) {
			input.add(Integer.toString(position++));
		}

		List<List<String>> orderSortedArray = arr.stream()
				.sorted((r1, r2) -> Integer.compare(Integer.parseInt(r1.get(0)),
						Integer.parseInt(r2.get(0))))
				.collect(Collectors.toList());

		int halfSize = arr.size() / 2;

		for (List<String> input : orderSortedArray) {
			if (Integer.parseInt(input.get(2)) < halfSize) {
				System.out.println("-");
			} else {
				System.out.println(input.get(1));
			}
		}

		return "";
	}

	public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<String>> arr = new ArrayList<>();

        IntStream.range(0, n).forEach(i -> {
            try {
                arr.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .collect(Collectors.toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        countSort(arr);

        bufferedReader.close();
    }
}
