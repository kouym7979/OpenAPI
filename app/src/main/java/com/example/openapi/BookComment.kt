package com.example.openapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.openapi.Data.BookInfo
import com.example.openapi.databinding.ActivityBookCommentBinding
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.GraphResponse
import com.google.firebase.firestore.FirebaseFirestore
import org.json.JSONException
import org.json.JSONObject

class BookComment : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding :ActivityBookCommentBinding
    private var title : String?=null
    private var author :String?=null
    private var isbn : String?=null
    private var name :String ?=null
    private var email:String?=null
    private var img_url:String ?=null
    private var mfirestore:FirebaseFirestore?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_book_comment)

        binding= ActivityBookCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mfirestore= FirebaseFirestore.getInstance()


        if(intent.hasExtra("title") && intent.hasExtra("author") && intent.hasExtra("isbn"))
        {
            title=intent.getStringExtra("title")
            author=intent.getStringExtra("author")
            isbn=intent.getStringExtra("isbn")//일련변호
        }

        binding.commentTitle.text=title
        binding.commentAuthor.text=author

        binding.editBtn.setOnClickListener(this)

    }

    override fun onStart() {
        super.onStart()

        val accessToken= AccessToken.getCurrentAccessToken()
        if(accessToken!=null){
            getFacebookInfo(accessToken)
        }
    }

    private fun getFacebookInfo(accessToken:AccessToken){//로그인된 정보 확인
        val graphrequest: GraphRequest =
            GraphRequest.newMeRequest(accessToken,object : GraphRequest.GraphJSONObjectCallback{
            override fun onCompleted(resultObject: JSONObject?, response: GraphResponse?){
                try {
                    name =resultObject?.getString("name");
                    email =resultObject?.getString("email");
                    img_url=resultObject?.getJSONObject("picture")?.getJSONObject("data")?.getString("url")
                    Log.d("확인","이름:${name} 이메일: ${email} image : ${img_url}")
                }catch (e : JSONException){
                    Log.d("확인",e.toString())
                }
            }
        })
        val parameters:Bundle = Bundle()
        parameters.putString("fields","id,name,email,picture.width(200)")
        graphrequest.parameters=parameters
        graphrequest.executeAsync()//새로운 스레드를 만들어서 처리함.

    }

    override fun onClick(v: View?) {

        var check : String?=binding.commentEdit.text.toString()
        if(check.equals("")){
            Toast.makeText(this,"리뷰를 작성해주세요",Toast.LENGTH_SHORT).show()
        }else{
            var review_id=isbn

            var review_info =BookInfo()
        }

    }
}