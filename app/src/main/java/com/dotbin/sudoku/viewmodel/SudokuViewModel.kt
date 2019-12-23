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
import kotlinx.coroutines.launch

class SudokuViewModel(application: Application) : AndroidViewModel(application) {
    val sudokuDao: SudokuDao = SudokuRoomDatabase.getDatabase(application).SudokuDao()
    //val sudoList :MutableLiveData<List<SudokuBaseInfo>> =MutableLiveData()
    val sudokuList: Array<LiveData<List<SudokuGame>>> by lazy {
        arrayOf(
            sudokuDao.loadSudoku(SudokuDegree.LOW),
            sudokuDao.loadSudoku(SudokuDegree.MIDDLE),
            sudokuDao.loadSudoku(SudokuDegree.HIGH)
        )
    }

    fun updateSudoku(sudokuGame: SudokuGame){
        viewModelScope.launch {
            sudokuDao.updateSudoku(sudokuGame)
        }
    }

    fun insertSudoku(sudokuGame: SudokuGame){
        viewModelScope.launch {
            sudokuDao.insertSudoku(sudokuGame)
        }
    }

    fun deleteSudoku(sudokuGame: SudokuGame){
        viewModelScope.launch {
            sudokuDao.deleteSudoku(sudokuGame)
        }
    }

}