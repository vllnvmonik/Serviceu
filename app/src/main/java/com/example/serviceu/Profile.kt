package com.example.serviceu

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.serviceu.classes.SharedPreferenceClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class Profile : AppCompatActivity() {
    private lateinit var logoutBtn: Button
    private lateinit var backButton: ImageView
    private lateinit var userFullname: TextView
    private lateinit var userEmail: TextView
    private lateinit var userPhone: TextView
    private lateinit var userAddress: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        logoutBtn = findViewById(R.id.logout_btn)
        backButton = findViewById(R.id.back_button)
        userFullname = findViewById(R.id.userFullname)
        userEmail = findViewById(R.id.userEmail)
        userPhone = findViewById(R.id.userPhone)
        userAddress = findViewById(R.id.userAddress)

        // call the function request
        getUserInfo()

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
        val sharedPreferenceHelper = SharedPreferenceClass(this)

        confirmButton.setOnClickListener {
            if (sharedPreferenceHelper.getLoginStatus()){
                // make false when logged out
                sharedPreferenceHelper.saveLoginStatus(false)
                sharedPreferenceHelper.saveSessionToken(null)
                Toast.makeText(this,"Logged out", Toast.LENGTH_LONG).show()
                val intent = Intent(this, LoginSignUp::class.java)
                this.startActivity(intent)
                finish()
                dialog.dismiss()
            }
        }
        cancelButton.setOnClickListener {
            if (sharedPreferenceHelper.getLoginStatus()) {
                sharedPreferenceHelper.saveLoginStatus(true)
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                dialog.dismiss()
            }
        }
        dialog.show()
    }


    private fun getUserInfo() {
        // Retrieve the session token using SharedPreferenceClass
        val sharedPreferenceHelper = SharedPreferenceClass(this)
        val sessionToken = sharedPreferenceHelper.getSessionToken()
        Log.d("Profile", "Session Token: $sessionToken")

        if (sessionToken != null) {
            GlobalScope.launch(Dispatchers.IO) {
                //network request to a background thread
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

                        if (userInfo.has("error")) {
                            // handle error returned by userinfo.php
                            val errorMessage = userInfo.getString("error")
                            showToast(errorMessage)
                        } else {
                            // access user information and display in tv
                            val fullname = userInfo.getString("fullname")
                            val email = userInfo.getString("email")
                            val phone = userInfo.getString("phone")
                            val address = userInfo.getString("address")

                            Log.d("Profile", "Full Name: $fullname, Email: $email, Phone: $phone, Address: $address")
                            // update the tv with the fetched user information
                            withContext(Dispatchers.Main) {
                                userFullname.text = fullname
                                userEmail.text = email
                                userPhone.text = phone
                                userAddress.text = address
                            }
                        }
                    } else {
                        // handle HTTP error
                        showToast("Failed to fetch user info. Response code: $responseCode")
                    }

                    // close the connection
                    connection.disconnect()
                } catch (e: Exception) {
                    // handle exception and log the error message
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

}