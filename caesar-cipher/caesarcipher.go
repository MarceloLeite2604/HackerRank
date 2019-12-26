package main

import (
	"bufio"
	"fmt"
	"io"
	"os"
	"strconv"
	"strings"
	"unicode"
)

var alphabet = []rune("abcdefghijklmnopqrstuvwxyz")

// Complete the caesarCipher function below.
func caesarCipher(s string, k int32) string {

	runes := make([]rune, len(s))
	var value rune

	var checkedCharacter rune

	for index, character := range []rune(s) {

		uppercase := unicode.IsUpper(character)

		if uppercase {
			checkedCharacter = unicode.ToLower(character)
		} else {
			checkedCharacter = character
		}

		characterIndex := indexOf(alphabet, checkedCharacter)
		if characterIndex != -1 {
			replacementIndex := (characterIndex + int(k)) % len(alphabet)

			value = alphabet[replacementIndex]
			if uppercase {
				value = unicode.ToUpper(value)
			}
		} else {
			value = character
		}
		runes[index] = value
	}

	return string(runes)
}

func indexOf(runes []rune, r rune) int {
	for index, value := range runes {
		if value == r {
			return index
		}
	}

	return -1
}

func main() {
	reader := bufio.NewReaderSize(os.Stdin, 1024*1024)

	stdout, err := os.Create(os.Getenv("OUTPUT_PATH"))
	checkError(err)

	defer stdout.Close()

	writer := bufio.NewWriterSize(stdout, 1024*1024)

	nTemp, err := strconv.ParseInt(readLine(reader), 10, 64)
	checkError(err)
	_ = int32(nTemp)

	s := readLine(reader)

	kTemp, err := strconv.ParseInt(readLine(reader), 10, 64)
	checkError(err)
	k := int32(kTemp)

	result := caesarCipher(s, k)

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
