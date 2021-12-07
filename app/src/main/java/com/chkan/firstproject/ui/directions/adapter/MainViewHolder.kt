package com.chkan.firstproject.ui.directions.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chkan.domain.models.LocalModelUI
import com.chkan.firstproject.R

class MainViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

    fun bindData(item: LocalModelUI){
        val tv = itemView.findViewById<TextView>(R.id.tv_rv_item)
        tv.text = item.name
    }
}