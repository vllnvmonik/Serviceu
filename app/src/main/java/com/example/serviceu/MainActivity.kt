package com.example.serviceu

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.serviceu.classes.SharedPreferenceClass

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferenceHelper = SharedPreferenceClass(this)

        Handler().postDelayed({
            if (sharedPreferenceHelper.getLoginStatus()){
                sharedPreferenceHelper.saveLoginStatus(true)
                startActivity(Intent(this, Services::class.java))
                finish()
            }else{
                startActivity(Intent(this,LoginSignUp::class.java))
                finish()
            }
        },2000)
    }

}