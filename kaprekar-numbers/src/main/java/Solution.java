import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Solution {

	// Complete the kaprekarNumbers function below.
	static void kaprekarNumbers(int p, int q) {

		List<Integer> modifiedKaprekarNumbers = new ArrayList<>();
		for (int counter = p; counter <= q; counter++) {
			if (isModifiedKaprekar(counter)) {
				modifiedKaprekarNumbers.add(counter);
			}
		}

		String result;
		if (modifiedKaprekarNumbers.isEmpty()) {
			result = "INVALID RANGE";
		} else {
			result = createNumbersString(modifiedKaprekarNumbers);
		}

		System.out.println(result);
	}

	private static String createNumbersString(List<Integer> numbers) {
		return numbers.stream()
				.map(Object::toString)
				.collect(Collectors.joining(" "));
	}

	private static boolean isModifiedKaprekar(int intNumber) {
		BigDecimal number = BigDecimal.valueOf((long) intNumber);
		BigDecimal poweredNumber = number.multiply(number);
		int divisionIndex = poweredNumber.toString()
				.length()
				- number.toString()
						.length();

		BigDecimal[] splittedNumbers = splitNumber(poweredNumber, divisionIndex);

		BigDecimal sum = splittedNumbers[0].add(splittedNumbers[1]);

		return number.equals(sum);
	}

	private static BigDecimal[] splitNumber(BigDecimal number, int divisionIndex) {
		BigDecimal[] result = new BigDecimal[2];
		String stringNumber = number.toString();

		String stringRightNumber = stringNumber.substring(divisionIndex, stringNumber.length());
		String stringLeftNumber = stringNumber.substring(0, divisionIndex);

		result[1] = new BigDecimal(stringRightNumber);

		if (!stringLeftNumber.isEmpty()) {
			result[0] = new BigDecimal(stringLeftNumber);
		} else {
			result[0] = BigDecimal.ZERO;
		}

		return result;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		int p = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		int q = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		kaprekarNumbers(p, q);

		scanner.close();
	}
}
