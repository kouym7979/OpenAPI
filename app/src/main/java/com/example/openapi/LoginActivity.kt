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
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.openapi.databinding.ActivityLoginBinding
import com.facebook.*
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import java.security.MessageDigest
import java.util.*

class LoginActivity : AppCompatActivity() {

    private var userEmail:String ?=null
    private var userName: String?=null


    private var callbackManager: CallbackManager? = null
    private lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        binding.faceLogin.setOnClickListener {
            callbackManager = CallbackManager.Factory.create()
            LoginManager.getInstance().logInWithReadPermissions(this,
                Arrays.asList("public_profile","email"))

            LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult>{
                override fun onSuccess(result: LoginResult?) {
                    //유저데이터를 여기서 가져올 수 있음
                    //Log.d("MainActivity", "Facebook token: " + loginResult.accessToken.token)
                    var intent :Intent= Intent(applicationContext, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    finish()
                }

                override fun onCancel() {

                }

                override fun onError(error: FacebookException?) {
                   Toast.makeText(applicationContext,"로그인에 실패했습니다",Toast.LENGTH_SHORT).show()
                }

            })
        }
    }
    //로그인 결과를 callbackManager를 통해 로그인 매니저에 전달함
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }



}