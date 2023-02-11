import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'countingSort' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts INTEGER_ARRAY arr as parameter.
     */

    public static List<Integer> countingSort(List<Integer> arr) {
        final var countArray = IntStream.range(0, 100)
                .mapToObj(value -> 0)
                .collect(Collectors.toCollection(ArrayList::new));

        for (Integer value : arr) {
            var count = countArray.remove(value.intValue());
            countArray.add(value, ++count);
        }

        final var result = new LinkedList<Integer>();

        for (int value = 0; value < countArray.size(); value++) {
            final var count = countArray.get(value);
            for (int occurrence = 0; occurrence < count; occurrence++) {
                result.add(value);
            }
        }

        return result;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine()
                .trim());

        List<Integer> arr = Stream.of(bufferedReader.readLine()
                        .replaceAll("\\s+$", "")
                        .split(" "))
                .map(Integer::parseInt)
                .collect(toList());

        List<Integer> result = Result.countingSort(arr);

        bufferedWriter.write(
                result.stream()
                        .map(Object::toString)
                        .collect(joining(" "))
                        + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}
