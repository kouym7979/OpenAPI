package com.example.openapi

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.openapi.databinding.ActivityLoginBinding
import com.facebook.*
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import org.json.JSONException
import org.json.JSONObject
import java.security.MessageDigest
import java.util.*

class LoginActivity : AppCompatActivity() {

    private var userEmail:String ?=null
    private var userName: String?=null
    private var name :String ?=null
    private var email:String?=null
    private var img_url:String ?=null

    private var callbackManager: CallbackManager? = null
    private lateinit var binding:ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);


        binding.faceLogin.setOnClickListener {
            Log.d("확인", "로그인버튼 눌림" )
            callbackManager = CallbackManager.Factory.create()
            LoginManager.getInstance().logInWithReadPermissions(this,
                Arrays.asList("public_profile","email"))

            LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult>{
                override fun onSuccess(result: LoginResult?) {
                    //유저데이터를 여기서 가져올 수 있음
                    Log.d("확인", "성공 " )
                    if(result?.accessToken!=null) {//회원정보 가져옴
                        val accessToken = result?.accessToken
                        getFacebookInfo(accessToken)
                    }

                    var intent :Intent= Intent(applicationContext, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    intent.putExtra("name",name)
                    intent.putExtra("email",email)
                    intent.putExtra("img_url",img_url)
                    startActivity(intent)
                    finish()
                }

                override fun onCancel() {
                    Log.d("확인", "캔슬 " )
                }

                override fun onError(error: FacebookException?) {
                    Log.d("확인", "에러 " )
                   Toast.makeText(applicationContext,"로그인에 실패했습니다",Toast.LENGTH_SHORT).show()
                }

            })
        }
    }
    //로그인 결과를 callbackManager를 통해 로그인 매니저에 전달함
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun getFacebookInfo(accessToken:AccessToken){
        val graphrequest: GraphRequest=GraphRequest.newMeRequest(accessToken,object : GraphRequest.GraphJSONObjectCallback{
            override fun onCompleted(resultObject:JSONObject? ,response:GraphResponse?){
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



}