import java.io.*;

class Result {

    /*
     * Complete the 'marsExploration' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts STRING s as parameter.
     */

    public static int marsExploration(String s) {
        var changedCharacters = 0;
        for (int index = 0; index < s.length(); index += 3) {
            if (s.charAt(index) != 'S') {
                changedCharacters++;
            }
            if (s.charAt(index + 1) != 'O') {
                changedCharacters++;
            }
            if (s.charAt(index + 2) != 'S') {
                changedCharacters++;
            }
        }
        return changedCharacters;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String s = bufferedReader.readLine();

        int result = Result.marsExploration(s);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
