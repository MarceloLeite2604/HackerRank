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
   * Complete the 'pangrams' function below.
   *
   * The function is expected to return a STRING.
   * The function accepts STRING s as parameter.
   */

  private static final int ALPHABET_LENGTH = 26;

  public static String pangrams(String text) {
    // Write your code here

    final Set<Character> characters = new HashSet<>();

    for (char character : text.replace(" ", "")
      .toCharArray()) {
      characters.add(Character.toLowerCase(character));
      if (characters.size() == ALPHABET_LENGTH) {
        return "pangram";
      }
    }
    return "not pangram";
  }

}

public class Solution {
  public static void main(String[] args) throws IOException {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

    String s = bufferedReader.readLine();

    String result = Result.pangrams(s);

    bufferedWriter.write(result);
    bufferedWriter.newLine();

    bufferedReader.close();
    bufferedWriter.close();
  }
}
