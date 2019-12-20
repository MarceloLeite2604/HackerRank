package main

import (
	"bufio"
	"fmt"
	"io"
	"os"
	"strconv"
	"strings"
)

// Complete the beautifulTriplets function below.
func beautifulTriplets(d int32, arr []int32) int32 {

	result := int32(0)
	for firstIndex := 0; firstIndex < len(arr)-2; firstIndex++ {
		firstValue := arr[firstIndex]
		for secondIndex := firstIndex + 1; secondIndex < len(arr)-1; secondIndex++ {
			secondValue := arr[secondIndex]
			if secondValue-firstValue == d {
				for thirdIndex := secondIndex + 1; thirdIndex < len(arr); thirdIndex++ {
					thirdValue := arr[thirdIndex]

					if thirdValue-secondValue == d {
						result++
					}
				}
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

	nd := strings.Split(readLine(reader), " ")

	nTemp, err := strconv.ParseInt(nd[0], 10, 64)
	checkError(err)
	n := int32(nTemp)

	dTemp, err := strconv.ParseInt(nd[1], 10, 64)
	checkError(err)
	d := int32(dTemp)

	arrTemp := strings.Split(readLine(reader), " ")

	var arr []int32

	for i := 0; i < int(n); i++ {
		arrItemTemp, err := strconv.ParseInt(arrTemp[i], 10, 64)
		checkError(err)
		arrItem := int32(arrItemTemp)
		arr = append(arr, arrItem)
	}

	result := beautifulTriplets(d, arr)

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
