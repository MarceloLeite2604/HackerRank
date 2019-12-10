package main

import (
	"bufio"
	"fmt"
	"io"
	"os"
	"sort"
	"strconv"
	"strings"
)

const noAnswer = "no answer"

func orderRunesAscending(runes []rune) {
	strings := make([]string, 0, len(runes))
	for _, r := range runes {
		strings = append(strings, string(r))
	}

	sort.Strings(strings)

	for i, s := range strings {
		runes[i] = []rune(s)[0]
	}
}

func retrieveCandidateIndexToSwap(runes []rune, index int) int {
	candidateIndex := -1

	for indexAnalized, runeAnalized := range runes[index+1:] {
		if runeAnalized > runes[index] {
			if candidateIndex == -1 || runeAnalized < runes[candidateIndex] {
				candidateIndex = index + 1 + indexAnalized
			}
		}
	}

	return candidateIndex
}

func shouldSwap(index int, swapped bool) bool {
	return index != -1 && !swapped
}

func orderRunesHigherThan(runes []rune, index int) {
	startingIndex := index + 1

	higherRunes := runes[startingIndex:]
	orderRunesAscending(higherRunes)

	for i, r := range higherRunes {
		runes[startingIndex+i] = r
	}
}

func swapHigherClosestToEnd(runes []rune, index int, swapped *bool) {
	if index < len(runes)-1 {

		candidateIndex := retrieveCandidateIndexToSwap(runes, index)

		swapHigherClosestToEnd(runes, index+1, swapped)

		if shouldSwap(candidateIndex, *swapped) {

			runes[index], runes[candidateIndex] = runes[candidateIndex], runes[index]
			orderRunesHigherThan(runes, index)
			*swapped = true
		}
	}
}

// Complete the biggerIsGreater function below.
func biggerIsGreater(w string) string {

	runes := []rune(w)

	swapped := false
	swapHigherClosestToEnd(runes, 0, &swapped)

	if swapped {
		return string(runes)
	}

	return noAnswer
}

func main() {
	reader := bufio.NewReaderSize(os.Stdin, 1024*1024)

	stdout, err := os.Create(os.Getenv("OUTPUT_PATH"))
	checkError(err)

	defer stdout.Close()

	writer := bufio.NewWriterSize(stdout, 1024*1024)

	TTemp, err := strconv.ParseInt(readLine(reader), 10, 64)
	checkError(err)
	T := int32(TTemp)

	for TItr := 0; TItr < int(T); TItr++ {
		w := readLine(reader)

		result := biggerIsGreater(w)

		fmt.Fprintf(writer, "%s\n", result)
	}

	writer.Flush()
}

func readLine(reader *bufio.Reader) string {
	str, _, err := reader.ReadLine()
	if err == io.EOF {
		return ""
	}

	return strings.TrimRight(string(str), "\r\n")
}

func checkError(err error) {
	if err != nil {
		panic(err)
	}
}
