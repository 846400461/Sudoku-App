package com.dotbin.sudoku.db

import androidx.room.TypeConverter
import com.dotbin.sudoku.adapter.SudokuBaseInfo
import com.dotbin.sudoku.algorithm.SudokuDegree
import com.google.gson.Gson
import java.util.*

class SudokuConverters {
    private val json=Gson()
    @TypeConverter
    fun fromSudokuString(data: String?): SudokuBaseInfo? {
        return json.fromJson<SudokuBaseInfo>(data,SudokuBaseInfo::class.java)
    }

    @TypeConverter
    fun SudokuInfoToString(info: SudokuBaseInfo?): String? {
        return json.toJson(info)
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
    fun fromDegreeInt(data: Int?): SudokuDegree? {
        return when(data){
            SudokuDegree.LOW.ordinal -> SudokuDegree.LOW
            SudokuDegree.MIDDLE.ordinal ->SudokuDegree.MIDDLE
            SudokuDegree.HIGH.ordinal->SudokuDegree.HIGH
            else -> null
        }
    }

    @TypeConverter
    fun degreeToInt(degree: SudokuDegree?) = degree?.ordinal
}