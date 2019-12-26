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
	empty       = rune('.')
	plantedBomb = rune('P')
	bomb        = rune('O')
)

// Complete the bomberMan function below.
func bomberMan(n int32, grid []string) []string {

	var result []string

	switch {
	case n == 1:
		result = grid
	case n%2 == 0:
		result = make([]string, len(grid))
		for index, gridRow := range grid {
			format := "%0" + strconv.Itoa(len(gridRow)) + "v"
			fmt.Printf("%v", format)
			resultRow := fmt.Sprintf(format, "")
			resultRow = strings.ReplaceAll(resultRow, "0", string(bomb))
			result[index] = resultRow
		}
	default:

		runesGrid := createRunes(grid)
		for time := 2; time < 6; time++ {
			modifiedCells := make([][]int, 0)
			for row := 0; row < len(runesGrid); row++ {
				for column := 0; column < len(runesGrid[row]); column++ {
					cell := &runesGrid[row][column]
					if time%2 == 0 {
						if *cell == empty {
							addCellToModify(&modifiedCells, runesGrid, row, column)
						}
					} else {
						switch *cell {
						case bomb:
							addCellsToModify(&modifiedCells, runesGrid, row, column)
						case plantedBomb:
							runesGrid[row][column] = bomb
						}
					}
				}
			}

			if time%2 == 0 {
				plantBombs(runesGrid, modifiedCells)
			} else {
				cleanCells(runesGrid, modifiedCells)
			}

			if n%4 == 3 && time%4 == 3 {
				result = createBoard(runesGrid)
			} else if n%4 == 1 && time%4 == 1 {
				result = createBoard(runesGrid)
			}
		}
	}
	return result
}

func createRunes(grid []string) [][]rune {
	result := make([][]rune, len(grid))
	for row := 0; row < len(grid); row++ {
		result[row] = []rune(grid[row])
	}
	return result
}

func createBoard(runesGrid [][]rune) []string {
	result := make([]string, len(runesGrid))

	for row := 0; row < len(runesGrid); row++ {
		result[row] = string(runesGrid[row])
	}
	return result
}

func addCellsToModify(modifiedCells *[][]int, runesGrid [][]rune, row int, column int) {
	addCellToModify(modifiedCells, runesGrid, row-1, column)
	addCellToModify(modifiedCells, runesGrid, row+1, column)
	addCellToModify(modifiedCells, runesGrid, row, column-1)
	addCellToModify(modifiedCells, runesGrid, row, column+1)
	addCellToModify(modifiedCells, runesGrid, row, column)
}

func addCellToModify(modifiedCells *[][]int, runesGrid [][]rune, row int, column int) {
	if row >= 0 && row < len(runesGrid) && column >= 0 && column < len(runesGrid[row]) {
		coordinates := make([]int, 2)
		coordinates[0] = row
		coordinates[1] = column
		*modifiedCells = append(*modifiedCells, coordinates)
	}
}

func cleanCells(runesGrid [][]rune, cellsToClean [][]int) {
	for _, cellToClean := range cellsToClean {
		runesGrid[cellToClean[0]][cellToClean[1]] = empty
	}
}

func plantBombs(runesGrid [][]rune, cellsToPlantBomb [][]int) {
	for _, cellToPlantBomb := range cellsToPlantBomb {
		runesGrid[cellToPlantBomb[0]][cellToPlantBomb[1]] = plantedBomb
	}
}

func main() {
	reader := bufio.NewReaderSize(os.Stdin, 1024*1024)

	stdout, err := os.Create(os.Getenv("OUTPUT_PATH"))
	checkError(err)

	defer stdout.Close()

	writer := bufio.NewWriterSize(stdout, 1024*1024)

	rcn := strings.Split(readLine(reader), " ")

	rTemp, err := strconv.ParseInt(rcn[0], 10, 64)
	checkError(err)
	r := int32(rTemp)

	cTemp, err := strconv.ParseInt(rcn[1], 10, 64)
	checkError(err)
	_ = int32(cTemp)

	nTemp, err := strconv.ParseInt(rcn[2], 10, 64)
	checkError(err)
	n := int32(nTemp)

	var grid []string

	for i := 0; i < int(r); i++ {
		gridItem := readLine(reader)
		grid = append(grid, gridItem)
	}

	result := bomberMan(n, grid)

	for i, resultItem := range result {
		fmt.Fprintf(writer, "%s", resultItem)

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
