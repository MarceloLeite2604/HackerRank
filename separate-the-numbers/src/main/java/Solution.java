import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.IntStream;

class Result {

  /*
   * Complete the 'separateNumbers' function below.
   *
   * The function accepts STRING s as parameter.
   */

  public static void separateNumbers(String input) {

    boolean beautiful = false;
    int initialSize = 1;
    Long initialValue = null;
    int index;

    while (initialSize <= input.length() / 2 && !beautiful) {
      index = 0;

      initialValue = null;
      Long previousValue = null;
      long currentValue;
      beautiful = true;
      int size = initialSize;

      while (index < input.length() && beautiful) {
        currentValue = Long.parseLong(input.substring(index, index + size));

        if (initialValue == null) {
          initialValue = currentValue;
        }

        if (previousValue != null) {
          beautiful = currentValue - previousValue == 1;
        }

        index += size;
        int expectedNextValueSize = Long.toString(currentValue + 1).length();
        size = Math.min(expectedNextValueSize, input.length() - index);
        previousValue = currentValue;
      }
      initialSize++;
    }

    if (beautiful) {
      System.out.println("YES " + initialValue);
    } else {
      System.out.println("NO");
    }
  }
}

public class Solution {
  public static void main(String[] args) throws IOException {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

      int q = Integer.parseInt(bufferedReader.readLine().trim());

      IntStream.range(0, q).forEach(qItr -> {
        try {
          String s = bufferedReader.readLine();

          Result.separateNumbers(s);
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
      });

      bufferedReader.close();
    }
}
