import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Solution {

	// Complete the appendAndDelete function below.
	static String appendAndDelete(String s, String t, int k) {
		Context context = new Context(s, t, k);
		
		if (canFullyRewrite(context)) {
			return "Yes";
		}

		if (areSameSequences(context)) {
			return hasOddRemainingMoves(context.getMoves()) ? "Yes" : "No";
		}

		// Append only.
		if (shouldAppendOnly(context)) {
			if (hasEnoughMovesToAppend(context)) {
				int remainingMoves = context.getRemainingTargetCharacters() - context.getMoves();
				return hasOddRemainingMoves(remainingMoves) ? "Yes" : "No";
			} else {
				return "No";
			}
		}

		// Delete only.
		if (shouldDeleteOnly(context)) {
				if (hasEnoughMovesToDelete(context)) {
					int remainingMoves = context.getRemainingSourceCharacters() - context.getMoves();
					return hasOddRemainingMoves(remainingMoves) ? "Yes" : "No";
				} else {
					return "No";
				}
			}

		// Append and delete.
		if (hasEnoughMoves(k, context.getTotalRemainingCharacters())) {
			if (context.isNoMatchingPrefix()) {
				return "Yes";
			} else {
				int remainingMoves = context.getTotalRemainingCharacters() - context.getMoves();
				return hasOddRemainingMoves(remainingMoves) ? "Yes" : "No";
			}
		}
		
		return "No";
	}

	private static boolean canFullyRewrite(Context context) {
		return context.getMoves() >= context.getSource().length() + context.getTarget().length();
	}

	private static boolean hasEnoughMoves(int totalMoves, int requiredMoves) {
		return totalMoves >= requiredMoves;
	}

	private static boolean shouldDeleteOnly(Context context) {
		return context.getSource()
				.length() > context.getTarget()
						.length()
				&& context.getMatchingPrefix()
						.equals(context.getTarget());
	}

	private static boolean hasEnoughMovesToAppend(Context context) {
		return hasEnoughMoves(context.getMoves(), context.getRemainingTargetCharacters());
	}
	
	private static boolean hasEnoughMovesToDelete(Context context) {
		return hasEnoughMoves(context.getMoves(), context.getRemainingSourceCharacters());
	}
	
	private static boolean shouldAppendOnly(Context context) {
		return context.getSource()
				.length() < context.getTarget()
						.length()
				&& context.getMatchingPrefix()
						.equals(context.getSource());
	}

	private static boolean hasOddRemainingMoves(int moves) {
		return moves % 2 == 0;
	}

	private static boolean areSameSequences(Context context) {
		return context.getSource()
				.equals(context.getTarget());
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(
		new FileWriter(System.getenv("OUTPUT_PATH")));

		String s = scanner.nextLine();

		String t = scanner.nextLine();

		int k = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		String result = appendAndDelete(s, t, k);

		bufferedWriter.write(result);
		bufferedWriter.newLine();

		bufferedWriter.close();

		scanner.close();
	}

	private static class Context {

		private String matchingPrefix;
		private int remainingTargetCharacters;
		private int remainingSourceCharacters;
		private int totalRemainingCharacters;
		private boolean noMatchingPrefix;
		private String source;
		private String target;
		private int moves;

		public Context(String s, String t, int k) {
			source = s;
			target = t;
			moves = k;
			matchingPrefix = retrieveMatchingPrefix(s, t);
			remainingTargetCharacters = t.replaceFirst(matchingPrefix, "")
					.length();
			remainingSourceCharacters = s.replaceFirst(matchingPrefix, "")
					.length();
			totalRemainingCharacters = remainingSourceCharacters + remainingTargetCharacters;
			noMatchingPrefix = matchingPrefix.length() == 0;
		}

		public String getMatchingPrefix() {
			return matchingPrefix;
		}

		public int getRemainingTargetCharacters() {
			return remainingTargetCharacters;
		}

		public int getRemainingSourceCharacters() {
			return remainingSourceCharacters;
		}

		public int getTotalRemainingCharacters() {
			return totalRemainingCharacters;
		}

		public boolean isNoMatchingPrefix() {
			return noMatchingPrefix;
		}

		public String getSource() {
			return source;
		}

		public String getTarget() {
			return target;
		}

		public int getMoves() {
			return moves;
		}

		private String retrieveMatchingPrefix(String s, String t) {
			int index = 0;

			while (index < s.length() && index < t.length() && s.charAt(index) == t.charAt(index)) {
				index++;
			}

			return s.substring(0, index);
		}
	}
}
