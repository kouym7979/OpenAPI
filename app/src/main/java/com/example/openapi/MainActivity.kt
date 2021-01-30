package com.example.openapi

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openapi.Adapter.RecyclerViewAdapter
import com.example.openapi.databinding.ActivityMainBinding
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.*


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.mainList.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.mainList.setHasFixedSize(true)
        //binding.mainList.adapter = RecyclerViewAdapter(ResponseDTO(0,List(Place)))
        fetchJson()
    }


    fun fetchJson(){
        //val url ="http://apis.data.go.kr/6300000/eventDataService/eventDataListJson?serviceKey=528dJYSZAL4MMDEHve563fHM6WAvppwdZfW1xc15tszh9OIoZE7HTpLIObCxY%2BrWUIRL0O9SQiMqKp6GG6vA9Q%3D%3D&numOfRows=10&pageNo=1"
        //val url ="http://data.seoul.go.kr/dataList/datasetView.do?infId=OA-149&srvType=A&serviceKind=1&currentPageNo=1"
        val key ="756f7243706b6f7538305269594e44"
        val url="http://openapi.seoul.go.kr:8088/"+key+"/json/SeoulLibraryBookRentNumInfo/1/15/"
        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback{
            override fun onResponse(call: Call, response: Response) {
                val body = response?.body()?.string()
                println(body)

                //파싱 - 가져온 데이터를 모델오브젝트로 변환해 줘야한다.
                val gson =GsonBuilder().create()
                val parser = JsonParser()

                val rootObj = parser.parse(body.toString())
                    .asJsonObject.get("SeoulLibraryBookRentNumInfo")

                val books = gson.fromJson(rootObj,ResponseDTO::class.java)


                //Log.d("확인","데이터확인:"+attractions.msgBody[0].title.toString())
                //Log.d("확인","데이터 확인:"+attractions.totalCount)
                Log.d("확인",books.row[0].TITLE)
                Log.d("확인",books.row[1].TITLE)

                runOnUiThread {
                    binding.mainList.adapter = RecyclerViewAdapter(books)
                }

            }
            override fun onFailure(call: Call, e: IOException) {
                Log.d("확인",e.toString())
            }
        })
    }
    data class ResponseDTO(val list_total_count:Int, val row: List<Book>)
    data class Book(
        val TITLE: String,
        val AUTHOR : String,
        val PUBLISHER : String,
        val PUBLISHER_YEAR : Int,
        val ISBN : Long
        )
}