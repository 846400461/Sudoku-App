package com.dotbin.sudoku.algorithm

import java.lang.IllegalArgumentException

object SudokuPuzzleBuilder : Throwable() {
    fun getSudokuPuzzle(sudokuArray: Array<IntArray>, sudokuDegree: SudokuDegree): Boolean {
        if (sudokuArray.size != 9)
            throw IllegalArgumentException("Illegal sudoku array size")
        sudokuArray.forEach {
            if (it.size != 9)
                throw IllegalArgumentException("Illegal sudoku array size")
            it.forEach { num ->
                if (num == 0) throw IllegalArgumentException("Illegal sudoku array value")
            }
        }

        return when (sudokuDegree) {
            SudokuDegree.LOW -> getLowPuzzle(sudokuArray, (25..27).random())
            SudokuDegree.MIDDLE -> getLowPuzzle(sudokuArray, (35..40).random())
            SudokuDegree.HIGH -> getLowPuzzle(sudokuArray, (50..55).random())
        }

    }

    private fun isEmptyCell(sudokuArray: Array<IntArray>, lx: Int, ly: Int): Boolean {
        val tempValue = sudokuArray[lx][ly]
        val tempArray = sudokuArray.clone()
        for (i in 1..9) {
            if (i == tempValue) continue
            if (SudokuSolver.isLegalSudoKuArray(tempArray, lx, ly, i)) continue
            tempArray[lx][ly] = i
            if (SudokuSolver.getSolution(tempArray)) return false
            SudokuKeyBuilder.sudokuCopy(sudokuArray, tempArray)
        }
        return true
    }

    private fun getLowPuzzle(sudokuArray: Array<IntArray>, amount: Int): Boolean {
        val loSet = mutableSetOf<SudokuLocation>()
        val unLoSet = mutableSetOf<SudokuLocation>()
        while (loSet.size < amount && (loSet.size + unLoSet.size) < 70) {
            val lo = SudokuLocation((0..8).random(), (0..8).random(), 0)
            if (loSet.contains(lo) || unLoSet.contains(lo))
                continue
            if (isEmptyCell(sudokuArray, lo.x, lo.y)) {
                sudokuArray[lo.x][lo.y] = 0
                loSet.add(lo)
            } else
                unLoSet.add(lo)
        }
        return loSet.size == amount
    }

//    private fun getMidPuzzle(sudokuArray: Array<IntArray>, amount: Int): Boolean {
//        if(!getLowPuzzle(sudokuArray,amount/3))return false
//        val newAmount=amount-amount/3
//
//    }

//    private fun getHighPuzzle(sudokuArray: Array<IntArray>, amount: Int): Boolean {
//        var count = 0
//        for (ly in 0..8) {
//            val rowAmount = if (ly < 4) (0..9).random() else amount
//            var rCount = 0
//            for (lx in 0..8) {
//                if (rCount >= rowAmount) break
//                if (isEmptyCell(sudokuArray, lx, ly)) {
//                    sudokuArray[lx][ly] = 0
//                    rCount++
//                    count++
//                    if (count == amount) return true
//                }
//            }
//        }
//        return false
//    }
}