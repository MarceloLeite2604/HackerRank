import java.io.*;
import java.util.*;
import java.util.stream.*;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'insertionSort2' function below.
     *
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. INTEGER_ARRAY arr
     */

    public static void insertionSort2(int n, List<Integer> arr) {
        for (int endIndex = 1; endIndex < arr.size(); endIndex++) {
            partialSort(endIndex, arr);
            printList(arr);
        }
    }

    private static void partialSort(int checkedIndex, List<Integer> arr) {
        for (int index = checkedIndex; index > 0; index--) {
            if (arr.get(index) < arr.get(index - 1)) {
                arr.add(index - 1, arr.remove(index));
            }
        }
    }

    private static void printList(List<Integer> arr) {
        final var output = arr.stream()
                .map(Object::toString)
                .collect(joining(" "));
        System.out.println(output);
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(bufferedReader.readLine()
                .trim());

        List<Integer> arr = Stream.of(bufferedReader.readLine()
                        .replaceAll("\\s+$", "")
                        .split(" "))
                .map(Integer::parseInt)
                .collect(toList());

        Result.insertionSort2(n, arr);

        bufferedReader.close();
    }
}
