package com.example.openapi

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.openapi.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class Coroutine : AppCompatActivity(){

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //coroutine()
    }
    //okhttp의 흐름: 클라이언트 -> 요청 -> 응답
    /*fun coroutine(){//Default는 백그라운드 쓰레드
        CoroutineScope(Dispatchers.Main).launch {

            val json =CoroutineScope(Dispatchers.Default).async {
                //네트워크 통신
                getjson()
            }.await()//비동기실행

            binding.test.text=json
            //main thread
        }//동기 실행

    }
    fun getjson() :String{
        //1.클라이언트 만들기
        val client =OkHttpClient.Builder().build()
        //2.요청
        val req = Request.Builder().url("http://www.google.com").build()
        //응답
        /*client.newCall(req).execute().use {
            response -> return if (response.body !=null){

            }
        }*/
    }*/


}