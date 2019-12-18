package com.dotbin.sudoku.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dotbin.sudoku.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sudokuGameView.setOnCellClickedListener{
            if(it)
                 Random.nextInt(10)
            else 0
        }
        val sudokuCells=sudokuGameView.cellIfs
        sudokuCells[1][1].enabled=false
        sudokuCells[1][1].value=1
        sudokuCells[5][5].enabled=false
        sudokuGameView.postCellIfs()
    }
}
