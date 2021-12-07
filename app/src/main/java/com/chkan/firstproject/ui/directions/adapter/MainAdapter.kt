package com.chkan.firstproject.ui.directions.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chkan.domain.models.LocalModelUI
import com.chkan.firstproject.R

class MainAdapter (private val listener: (LocalModelUI) -> Unit): RecyclerView.Adapter<MainViewHolder>() {

    private var list: List<LocalModelUI> = listOf()

    fun setList (list: List<LocalModelUI>){
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_history_item,parent,false))
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = list[position]
        holder.bindData(item)
        holder.itemView.setOnClickListener { listener(item) }
    }

    override fun getItemCount(): Int = list.size
}