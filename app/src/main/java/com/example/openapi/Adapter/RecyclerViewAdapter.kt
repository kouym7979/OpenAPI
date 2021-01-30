package com.example.openapi.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.openapi.MainActivity
import com.example.openapi.R
import com.example.openapi.ResponseDTO
import kotlinx.android.synthetic.main.list_item.view.*

class RecyclerViewAdapter(val bookList : MainActivity.ResponseDTO):RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapter.ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(bookList.row[position])
    }

    override fun getItemCount(): Int {
        return bookList.row.count()
    }
    
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        fun bindItems(data : MainActivity.Book){
            itemView.title.text=data.TITLE
            itemView.author.text=data.AUTHOR
            itemView.publish.text=data.PUBLISHER
            itemView.year.text=data.PUBLISHER_YEAR.toString()+"년"
            itemView.isbn.text=data.ISBN.toString()
        }
    }
}