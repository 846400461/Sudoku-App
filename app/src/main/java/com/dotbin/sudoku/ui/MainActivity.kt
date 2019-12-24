package com.dotbin.sudoku.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dotbin.sudoku.R
import com.dotbin.sudoku.adapter.Difficulty
import com.dotbin.sudoku.adapter.DifficultyAdapter
import com.dotbin.sudoku.algorithm.SudokuValue
import com.dotbin.sudoku.algorithm.SudokuDegree
import com.dotbin.sudoku.algorithm.SudokuState
import com.dotbin.sudoku.db.SudokuGame
import com.dotbin.sudoku.viewmodel.SudokuViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,

            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

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
                setOnRecyclerViewClickListener { info, _ ->
                    Intent(this@MainActivity, SudokuListActivity::class.java).apply {
                        putExtra(SudokuValue.sudokuDegree, (info as Difficulty).difficulty)
                        startActivity(this)
                    }
                }
            }
        }

        val adapter = difficultyRecyclerview.adapter as DifficultyAdapter
        val dList = adapter.getDifficultyList()
        val sudokuViewModel = ViewModelProvider(this).get(SudokuViewModel::class.java)
        sudokuViewModel.sudokuDegreeCounts[SudokuDegree.LOW.ordinal].observe(this, Observer {
            handlerDegreeCounts(dList, it, SudokuDegree.LOW)
            adapter.notifyItemChanged(SudokuDegree.LOW.ordinal)
        })
        sudokuViewModel.sudokuDegreeCounts[SudokuDegree.MIDDLE.ordinal].observe(this, Observer {
            handlerDegreeCounts(dList, it, SudokuDegree.MIDDLE)
            adapter.notifyItemChanged(SudokuDegree.MIDDLE.ordinal)
        })
        sudokuViewModel.sudokuDegreeCounts[SudokuDegree.HIGH.ordinal].observe(this, Observer {
            handlerDegreeCounts(dList, it, SudokuDegree.HIGH)
            adapter.notifyItemChanged(SudokuDegree.HIGH.ordinal)
        })
    }

    fun handlerDegreeCounts(
        dList: List<Difficulty>,
        games: List<SudokuGame>,
        degree: SudokuDegree
    ) {
        var fin = 0
        var unfin = 0
        var proc = 0
        for (temp in games) {
            if (temp.sudokuGame.state == SudokuState.FINISHED) fin++
            if (temp.sudokuGame.state == SudokuState.UNFINISHED) unfin++
            if (temp.sudokuGame.state == SudokuState.PROCESSING) proc++
        }
        dList[degree.ordinal].unfinished = unfin
        dList[degree.ordinal].finished = fin
        dList[degree.ordinal].processing = proc
    }

}
