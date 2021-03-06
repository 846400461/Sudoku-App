package com.dotbin.sudoku.ui

import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dotbin.sudoku.R
import com.dotbin.sudoku.algorithm.SudokuState
import com.dotbin.sudoku.algorithm.SudokuValue
import com.dotbin.sudoku.db.SudokuGame
import com.dotbin.sudoku.viewmodel.SudokuViewModel
import kotlinx.android.synthetic.main.activity_game.*


class GameActivity : AppCompatActivity() {
    private lateinit var sudokuViewModel: SudokuViewModel
    private lateinit var sudoku: SudokuGame
    private var keyNum = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,

            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_game)
        keyNum = numKeyboard.getNum()
        sudokuViewModel = ViewModelProvider(this).get(SudokuViewModel::class.java)
        intent.apply {
            val loc = getSerializableExtra(SudokuValue.sudokuGame) as Int?
            loc?.let { id ->
                sudokuViewModel.findSudokuFromId(id).observe(this@GameActivity, Observer {
                    sudoku = it
                    sudokuGameView.cellIfs = it.sudokuGame.sudokuArray
                    sudokuGameView.invalidate()
                    sudokuViewModel.startTime(it.sudokuGame.time)
                    sudoku.sudokuGame.state = SudokuState.PROCESSING
                })
            }
        }
        sudokuViewModel.setSudokuTimeListener {
            sudokuToolbar.title =
                (if (it / 60 < 10) "0" else "") + "${it / 60}:" + (if (it % 60 < 10) "0" else "") + "${it % 60}"
        }
        numKeyboard.setNumclickListener {
            keyNum = it
            sudokuGameView.setCurrentCellValue(
                when (keyNum) {
                    10 -> 0
                    else -> keyNum
                }
            )
        }
        sudokuGameView.setOnClickListener {
            numKeyboard.setNum(11)
            11
        }
        sudokuGameView.setOnSuccessListener {
            val t = sudokuViewModel.stopTime()
            sudokuToolbar.title =
                "已通关，通关时长：" + (if (t / 60 < 10) "0" else "") + "${t / 60}:" + (if (t % 60 < 10) "0" else "") + "${t % 60}"
            sudoku.sudokuGame.state = SudokuState.FINISHED
        }
    }

    override fun onPause() {
        super.onPause()
        sudoku.sudokuGame.sudokuArray = sudokuGameView.cellIfs
        sudoku.sudokuGame.time = sudokuViewModel.stopTime()
        sudokuViewModel.updateSudoku(sudoku)
    }

}
