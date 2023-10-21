package com.example.serviceu

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.serviceu.classes.SharedPreferenceClass
import com.google.android.material.bottomnavigation.BottomNavigationView

class ServiceProviderInterface : AppCompatActivity() {
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var sharedPreferenceHelper:SharedPreferenceClass
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_provider_interface)

        sharedPreferenceHelper = SharedPreferenceClass(this)


        bottomNav = findViewById(R.id.navmenu)
        bottomNav.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.home -> {
                    val intent = Intent(this, ServiceProviderInterface::class.java)
                    startActivity(intent)
                    finish() }
                R.id.bookings -> {
                    val currentRole = sharedPreferenceHelper.getUserRole()
                    try {
                        if (currentRole == "Customer") {
                            val intent = Intent(this, BookingsCustomer::class.java)
                            startActivity(intent)
                            finish()
                        }
                        if (currentRole == "Service Provider") {
                            val intent = Intent(this, BookingsServiceProvider::class.java)
                            startActivity(intent)
                            finish()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                        e.printStackTrace()
                    }

                }
                R.id.profile -> {
                    val intent = Intent(this, Profile::class.java)
                    startActivity(intent)
                    finish() }
            }
            true
        }
    }
}