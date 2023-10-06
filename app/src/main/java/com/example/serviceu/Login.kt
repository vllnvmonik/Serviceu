package com.example.serviceu

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView

class Login : AppCompatActivity() {

    private lateinit var backButton: ImageView
    private lateinit var loginBtn: Button
    private lateinit var email: EditText
    private lateinit var password: EditText


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        backButton = findViewById(R.id.back_button)
        loginBtn = findViewById(R.id.loginBtn)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)


        backButton.setOnClickListener{
            val intent = Intent(this, LoginSignUp::class.java)
            startActivity(intent)
        }

        loginBtn.setOnClickListener {

            val emailAddress = email.text.toString().trim()
            val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-zA-Z]+\\.+[a-zA-Z]+")

            if (emailAddress.isEmpty() || !emailAddress.matches(emailPattern)) {
                email.error = "Invalid Email Address"
                email.visibility = View.VISIBLE
                return@setOnClickListener
            }else {
                email.error = null
                val intent = Intent(this, Services::class.java)
                startActivity(intent)
            }
        }
    }


}
