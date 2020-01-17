package main

import (
	"bufio"
	"fmt"
	"io"
	"os"
	"strconv"
	"strings"
)

func contains(slice []rune, value rune) bool {

	for _, sliceValue := range slice {
		if sliceValue == value {
			return true
		}
	}

	return false
}

func findRunes(text string) []rune {
	result := make([]rune, 0)

	for _, checkedRune := range []rune(text) {
		if !contains(result, checkedRune) {
			result = append(result, checkedRune)
		}
	}

	return result
}

// Complete the alternate function below.
func alternate(s string) int32 {

	differentRunes := findRunes(s)

	largestResult := 0

	for firstIndex := 0; firstIndex < len(differentRunes)-1; firstIndex++ {
		firstRune := differentRunes[firstIndex]
		for secondIndex := firstIndex + 1; secondIndex < len(differentRunes); secondIndex++ {
			secondRune := differentRunes[secondIndex]
			reducedText := elaborateReducedText(s, differentRunes, firstIndex, secondIndex)
			largestResult = checkLargestResult(reducedText, largestResult, firstRune, secondRune)
		}
	}

	return int32(largestResult)
}

func elaborateReducedText(text string, runes []rune, firstIndex, secondIndex int) string {
	reducedText := text
	for checkedRuneIndex, checkedRune := range runes {
		if checkedRuneIndex != firstIndex && checkedRuneIndex != secondIndex {
			reducedText = strings.ReplaceAll(reducedText, string(checkedRune), "")
		}
	}

	return reducedText
}

func checkLargestResult(reducedText string, largestResult int, firstRune, secondRune rune) int {
	result := largestResult

	if len(reducedText) > largestResult && isAnAcceptableAnswer(reducedText, firstRune, secondRune) {
		result = len(reducedText)
	}

	return result
}

func isAnAcceptableAnswer(text string, firstRune, secondRune rune) bool {

	if len(text) == 0 {
		return true
	}

	runes := []rune(text)

	shouldBeEqualToFirstRune := runes[0] == firstRune

	for _, checkedRune := range runes {

		if shouldBeEqualToFirstRune {

			if checkedRune != firstRune {
				return false
			}

		} else {
			if checkedRune != secondRune {
				return false
			}
		}

		shouldBeEqualToFirstRune = !shouldBeEqualToFirstRune
	}

	return true
}

func main() {
	reader := bufio.NewReaderSize(os.Stdin, 16*1024*1024)

	stdout, err := os.Create(os.Getenv("OUTPUT_PATH"))
	checkError(err)

	defer stdout.Close()

	writer := bufio.NewWriterSize(stdout, 16*1024*1024)

	lTemp, err := strconv.ParseInt(strings.TrimSpace(readLine(reader)), 10, 64)
	checkError(err)
	_ = int32(lTemp)

	s := readLine(reader)

	result := alternate(s)

	fmt.Fprintf(writer, "%d\n", result)

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
