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

// Complete the flatlandSpaceStations function below.
func flatlandSpaceStations(n int32, c []int32) int32 {

	maxOverallDistance := int32(-1)
	for city := int32(0); city < n; city++ {
		lowestDistance := int32(-1)
		for _, stationCity := range c {
			distance := int32(math.Abs(float64(stationCity - city)))
			if lowestDistance == -1 || distance < lowestDistance {
				lowestDistance = distance
			}
		}

		if maxOverallDistance == -1 || maxOverallDistance < lowestDistance {
			maxOverallDistance = lowestDistance
		}
	}

	return maxOverallDistance
}

func main() {
	reader := bufio.NewReaderSize(os.Stdin, 1024*1024)

	stdout, err := os.Create(os.Getenv("OUTPUT_PATH"))
	checkError(err)

	defer stdout.Close()

	writer := bufio.NewWriterSize(stdout, 1024*1024)

	nm := strings.Split(readLine(reader), " ")

	nTemp, err := strconv.ParseInt(nm[0], 10, 64)
	checkError(err)
	n := int32(nTemp)

	mTemp, err := strconv.ParseInt(nm[1], 10, 64)
	checkError(err)
	m := int32(mTemp)

	cTemp := strings.Split(readLine(reader), " ")

	var c []int32

	for i := 0; i < int(m); i++ {
		cItemTemp, err := strconv.ParseInt(cTemp[i], 10, 64)
		checkError(err)
		cItem := int32(cItemTemp)
		c = append(c, cItem)
	}

	result := flatlandSpaceStations(n, c)

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
