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
     * Complete the 'insertionSort1' function below.
     *
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. INTEGER_ARRAY arr
     */

    public static void insertionSort1(int n, List<Integer> arr) {
        final var value = arr.get(n - 1);
        var index = arr.size() - 1;

        while (index > 0 && arr.get(index - 1) >= value) {
            arr.remove(index);
            arr.add(index, arr.get(index - 1));
            printList(arr);
            index--;
        }

        arr.remove(index);
        arr.add(index, value);
        printList(arr);
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

        Result.insertionSort1(n, arr);

        bufferedReader.close();
    }
}
