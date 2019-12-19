package com.dotbin.sudoku.ui

import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.dotbin.sudoku.R
import com.dotbin.sudoku.algorithm.SudokuConstValue
import com.dotbin.sudoku.algorithm.SudokuDegree

import kotlinx.android.synthetic.main.activity_sudoku_list.*

class SudokuListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sudoku_list)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        intent.apply {
            val temp=getSerializableExtra(SudokuConstValue.sudokuDegree) as SudokuDegree
            Toast.makeText(this@SudokuListActivity, temp.name,Toast.LENGTH_SHORT).show()
        }
    }

}
