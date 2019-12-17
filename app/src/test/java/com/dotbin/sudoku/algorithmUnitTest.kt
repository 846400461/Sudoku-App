package com.dotbin.sudoku

import com.dotbin.sudoku.algorithm.SudokuSolver
import org.junit.Assert
import org.junit.Test

class algorithmUnitTest {

    @Test
    fun testSolveSudoku() {
        for(i in TestSolveSudokuArr.puzzleSudoku.indices){
            SudokuSolver.getSolution(TestSolveSudokuArr.puzzleSudoku[i])
        }
        Assert.assertEquals(TestSolveSudokuArr.expectSudoku,TestSolveSudokuArr.puzzleSudoku)
    }
}