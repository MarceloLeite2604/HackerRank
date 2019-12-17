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
	emptySpace = rune('_')
	yes        = "YES"
	no         = "NO"
)

// Complete the happyLadybugs function below.
func happyLadybugs(b string) string {

	if isCompleted(b) {
		return yes
	}

	colourQuantity := make(map[rune]int)
	hasEmptySpace := false

	for _, value := range []rune(b) {
		if !hasEmptySpace && value == emptySpace {
			hasEmptySpace = true
		} else {
			if value != emptySpace {
				quantity := colourQuantity[value]
				quantity++
				colourQuantity[value] = quantity
			}
		}
	}

	hasEnoughColorQuantities := true

	colours := make([]rune, 0, len(colourQuantity))
	for colour := range colourQuantity {
		colours = append(colours, colour)
	}

	colourIndex := 0
	for hasEnoughColorQuantities && colourIndex < len(colours) {
		hasEnoughColorQuantities = colourQuantity[colours[colourIndex]] > 1
		colourIndex++
	}

	if hasEmptySpace && hasEnoughColorQuantities {
		return yes
	}

	return no
}

func isCompleted(b string) bool {
	leftColour := '\x00'
	rightColour := '\x00'
	completed := true
	index := 0
	for completed && index < len(b) {

		colour := rune(b[index])

		if colour != emptySpace {
			if index > 0 {
				leftColour = rune(b[index-1])
			} else {
				leftColour = '\x00'
			}

			if index < len(b)-1 {
				rightColour = rune(b[index+1])
			} else {
				rightColour = '\x00'
			}

			completed = (leftColour == colour || rightColour == colour)
		}
		index++
	}

	return completed
}

func main() {
	reader := bufio.NewReaderSize(os.Stdin, 1024*1024)

	stdout, err := os.Create(os.Getenv("OUTPUT_PATH"))
	checkError(err)

	defer stdout.Close()

	writer := bufio.NewWriterSize(stdout, 1024*1024)

	gTemp, err := strconv.ParseInt(readLine(reader), 10, 64)
	checkError(err)
	g := int32(gTemp)

	for gItr := 0; gItr < int(g); gItr++ {
		nTemp, err := strconv.ParseInt(readLine(reader), 10, 64)
		checkError(err)
		_ = int32(nTemp)

		b := readLine(reader)

		result := happyLadybugs(b)

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
