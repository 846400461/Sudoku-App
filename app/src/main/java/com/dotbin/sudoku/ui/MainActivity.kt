package com.dotbin.sudoku.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dotbin.sudoku.R
import com.dotbin.sudoku.adapter.Difficulty
import com.dotbin.sudoku.adapter.DifficultyAdapter
import com.dotbin.sudoku.algorithm.SudokuConstValue
import com.dotbin.sudoku.algorithm.SudokuDegree
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        difficultyRecyclerview.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            val diList = listOf<Difficulty>(
                Difficulty(SudokuDegree.LOW, 30, 0, 0),
                Difficulty(SudokuDegree.MIDDLE, 30, 0, 0),
                Difficulty(SudokuDegree.HIGH, 30, 0, 0)
            )
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = DifficultyAdapter(diList).apply {
                setOnRecyclerViewClickListener { info,_ ->
                    Intent(this@MainActivity,SudokuListActivity::class.java).apply{
                        putExtra(SudokuConstValue.sudokuDegree,info.difficulty)
                        startActivity(this)
                    }
                }
            }
        }
    }

}
