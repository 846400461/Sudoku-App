package com.dotbin.sudoku.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dotbin.sudoku.algorithm.SudokuDegree
import com.dotbin.sudoku.db.SudokuDao
import com.dotbin.sudoku.db.SudokuGame
import com.dotbin.sudoku.db.SudokuRoomDatabase
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    val sudokuDegreeCounts:Array<LiveData<List<SudokuGame>>> by lazy {
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
}