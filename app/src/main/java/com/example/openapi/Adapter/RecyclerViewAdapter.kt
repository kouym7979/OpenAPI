package com.example.openapi.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.openapi.MainActivity
import com.example.openapi.R
import com.example.openapi.ResponseDTO
import kotlinx.android.synthetic.main.list_item.view.*

class RecyclerViewAdapter(val placeList : MainActivity.ResponseDTO):RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(placeList.row[position])
    }

    override fun getItemCount(): Int {

        return placeList.row.count()
    }
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        fun bindItems(data : MainActivity.Place){
            itemView.list_num.text=data.eventSeq
            itemView.s_date.text=data.beginDt
            itemView.e_date.text=data.endDt
            itemView.place_name.text=data.placeCdNm
            itemView.content.text=data.contents
        }
    }
}