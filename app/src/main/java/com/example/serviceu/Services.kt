package com.example.serviceu

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceu.classes.RVClick
import com.example.serviceu.classes.SelectedCategory
import com.example.serviceu.classes.ServicesAdapterClass
import com.example.serviceu.classes.ServicesClass
import com.example.serviceu.classes.SharedPreferenceClass
import com.google.android.material.bottomnavigation.BottomNavigationView

class Services : AppCompatActivity(), RVClick {
    private lateinit var recyclerView: RecyclerView
    private lateinit var servicesList: ArrayList<ServicesClass>
    private lateinit var imageList: Array <Int>
    private lateinit var titleList: Array <String>
    private lateinit var bottomNav: BottomNavigationView

    private lateinit var selectedCategory: SelectedCategory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_services)
        val sharedPreferenceHelper = SharedPreferenceClass(this)

        selectedCategory = SelectedCategory("Null")

        bottomNav = findViewById(R.id.navmenu)
        bottomNav.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.home -> {
                    val intent = Intent(this, Services::class.java)
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


        imageList = arrayOf(
            R.drawable.laundry,
            R.drawable.electrician,
            R.drawable.cleaning,
            R.drawable.pestcontrol,
            R.drawable.plumber,
            R.drawable.makeupartist,
            R.drawable.hairstylist,
            R.drawable.mechanic,
            R.drawable.manipedi)

        titleList = arrayOf(
            "Laundry",
            "Electrician",
            "Cleaning",
            "Pest Control",
            "Plumber",
            "MakeUp Artist",
            "Hairstylist",
            "Mechanic",
            "Manicure/Pedicure"
        )

        recyclerView = findViewById(R.id.rv_services)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        servicesList = arrayListOf()
        getData()

    }


    private fun getData(){
        for ( i in imageList.indices){
            val servicesClass = ServicesClass(imageList[i], titleList[i])
            servicesList.add(servicesClass)
        }
        recyclerView.adapter = ServicesAdapterClass(servicesList, this)

    }

    override fun rvCLick(position: Int) {
        val category: String = when (position) {
            0 -> "Laundry"
            1 -> "Electrician"
            2 -> "Cleaning"
            3 -> "Pest Control"
            4 -> "Plumber"
            5 -> "Makeup Artist"
            6 -> "Hairstylist"
            7 -> "Mechanic"
            8 -> "Manicure/Pedicure"
            else -> "Null"
        }
        selectedCategory.selectedCategory = category
        val intent = Intent(this, ServiceProviderProfile::class.java)
        intent.putExtra("category", category)
        startActivity(intent)
        finish()
    }

}

