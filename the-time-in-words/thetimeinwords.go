package main

import (
	"bufio"
	"fmt"
	"io"
	"os"
	"strconv"
	"strings"
)

var (
	timeNumberWords = map[int]string{
		1: "one", 2: "two", 3: "three", 4: "four", 5: "five", 6: "six",
		7: "seven", 8: "eight", 9: "nine", 10: "ten", 11: "eleven", 12: "twelve",
		13: "thirteen", 14: "fourteen", 15: "quarter", 16: "sixteen", 17: "seventeen", 18: "eighteen",
		19: "nineteen", 20: "twenty", 30: "half", 40: "fourty", 45: "quarter", 50: "fifty"}
)

const (
	oClock      = "o' clock"
	wordMinute  = "minute"
	wordMinutes = "minutes"
	past        = "past"
	to          = "to"
)

func retrieveComposedTimeWordNumber(number int) string {
	var parts []string
	switch {
	case number < 20:
		parts = append(parts, timeNumberWords[number])
	default:
		digit := number % 10
		decimal := number - digit
		parts = append(parts, timeNumberWords[decimal])
		parts = append(parts, timeNumberWords[digit])
	}

	return strings.Join(parts, " ")
}

// Complete the timeInWords function below.
func timeInWords(h int32, m int32) string {
	hour := int(h)
	minute := int(m)

	var parts []string
	switch {
	case minute == 0:
		parts = append(parts, timeNumberWords[hour])
		parts = append(parts, oClock)
	case minute == 1:
		parts = append(parts, timeNumberWords[minute])
		parts = append(parts, wordMinute)
		parts = append(parts, past)
		parts = append(parts, timeNumberWords[hour])
	case (minute > 1 && minute < 15) || (minute > 15 && minute < 30):
		parts = append(parts, retrieveComposedTimeWordNumber(minute))
		parts = append(parts, wordMinutes)
		parts = append(parts, past)
		parts = append(parts, timeNumberWords[hour])
	case minute == 15 || minute == 30:
		parts = append(parts, timeNumberWords[minute])
		parts = append(parts, past)
		parts = append(parts, timeNumberWords[hour])
	case (minute > 30 && minute < 45) || (minute > 45 && minute < 59):
		remainingMinutes := 60 - minute
		parts = append(parts, retrieveComposedTimeWordNumber(remainingMinutes))
		parts = append(parts, wordMinutes)
		parts = append(parts, to)
		parts = append(parts, timeNumberWords[hour+1])
	case minute == 45:
		parts = append(parts, timeNumberWords[minute])
		parts = append(parts, to)
		parts = append(parts, timeNumberWords[hour+1])
	default:
		remainingMinutes := 60 - minute
		parts = append(parts, retrieveComposedTimeWordNumber(remainingMinutes))
		parts = append(parts, wordMinute)
		parts = append(parts, to)
		parts = append(parts, timeNumberWords[hour+1])
	}

	return strings.Join(parts, " ")
}

func main() {
	reader := bufio.NewReaderSize(os.Stdin, 1024*1024)

	stdout, err := os.Create(os.Getenv("OUTPUT_PATH"))
	checkError(err)

	defer stdout.Close()

	writer := bufio.NewWriterSize(stdout, 1024*1024)

	hTemp, err := strconv.ParseInt(readLine(reader), 10, 64)
	checkError(err)
	h := int32(hTemp)

	mTemp, err := strconv.ParseInt(readLine(reader), 10, 64)
	checkError(err)
	m := int32(mTemp)

	result := timeInWords(h, m)

	fmt.Fprintf(writer, "%s\n", result)

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
