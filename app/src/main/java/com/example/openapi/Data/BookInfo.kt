package com.example.openapi.Data

data class BookInfo(
    var book_num:String?=null,//책 고유번호
    var comment :String?=null,//리뷰
    var user_email: String?=null,//유저 이메일
    var name : String?=null,//작성자 이름
    var img : String?=null,//작성자 프로필
    var date : String?=null,//작성 날짜
    var review_id:String?=null
)
