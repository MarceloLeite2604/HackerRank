package main

import (
	"bufio"
	"fmt"
	"io"
	"os"
	"strconv"
	"strings"
)

func retrieveDigits(number int32) []int32 {
	stringNumber := strconv.Itoa(int(number))

	runes := []rune(stringNumber)
	result := make([]int32, len(runes))

	for index, rune := range runes {
		value, err := strconv.Atoi(string(rune))
		checkError(err)
		result[index] = int32(value)
	}

	return result
}

// Complete the findDigits function below.
func findDigits(n int32) int32 {
	var result int32
	digits := retrieveDigits(n)

	for _, digit := range digits {
		if digit != 0 && n%digit == 0 {
			result++
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

	tTemp, err := strconv.ParseInt(readLine(reader), 10, 64)
	checkError(err)
	t := int32(tTemp)

	for tItr := 0; tItr < int(t); tItr++ {
		nTemp, err := strconv.ParseInt(readLine(reader), 10, 64)
		checkError(err)
		n := int32(nTemp)

		result := findDigits(n)

		fmt.Fprintf(writer, "%d\n", result)
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
