import java.io.*;
import java.security.DrbgParameters;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

class Result {

  /*
   * Complete the 'activityNotifications' function below.
   *
   * The function is expected to return an INTEGER.
   * The function accepts following parameters:
   *  1. INTEGER_ARRAY expenditure
   *  2. INTEGER d
   */

  private static final int MAX_SPENDING = 200;

  public static int activityNotifications(List<Integer> expenditure, int days) {

    int notifications = 0;

    final Queue<Integer> periodExpenditure = new ArrayDeque<>(days);

    final int[] spendingCount = new int[MAX_SPENDING];

    for (Integer spending : expenditure) {

      if (periodExpenditure.size() == days) {
        double median = retrieveMedian(spendingCount, days);
        if ((double) spending / median >= 2.0) {
          notifications++;
        }
        int lastSpending = periodExpenditure.remove();
        spendingCount[lastSpending]--;
      }

      periodExpenditure.offer(spending);
      spendingCount[spending]++;
    }
    return notifications;
  }

  private static double retrieveMedian(int[] spendingCount, int days) {
    final int medianCount = (int) Math.ceil(days / 2.0);

    Double median = null;
    int remainingCounts = medianCount;

    for (int index = 0; index < spendingCount.length && median == null; index++) {

      if (remainingCounts <= spendingCount[index]) {
        median = (double) index;

        if (days % 2 == 0) {
          if (spendingCount[index] > remainingCounts) {
            median += (double) index;
          } else {
            int nextAvailableIndex = index + 1;
            while (spendingCount[nextAvailableIndex] == 0) {
              nextAvailableIndex++;
            }
            median += (double) nextAvailableIndex;
          }
          median /= 2.0;
        }
      }

      remainingCounts -= Math.min(spendingCount[index], remainingCounts);

    }
    return median;
  }
}

public class Solution {
  public static void main(String[] args) throws IOException {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

    String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

    int n = Integer.parseInt(firstMultipleInput[0]);

    int d = Integer.parseInt(firstMultipleInput[1]);

    List<Integer> expenditure = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
      .map(Integer::parseInt)
      .collect(toList());

    int result = Result.activityNotifications(expenditure, d);

    bufferedWriter.write(String.valueOf(result));
    bufferedWriter.newLine();

    bufferedReader.close();
    bufferedWriter.close();
  }
}
