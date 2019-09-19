import java.math.BigInteger;
import java.util.Scanner;

public class Solution {

	// Complete the extraLongFactorials function below.
	static void extraLongFactorials(int n) {

		BigInteger bigInteger = BigInteger.valueOf(1);

		for (int number = n; number > 0; number--) {
			bigInteger = bigInteger.multiply(BigInteger.valueOf(number));
		}

		System.out.println(bigInteger.toString());
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		int n = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		extraLongFactorials(n);

		scanner.close();
	}
}
