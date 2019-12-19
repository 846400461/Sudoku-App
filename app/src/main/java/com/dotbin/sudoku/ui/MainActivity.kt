package com.dotbin.sudoku.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dotbin.sudoku.R
import com.dotbin.sudoku.adapter.Difficulty
import com.dotbin.sudoku.adapter.DifficultyAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        difficultyRecyclerview.apply{
            setHasFixedSize(true)
            layoutManager=LinearLayoutManager(this@MainActivity)
            val diList= listOf<Difficulty>(
                Difficulty("初级",30,0,0),
                Difficulty("中级",30,0,0),
                Difficulty("高级",30,0,0)
            )
            addItemDecoration(DividerItemDecoration(this@MainActivity,DividerItemDecoration.VERTICAL))
            adapter=DifficultyAdapter(diList)
        }
    }
}
