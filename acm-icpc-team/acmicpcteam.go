package main

import (
	"bufio"
	"fmt"
	"io"
	"math/bits"
	"os"
	"strconv"
	"strings"
)

const (
	uint64BitsLength = 64
)

// Complete the acmTeam function below.
func acmTeam(topic []string) []int32 {

	var higherMathingTopics int32 = 0
	var totalTeams int32 = 0
	for firstAttendee := 0; firstAttendee < len(topic)-1; firstAttendee++ {
		firstAttendeeTopics := topic[firstAttendee]
		for secondAttendee := firstAttendee + 1; secondAttendee < len(topic); secondAttendee++ {
			secondAttendeeTopics := topic[secondAttendee]
			matchingTopics := checkMatchingTopics(firstAttendeeTopics, secondAttendeeTopics)
			if matchingTopics > higherMathingTopics {
				higherMathingTopics = matchingTopics
				totalTeams = 1
			} else if matchingTopics == higherMathingTopics {
				totalTeams++
			}
		}
	}

	result := make([]int32, 2)
	result[0] = higherMathingTopics
	result[1] = totalTeams
	return result
}

func checkMatchingTopics(firstAttendeeTopics string, secondAttendeeTopics string) int32 {
	index := 0
	var totalMatchingTopics int32 = 0
	for index < len(firstAttendeeTopics) {

		var size int
		if index+uint64BitsLength < len(secondAttendeeTopics) {
			size = uint64BitsLength
		} else {
			size = len(firstAttendeeTopics) - index
		}

		firstAttendeeTopicValue, err := strconv.ParseUint(firstAttendeeTopics[index:index+size], 2, size)
		checkError(err)

		secondAttendeeTopicValue, err := strconv.ParseUint(secondAttendeeTopics[index:index+size], 2, size)
		checkError(err)

		matchingTopics := firstAttendeeTopicValue | secondAttendeeTopicValue
		totalMatchingTopics += int32(bits.OnesCount64(matchingTopics))
		index += size
	}

	return totalMatchingTopics
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

	var topic []string

	for i := 0; i < int(n); i++ {
		topicItem := readLine(reader)
		topic = append(topic, topicItem)
	}

	result := acmTeam(topic)

	for i, resultItem := range result {
		fmt.Fprintf(writer, "%d", resultItem)

		if i != len(result)-1 {
			fmt.Fprintf(writer, "\n")
		}
	}

	fmt.Fprintf(writer, "\n")

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
