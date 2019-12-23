package com.dotbin.sudoku.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dotbin.sudoku.R
import com.dotbin.sudoku.adapter.SudokuBaseInfo
import com.dotbin.sudoku.adapter.SudokuListAdapter
import com.dotbin.sudoku.algorithm.*
import com.dotbin.sudoku.db.SudokuGame
import com.dotbin.sudoku.viewmodel.SudokuViewModel

import kotlinx.android.synthetic.main.activity_sudoku_list.*
import kotlinx.android.synthetic.main.content_sudoku_list.*
import java.util.*

class SudokuListActivity : AppCompatActivity() {
    var sudokuListAdapter: SudokuListAdapter? = null
    var sudokuDegree: SudokuDegree? = null
    private lateinit var sudokuViewModel: SudokuViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sudoku_list)
        setSupportActionBar(toolbar)

        sudokuViewModel = ViewModelProvider(this).get(SudokuViewModel::class.java)

        intent.apply {
            sudokuDegree = getSerializableExtra(SudokuValue.sudokuDegree) as SudokuDegree
        }
        sudokuRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@SudokuListActivity)
            val SudokuList = mutableListOf<SudokuBaseInfo>()
            addItemDecoration(
                DividerItemDecoration(
                    this@SudokuListActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = SudokuListAdapter(SudokuList).apply {
                setOnRecyclerViewClickListener { info, _ ->
                    Toast.makeText(
                        this@SudokuListActivity,
                        (info as SudokuBaseInfo).state.name,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                sudokuListAdapter = this
            }
        }
        generatedButton.setOnClickListener {
            val temp = Array(9) { IntArray(9) }
            do {
                SudokuKeyBuilder.obtainSudokuKey(temp, false)
            } while (!SudokuPuzzleBuilder.getSudokuPuzzle(temp, sudokuDegree ?: SudokuDegree.LOW))
            val cells =
                Array(9) { i -> Array(9) { j -> SudokuCell(0, temp[i][j], temp[i][j] == 0, 0) } }
            val baseInfo = SudokuBaseInfo(cells, SudokuState.UNFINISHED, 0)
            sudokuViewModel.insertSudoku(SudokuGame(0, Date(), baseInfo, sudokuDegree))
        }
        sudokuDegree?.let { degree ->
            sudokuViewModel.sudokuList[degree.ordinal].observe(
                this,
                androidx.lifecycle.Observer { list ->
                    sudokuListAdapter?.setDataSource(list.map { it.sudokuGame })
                })
        }
    }

}
