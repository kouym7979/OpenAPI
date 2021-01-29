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
import org.json.JSONObject
import java.io.IOException


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)

        val JSON: MediaType = MediaType.get("application/json; charset=utf-8")

        binding.mainList.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.mainList.setHasFixedSize(true)
        //binding.mainList.adapter = RecyclerViewAdapter(ResponseDTO(0,List(Place)))
        fetchJson()
    }


    fun fetchJson(){
        val url ="http://apis.data.go.kr/6300000/eventDataService/eventDataListJson?serviceKey=528dJYSZAL4MMDEHve563fHM6WAvppwdZfW1xc15tszh9OIoZE7HTpLIObCxY%2BrWUIRL0O9SQiMqKp6GG6vA9Q%3D%3D&numOfRows=10&pageNo=1"
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
                    .asJsonObject.get("msgBody")
                val attractions = gson.fromJson(rootObj,ResponseDTO::class.java)


                runOnUiThread {
                    binding.mainList.adapter = RecyclerViewAdapter(attractions)
                }

            }
            override fun onFailure(call: Call, e: IOException) {
                Log.d("확인",e.toString())
            }
        })
    }
    data class ResponseDTO(val list_count: Int, val row: List<Place>)
    data class Place(
        var eventSeq: String? = "테스트",//행사 일련번호
        var contents: String? = null,// 행사 상세정보
        var placeCdNm: String? = null, //장소명
        var beginDt: String? = null, //시작일
        var endDt: String? = null, //종료일
        var imageLink: String? = null //이미지 링크
    )
}