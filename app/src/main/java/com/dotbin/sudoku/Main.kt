package com.dotbin.sudoku

import android.graphics.Color
import android.util.Log

fun main() {
    var cellColors = Array(9) { IntArray(9) }
    for(x in 0 until cellColors.count()){
        for (y in 0 until  cellColors[x].count())
            cellColors[x][y]=Color.BLACK
    }
    cellColors.forEach { ints ->
       ints.forEach { i -> print("$i ") }
        println()
    }
}

