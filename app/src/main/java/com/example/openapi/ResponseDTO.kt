package com.example.openapi

import java.util.*

data class ResponseDTO(val list_total_count:Int, val row: List<Book>)
data class Book(
    val TITLE: String,
    val AUTHOR : String,
    val PUBLISHER : String,
    val PUBLISHER_YEAR : Int,
    val ISBN : Long
)






