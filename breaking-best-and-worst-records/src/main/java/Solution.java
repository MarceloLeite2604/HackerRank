import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Solution {

	// Complete the breakingRecords function below.
	static int[] breakingRecords(int[] scores) {

		Context context = new Context();

		for (int score : scores) {
			checkMaxScore(score, context);
			checkMinScore(score, context);
		}
		
		return elaborateResultArray(context);
	}

	private static int[] elaborateResultArray(Context context) {
		int[] result = new int[2];
		
		result[0] = context.getMaxScoreBreaks();
		result[1] = context.getMinScoreBreaks();
		return result;
	}

	private static void checkMaxScore(int score, Context context) {
		if (Objects.isNull(context.getMaxScore())) {
			context.setMaxScore(score);
		} else {
			if (score > context.getMaxScore()) {
				context.increaseMaxScoreBreaks();
				context.setMaxScore(score);
			}
		}
	}

	private static void checkMinScore(int score, Context context) {
		if (Objects.isNull(context.getMinScore())) {
			context.setMinScore(score);
		} else {
			if (score < context.getMinScore()) {
				context.increaseMinScoreBreaks();
				context.setMinScore(score);
			}
		}
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(
				new FileWriter(System.getenv("OUTPUT_PATH")));

		int n = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		int[] scores = new int[n];

		String[] scoresItems = scanner.nextLine()
				.split(" ");
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int i = 0; i < n; i++) {
			int scoresItem = Integer.parseInt(scoresItems[i]);
			scores[i] = scoresItem;
		}

		int[] result = breakingRecords(scores);

		for (int i = 0; i < result.length; i++) {
			bufferedWriter.write(String.valueOf(result[i]));

			if (i != result.length - 1) {
				bufferedWriter.write(" ");
			}
		}

		bufferedWriter.newLine();

		bufferedWriter.close();

		scanner.close();
	}

	private static class Context {
		private Integer maxScore;

		private Integer minScore;

		private int maxScoreBreaks;

		private int minScoreBreaks;

		public Context() {
			maxScore = null;
			minScore = null;
			minScoreBreaks = 0;
			maxScoreBreaks = 0;
		}

		public Integer getMaxScore() {
			return maxScore;
		}

		public void setMaxScore(Integer maxScore) {
			this.maxScore = maxScore;
		}

		public Integer getMinScore() {
			return minScore;
		}

		public void setMinScore(Integer minScore) {
			this.minScore = minScore;
		}

		public int getMaxScoreBreaks() {
			return maxScoreBreaks;
		}

		public int getMinScoreBreaks() {
			return minScoreBreaks;
		}

		public void increaseMinScoreBreaks() {
			minScoreBreaks++;
		}

		public void increaseMaxScoreBreaks() {
			maxScoreBreaks++;
		}
	}
}
