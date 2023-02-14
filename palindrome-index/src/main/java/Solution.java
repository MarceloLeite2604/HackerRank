import java.io.*;
import java.util.Optional;
import java.util.stream.IntStream;

class Result {

    /*
     * Complete the 'palindromeIndex' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts STRING s as parameter.
     */

    public static int palindromeIndex(String s) {
        final var removedIndexes = new Integer[]{null, null};
        Integer selectedIndexDirection = null;
        var leftIndex = 0;
        var rightIndex = s.length() - 1;

        while (leftIndex < rightIndex) {
            if (s.charAt(leftIndex) != s.charAt(rightIndex)) {
                if (
                        (removedIndexes[0] == null && removedIndexes[1] != null) ||
                        (removedIndexes[0] != null && removedIndexes[1] == null)
                ) {
                    return -1;
                } else if (removedIndexes[0] == null) {
                    if (s.charAt(leftIndex + 1) == s.charAt(rightIndex)) {
                        removedIndexes[0] = leftIndex;
                    }
                    if (s.charAt(leftIndex) == s.charAt(rightIndex - 1)) {
                        removedIndexes[1] = rightIndex;
                    }

                    if (removedIndexes[0] != null) {
                        selectedIndexDirection = 0;
                        leftIndex = removedIndexes[0] + 1;
                    } else if (removedIndexes[1] != null) {
                        selectedIndexDirection = 1;
                        rightIndex = removedIndexes[1] - 1;
                    } else {
                        return -1;
                    }
                } else {
                    if (selectedIndexDirection == 1) {
                        return -1;
                    } else {
                        leftIndex = removedIndexes[0];
                        rightIndex = removedIndexes[1] - 1;
                        selectedIndexDirection = 1;
                    }
                }
            }

            leftIndex++;
            rightIndex--;
        }
        return Optional.ofNullable(selectedIndexDirection)
                .map(direction -> removedIndexes[direction])
                .orElse(-1);
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int q = Integer.parseInt(bufferedReader.readLine()
                .trim());

        IntStream.range(0, q)
                .forEach(qItr -> {
                    try {
                        String s = bufferedReader.readLine();

                        int result = Result.palindromeIndex(s);

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
