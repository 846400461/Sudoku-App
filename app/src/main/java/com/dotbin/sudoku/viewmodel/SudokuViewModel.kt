package com.dotbin.sudoku.viewmodel

import android.app.Application
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dotbin.sudoku.adapter.SudokuBaseInfo
import com.dotbin.sudoku.algorithm.*
import com.dotbin.sudoku.db.SudokuDao
import com.dotbin.sudoku.db.SudokuGame
import com.dotbin.sudoku.db.SudokuRoomDatabase
import com.dotbin.sudoku.ui.SudokuCell
import kotlinx.coroutines.*
import java.util.*

typealias TimeListener = ((time: Long) -> Unit)

class SudokuViewModel(application: Application) : AndroidViewModel(application) {
    val sudokuDao: SudokuDao = SudokuRoomDatabase.getDatabase(application).SudokuDao()
    var timeCount = 0L
    var jobTime: Job? = null
    var timeListener: TimeListener? = null
    //val sudoList :MutableLiveData<List<SudokuBaseInfo>> =MutableLiveData()
    val sudokuList: Array<LiveData<List<SudokuGame>>> by lazy {
        arrayOf(
            sudokuDao.loadSudoku(SudokuDegree.LOW),
            sudokuDao.loadSudoku(SudokuDegree.MIDDLE),
            sudokuDao.loadSudoku(SudokuDegree.HIGH)
        )
    }
    val sudokuDegreeCounts: Array<LiveData<List<SudokuGame>>> by lazy {
        arrayOf(
            sudokuDao.getCountDegree(SudokuDegree.LOW),
            sudokuDao.getCountDegree(SudokuDegree.MIDDLE),
            sudokuDao.getCountDegree(SudokuDegree.HIGH)
        )
    }

    fun findSudokuFromId(id: Int) = sudokuDao.findSudokuFromId(id)

    fun updateSudoku(vararg sudokuGame: SudokuGame) {
        viewModelScope.launch {
            sudokuDao.updateSudoku(*sudokuGame)
        }
    }

    fun insertSudoku(sudokuGame: SudokuGame) {
        viewModelScope.launch {
            sudokuDao.insertSudoku(sudokuGame)
        }
    }

    fun deleteSudoku(sudokuGame: SudokuGame) {
        viewModelScope.launch {
            sudokuDao.deleteSudoku(sudokuGame)
        }
    }

    fun startTime(time: Long) {
        timeCount = time
        stopTime()
        jobTime = viewModelScope.launch {
            while (true) {
                delay(1000)
                timeCount++
                timeListener?.invoke(timeCount)
            }
        }
    }

    fun stopTime(): Long {
        jobTime?.cancel()
        jobTime = null
        return timeCount
    }

    fun setSudokuTimeListener(listener: TimeListener) {
        timeListener = listener
    }

    fun generateSudokuPuzzle(sudokuDegree: SudokuDegree?) {
        val tempArr = Array(9) { IntArray(9) }
        val realGeJob = viewModelScope.launch {
            SudokuKeyBuilder.obtainSudokuKey(tempArr, true)
        }
        viewModelScope.launch {
            delay(200)
            var tArray = tempArr
            if (!realGeJob.isCompleted) {
                realGeJob.cancelAndJoin()
                Log.w("generateSudokuPuzzle","can not generate real random Puzzle")
            }
            while (!SudokuPuzzleBuilder.getSudokuPuzzle(
                    tempArr,
                    sudokuDegree ?: SudokuDegree.LOW
                )
            )
            {
                SudokuKeyBuilder.obtainSudokuKey(tempArr, false)
                tArray = tempArr
            }
            val cells =
                Array(9) { i ->
                    Array(9) { j ->
                        SudokuCell(
                            if (tempArr[i][j] == 0) Color.rgb(
                                54,
                                54,
                                54
                            ) else Color.rgb(105, 105, 105),
                            tempArr[i][j],
                            tempArr[i][j] == 0,
                            Color.WHITE
                        )
                    }
                }
            val baseInfo = SudokuBaseInfo(cells, SudokuState.UNFINISHED, 0)
            insertSudoku(SudokuGame(0, Date(), baseInfo, sudokuDegree, tArray))
        }
    }
}