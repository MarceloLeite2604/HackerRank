import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Solution {

	private static Map<Integer, String> positionCharactersMap = new HashMap<>();

	public static int[][] calculateLcsTable(char[] firstGroup, char[] secondGroup) {
		int[][] table = new int[firstGroup.length + 1][secondGroup.length + 1];

		for (int position = 0; position <= firstGroup.length; position++) {
			table[position][0] = 0;
		}

		for (int position = 0; position <= secondGroup.length; position++) {
			table[0][position] = 0;
		}

		for (int firstPosition = 1; firstPosition <= firstGroup.length; firstPosition++) {
			for (int secondPosition = 1; secondPosition <= secondGroup.length; secondPosition++) {
				if (firstGroup[firstPosition - 1] == secondGroup[secondPosition - 1]) {
					table[firstPosition][secondPosition] = table[firstPosition - 1][secondPosition
							- 1] + 1;
				} else {
					table[firstPosition][secondPosition] = max(
							table[firstPosition][secondPosition - 1],
							table[firstPosition - 1][secondPosition]);
				}
			}
		}

		return table;
	}

	public static int max(int first, int second) {
		return (first > second ? first : second);
	}

	public static char[] retrieveLcsChars(int[][] table, char[] firstGroup, char[] secondGroup,
			int firstPosition, int secondPosition) {

		if (firstPosition == 0 || secondPosition == 0) {
			return new char[0];
		}

		if (firstGroup[firstPosition - 1] == secondGroup[secondPosition - 1]) {
			char[] partialResult = retrieveLcsChars(table, firstGroup, secondGroup,
					firstPosition - 1, secondPosition - 1);
			char[] result = new char[partialResult.length + 1];
			System.arraycopy(partialResult, 0, result, 0, partialResult.length);
			System.arraycopy(firstGroup, firstPosition - 1, result, partialResult.length, 1);
			return result;
		}

		if (table[firstPosition][secondPosition - 1] > table[firstPosition - 1][secondPosition]) {
			return retrieveLcsChars(table, firstGroup, secondGroup, firstPosition,
					secondPosition - 1);
		} else {
			return retrieveLcsChars(table, firstGroup, secondGroup, firstPosition - 1,
					secondPosition);
		}
	}

	public static String lcs(String a, String b) {
		char[] firstGroup = a.toCharArray();
		char[] secondGroup = b.toCharArray();

		int[][] lcsTable = calculateLcsTable(firstGroup, secondGroup);

		char[] lcs = retrieveLcsChars(lcsTable, firstGroup, secondGroup, firstGroup.length,
				secondGroup.length);

		return new String(lcs);
	}

	/*
	 * Complete the tutzkiAndLcs function below.
	 */
	static int tutzkiAndLcs(String a, String b) throws InterruptedException, ExecutionException {
		int availableProcessors = Runtime.getRuntime()
				.availableProcessors();

		ExecutorService executorService = Executors.newFixedThreadPool(availableProcessors);

		List<Integer[]> ranges = calculateRanges(b, availableProcessors);

		String originalLcs = lcs(a, b);

		List<Future<Integer>> futures = new ArrayList<>();
		for (Integer[] range : ranges) {
			Callable<Integer> callable = () -> partialTutzkiAndLcs(a, b, originalLcs, range);
			futures.add(executorService.submit(callable));
		}

		int result = 0;
		while (!futures.isEmpty()) {
			Iterator<Future<Integer>> iterator = futures.iterator();
			while (iterator.hasNext()) {
				Future<Integer> future = iterator.next();
				if (future.isDone()) {
					result += future.get();
					iterator.remove();
				}
			}
		}

		executorService.shutdown();
		return result;
	}

	private static List<Integer[]> calculateRanges(String text, int availableProcessors) {
		int numberOfThreads = (text.length() < availableProcessors ? text.length()
				: availableProcessors);
		List<Integer[]> ranges = new ArrayList<>();

		int amount = (text.length() / numberOfThreads);

		for (int threadCount = 1; threadCount <= numberOfThreads; threadCount++) {
			int startPosition = amount * (threadCount - 1);

			int endPosition;
			if (threadCount == numberOfThreads) {
				endPosition = text.length();
			} else {
				endPosition = (amount * threadCount);
			}

			Integer[] range = new Integer[2];
			range[0] = startPosition;
			range[1] = endPosition;
			ranges.add(range);
		}

		return ranges;
	}

	private static int partialTutzkiAndLcs(String a, String b, String originalLcs,
			Integer[] range) {
		int firstGroupLength = a.length();
		int result = 0;

		int originalLcsSize = originalLcs.length();

		for (int secondIndex = range[0]; secondIndex < range[1]; secondIndex++) {
			for (int firstIndex = 0; firstIndex <= firstGroupLength; firstIndex++) {
				String preCharLcs = "";
				String postCharLcs = "";
				if (firstIndex > 0 && secondIndex > 0) {
					preCharLcs = lcs(a.substring(0, firstIndex), b.substring(0, secondIndex));
				}
				if (firstIndex < firstGroupLength) {
					postCharLcs = lcs(a.substring(firstIndex), b.substring(secondIndex + 1));
				}

				char character = b.charAt(secondIndex);
				String lcs = preCharLcs + character + postCharLcs;
				if (lcs.length() > originalLcsSize) {
					synchronized (Solution.class) {
						if (positionCharactersMap.containsKey(firstIndex)) {
							String charactersAtPosition = positionCharactersMap.get(firstIndex);
							if (charactersAtPosition.indexOf(character) == -1) {
								result++;
								positionCharactersMap.put(firstIndex,
										charactersAtPosition.concat(Character.toString(character)));
							}
						} else {
							result++;
							positionCharactersMap.put(firstIndex, Character.toString(character));
						}
					}

				}

			}
		}

		return result;
	}

	/*
	 * This hasn't been used to solve the problem, but since I've developed it (and
	 * it works) I thought it would be nice to leave it here. =)
	 */
	public static void printDiff(int[][] table, char[] firstGroup, char[] secondGroup,
			int firstPosition, int secondPosition) {

		if (firstPosition > 0 && secondPosition > 0
				&& firstGroup[firstPosition - 1] == secondGroup[secondPosition - 1]) {
			printDiff(table, firstGroup, secondGroup, firstPosition - 1, secondPosition - 1);
			System.out.println(" " + firstGroup[firstPosition - 1]);
		} else {
			if (secondPosition > 0 && (firstPosition == 0 || table[firstPosition][secondPosition
					- 1] >= table[firstPosition - 1][secondPosition])) {

				printDiff(table, firstGroup, secondGroup, firstPosition, secondPosition - 1);
				System.out.println("+ " + secondGroup[secondPosition - 1]);
			} else {
				if (firstPosition > 0 && (secondPosition == 0 || table[firstPosition][secondPosition
						- 1] < table[firstPosition - 1][secondPosition])) {
					printDiff(table, firstGroup, secondGroup, firstPosition - 1, secondPosition);
					System.out.println("- " + firstGroup[firstPosition - 1]);
				}
			}
		}
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args)
			throws IOException, InterruptedException, ExecutionException {
		BufferedWriter bufferedWriter = new BufferedWriter(
				new FileWriter(System.getenv("OUTPUT_PATH")));

		String a = scanner.nextLine();

		String b = scanner.nextLine();

		int result = tutzkiAndLcs(a, b);

		bufferedWriter.write(String.valueOf(result));
		bufferedWriter.newLine();

		bufferedWriter.close();

	}
}
