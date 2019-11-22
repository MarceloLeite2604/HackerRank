import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {

	private static final String SWAP_OPERATION = "swap";
	private static final String REVERSE_OPERATION = "reverse";

	// Complete the almostSorted function below.
	static void almostSorted(int[] arr) {

		Context context = createContext(arr);

		if (isSorted(context)) {
			System.out.println("yes");
		} else {

			boolean almostSorted = false;

			String operation = null;
			int firstIndex = 0;
			int secondIndex = 0;

			List<Integer> upIndexes = context.getUpIndexes();
			List<Integer> dipIndexes = context.getDipIndexes();

			if (upIndexes.size() == 1 && dipIndexes.size() == 1) {
				almostSorted = true;

				firstIndex = dipIndexes.get(0);
				secondIndex = upIndexes.get(0);
				if (arr.length == 3) {
					almostSorted = false;
				} else {
					if (firstIndex + 1 == secondIndex) {
						operation = SWAP_OPERATION;
						almostSorted = isSortedAfterSwap(arr, firstIndex, secondIndex);
					} else {
						operation = REVERSE_OPERATION;
					}
				}

			}

			if (!almostSorted && upIndexes.size() == 2 && dipIndexes.size() == 2) {
				almostSorted = true;

				int firstDipIndex = dipIndexes.get(0);
				int secondDipIndex = dipIndexes.get(1);
				int firstUpIndex = upIndexes.get(0);
				int secondUpIndex = upIndexes.get(1);

				if (firstDipIndex + 1 == firstUpIndex && secondDipIndex + 1 == secondUpIndex) {
					operation = SWAP_OPERATION;
					firstIndex = dipIndexes.get(0);
					secondIndex = upIndexes.get(1);
					almostSorted = isSortedAfterSwap(arr, firstIndex, secondIndex);
				} else {
					almostSorted = false;
				}
			}

			if (!almostSorted && upIndexes.isEmpty() && dipIndexes.size() == 2) {
				almostSorted = true;

				firstIndex = dipIndexes.get(0);
				secondIndex = dipIndexes.get(1);

				if (firstIndex + 1 == secondIndex && secondIndex + 1 == arr.length) {
					operation = SWAP_OPERATION;
					almostSorted = isSortedAfterSwap(arr, firstIndex, secondIndex);
				} else {
					operation = REVERSE_OPERATION;
				}

			}
			if (!almostSorted && upIndexes.size() == 1 && dipIndexes.size() == 3) {
				almostSorted = true;
				operation = SWAP_OPERATION;
				firstIndex = 0;
				secondIndex = arr.length - 1;
				almostSorted = isSortedAfterSwap(arr, firstIndex, secondIndex);
			}

			if (almostSorted) {
				// The answer is 1-based indexing, but Java is zero-based indexing. Thus, an
				// increment is necessary.
				System.out.println("yes");
				System.out.println(operation + " " + (firstIndex + 1) + " " + (secondIndex + 1));
			} else {
				System.out.println("no");
			}

		}

	}

	private static boolean isSortedAfterSwap(int[] arr, int firstIndex, int secondIndex) {
		swap(arr, firstIndex, secondIndex);
		Context context = createContext(arr);
		return isSorted(context);
	}

	private static void swap(int[] array, int firstIndex, int secondIndex) {
		int temp = array[firstIndex];
		array[firstIndex] = array[secondIndex];
		array[secondIndex] = temp;
	}

	private static boolean isSorted(Context context) {
		return context.getUpIndexes()
				.isEmpty()
				&& context.getDipIndexes()
						.isEmpty();
	}

	private static Context createContext(int[] arr) {
		List<Integer> dipIndexes = new ArrayList<>();
		List<Integer> upIndexes = new ArrayList<>();

		for (int index = 0; index < arr.length; index++) {
			Growth growth = Growth.check(arr, index);

			switch (growth) {
			case DIP:
				dipIndexes.add(index);
				break;
			case UP:
				upIndexes.add(index);
				break;
			case NONE:
			default:
				break;
			}
		}

		return new Context(dipIndexes, upIndexes);
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws Exception {
		int n = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		int[] arr = new int[n];

		String[] arrItems = scanner.nextLine()
				.split(" ");
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int i = 0; i < n; i++) {
			int arrItem = Integer.parseInt(arrItems[i]);
			arr[i] = arrItem;
		}

		almostSorted(arr);

		scanner.close();
	}

	private static class Context {
		private List<Integer> dipIndexes;

		private List<Integer> upIndexes;

		public Context(List<Integer> dipIndexes, List<Integer> upIndexes) {
			this.dipIndexes = dipIndexes;
			this.upIndexes = upIndexes;
		}

		public List<Integer> getDipIndexes() {
			return dipIndexes;
		}

		public List<Integer> getUpIndexes() {
			return upIndexes;
		}
	}

	private enum Growth {
		NONE,
		DIP,
		UP;

		private static Growth check(int[] array, int index) {

			if (index == 0) {
				return checkFirstValue(array);
			}

			if (index == array.length - 1) {
				return checkLastValue(array);
			}

			if (isDip(array, index)) {
				return DIP;
			}

			if (isUp(array, index)) {
				return UP;
			}

			return NONE;
		}

		private static Growth checkFirstValue(int[] array) {
			if (array[0] > array[1]) {
				return DIP;
			}
			return NONE;
		}

		private static Growth checkLastValue(int[] array) {
			if (array[array.length - 1] < array[array.length - 2]) {
				return DIP;
			}
			return NONE;
		}

		private static boolean isDip(int[] arr, int index) {
			return arr[index] > arr[index - 1] && arr[index] > arr[index + 1];
		}

		private static boolean isUp(int[] arr, int index) {
			return arr[index] < arr[index - 1] && arr[index] < arr[index + 1];
		}
	}
}
