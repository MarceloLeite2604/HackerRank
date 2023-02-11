import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

class Result {

    public static final String DASH = "-";

    enum InputIndex {
        SORTED_ARRAY_INDEX,
        VALUE
    }

    ;

    /*
     * Complete the 'countSort' function below.
     *
     * The function accepts 2D_STRING_ARRAY arr as parameter.
     */

    public static void countSort(List<List<String>> arr) {

        final var sortedArray = new ArrayList<List<String>>();

        for (int inputIndex = 0; inputIndex < arr.size(); inputIndex++) {
            final var inputs = arr.get(inputIndex);
            final var resultIndex = Integer.parseInt(inputs.get(InputIndex.SORTED_ARRAY_INDEX.ordinal()));
            final var value = inputs.get(InputIndex.VALUE.ordinal());

            final var output = inputIndex < arr.size() / 2 ? DASH : value;

            IntStream.rangeClosed(sortedArray.size(), resultIndex)
                    .mapToObj(intValue -> new ArrayList<String>())
                    .forEach(sortedArray::add);

            final var strings = sortedArray.remove(resultIndex);
            strings.add(output);
            sortedArray.add(resultIndex, strings);
        }

        final var result = sortedArray.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.joining(" "));

        System.out.println(result);
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(bufferedReader.readLine()
                .trim());

        List<List<String>> arr = new ArrayList<>();

        IntStream.range(0, n)
                .forEach(i -> {
                    try {
                        arr.add(
                                Stream.of(bufferedReader.readLine()
                                                .replaceAll("\\s+$", "")
                                                .split(" "))
                                        .collect(toList())
                        );
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

        Result.countSort(arr);

        bufferedReader.close();
    }
}