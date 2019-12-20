package main

import (
	"bufio"
	"fmt"
	"io"
	"os"
	"strconv"
	"strings"
)

// Complete the surfaceArea function below.
func surfaceArea(A [][]int32) int32 {

	totalSurface := int32(0)
	for row := 0; row < len(A); row++ {
		for column := 0; column < len(A[row]); column++ {
			height := A[row][column]
			if height != 0 {
				surfaceArea := int32(2)
				surfaceArea += calculateSurfaceAreaAgainstOpposingStack(A, row, column, row-1, column)
				surfaceArea += calculateSurfaceAreaAgainstOpposingStack(A, row, column, row+1, column)
				surfaceArea += calculateSurfaceAreaAgainstOpposingStack(A, row, column, row, column-1)
				surfaceArea += calculateSurfaceAreaAgainstOpposingStack(A, row, column, row, column+1)
				totalSurface += surfaceArea
			}
		}
	}
	return totalSurface
}

func calculateSurfaceAreaAgainstOpposingStack(A [][]int32, row int, column int, opposingRow int, opposingColumn int) int32 {

	surfaceArea := A[row][column]

	if opposingRow < 0 || opposingRow >= len(A) || opposingColumn < 0 || opposingColumn >= len(A[row]) {
		return surfaceArea
	}

	opposingSurfaceArea := A[opposingRow][opposingColumn]

	if opposingSurfaceArea > surfaceArea {
		surfaceArea = 0
	} else {
		surfaceArea -= opposingSurfaceArea
	}

	return surfaceArea
}

func main() {
	reader := bufio.NewReaderSize(os.Stdin, 1024*1024)

	stdout, err := os.Create(os.Getenv("OUTPUT_PATH"))
	checkError(err)

	defer stdout.Close()

	writer := bufio.NewWriterSize(stdout, 1024*1024)

	HW := strings.Split(readLine(reader), " ")

	HTemp, err := strconv.ParseInt(HW[0], 10, 64)
	checkError(err)
	H := int32(HTemp)

	WTemp, err := strconv.ParseInt(HW[1], 10, 64)
	checkError(err)
	W := int32(WTemp)

	var A [][]int32
	for i := 0; i < int(H); i++ {
		ARowTemp := strings.Split(readLine(reader), " ")

		var ARow []int32
		for _, ARowItem := range ARowTemp {
			AItemTemp, err := strconv.ParseInt(ARowItem, 10, 64)
			checkError(err)
			AItem := int32(AItemTemp)
			ARow = append(ARow, AItem)
		}

		if len(ARow) != int(int(W)) {
			panic("Bad input")
		}

		A = append(A, ARow)
	}

	result := surfaceArea(A)

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
