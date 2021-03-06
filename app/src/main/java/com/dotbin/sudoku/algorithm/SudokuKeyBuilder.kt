package com.dotbin.sudoku.algorithm

import java.lang.IllegalArgumentException

object SudokuKeyBuilder : Throwable() {
    private val ramSet = mutableSetOf<SudokuLocation>()
    private fun buildRandomSudokuKey(sudokuArray: Array<IntArray>) {
        do {
            ramSet.clear()
            for (i in sudokuArray.indices)
                for (j in sudokuArray[i].indices)
                    sudokuArray[i][j] = 0
            repeat(11) {
                var location = SudokuLocation((0..8).random(), (0..8).random(), 0)
                while (ramSet.contains(location)) {
                    location = SudokuLocation((0..8).random(), (0..8).random(), 0)
                }
                ramSet.add(location)
                sudokuArray[location.x][location.y] = (1..9).random()
            }
        } while (!SudokuSolver.getSolution(sudokuArray))
    }

    private fun buildSudokuKey(sudokuArray: Array<IntArray>) {
        sudokuCopy(SudokuValue.templateSudokus[(0..8).random()],sudokuArray)
    }

    private fun sudokuObfuscator(sudokuArray: Array<IntArray>){
        val baseNum= mutableListOf<Int>()
        while (baseNum.size!=9){
            val temp=(1..9).random()
            if(!baseNum.contains(temp))
                baseNum.add(temp)
        }
        for(i in sudokuArray.indices)
            for(j in sudokuArray[i].indices){
                sudokuArray[i][j]=baseNum[sudokuArray[i][j]-1]
            }
    }

    suspend fun obtainSudokuKey(sudokuArray: Array<IntArray>, isRandom: Boolean){
        if (sudokuArray.size != 9)
            throw IllegalArgumentException("Illegal sudoku array size")
        sudokuArray.forEach {
            if (it.size != 9)
                throw IllegalArgumentException("Illegal sudoku array size")
        }
        if(isRandom)
            buildRandomSudokuKey(sudokuArray)
        else
            buildSudokuKey(sudokuArray)

        sudokuObfuscator(sudokuArray)
    }

    fun sudokuCopy(src:Array<IntArray>,des:Array<IntArray>){
        for(i in src.indices)
            for(j in src[i].indices)
                des[i][j]=src[i][j]
    }
}