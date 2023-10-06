package com.example.serviceu

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Profile : AppCompatActivity() {
    private lateinit var logoutBtn: Button
    private lateinit var backButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        logoutBtn = findViewById(R.id.logout_btn)
        backButton = findViewById(R.id.back_button)

        backButton.setOnClickListener {
            val intent = Intent(this, Services::class.java)
            startActivity(intent)
        }

        logoutBtn.setOnClickListener {
            logOutDialog()
        }
    }

    private fun logOutDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.logout_dialogue_box)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val confirmButton = dialog.findViewById(R.id.confirm_button) as Button
        val cancelButton = dialog.findViewById(R.id.cancel_button) as Button

        confirmButton.setOnClickListener {
            Toast.makeText(this,"Logged out", Toast.LENGTH_LONG).show()
            val intent = Intent(this, LoginSignUp::class.java)
            this.startActivity(intent)
        }

        cancelButton.setOnClickListener {
            Toast.makeText(this,"Cancelled", Toast.LENGTH_LONG).show()
            dialog.dismiss()
        }
        dialog.show()
    }

}