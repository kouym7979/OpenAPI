package com.example.openapi.Data

data class BookInfo(
    val book_num:String?=null,//책 고유번호
    val comment :String?=null,//리뷰
    val user_email: String?=null,//유저 이메일
    val name : String?=null,//작성자 이름
    val img : String?=null,//작성자 프로필
    val date : String?=null//작성 날짜 
)
