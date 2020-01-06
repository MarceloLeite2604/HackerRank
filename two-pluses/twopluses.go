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

const (
	good = 'G'
	bad  = 'B'
)

type plus struct {
	row    int
	column int
	size   int
}

func (p plus) area() int32 {
	return int32((p.size-1)*4 + 1)
}

func (p plus) conflictsWith(other plus) bool {
	rowDiff := int(math.Abs(float64(p.row - other.row)))
	columnDiff := int(math.Abs(float64(p.column - other.column)))

	largestSize := int(math.Max(float64(p.size), float64(other.size)))
	smallestSize := int(math.Min(float64(p.size), float64(other.size)))

	result := rowDiff < largestSize && columnDiff < smallestSize

	if !result {
		result = columnDiff < largestSize && rowDiff < smallestSize
	}

	if !result {
		result = rowDiff == 0 && columnDiff < largestSize+smallestSize-1
	}

	if !result {
		result = columnDiff == 0 && rowDiff < largestSize+smallestSize-1
	}

	return result
}

func (p plus) isValid(grid []string) bool {

	if grid[p.row][p.column] == bad {
		return false
	}

	rowContent := grid[p.row]

	for radius := 0; radius < p.size; radius++ {

		if p.row-radius < 0 || grid[p.row-radius][p.column] == bad {
			return false
		}

		if p.row+radius >= len(grid) || grid[p.row+radius][p.column] == bad {
			return false
		}

		if p.column-radius < 0 || rowContent[p.column-radius] == bad {
			return false
		}

		if p.column+radius >= len(rowContent) || rowContent[p.column+radius] == bad {
			return false
		}
	}

	return true
}

// Complete the twoPluses function below.
func twoPluses(grid []string) int32 {

	largestMultipliedArea := int32(-1)
	for firstPlusRow := 0; firstPlusRow < len(grid); firstPlusRow++ {
		for firstPlusColumn := 0; firstPlusColumn < len(grid[firstPlusRow]); firstPlusColumn++ {

			firstPlus := plus{
				row:    firstPlusRow,
				column: firstPlusColumn,
				size:   1,
			}

			for firstPlus.isValid(grid) {
				largestMultipliedArea = analizeSecondPluses(grid, firstPlus, largestMultipliedArea)
				firstPlus.size++
			}
		}
	}
	return largestMultipliedArea
}

func analizeSecondPluses(grid []string, firstPlus plus, largestMultipliedArea int32) int32 {
	result := largestMultipliedArea
	for secondPlusRow := firstPlus.row; secondPlusRow < len(grid); secondPlusRow++ {
		for secondPlusColumn := 0; secondPlusColumn < len(grid[secondPlusRow]); secondPlusColumn++ {

			if secondPlusRow != firstPlus.row || secondPlusColumn > firstPlus.column {

				secondPlus := plus{
					row:    secondPlusRow,
					column: secondPlusColumn,
					size:   1,
				}

				for secondPlus.isValid(grid) && !secondPlus.conflictsWith(firstPlus) {
					result = checkMultipliedAreaLargerThan(firstPlus, secondPlus, result)
					secondPlus.size++
				}
			}
		}
	}
	return result
}

func checkMultipliedAreaLargerThan(first, second plus, largestMultipliedArea int32) int32 {

	result := largestMultipliedArea

	multipliedArea := first.area() * second.area()

	if multipliedArea > largestMultipliedArea {
		result = multipliedArea
	}

	return result
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
	_ = int32(mTemp)

	var grid []string

	for i := 0; i < int(n); i++ {
		gridItem := readLine(reader)
		grid = append(grid, gridItem)
	}

	result := twoPluses(grid)

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
