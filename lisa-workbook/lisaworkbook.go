package main

import (
	"bufio"
	"fmt"
	"io"
	"os"
	"strconv"
	"strings"
)

// Complete the workbook function below.
func workbook(n int32, k int32, arr []int32) int32 {
	maxProblemsPerPage := int(k)

	specialProblems := 0

	lastPage := 0

	for _, problems := range arr {
		firstPage := lastPage + 1

		if int(problems)%maxProblemsPerPage == 0 {
			lastPage = firstPage + (int(problems) / maxProblemsPerPage) - 1
		} else {
			lastPage = firstPage + (int(problems) / maxProblemsPerPage)
		}

		lastProblemIndex := 0
		for page := firstPage; page <= lastPage; page++ {
			firstProblemIndex := lastProblemIndex + 1
			var problemsOnPage int

			if int(problems)-lastProblemIndex >= maxProblemsPerPage {
				problemsOnPage = maxProblemsPerPage
			} else {
				problemsOnPage = int(problems) - lastProblemIndex
			}

			lastProblemIndex = firstProblemIndex + problemsOnPage - 1

			if page >= firstProblemIndex && page <= lastProblemIndex {
				specialProblems++
			}
		}
	}
	return int32(specialProblems)
}

func main() {
	reader := bufio.NewReaderSize(os.Stdin, 1024*1024)

	stdout, err := os.Create(os.Getenv("OUTPUT_PATH"))
	checkError(err)

	defer stdout.Close()

	writer := bufio.NewWriterSize(stdout, 1024*1024)

	nk := strings.Split(readLine(reader), " ")

	nTemp, err := strconv.ParseInt(nk[0], 10, 64)
	checkError(err)
	n := int32(nTemp)

	kTemp, err := strconv.ParseInt(nk[1], 10, 64)
	checkError(err)
	k := int32(kTemp)

	arrTemp := strings.Split(readLine(reader), " ")

	var arr []int32

	for i := 0; i < int(n); i++ {
		arrItemTemp, err := strconv.ParseInt(arrTemp[i], 10, 64)
		checkError(err)
		arrItem := int32(arrItemTemp)
		arr = append(arr, arrItem)
	}

	result := workbook(n, k, arr)

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
