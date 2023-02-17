import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

class Result {

    enum GradeDifference {
        POSITIVE,
        NEGATIVE;

        public static GradeDifference checkGradeDifference(int firstGrade, int secondGrade) {
            if (firstGrade <= secondGrade) {
                return POSITIVE;
            }
            return NEGATIVE;
        }
    }

    /*
     * Complete the 'candies' function below.
     *
     * The function is expected to return a LONG_INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. INTEGER_ARRAY arr
     */
    public static long candies(int n, List<Integer> arr) {
        final var slopes = new ArrayList<Long>(arr.size());
        var previousStudentCandies = 0L;
        var totalCandies = 0L;

        for (int index = 0; index < arr.size(); index++) {
            var decreasingSlopeIndex = index + 1;
            var slope = 1L;

            if (index > 0 && slopes.get(index - 1) > 1) {
                slope = slopes.get(index - 1) - 1;
            } else {
                while (decreasingSlopeIndex < arr.size() && GradeDifference.checkGradeDifference(arr.get(decreasingSlopeIndex - 1), arr.get(decreasingSlopeIndex))
                        .equals(GradeDifference.NEGATIVE)) {
                    decreasingSlopeIndex++;
                    slope++;
                }
            }
            slopes.add(slope);
            long candiesForCurrentStudent;
            if (index > 0 && arr.get(index) > arr.get(index - 1)) {
                candiesForCurrentStudent = Long.max(slopes.get(index), previousStudentCandies + 1L);
            } else {
                candiesForCurrentStudent = slopes.get(index);
            }
            totalCandies += candiesForCurrentStudent;
            previousStudentCandies = candiesForCurrentStudent;
        }

        return totalCandies;
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine()
                .trim());

        List<Integer> arr = IntStream.range(0, n)
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

        long result = Result.candies(n, arr);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
