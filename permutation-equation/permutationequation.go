package main

import (
	"bufio"
	"errors"
	"fmt"
	"io"
	"os"
	"strconv"
	"strings"
)

func findBaseOneIndexOf(slice []int32, value int32) (int32, error) {
	var result int32 = -1
	found := false
	var index int32 = 1
	var err error

	for !found && index <= int32(len(slice)) {
		found = (value == slice[index-1])
		if found {
			result = index
		}
		index++
	}

	if !found {
		message := fmt.Sprintf("could not find index for value %v", value)
		err = errors.New(message)
	}

	return result, err
}

// Complete the permutationEquation function below.
func permutationEquation(p []int32) []int32 {
	result := make([]int32, len(p))
	for counter := 1; counter <= len(p); counter++ {
		position, err := findBaseOneIndexOf(p, int32(counter))
		checkError(err)
		position, err = findBaseOneIndexOf(p, int32(position))
		checkError(err)
		result[counter-1] = int32(position)
	}
	return result
}

func main() {
	reader := bufio.NewReaderSize(os.Stdin, 1024*1024)
	// stdin, err := os.Open("test-cases/test-case-0.txt")
	// checkError(err)
	// reader := bufio.NewReaderSize(stdin, 1024*1024)

	stdout, err := os.Create(os.Getenv("OUTPUT_PATH"))
	checkError(err)
	// stdout := os.Stdout

	defer stdout.Close()

	writer := bufio.NewWriterSize(stdout, 1024*1024)

	nTemp, err := strconv.ParseInt(readLine(reader), 10, 64)
	checkError(err)
	n := int32(nTemp)

	pTemp := strings.Split(readLine(reader), " ")

	var p []int32

	for i := 0; i < int(n); i++ {
		pItemTemp, err := strconv.ParseInt(pTemp[i], 10, 64)
		checkError(err)
		pItem := int32(pItemTemp)
		p = append(p, pItem)
	}

	result := permutationEquation(p)

	for i, resultItem := range result {
		fmt.Fprintf(writer, "%d", resultItem)

		if i != len(result)-1 {
			fmt.Fprintf(writer, "\n")
		}
	}

	fmt.Fprintf(writer, "\n")

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
