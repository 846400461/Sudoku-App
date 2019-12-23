package com.dotbin.sudoku.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dotbin.sudoku.adapter.SudokuBaseInfo
import com.dotbin.sudoku.algorithm.SudokuDegree
import java.util.*

@Entity
data class SudokuGame(
        @PrimaryKey(autoGenerate = true) var id:Int,
        var updateOn: Date?,
        var sudokuGame:SudokuBaseInfo,
        var Degree:SudokuDegree?
)