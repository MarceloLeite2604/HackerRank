package main

import (
	"bufio"
	"fmt"
	"io"
	"os"
	"strconv"
	"strings"
)

// Complete the repeatedString function below.
func repeatedString(s string, n int64) int64 {
	var aIndexes []int

	for index, rune := range []rune(s) {
		if rune == 'a' {
			aIndexes = append(aIndexes, index)
		}
	}

	sLength := int64(len(s))

	fullRepetitions := int64(n / sLength)

	remainder := n % sLength

	result := fullRepetitions * int64(len(aIndexes))

	if remainder != 0 {
		for _, aIndex := range aIndexes {

			if int64(aIndex) < remainder {
				result++
			}
		}
	}

	return result
}

func main() {
	reader := bufio.NewReaderSize(os.Stdin, 1024*1024)

	stdout, err := os.Create(os.Getenv("OUTPUT_PATH"))
	checkError(err)

	defer stdout.Close()

	writer := bufio.NewWriterSize(stdout, 1024*1024)

	s := readLine(reader)

	n, err := strconv.ParseInt(readLine(reader), 10, 64)
	checkError(err)

	result := repeatedString(s, n)

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
