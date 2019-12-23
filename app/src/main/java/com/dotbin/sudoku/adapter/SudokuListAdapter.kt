package com.dotbin.sudoku.adapter

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dotbin.sudoku.R
import com.dotbin.sudoku.algorithm.SudokuState
import com.dotbin.sudoku.ui.SudokuCell
import com.dotbin.sudoku.ui.SudokuGameView
import java.io.Serializable

class SudokuListAdapter(private var sudokuBaseInfoList: List<SudokuBaseInfo>) :
    RecyclerView.Adapter<SudokuListAdapter.ViewHolder>() {

    private var recyclerViewClick: RecyclerViewClick? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sudokuGameView: SudokuGameView = itemView.findViewById(R.id.itemGameView)
        val timeView: TextView = itemView.findViewById(R.id.sudokuTime)
        val stateView: TextView = itemView.findViewById(R.id.sudokuState)
        val view: View = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_sudoku, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = sudokuBaseInfoList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.stateView.text = when (sudokuBaseInfoList[position].state) {
            SudokuState.FINISHED -> "已完成"
            SudokuState.PROCESSING -> "进行中"
            SudokuState.UNFINISHED -> ""
        }
        holder.timeView.text = if (sudokuBaseInfoList[position].time != 0)
            "${sudokuBaseInfoList[position].time / 60}:${sudokuBaseInfoList[position].time % 60}" else ""
        holder.view.setOnClickListener {
            recyclerViewClick?.let {
                it(sudokuBaseInfoList[position], position)
            }
        }
        holder.sudokuGameView.cellIfs = sudokuBaseInfoList[position].sudokuArray
        holder.sudokuGameView.firstPostCellIfs()
        holder.sudokuGameView.touchEnale = false

    }

    fun setOnRecyclerViewClickListener(click: RecyclerViewClick) {
        recyclerViewClick = click
    }

//    fun addItem(item: SudokuBaseInfo) {
//        sudokuBaseInfoList.add(item)
//        notifyItemInserted(sudokuBaseInfoList.lastIndex)
//    }

    fun setDataSource(baseList: List<SudokuBaseInfo>?) {
        sudokuBaseInfoList = baseList ?: listOf()
        notifyDataSetChanged()
    }
}

data class SudokuBaseInfo(
    var sudokuArray: Array<Array<SudokuCell>>,
    var state: SudokuState,
    var time: Int
)
