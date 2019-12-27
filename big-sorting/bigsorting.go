package main

import (
	"bufio"
	"fmt"
	"io"
	"math/big"
	"os"
	"sort"
	"strconv"
	"strings"
)

type bigIntSlice struct {
	Integers []big.Int
}

func (bigInts bigIntSlice) Len() int {
	return len(bigInts.Integers)
}

func (bigInts bigIntSlice) Less(i, j int) bool {
	first := bigInts.Integers[i]
	second := bigInts.Integers[j]

	return first.Cmp(&second) < 0
}

func (bigInts bigIntSlice) Swap(i, j int) {
	bigInts.Integers[i], bigInts.Integers[j] = bigInts.Integers[j], bigInts.Integers[i]
}

// Complete the bigSorting function below.
func bigSorting(unsorted []string) []string {

	bigInts := createSliceOfBigInts(unsorted)

	sort.Sort(bigInts)

	return createSliceOfStrings(bigInts)
}

func createSliceOfBigInts(unsorted []string) bigIntSlice {
	integers := make([]big.Int, len(unsorted))

	for index, stringValue := range unsorted {

		big := big.NewInt(0)

		_, ok := big.SetString(stringValue, 10)
		if !ok {
			panic(fmt.Errorf("Could not parse value \"%v\"", stringValue))
		}

		integers[index] = *big
	}

	return bigIntSlice{Integers: integers}
}

func createSliceOfStrings(bigInts bigIntSlice) []string {
	result := make([]string, len(bigInts.Integers))

	for index, bigInt := range bigInts.Integers {
		result[index] = bigInt.String()
	}

	return result
}

func main() {
	reader := bufio.NewReaderSize(os.Stdin, 1024*1024)

	stdout, err := os.Create(os.Getenv("OUTPUT_PATH"))
	checkError(err)

	defer stdout.Close()

	writer := bufio.NewWriterSize(stdout, 1024*1024)

	nTemp, err := strconv.ParseInt(readLine(reader), 10, 64)
	checkError(err)
	n := int32(nTemp)

	var unsorted []string

	for i := 0; i < int(n); i++ {
		unsortedItem := readLine(reader)
		unsorted = append(unsorted, unsortedItem)
	}

	result := bigSorting(unsorted)

	for i, resultItem := range result {
		fmt.Fprintf(writer, "%s", resultItem)

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
