import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'closestNumbers' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts INTEGER_ARRAY arr as parameter.
     */

    public static List<Integer> closestNumbers(List<Integer> arr) {
        arr.sort(Integer::compareTo);
        List<Integer> values = new LinkedList<>();
        Integer minimumDifference = null;
        for (int index = 0; index < arr.size() - 1; index++) {
            final var minor = arr.get(index);
            final var major = arr.get(index + 1);
            final var difference = major - minor;

            if (minimumDifference == null) {
                values = new LinkedList<>();
                values.add(minor);
                values.add(major);
                minimumDifference = difference;
                continue;
            }

            if (minimumDifference.compareTo(difference) > 0) {
                values = new LinkedList<>();
                values.add(minor);
                values.add(major);
                minimumDifference = difference;
            } else if (minimumDifference.compareTo(difference) == 0) {
                values.add(minor);
                values.add(major);
            }
        }
        return values;
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

        List<Integer> result = Result.closestNumbers(arr);

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