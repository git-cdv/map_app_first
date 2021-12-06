package com.chkan.firstproject.ui.directions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chkan.domain.models.LocalModelUI
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chkan.firstproject.R

class MainAdapter(private val list: List<LocalModelUI>,): RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_history_item,parent,false))
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindData(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}