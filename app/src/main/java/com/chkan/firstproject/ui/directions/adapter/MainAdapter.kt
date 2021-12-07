package com.chkan.firstproject.ui.directions.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chkan.domain.models.LocalModelUI
import com.chkan.firstproject.R

class MainAdapter: RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private var list: List<LocalModelUI> = listOf()
    var onItemClick: ((LocalModelUI) -> Unit)? = null

    inner class MainViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(list[adapterPosition])
            }}

        fun bindData(item: LocalModelUI){
            val tv = itemView.findViewById<TextView>(R.id.tv_rv_item)
            tv.text = item.name
        }
    }

    fun setList (list: List<LocalModelUI>){
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_history_item,parent,false))
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindData(list[position])
    }

    override fun getItemCount(): Int = list.size
}