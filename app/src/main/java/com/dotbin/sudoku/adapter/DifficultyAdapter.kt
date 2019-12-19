package com.dotbin.sudoku.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dotbin.sudoku.R

class DifficultyAdapter(private val difficultyList: List<Difficulty>) :
    RecyclerView.Adapter<DifficultyAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val difficulty: TextView = itemView.findViewById(R.id.difficulty)
        val brief: TextView = itemView.findViewById(R.id.brief)
        val view=itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_difficulty, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = difficultyList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.difficulty.text = difficultyList[position].difficultySir
        holder.brief.text =
            if (difficultyList[position].finished > 0) "已解决 : ${difficultyList[position].finished}  " else "" +
                    if (difficultyList[position].processing > 0) "解题中 : ${difficultyList[position].processing}  " else "" +
                            if (difficultyList[position].unfinished > 0) "未解决 : ${difficultyList[position].unfinished}  " else ""
        holder.view.setOnClickListener {

        }
    }
}

data class Difficulty(
    var difficultySir: String,
    var unfinished: Int,
    var processing: Int,
    var finished: Int
)