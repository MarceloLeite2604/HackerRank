import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Solution {

	private static final int DAY_OF_THE_PROGRAMMER_DAYS = 256;

	private static final int RUSSIA_GREGORIAN_CALENDAR_CHANGE_YEAR = 1918;
	
	private static final String DAY_OF_THE_PROGRAMMER_AT_1918 = "26.09.1918";

	// Complete the dayOfProgrammer function below.
	static String dayOfProgrammer(int year) {
		
		if (year == RUSSIA_GREGORIAN_CALENDAR_CHANGE_YEAR) {
			return DAY_OF_THE_PROGRAMMER_AT_1918;
		}
		
		GregorianCalendar gregorianCalendar = new GregorianCalendar(year, 1, 1);

		if (year < RUSSIA_GREGORIAN_CALENDAR_CHANGE_YEAR) {
			gregorianCalendar.setGregorianChange(new Date(Long.MAX_VALUE));
		}
		
		gregorianCalendar.set(Calendar.YEAR, year);
		gregorianCalendar.set(Calendar.DAY_OF_YEAR, DAY_OF_THE_PROGRAMMER_DAYS);
		
		// Calendar months are 0-based index, thus the + 1.
		int month = gregorianCalendar.get(Calendar.MONTH) + 1;
		int day = gregorianCalendar.get(Calendar.DAY_OF_MONTH);
		
		return String.format("%02d.%02d.%04d", day, month, year);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bufferedWriter = new BufferedWriter(
				new FileWriter(System.getenv("OUTPUT_PATH")));

		int year = Integer.parseInt(bufferedReader.readLine()
				.trim());

		String result = dayOfProgrammer(year);

		bufferedWriter.write(result);
		bufferedWriter.newLine();

		bufferedReader.close();
		bufferedWriter.close();
	}
}
