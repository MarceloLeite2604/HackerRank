package main

import (
	"bufio"
	"fmt"
	"io"
	"os"
	"regexp"
	"strconv"
	"strings"
)

const (
	minimumPasswordLength      = 6
	minimumDigits              = 1
	minimumLowercaseCharacters = 1
	minimumUppercaseCharacters = 1
	minimumSpecialCharacters   = 1
)

var (
	regexDigit              = regexp.MustCompile("[0-9]")
	regexLowercaseCharacter = regexp.MustCompile("[a-z]")
	regexUppercaseCharacter = regexp.MustCompile("[A-Z]")
	regexSpecialCharacter   = regexp.MustCompile(`[\!\@\#\$\%\^\&\*\(\)\-\+]`)
)

func checkRemainingLength(password string) int {
	result := 0
	if len(password) < minimumPasswordLength {
		result = minimumPasswordLength - len(password)
	}

	return result
}

func checkRemainingDigits(password string) int {
	return checkRemainingCountsForRegex(password, regexDigit, minimumDigits)
}

func checkRemainingLowercaseCharacters(password string) int {
	return checkRemainingCountsForRegex(password, regexLowercaseCharacter, minimumLowercaseCharacters)
}

func checkRemainingUppercaseCharacters(password string) int {
	return checkRemainingCountsForRegex(password, regexUppercaseCharacter, minimumUppercaseCharacters)
}

func checkRemainingSpecialCharacters(password string) int {
	return checkRemainingCountsForRegex(password, regexSpecialCharacter, minimumSpecialCharacters)
}

func checkRemainingCountsForRegex(text string, regexp *regexp.Regexp, minimumCounts int) int {
	regexResults := regexp.FindAllString(text, -1)
	totalCounts := len(regexResults)

	result := 0
	if totalCounts < minimumCounts {
		result = minimumCounts - totalCounts
	}
	return result
}

func checkRemainingCharacters(password string) int {
	result := checkRemainingDigits(password)
	result += checkRemainingUppercaseCharacters(password)
	result += checkRemainingLowercaseCharacters(password)
	result += checkRemainingSpecialCharacters(password)

	return result
}

// Complete the minimumNumber function below.
func minimumNumber(n int32, password string) int32 {
	remainingCaracters := checkRemainingCharacters(password)

	remainingSize := checkRemainingLength(password)

	var result int32

	if remainingCaracters > remainingSize {
		result = int32(remainingCaracters)
	} else {
		result = int32(remainingSize)
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

	password := readLine(reader)

	answer := minimumNumber(n, password)

	fmt.Fprintf(writer, "%d\n", answer)

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
