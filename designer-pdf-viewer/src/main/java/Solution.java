import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Solution {

	private static final char[] ENGLISH_ALPHABET = "abcdefghijklmnopqrstuvwxyz".toCharArray();

	// Complete the designerPdfViewer function below.
	static int designerPdfViewer(int[] h, String word) {
		Map<Character, Integer> characterHeightMap = createCharacterHeightMap(h);

		int highestHeight = retrieveHighestHeight(word, characterHeightMap);

		return word.length() * highestHeight;
	}

	private static Map<Character, Integer> createCharacterHeightMap(int[] h) {
		Map<Character, Integer> characterHeightMap = new HashMap<>();

		for (int index = 0; index < h.length; index++) {
			characterHeightMap.put(ENGLISH_ALPHABET[index], h[index]);
		}

		return characterHeightMap;
	}

	private static int retrieveHighestHeight(String word,
			Map<Character, Integer> characterHeightMap) {
		int highestHeight = 0;

		for (char letter : word.toLowerCase()
				.toCharArray()) {
			Integer letterHeight = characterHeightMap.get(letter);
			if (letterHeight > highestHeight) {
				highestHeight = letterHeight;
			}
		}

		return highestHeight;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(
				new FileWriter(System.getenv("OUTPUT_PATH")));

		int[] h = new int[26];

		String[] hItems = scanner.nextLine()
				.split(" ");
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int i = 0; i < 26; i++) {
			int hItem = Integer.parseInt(hItems[i]);
			h[i] = hItem;
		}

		String word = scanner.nextLine();

		int result = designerPdfViewer(h, word);

		bufferedWriter.write(String.valueOf(result));
		bufferedWriter.newLine();

		bufferedWriter.close();

		scanner.close();
	}
}
