package com.example.serviceu

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
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
                when (sharedPreferenceHelper.getUserRole()) {
                    "Customer" -> {
                        startActivity(Intent(this, Services::class.java))
                        finish()
                    }
                    "Service Provider" -> {
                        startActivity(Intent(this, ServiceProviderInterface::class.java))
                        finish()
                    }
                    else -> {
                        Toast.makeText(this,"No role", Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                startActivity(Intent(this,LoginSignUp::class.java))
                finish()
            }
        },2000)
    }

}