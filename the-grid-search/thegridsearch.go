package main

import (
	"bufio"
	"fmt"
	"io"
	"os"
	"reflect"
	"strconv"
	"strings"
)

// Complete the gridSearch function below.
func gridSearch(G []string, P []string) string {

	found := false
	rowIndex := 0
	for !found && rowIndex < len(G)-len(P)+1 {
		columnIndex := 0
		for !found && columnIndex < len(G[rowIndex])-len(P[0])+1 {
			found = checkGridMatches(G, P, rowIndex, columnIndex)
			columnIndex++
		}
		rowIndex++
	}

	if found {
		return "YES"
	}

	return "NO"
}

func checkGridMatches(G []string, P []string, startRow int, startColumn int) bool {
	matches := true

	row := 0
	for matches && row < len(P) {
		gSlice := G[startRow+row][startColumn : startColumn+len(P[row])]
		matches = reflect.DeepEqual(gSlice, P[row])
		row++
	}

	return matches
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
		RC := strings.Split(readLine(reader), " ")

		RTemp, err := strconv.ParseInt(RC[0], 10, 64)
		checkError(err)
		R := int32(RTemp)

		var G []string

		for i := 0; i < int(R); i++ {
			GItem := readLine(reader)
			G = append(G, GItem)
		}

		rc := strings.Split(readLine(reader), " ")

		rTemp, err := strconv.ParseInt(rc[0], 10, 64)
		checkError(err)
		r := int32(rTemp)

		var P []string

		for i := 0; i < int(r); i++ {
			PItem := readLine(reader)
			P = append(P, PItem)
		}

		result := gridSearch(G, P)

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
