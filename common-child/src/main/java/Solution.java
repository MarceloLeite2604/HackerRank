import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

class Result {

    public static int commonChild(String s1, String s2) {
        final var lcsLength = calculateLcsLength(s1, s2);
        return lcsLength[s1.length()][s2.length()];
    }

    private static int[][] calculateLcsLength(String firstString, String secondString) {
        final var firstStringLength = firstString.length();
        final var secondStringLength = secondString.length();

        final var walkthroughMatrix = new int[firstStringLength + 1][secondStringLength + 1];

        for (int firstIndex = 0; firstIndex < firstStringLength; firstIndex++) {
            for (int secondIndex = 0; secondIndex < secondStringLength; secondIndex++) {
                walkthroughMatrix[firstIndex][secondIndex] = 0;
            }
        }

        for (int firstPosition = 1; firstPosition <= firstStringLength; firstPosition++) {
            for (int secondPosition = 1; secondPosition <= secondStringLength; secondPosition++) {
                int value;
                if (firstString.charAt(firstPosition - 1) == secondString.charAt(secondPosition - 1)) {
                    value = walkthroughMatrix[firstPosition][secondPosition] = walkthroughMatrix[firstPosition - 1][secondPosition - 1] + 1;
                } else {
                    value = Integer.max(walkthroughMatrix[firstPosition][secondPosition - 1], walkthroughMatrix[firstPosition - 1][secondPosition]);
                }
                walkthroughMatrix[firstPosition][secondPosition] = value;
            }
        }
        return walkthroughMatrix;
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String s1 = bufferedReader.readLine();

        String s2 = bufferedReader.readLine();

        int result = Result.commonChild(s1, s2);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}