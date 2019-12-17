package com.dotbin.sudoku

import com.dotbin.sudoku.algorithm.SudokuSolver

fun main() {
    SudokuSolver.getSolution(sudoArr)
    for (x in sudoArr.indices)
        for(y in sudoArr[x].indices){
            if(sudoArr[x][y]!=sudoArr2[x][y])
                println("($x,$y) expect : ${sudoArr2[x][y]} act: ${sudoArr[x][y]}")
        }
}
val sudoArr= arrayOf(
    intArrayOf(0,0,4,0,7,9,0,0,0),
    intArrayOf(3,0,0,0,4,0,2,0,9),
    intArrayOf(7,8,0,3,0,0,0,4,0),
    intArrayOf(0,6,8,0,9,1,0,0,3),
    intArrayOf(0,0,0,4,8,3,0,0,0),
    intArrayOf(1,0,0,2,6,0,9,8,0),
    intArrayOf(0,3,0,0,0,7,0,9,4),
    intArrayOf(6,0,1,0,3,0,0,0,5),
    intArrayOf(0,0,0,9,5,0,3,0,0)
)


val sudoArr2= arrayOf(
    intArrayOf(5,2,4,6,7,9,8,3,1),
    intArrayOf(3,1,6,5,4,8,2,7,9),
    intArrayOf(7,8,9,3,1,2,5,4,6),
    intArrayOf(2,6,8,7,9,1,4,5,3),
    intArrayOf(9,5,7,4,8,3,1,6,2),
    intArrayOf(1,4,3,2,6,5,9,8,7),
    intArrayOf(8,3,5,1,2,7,6,9,4),
    intArrayOf(6,9,1,8,3,4,7,2,5),
    intArrayOf(4,7,2,9,5,6,3,1,8)
)


