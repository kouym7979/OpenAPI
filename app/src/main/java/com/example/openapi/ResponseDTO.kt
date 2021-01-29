package com.example.openapi

data class ResponseDTO(val list_count: Int, val row: List<Place>)
data class Place(
    var eventSeq: String? = null,//행사 일련번호
    var contents: String? = null,// 행사 상세정보
    var placeCdNm: String? = null, //장소명
    var beginDt: String? = null, //시작일
    var endDt: String? = null, //종료일
    var imageLink: String? = null //이미지 링크
)






