import java.io.*;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

class Result {

    private static int sumBit(long[] bit, int position) {
        var sum = 0;
        while (position > 0) {
            sum += bit[position];
            position -= position & -position;
        }
        return sum;
    }

    private static void updateBit(long[] bit, int position) {
        while (position < bit.length) {
            bit[position] += 1;
            position += position & -position;
        }
    }

    /*
     * Complete the 'insertionSort' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts INTEGER_ARRAY arr as parameter.
     */
    public static long insertionSort(List<Integer> arr) {
        final var maxValue = arr.stream()
                .max(Integer::compareTo)
                .orElseThrow();

        var bit = new long[maxValue + 1];

        var shifts = 0L;

        for (int index = arr.size() - 1; index >= 0; index--) {
            final var value = arr.get(index);

            shifts += sumBit(bit, value - 1);
            updateBit(bit, value);
        }

        return shifts;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int t = Integer.parseInt(bufferedReader.readLine()
                .trim());

        IntStream.range(0, t)
                .forEach(tItr -> {
                    try {
                        int n = Integer.parseInt(bufferedReader.readLine()
                                .trim());

                        List<Integer> arr = Stream.of(bufferedReader.readLine()
                                        .replaceAll("\\s+$", "")
                                        .split(" "))
                                .map(Integer::parseInt)
                                .collect(toList());

                        long result = Result.insertionSort(arr);

                        bufferedWriter.write(String.valueOf(result));
                        bufferedWriter.newLine();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

        bufferedReader.close();
        bufferedWriter.close();
    }
}
