package com.example.serviceu

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.serviceu.classes.SharedPreferenceClass
import com.vishnusivadas.advanced_httpurlconnection.PutData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.UUID

class Login : AppCompatActivity() {

    private lateinit var backButton: ImageView
    private lateinit var loginBtn: Button
    private lateinit var email: EditText
    private lateinit var password: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        backButton = findViewById(R.id.back_button)
        loginBtn = findViewById(R.id.loginBtn)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)

        val sharedPreferenceHelper = SharedPreferenceClass(this)


        backButton.setOnClickListener{
            val intent = Intent(this, LoginSignUp::class.java)
            startActivity(intent)
            finish()
        }

        loginBtn.setOnClickListener {
            if (logInValidation()) {

                val handler = Handler(Looper.getMainLooper())
                handler.post {

                    // stores the generated token
                    val sessionToken = generateSessionToken()
                    // creating array for parameters
                    val field = arrayOf("email", "password", "token")

                    // creating array for data
                    val data = arrayOf(email.text.toString(),
                        password.text.toString(),
                        sessionToken)

                    // starting Write and Read data with URL
                    val putData = PutData("https://serviceuapp.000webhostapp.com/login.php", "POST", field, data)

                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            val result = putData.result
                            if (result == "Login Success") {
                                clearErrors()
                                // true, logged in user
                                sharedPreferenceHelper.saveLoginStatus(true)
                                // token for checking if user is logged in
                                sharedPreferenceHelper.saveSessionToken(sessionToken)
                                getUserInfo()
                                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, Services::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getUserInfo() {
        // Retrieve the session token using SharedPreferenceClass
        val sharedPreferenceHelper = SharedPreferenceClass(this)
        val sessionToken = sharedPreferenceHelper.getSessionToken()

        if (sessionToken != null) {
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val apiUrl = "https://serviceuapp.000webhostapp.com/userinfo.php?token=$sessionToken"
                    val url = URL(apiUrl)
                    val connection = url.openConnection() as HttpURLConnection
                    connection.requestMethod = "GET"

                    val responseCode = connection.responseCode
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        val reader = BufferedReader(InputStreamReader(connection.inputStream))
                        val response = StringBuilder()
                        var line: String?

                        while (reader.readLine().also { line = it } != null) {
                            response.append(line)
                        }

                        // parse the JSON response
                        val userInfo = JSONObject(response.toString())

                        if (userInfo.has("user_id")) {
                            // retrieve user ID
                            val userId = userInfo.getInt("user_id")

                            sharedPreferenceHelper.saveUserId(userId)
                            Log.d("Profile", "User ID: $userId")

                            withContext(Dispatchers.Main) {
                            }
                        } else {
                            showToast("User ID not found in response")
                        }
                    } else {
                        showToast("Failed to fetch user info. Response code: $responseCode")
                    }

                    connection.disconnect()
                } catch (e: Exception) {
                    e.printStackTrace()
                    showToast("An error occurred: ${e.message}")
                }
            }
        } else {
            showToast("Session token is null")
        }
    }

    private fun showToast(message: String) {
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }


    // this create a random strings
    private fun generateSessionToken(): String {
        val sessionToken = UUID.randomUUID().toString()
        Log.d("SessionToken", "Generated session token: $sessionToken")
        return sessionToken
    }

    private fun clearErrors() {
        email.error = null
        password.error = null
    }
    private fun showError(field: EditText, message : String) {
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
