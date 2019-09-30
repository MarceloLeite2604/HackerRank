import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class Solution {

	private static final BigDecimal TWO = BigDecimal.valueOf(2);

	private static final Trie TRIE = buildTrie();

	/*
	 * Complete the twoTwo function below.
	 */
	static int twoTwo(String a) {

		int result = 0;
		for (int index = 0; index < a.length(); index++) {
			result += traverseString(a, index, TRIE.getRoot());
		}
		return result;
	}

	private static int traverseString(String text, int index, TrieNode previousTrieNode) {

		if (index >= text.length()) {
			return 0;
		}

		TrieNode trieNode = previousTrieNode.getChildren()
				.get(text.charAt(index));

		if (Objects.isNull(trieNode)) {
			return 0;
		}

		int result = traverseString(text, index + 1, trieNode);

		if (trieNode.isWord()) {
			result++;
		}

		return result;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(
				new FileWriter(System.getenv("OUTPUT_PATH")));

		int t = Integer.parseInt(scanner.nextLine()
				.trim());

		for (int tItr = 0; tItr < t; tItr++) {
			String a = scanner.nextLine();

			int result = twoTwo(a);

			bufferedWriter.write(String.valueOf(result));
			bufferedWriter.newLine();
		}

		bufferedWriter.close();
	}

	private static Trie buildTrie() {
		BigDecimal value = BigDecimal.ONE;

		Trie result = new Trie();

		for (int counter = 0; counter <= 800; counter++) {
			result.insert(value.toString()
					.toCharArray());
			value = value.multiply(TWO);
		}

		return result;
	}

	private static class TrieNode {
		private Map<Character, TrieNode> children;
		private boolean word;

		public TrieNode() {
			this.children = new HashMap<>();
			this.word = false;
		}

		public Map<Character, TrieNode> getChildren() {
			return children;
		}

		public boolean isWord() {
			return word;
		}

		public void setWord(boolean word) {
			this.word = word;
		}
	}

	private static class Trie {
		private TrieNode root;

		public Trie() {
			root = new TrieNode();
		}

		public TrieNode getRoot() {
			return root;
		}

		public void insert(char[] array) {
			TrieNode current = root;

			for (int index = 0; index < array.length; index++) {
				current = current.getChildren()
						.computeIfAbsent(array[index], character -> new TrieNode());
			}
			current.setWord(true);
		}
	}
}
