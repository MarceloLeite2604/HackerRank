import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

  /*
   * Complete the 'weightedUniformStrings' function below.
   *
   * The function is expected to return a STRING_ARRAY.
   * The function accepts following parameters:
   *  1. STRING s
   *  2. INTEGER_ARRAY queries
   */

  private static final int START_VALUE = 'a' - 1;

  public static List<String> weightedUniformStrings(String text, List<Integer> queries) {

    final Set<Integer> weights = new HashSet<>();

    int currentCharacterIndex = 0;
    while(currentCharacterIndex < text.length()) {
      final char currentCharacter = Character.toLowerCase(text.charAt(currentCharacterIndex));

      int weight = 0;
      int sameCharacterIndex = currentCharacterIndex;

      while (sameCharacterIndex < text.length() &&
        Character.toLowerCase(text.charAt(sameCharacterIndex)) == currentCharacter
      ) {
        weight += currentCharacter - START_VALUE;
        weights.add(weight);
        sameCharacterIndex++;
      }
      currentCharacterIndex = sameCharacterIndex;
    }

    System.out.println("Weights: " + weights);

    return queries.stream()
      .map(query -> weights.contains(query) ? "Yes" : "No")
      .collect(toList());
  }

}

public class Solution {
  public static void main(String[] args) throws IOException {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

    String s = bufferedReader.readLine();

    int queriesCount = Integer.parseInt(bufferedReader.readLine().trim());

    List<Integer> queries = IntStream.range(0, queriesCount).mapToObj(i -> {
        try {
          return bufferedReader.readLine().replaceAll("\\s+$", "");
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
      })
      .map(String::trim)
      .map(Integer::parseInt)
      .collect(toList());

    List<String> result = Result.weightedUniformStrings(s, queries);

    bufferedWriter.write(
      result.stream()
        .collect(joining("\n"))
        + "\n"
    );

    bufferedReader.close();
    bufferedWriter.close();
  }
}
