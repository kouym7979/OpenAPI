package com.example.openapi.Adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.openapi.Data.BookInfo
import com.example.openapi.R
import com.example.openapi.databinding.ActivityBookCommentBinding.inflate
import com.example.openapi.databinding.ActivityLoginBinding.inflate
import com.example.openapi.databinding.ActivityMainBinding.inflate
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.review_item.view.*

class ReviewAdapter ( val context:Context,val review : List<BookInfo>,val email:String): RecyclerView.Adapter<ReviewAdapter.ViewHolder>(){

    private var mcontext:Context?=null
    private var mfirestore :FirebaseFirestore?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewAdapter.ViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.review_item,parent,false)
        mcontext=parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewAdapter.ViewHolder, position: Int) {
        holder.itembind(review[position])

        mfirestore= FirebaseFirestore.getInstance()

        if(email.equals(review!![position].user_email))
        {
            holder.itemView.review_menu.visibility=View.VISIBLE
        }

        else
            holder.itemView.review_menu.visibility=View.GONE

        holder.itemView.review_menu.setOnClickListener {
            var builder = AlertDialog.Builder(context)
            builder.setTitle("삭제하시겠습니까?")

            var delete=DialogInterface.OnClickListener { dialog, which ->
                mfirestore?.collection("review")?.document(review!![position].review_id!!)
                    ?.delete()?.addOnSuccessListener {
                        Log.d("확인","작성한 리뷰가 삭제되었습니다")
                        Toast.makeText(context,"작성한 리뷰가 삭제되었습니다",Toast.LENGTH_SHORT).show()
                    }
            }
            builder.setPositiveButton("확인",delete)
            builder.setNegativeButton("취소",null)
            builder.show()

        }
    }

    override fun getItemCount(): Int {

        if(review.size>5) {
            return 5
        }
        return review.size
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){

        fun itembind(data : BookInfo){
            itemView.review_content.text=data.comment
            itemView.review_date.text=data.date
            itemView.review_name.text=data.name
        }
    }
}