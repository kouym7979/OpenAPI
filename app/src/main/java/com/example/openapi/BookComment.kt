package com.example.openapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.openapi.databinding.ActivityBookCommentBinding

class BookComment : AppCompatActivity() {

    private lateinit var binding :ActivityBookCommentBinding
    private var title : String?=null
    private var author :String?=null
    private var isbn : String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_book_comment)

        binding= ActivityBookCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if(intent.hasExtra("title") && intent.hasExtra("author"))
        {
            title=intent.getStringExtra("title")
            author=intent.getStringExtra("author")
        }

        /*var intent=getIntent()
        title=intent?.getStringExtra("title")
        author=intent?.getStringExtra("author")
        isbn = intent?.getStringExtra("isbn")*/


        Log.d("확인","코멘트 테스트:"+title+"작가: "+author)
        binding.commentTitle.text=title
        binding.commentAuthor.text=author

    }
}