package com.dotbin.sudoku.db

import androidx.room.*

@Dao
interface SudokuDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSudoku(vararg sudokuses: SudokuGame):List<Long>

    @Update
    fun updateSudoku(vararg sudokuses:SudokuGame)

    @Delete
    fun deleteSudoku(vararg sudokuses: SudokuGame)

    @Query("SELECT * FROM SudokuGame  ORDER BY updateOn")
    fun loadAllSudoku():List<SudokuGame>

}