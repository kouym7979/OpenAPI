package com.example.openapi

class GridViewItem {

    private var attraction_name:String?=null
    private var start_time:String?=null//운영 시작시간
    private var end_time:String?=null//운영 종료시간

    constructor(attraction_name:String, start_time:String, end_time:String){
        this.attraction_name=attraction_name
        this.end_time=end_time
        this.start_time=start_time
    }



}