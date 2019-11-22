package main

import (
	"bufio"
	"fmt"
	"io"
	"os"
	"strconv"
	"strings"
)

// Complete the migratoryBirds function below.
func migratoryBirds(arr []int32) int32 {
	var birdCounts map[int32]int32 = make(map[int32]int32)

	for _, birdType := range arr {
		birdCount := birdCounts[birdType]
		birdCount++
		birdCounts[birdType] = birdCount
	}

	var highestOcurrenceBirdType, highestBirdCount int32
	for birdType, birdCount := range birdCounts {
		if highestOcurrenceBirdType == 0 {
			highestOcurrenceBirdType, highestBirdCount = birdType, birdCount
		} else {
			if birdCount > highestBirdCount {
				highestOcurrenceBirdType, highestBirdCount = birdType, birdCount
			} else if birdCount == highestBirdCount && birdType < highestOcurrenceBirdType {
				highestOcurrenceBirdType, highestBirdCount = birdType, birdCount
			}
		}
	}
	return highestOcurrenceBirdType
}

func main() {
	reader := bufio.NewReaderSize(os.Stdin, 16*1024*1024)

	stdout, err := os.Create(os.Getenv("OUTPUT_PATH"))
	checkError(err)

	defer stdout.Close()

	writer := bufio.NewWriterSize(stdout, 16*1024*1024)

	arrCount, err := strconv.ParseInt(strings.TrimSpace(readLine(reader)), 10, 64)
	checkError(err)

	arrTemp := strings.Split(strings.TrimSpace(readLine(reader)), " ")

	var arr []int32

	for i := 0; i < int(arrCount); i++ {
		arrItemTemp, err := strconv.ParseInt(arrTemp[i], 10, 64)
		checkError(err)
		arrItem := int32(arrItemTemp)
		arr = append(arr, arrItem)
	}

	result := migratoryBirds(arr)

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
