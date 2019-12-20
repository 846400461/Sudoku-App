package com.dotbin.sudoku.db

import androidx.room.TypeConverter
import com.dotbin.sudoku.adapter.SudokuBaseInfo
import com.dotbin.sudoku.algorithm.SudokuDegree
import java.util.*

class SudokuConverters {
    @TypeConverter
    fun fromSudokuString(data: String?): SudokuBaseInfo? {
        TODO("json String to SudokuBaseInfo")
    }

    @TypeConverter
    fun SudokuInfoToString(info: SudokuBaseInfo?): String? {
            TODO("SudokuBaseInfo to json String")
    }

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromDegreeString(data: Int?): SudokuDegree? {
        TODO("Int to SudoDegree")
    }

    @TypeConverter
    fun degreeToString(degree: SudokuDegree?): Int? {
        TODO("SudoDegree to Int")
    }
}