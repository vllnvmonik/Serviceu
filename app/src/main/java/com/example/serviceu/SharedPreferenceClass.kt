package com.example.serviceu

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceClass (private val context: Context){
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    fun saveLoginStatus(isLoggedIn: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", isLoggedIn)
        editor.apply()
    }

    fun getLoginStatus(): Boolean {
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }
}
