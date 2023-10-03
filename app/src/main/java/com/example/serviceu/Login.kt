package com.example.serviceu

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class Login : AppCompatActivity() {

    private lateinit var imageView: ImageView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        imageView = findViewById(R.id.back_button)

        imageView.setOnClickListener{
            val intent = Intent(this, first_page::class.java)
            startActivity(intent)
        }

        }


    }
