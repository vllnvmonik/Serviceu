package com.example.serviceu.classes

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

    //
    fun saveSessionToken(token: String?) {
        val editor = sharedPreferences.edit()
        editor.putString("sessionToken", token)
        editor.apply()
    }

    fun getSessionToken(): String? {
        return sharedPreferences.getString("sessionToken", null)
    }

    fun saveUserId(userId: Int){
        val editor = sharedPreferences.edit()
        editor.putInt("userId", userId)
        editor.apply()
    }

    fun getUserId(): Int{
        return sharedPreferences.getInt("userId", -1)
    }
}
