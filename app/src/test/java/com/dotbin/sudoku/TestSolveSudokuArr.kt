package com.dotbin.sudoku

object TestSolveSudokuArr {
    val puzzleSudoku = arrayOf(
        arrayOf(
            intArrayOf(0, 0, 1, 8, 0, 0, 0, 4, 0),
            intArrayOf(0, 0, 0, 0, 6, 3, 0, 0, 0),
            intArrayOf(0, 5, 0, 4, 0, 0, 0, 0, 6),
            intArrayOf(0, 8, 0, 0, 2, 9, 0, 0, 5),
            intArrayOf(2, 0, 0, 0, 0, 0, 9, 0, 0),
            intArrayOf(9, 0, 0, 0, 0, 0, 1, 0, 0),
            intArrayOf(0, 0, 3, 7, 0, 0, 0, 5, 0),
            intArrayOf(0, 0, 0, 0, 8, 2, 0, 0, 0),
            intArrayOf(0, 4, 0, 6, 0, 0, 0, 0, 7)
        ),
        arrayOf(
            intArrayOf(0, 0, 8, 0, 1, 3, 5, 0, 0),
            intArrayOf(3, 1, 6, 2, 5, 9, 7, 8, 4),
            intArrayOf(5, 0, 0, 8, 0, 0, 1, 0, 3),
            intArrayOf(7, 8, 0, 0, 0, 0, 3, 1, 0),
            intArrayOf(0, 0, 4, 0, 0, 1, 8, 0, 2),
            intArrayOf(1, 0, 5, 0, 0, 8, 0, 0, 7),
            intArrayOf(0, 0, 3, 1, 8, 0, 0, 7, 0),
            intArrayOf(8, 0, 0, 3, 0, 0, 6, 0, 1),
            intArrayOf(4, 0, 1, 0, 9, 6, 2, 3, 8)
        ),
        arrayOf(
            intArrayOf(0, 0, 1, 3, 0, 0, 0, 0, 7),
            intArrayOf(0, 0, 0, 4, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 2),
            intArrayOf(5, 0, 0, 0, 0, 0, 0, 1, 0),
            intArrayOf(2, 0, 0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 1, 9, 0, 4, 0, 0),
            intArrayOf(0, 0, 0, 0, 5, 0, 0, 0, 0),
            intArrayOf(0, 0, 9, 0, 0, 0, 3, 0, 0),
            intArrayOf(0, 0, 7, 0, 2, 8, 0, 0, 0)
        ),
        arrayOf(
            intArrayOf(0, 0, 0, 3, 5, 0, 0, 0, 0),
            intArrayOf(0, 8, 0, 0, 0, 0, 6, 0, 9),
            intArrayOf(0, 0, 6, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 7, 0, 0, 6, 9, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
            intArrayOf(1, 0, 0, 0, 0, 0, 0, 5, 0),
            intArrayOf(0, 0, 0, 0, 0, 7, 4, 0, 0),
            intArrayOf(0, 1, 0, 0, 8, 0, 0, 0, 0),
            intArrayOf(0, 0, 5, 0, 0, 0, 0, 3, 0)
        ),
        arrayOf(
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 4),
            intArrayOf(2, 6, 0, 1, 0, 0, 0, 0, 0),
            intArrayOf(0, 1, 0, 0, 5, 9, 0, 3, 0),
            intArrayOf(6, 0, 7, 2, 0, 0, 4, 9, 0),
            intArrayOf(0, 0, 0, 0, 7, 0, 1, 6, 8),
            intArrayOf(0, 8, 4, 0, 0, 5, 3, 0, 0),
            intArrayOf(3, 9, 1, 8, 6, 2, 5, 0, 7),
            intArrayOf(0, 4, 6, 5, 1, 7, 9, 0, 3),
            intArrayOf(7, 2, 5, 9, 0, 4, 0, 0, 0)
        )
        )
    val expectSudoku = arrayOf(
        arrayOf(
            intArrayOf(6, 9, 1, 8, 5, 7, 2, 4, 3),
            intArrayOf(4, 7, 8, 2, 6, 3, 5, 9, 1),
            intArrayOf(3, 5, 2, 4, 9, 1, 7, 8, 6),
            intArrayOf(7, 8, 6, 1, 2, 9, 4, 3, 5),
            intArrayOf(2, 1, 5, 3, 4, 6, 9, 7, 8),
            intArrayOf(9, 3, 4, 5, 7, 8, 1, 6, 2),
            intArrayOf(8, 2, 3, 7, 1, 4, 6, 5, 9),
            intArrayOf(5, 6, 7, 9, 8, 2, 3, 1, 4),
            intArrayOf(1, 4, 9, 6, 3, 5, 8, 2, 7)
        ),
        arrayOf(
            intArrayOf(2, 4, 8, 7, 1, 3, 5, 6, 9),
            intArrayOf(3, 1, 6, 2, 5, 9, 7, 8, 4),
            intArrayOf(5, 9, 7, 8, 6, 4, 1, 2, 3),
            intArrayOf(7, 8, 9, 4, 2, 5, 3, 1, 6),
            intArrayOf(6, 3, 4, 9, 7, 1, 8, 5, 2),
            intArrayOf(1, 2, 5, 6, 3, 8, 9, 4, 7),
            intArrayOf(9, 6, 3, 1, 8, 2, 4, 7, 5),
            intArrayOf(8, 5, 2, 3, 4, 7, 6, 9, 1),
            intArrayOf(4, 7, 1, 5, 9, 6, 2, 3, 8)
        ),
        arrayOf(
            intArrayOf(4, 8, 1, 3, 6, 2, 5, 9, 7),
            intArrayOf(9, 6, 2, 4, 7, 5, 8, 3, 1),
            intArrayOf(7, 3, 5, 8, 1, 9, 6, 4, 2),
            intArrayOf(5, 9, 6, 2, 8, 4, 7, 1, 3),
            intArrayOf(2, 1, 4, 5, 3, 7, 9, 8, 6),
            intArrayOf(8, 7, 3, 1, 9, 6, 4, 2, 5),
            intArrayOf(1, 4, 8, 6, 5, 3, 2, 7, 9),
            intArrayOf(6, 2, 9, 7, 4, 1, 3, 5, 8),
            intArrayOf(3, 5, 7, 9, 2, 8, 1, 6, 4)
        ),
        arrayOf(
            intArrayOf(7, 9, 1, 3, 5, 6, 8, 2, 4),
            intArrayOf(5, 8, 3, 2, 4, 1, 6, 7, 9),
            intArrayOf(4, 2, 6, 9, 7, 8, 5, 1, 3),
            intArrayOf(2, 7, 8, 5, 6, 9, 3, 4, 1),
            intArrayOf(6, 5, 4, 7, 1, 3, 2, 9, 8),
            intArrayOf(1, 3, 9, 8, 2, 4, 7, 5, 6),
            intArrayOf(9, 6, 2, 1, 3, 7, 4, 8, 5),
            intArrayOf(3, 1, 7, 4, 8, 5, 9, 6, 2),
            intArrayOf(8, 4, 5, 6, 9, 2, 1, 3, 7)
        ),
        arrayOf(
            intArrayOf(5, 7, 9, 3, 2, 6, 8, 1, 4),
            intArrayOf(2, 6, 3, 1, 4, 8, 7, 5, 9),
            intArrayOf(4, 1, 8, 7, 5, 9, 2, 3, 6),
            intArrayOf(6, 3, 7, 2, 8, 1, 4, 9, 5),
            intArrayOf(9, 5, 2, 4, 7, 3, 1, 6, 8),
            intArrayOf(1, 8, 4, 6, 9, 5, 3, 7, 2),
            intArrayOf(3, 9, 1, 8, 6, 2, 5, 4, 7),
            intArrayOf(8, 4, 6, 5, 1, 7, 9, 2, 3),
            intArrayOf(7, 2, 5, 9, 3, 4, 6, 8, 1)
        )
        )
}