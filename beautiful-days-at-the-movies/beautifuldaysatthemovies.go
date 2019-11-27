package main

import (
	"bufio"
	"fmt"
	"io"
	"math"
	"os"
	"strconv"
	"strings"
)

// Complete the beautifulDays function below.
func beautifulDays(i int32, j int32, k int32) int32 {

	var beautifulDays int32
	for counter := i; counter <= j; counter++ {
		absDiff := calculateAbsDiffAgainstReverseNumber(counter)
		if absDiff%k == 0 {
			beautifulDays++
		}
	}
	return beautifulDays
}

func calculateAbsDiffAgainstReverseNumber(number int32) int32 {
	reversedNumber := reverseNumber(number)
	return int32(math.Abs(float64(number - reversedNumber)))
}

func reverseNumber(number int32) int32 {
	stringNumber := strconv.Itoa(int(number))
	runes := []rune(stringNumber)

	for i, j := 0, len(runes)-1; i < len(runes)/2; i, j = i+1, j-1 {
		runes[i], runes[j] = runes[j], runes[i]
	}

	reversedNumber, error := strconv.Atoi(string(runes))
	if error != nil {
		panic(error)
	}

	return int32(reversedNumber)
}

func main() {
	reader := bufio.NewReaderSize(os.Stdin, 1024*1024)

	stdout, err := os.Create(os.Getenv("OUTPUT_PATH"))
	checkError(err)

	defer stdout.Close()

	writer := bufio.NewWriterSize(stdout, 1024*1024)

	ijk := strings.Split(readLine(reader), " ")

	iTemp, err := strconv.ParseInt(ijk[0], 10, 64)
	checkError(err)
	i := int32(iTemp)

	jTemp, err := strconv.ParseInt(ijk[1], 10, 64)
	checkError(err)
	j := int32(jTemp)

	kTemp, err := strconv.ParseInt(ijk[2], 10, 64)
	checkError(err)
	k := int32(kTemp)

	result := beautifulDays(i, j, k)

	fmt.Println(result)

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
