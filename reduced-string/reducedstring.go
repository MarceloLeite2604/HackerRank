package main

import (
	"bufio"
	"fmt"
	"io"
	"os"
	"strings"
)

const (
	emptyString = "Empty String"
)

// Complete the superReducedString function below.
func superReducedString(s string) string {

	runes := []rune(s)

	var indexesToRemove []int
	reduced := true

	for reduced {
		indexesToRemove, reduced = checkIndexesToRemove(runes)
		runes = removeIndexes(runes, indexesToRemove)
	}

	if len(runes) == 0 {
		return emptyString
	}

	return string(runes)
}

func checkIndexesToRemove(runes []rune) ([]int, bool) {
	indexesToRemove := make([]int, 0)
	reduced := false

	for index := 0; index < len(runes)-1; index++ {
		currentRune := runes[index]
		nextRune := runes[index+1]

		if nextRune == currentRune {
			indexesToRemove = append(indexesToRemove, index, index+1)
			index++
			reduced = true
		}
	}

	return indexesToRemove, reduced
}

func removeIndexes(runes []rune, indexesToRemove []int) []rune {
	var nextRunes []rune
	previousIndex := 0
	for _, indexToRemove := range indexesToRemove {
		nextRunes = append(nextRunes, runes[previousIndex:indexToRemove]...)
		previousIndex = indexToRemove + 1
	}
	nextRunes = append(nextRunes, runes[previousIndex:len(runes)]...)
	return nextRunes
}

func main() {
	reader := bufio.NewReaderSize(os.Stdin, 16*1024*1024)

	stdout, err := os.Create(os.Getenv("OUTPUT_PATH"))
	checkError(err)

	defer stdout.Close()

	writer := bufio.NewWriterSize(stdout, 16*1024*1024)

	s := readLine(reader)

	result := superReducedString(s)

	fmt.Fprintf(writer, "%s\n", result)

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
