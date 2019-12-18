package com.dotbin.sudoku.algorithm

object SudokuConstValue {
    val templateSudokus = arrayOf(
        arrayOf(
            intArrayOf(1, 3, 8, 2, 4, 6, 5, 9, 7),
            intArrayOf(2, 6, 7, 1, 5, 9, 3, 4, 8),
            intArrayOf(4, 5, 9, 3, 7, 8, 1, 6, 2),
            intArrayOf(3, 1, 4, 8, 9, 2, 6, 7, 5),
            intArrayOf(5, 9, 6, 4, 1, 7, 2, 8, 3),
            intArrayOf(7, 8, 2, 5, 6, 3, 4, 1, 9),
            intArrayOf(6, 2, 5, 7, 8, 1, 9, 3, 4),
            intArrayOf(8, 4, 1, 9, 3, 5, 7, 2, 6),
            intArrayOf(9, 7, 3, 6, 2, 4, 8, 5, 1)
        ),
        arrayOf(
            intArrayOf(3, 1, 2, 4, 6, 7, 5, 8, 9),
            intArrayOf(4, 7, 5, 1, 8, 9, 6, 3, 2),
            intArrayOf(6, 8, 9, 3, 5, 2, 4, 1, 7),
            intArrayOf(1, 2, 3, 5, 4, 6, 7, 9, 8),
            intArrayOf(5, 4, 7, 2, 9, 8, 3, 6, 1),
            intArrayOf(9, 6, 8, 7, 1, 3, 2, 4, 5),
            intArrayOf(2, 5, 1, 8, 3, 4, 9, 7, 6),
            intArrayOf(8, 9, 4, 6, 7, 5, 1, 2, 3),
            intArrayOf(7, 3, 6, 9, 2, 1, 8, 5, 4)
        ),
        arrayOf(
            intArrayOf(2, 1, 5, 3, 6, 7, 4, 8, 9),
            intArrayOf(3, 4, 6, 5, 9, 8, 2, 7, 1),
            intArrayOf(7, 8, 9, 1, 2, 4, 3, 5, 6),
            intArrayOf(5, 3, 1, 7, 4, 6, 8, 9, 2),
            intArrayOf(4, 6, 2, 8, 1, 9, 5, 3, 7),
            intArrayOf(9, 7, 8, 2, 3, 5, 1, 6, 4),
            intArrayOf(6, 2, 3, 9, 8, 1, 7, 4, 5),
            intArrayOf(8, 9, 7, 4, 5, 2, 6, 1, 3),
            intArrayOf(1, 5, 4, 6, 7, 3, 9, 2, 8)
        ),
        arrayOf(
            intArrayOf(2, 3, 4, 5, 1, 7, 6, 8, 9),
            intArrayOf(1, 5, 6, 2, 9, 8, 3, 4, 7),
            intArrayOf(7, 8, 9, 4, 6, 3, 1, 2, 5),
            intArrayOf(3, 1, 2, 6, 4, 5, 7, 9, 8),
            intArrayOf(4, 6, 7, 3, 8, 9, 5, 1, 2),
            intArrayOf(5, 9, 8, 1, 7, 2, 4, 6, 3),
            intArrayOf(6, 7, 3, 9, 2, 1, 8, 5, 4),
            intArrayOf(9, 4, 5, 8, 3, 6, 2, 7, 1),
            intArrayOf(8, 2, 1, 7, 5, 4, 9, 3, 6)
        ),
        arrayOf(
            intArrayOf(1, 2, 3, 5, 4, 7, 6, 8, 9),
            intArrayOf(4, 5, 8, 2, 6, 9, 1, 7, 3),
            intArrayOf(6, 7, 9, 8, 1, 3, 4, 2, 5),
            intArrayOf(2, 3, 4, 9, 7, 5, 8, 1, 6),
            intArrayOf(8, 9, 6, 1, 2, 4, 3, 5, 7),
            intArrayOf(5, 1, 7, 3, 8, 6, 2, 9, 4),
            intArrayOf(7, 6, 2, 4, 5, 1, 9, 3, 8),
            intArrayOf(3, 4, 1, 7, 9, 8, 5, 6, 2),
            intArrayOf(9, 8, 5, 6, 3, 2, 7, 4, 1)
        ),
        arrayOf(
            intArrayOf(1, 2, 3, 4, 6, 7, 5, 8, 9),
            intArrayOf(5, 4, 6, 1, 8, 9, 2, 7, 3),
            intArrayOf(7, 9, 8, 2, 3, 5, 1, 6, 4),
            intArrayOf(2, 8, 1, 3, 4, 6, 7, 9, 5),
            intArrayOf(9, 7, 4, 5, 1, 2, 6, 3, 8),
            intArrayOf(3, 6, 5, 7, 9, 8, 4, 1, 2),
            intArrayOf(6, 3, 7, 8, 5, 4, 9, 2, 1),
            intArrayOf(4, 1, 2, 9, 7, 3, 8, 5, 6),
            intArrayOf(8, 5, 9, 6, 2, 1, 3, 4, 7)
        ),
        arrayOf(
            intArrayOf(1, 2, 3, 4, 6, 9, 8, 7, 5),
            intArrayOf(5, 4, 8, 1, 2, 7, 9, 3, 6),
            intArrayOf(6, 7, 9, 3, 8, 5, 2, 4, 1),
            intArrayOf(3, 5, 4, 9, 1, 6, 7, 8, 2),
            intArrayOf(2, 8, 1, 7, 3, 4, 5, 6, 9),
            intArrayOf(7, 9, 6, 8, 5, 2, 4, 1, 3),
            intArrayOf(9, 3, 2, 6, 4, 8, 1, 5, 7),
            intArrayOf(8, 1, 7, 5, 9, 3, 6, 2, 4),
            intArrayOf(4, 6, 5, 2, 7, 1, 3, 9, 8)
        ),
        arrayOf(
            intArrayOf(1, 5, 2, 4, 7, 8, 3, 6, 9),
            intArrayOf(3, 4, 6, 1, 9, 2, 8, 5, 7),
            intArrayOf(8, 7, 9, 5, 3, 6, 1, 4, 2),
            intArrayOf(4, 1, 5, 3, 2, 7, 6, 9, 8),
            intArrayOf(2, 3, 8, 6, 4, 9, 5, 7, 1),
            intArrayOf(6, 9, 7, 8, 5, 1, 4, 2, 3),
            intArrayOf(7, 6, 1, 2, 8, 5, 9, 3, 4),
            intArrayOf(5, 2, 3, 9, 1, 4, 7, 8, 6),
            intArrayOf(9, 8, 4, 7, 6, 3, 2, 1, 5)
        ),
        arrayOf(
            intArrayOf(1, 3, 6, 4, 5, 7, 8, 9, 2),
            intArrayOf(2, 8, 4, 1, 3, 9, 6, 5, 7),
            intArrayOf(5, 7, 9, 2, 6, 8, 1, 3, 4),
            intArrayOf(3, 1, 2, 8, 9, 6, 4, 7, 5),
            intArrayOf(4, 6, 8, 5, 7, 2, 3, 1, 9),
            intArrayOf(7, 9, 5, 3, 4, 1, 2, 6, 8),
            intArrayOf(6, 4, 1, 7, 2, 5, 9, 8, 3),
            intArrayOf(8, 2, 7, 9, 1, 3, 5, 4, 6),
            intArrayOf(9, 5, 3, 6, 8, 4, 7, 2, 1)
        )
    )
}

enum class SudokuDegree {
    LOW,
    MIDDLE,
    HIGH,
}