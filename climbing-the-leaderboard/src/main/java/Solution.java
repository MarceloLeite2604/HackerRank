import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Scanner;
import java.util.TreeMap;

public class Solution {

	// Complete the climbingLeaderboard function below.
	static int[] climbingLeaderboard(int[] scores, int[] alice) {

		TreeMap<Integer, Integer> leaderboard = createLeaderboard(scores);

		return retrieveAlicePositions(alice, leaderboard);
	}

	private static int[] retrieveAlicePositions(int[] aliceScores,
			TreeMap<Integer, Integer> leaderboard) {

		int[] result = new int[aliceScores.length];

		for (int index = 0; index < aliceScores.length; index++) {
			int score = aliceScores[index];
			int position = retriveScorePosition(leaderboard, score);
			result[index] = position;
		}
		return result;
	}

	private static int retriveScorePosition(TreeMap<Integer, Integer> leaderboard, int aliceScore) {
		Optional<Entry<Integer, Integer>> optionalScore = Optional
				.ofNullable(leaderboard.floorEntry(aliceScore));
		return optionalScore.map(Entry::getValue)
				.orElse(leaderboard.size() + 1);
	}

	private static TreeMap<Integer, Integer> createLeaderboard(int[] scores) {
		TreeMap<Integer, Integer> leaderboard = new TreeMap<>();
		int position = 1;
		for (int score : scores) {
			if (!leaderboard.containsKey(score)) {
				leaderboard.put(score, position++);
			}
		}
		return leaderboard;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int scoresCount = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[] scores = new int[scoresCount];

        String[] scoresItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < scoresCount; i++) {
            int scoresItem = Integer.parseInt(scoresItems[i]);
            scores[i] = scoresItem;
        }

        int aliceCount = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[] alice = new int[aliceCount];

        String[] aliceItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < aliceCount; i++) {
            int aliceItem = Integer.parseInt(aliceItems[i]);
            alice[i] = aliceItem;
        }

        int[] result = climbingLeaderboard(scores, alice);

        for (int i = 0; i < result.length; i++) {
            bufferedWriter.write(String.valueOf(result[i]));

            if (i != result.length - 1) {
                bufferedWriter.write("\n");
            }
        }

        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
