import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

  /*
   * Complete the 'icecreamParlor' function below.
   *
   * The function is expected to return an INTEGER_ARRAY.
   * The function accepts following parameters:
   *  1. INTEGER m
   *  2. INTEGER_ARRAY arr
   */

  public static List<Integer> icecreamParlor(int m, List<Integer> arr) {
    for (int firstIndex = 0; firstIndex < arr.size() - 1; firstIndex++) {
      final int firstValue = arr.get(firstIndex);
      for (int secondIndex = firstIndex + 1; secondIndex < arr.size(); secondIndex++) {
        final int secondValue = arr.get(secondIndex);

        if (firstValue + secondValue == m) {
          return Stream.of(firstIndex + 1, secondIndex + 1).sorted()
            .collect(Collectors.toList());

        }
      }
    }
    return Collections.emptyList();
  }

}

public class Solution {
  public static void main(String[] args) throws IOException {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

    int t = Integer.parseInt(bufferedReader.readLine().trim());

    IntStream.range(0, t).forEach(tItr -> {
      try {
        int m = Integer.parseInt(bufferedReader.readLine().trim());

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> arr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
          .map(Integer::parseInt)
          .collect(toList());

        List<Integer> result = Result.icecreamParlor(m, arr);

        bufferedWriter.write(
          result.stream()
            .map(Object::toString)
            .collect(joining(" "))
            + "\n"
        );
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    });

    bufferedReader.close();
    bufferedWriter.close();
  }
}
