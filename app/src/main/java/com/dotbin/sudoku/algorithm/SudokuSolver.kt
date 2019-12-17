package com.dotbin.sudoku.algorithm

import java.lang.IllegalArgumentException

object SudokuSolver : Throwable() {
    private val solvedLocationStack = mutableListOf<SudokuLocation>()
    private val solvingLocation = mutableListOf<SudokuLocation>()
    fun getSolution(sudokuArray: Array<IntArray>): Boolean {
        if (sudokuArray.size != 9)
            throw IllegalArgumentException("Illegal sudoku array size")
        sudokuArray.forEach {
            if (it.size != 9)
                throw IllegalArgumentException("Illegal sudoku array size")
        }
        solvedLocationStack.clear()
        for (x in sudokuArray.indices)
            for (y in sudokuArray.indices) {
                if (sudokuArray[x][y] == 0)
                    solvingLocation.add(SudokuLocation(x, y, sudokuArray[x][y]))
            }
        return realSolution(sudokuArray)
    }

    private fun realSolution(sudokuArray: Array<IntArray>): Boolean {
        if (solvingLocation.size == 0) return true
        val maxLocationSize = solvingLocation.size
        do {
            var location = solvingLocation.first()
            val targetValue =
                findTarget(sudokuArray, location.x, location.y, location.lastValue)
            if (targetValue != 0) {
                sudokuArray[location.x][location.y] = targetValue
                location.lastValue = sudokuArray[location.x][location.y]
                solvedLocationStack.add(location)
                solvingLocation.removeAt(0)
                if (solvingLocation.size > 0)
                    solvingLocation.first().lastValue = 0
            } else {
                if (solvedLocationStack.lastIndex == -1)
                    return false
                location = solvedLocationStack.removeAt(solvedLocationStack.lastIndex)
                solvingLocation.add(0, location)
                sudokuArray[location.x][location.y] = 0
            }
        } while (solvedLocationStack.size != maxLocationSize)
        return true
    }

    private fun findTarget(sudokuArray: Array<IntArray>, lx: Int, ly: Int, minValue: Int): Int {
        var targetValue = 0
        val baseCol = lx - lx % 3
        val baseRow = ly - ly % 3
        loop@ for (ta in (minValue + 1)..9) {
            for (i in 0 until 9)
                if (sudokuArray[lx][i] == ta || sudokuArray[i][ly] == ta) continue@loop
            for (i in 0 until 3)
                for (j in 0 until 3) {
                    if (sudokuArray[baseCol + i][baseRow + j] == ta) continue@loop
                    else if (i == 2 && j == 2) {
                        targetValue = ta
                        break@loop
                    }
                }
        }
        return targetValue
    }
}

data class SudokuLocation(
    var x: Int,
    var y: Int,
    var lastValue: Int
)