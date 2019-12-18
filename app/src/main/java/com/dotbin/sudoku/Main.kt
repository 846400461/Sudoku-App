package com.dotbin.sudoku

import com.dotbin.sudoku.algorithm.SudokuDegree
import com.dotbin.sudoku.algorithm.SudokuKeyBuilder
import com.dotbin.sudoku.algorithm.SudokuPuzzleBuilder
import com.dotbin.sudoku.algorithm.SudokuSolver

fun main() {
    val testArray = Array(9) { Array(9) { IntArray(9) } }
    for(i in testArray.indices) {
        do {
            SudokuKeyBuilder.obtainSudokuKey(testArray[i], true)
        }while (!SudokuPuzzleBuilder.getSudokuPuzzle(testArray[i],SudokuDegree.HIGH))
    }
    testArray.forEach {top->
        top.forEach {mid->
            mid.forEach {
                print("$it ")
            }
            println()
        }
        println()
        println()
    }
}



