package com.example.serviceu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginSignUp : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var signUpButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_page)

        loginButton = findViewById(R.id.bt_login)
        signUpButton = findViewById(R.id.bt_sign_up)


        loginButton.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
        signUpButton.setOnClickListener {
//            val intent = Intent(this, address::class.java)
//            startActivity(intent)
        }
    }
}
