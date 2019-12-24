package com.dotbin.sudoku.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.dotbin.sudoku.algorithm.SudokuDegree

@Dao
interface SudokuDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSudoku(vararg sudokuses: SudokuGame):List<Long>

    @Update
    suspend fun updateSudoku(vararg sudokuses:SudokuGame)

    @Delete
    suspend fun deleteSudoku(vararg sudokuses: SudokuGame)

    @Query("SELECT * FROM SudokuGame  WHERE Degree = :degree ORDER BY updateOn")
    fun loadSudoku(degree: SudokuDegree): LiveData<List<SudokuGame>>

    @Query("SELECT * FROM SudokuGame WHERE id = :id LIMIT 1")
    fun findSudokuFromId(id:Int):LiveData<SudokuGame>
}