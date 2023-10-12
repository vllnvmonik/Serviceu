package com.example.serviceu

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.serviceu.database.DatabaseHelper

class Login : AppCompatActivity() {

    private lateinit var backButton: ImageView
    private lateinit var loginBtn: Button
    private lateinit var email: EditText
    private lateinit var password: EditText

    // database
    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // database
        db = DatabaseHelper(this)

        backButton = findViewById(R.id.back_button)
        loginBtn = findViewById(R.id.loginBtn)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)


        backButton.setOnClickListener{
            val intent = Intent(this, LoginSignUp::class.java)
            startActivity(intent)
        }

        loginBtn.setOnClickListener {
            if (logInValidation()) {

                if (checkEmail(email = email.text.toString())) {
                    val isSuccess = db.loginAccount(email = email.text.toString(), password = password.text.toString())
                    // checks if email and pass matches
                    if (isSuccess) {
                        clearErrors()
                        password.text.clear()
                        Toast.makeText(this, "Logged in", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, Services::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // checks if email and pass did not match
                        password.text.clear()
                        Toast.makeText(this, "Email and Password did not match", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // checks if the email is in the database
                    password.text.clear()
                    Toast.makeText(this, "Email does not exist", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    //email checker from database
    private fun checkEmail(email: String): Boolean {
        return db.checkEmail(email)
    }

    private fun clearErrors() {
        email.error = null
        password.error = null
    }
    private fun showError(field: EditText, message: String) {
        field.error = message
        field.visibility = View.VISIBLE
    }

    private fun logInValidation() : Boolean {
        val emailAddress = email.text.toString().trim()
        val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-zA-Z]+\\.+[a-zA-Z]+")

        try {
            if (emailAddress.isEmpty() || !emailAddress.matches(emailPattern)) {
                showError(email, "Invalid Email Address")
                email.visibility = View.VISIBLE
                return false
            }
        }catch (e:Exception){
            Toast.makeText(this, "An error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
        }
        return true
    }

}
