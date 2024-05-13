import java.io.*;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Result {

  /*
   * Complete the 'makingAnagrams' function below.
   *
   * The function is expected to return an INTEGER.
   * The function accepts following parameters:
   *  1. STRING s1
   *  2. STRING s2
   */

  public static int makingAnagrams(String s1, String s2) {
    final Map<Character, Long> s1CharactersCount = createCharactersCountMap(s1);
    final Map<Character, Long> s2CharactersCount = createCharactersCountMap(s2);

    return Stream.concat(
        s1CharactersCount.keySet()
          .stream(),
        s2CharactersCount.keySet()
          .stream())
      .collect(Collectors.toSet())
      .stream()
      .mapToInt(character -> {
        final Long s1Count = s1CharactersCount.get(character);
        final Long s2Count = s2CharactersCount.get(character);

        if (s1Count != null) {
          if (s2Count != null) {
            return Math.toIntExact(Math.abs(s1Count - s2Count));
          }
          return Math.toIntExact(s1Count);
        }
        return Math.toIntExact(s2Count);
      })
      .sum();

  }

  private static Map<Character, Long> createCharactersCountMap(String s1) {
    return s1.chars()
      .mapToObj(c -> (char) c)
      .collect(Collectors.groupingBy(
        Function.identity(),
        Collectors.counting()));
  }

}

public class Solution {
  public static void main(String[] args) throws IOException {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

    String s1 = bufferedReader.readLine();

    String s2 = bufferedReader.readLine();

    int result = Result.makingAnagrams(s1, s2);

    bufferedWriter.write(String.valueOf(result));
    bufferedWriter.newLine();

    bufferedReader.close();
    bufferedWriter.close();
  }
}
