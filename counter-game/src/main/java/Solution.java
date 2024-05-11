import java.io.*;
import java.util.stream.IntStream;

class Result {

  /*
   * Complete the 'counterGame' function below.
   *
   * The function is expected to return a STRING.
   * The function accepts LONG_INTEGER n as parameter.
   */

  public static String counterGame(long n) {
    // Write your code here
    long mask = Long.highestOneBit(n);
    int moves = 0;

    while (mask > 1) {

      if ((n & mask) == mask) {
        long remainingBitsMask = mask - 1;

        if ((n & remainingBitsMask) != 0) {
          moves++;
          mask >>= 1;
        } else {
          int remainingMoves = (int) (Math.log(mask) / Math.log(2));
          moves += remainingMoves;
          mask >>= remainingMoves;
        }
      } else {
        mask >>=1;
      }
    }

    return moves % 2 == 0 ? "Richard" : "Louise";
  }

}

public class Solution {
  public static void main(String[] args) throws IOException {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

    int t = Integer.parseInt(bufferedReader.readLine().trim());

    IntStream.range(0, t).forEach(tItr -> {
      try {
        long n = Long.parseLong(bufferedReader.readLine().trim());

        String result = Result.counterGame(n);

        bufferedWriter.write(result);
        bufferedWriter.newLine();
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    });

    bufferedReader.close();
    bufferedWriter.close();
  }
}
