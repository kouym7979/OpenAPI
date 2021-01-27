package com.example.openapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {

    val SPLASH_VEIW_TIME:Long =2000 //2초간 스플래시 화면을 보여줌

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({//delay를 위한 핸들러러
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        },SPLASH_VEIW_TIME)
    }
}