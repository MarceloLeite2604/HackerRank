package main

import (
	"bufio"
	"fmt"
	"io"
	"os"
	"strconv"
	"strings"
)

const (
	yes = "YES"
	no  = "NO"
)

// Complete the larrysArray function below.
func larrysArray(A []int32) string {

	totalInverions := calculateTotalInversions(A)

	if totalInverions%2 == 0 {
		return yes
	}

	return no
}

func calculateTotalInversions(grid []int32) int {

	totalInversions := 0

	for index := range grid {
		totalInversions += calculateInversions(grid, index)
	}

	return totalInversions
}

func calculateInversions(grid []int32, analyzedIndex int) int {
	analyzedValue := grid[analyzedIndex]

	inversions := 0

	for index := analyzedIndex + 1; index < len(grid); index++ {
		if analyzedValue > grid[index] {
			inversions++
		}
	}

	return inversions
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

		ATemp := strings.Split(readLine(reader), " ")

		var A []int32

		for i := 0; i < int(n); i++ {
			AItemTemp, err := strconv.ParseInt(ATemp[i], 10, 64)
			checkError(err)
			AItem := int32(AItemTemp)
			A = append(A, AItem)
		}

		result := larrysArray(A)

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
