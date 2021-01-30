package com.example.openapi.Adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.openapi.BookComment
import com.example.openapi.MainActivity
import com.example.openapi.R
import kotlinx.android.synthetic.main.list_item.view.*

class RecyclerViewAdapter(val bookList : MainActivity.ResponseDTO):RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {



    lateinit var listener: AdapterView.OnItemClickListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bookList.row.count()
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(bookList.row[position])

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView?.context,BookComment::class.java)

            intent.putExtra("isbn",bookList.row[position].ISBN.toString())//책 일련번호 전달
            intent.putExtra("title",bookList.row[position].TITLE)
            intent.putExtra("author",bookList.row[position].AUTHOR)
            //Log.d("확인","리싸이클러뷰"+bookList.row[position].TITLE+" "+bookList.row[position].AUTHOR)
            ContextCompat.startActivity(holder.itemView.context,intent,null)
        }

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