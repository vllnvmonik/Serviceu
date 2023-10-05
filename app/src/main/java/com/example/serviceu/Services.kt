package com.example.serviceu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class Services : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var servicesList: ArrayList<ServicesClass>
    private lateinit var imageList: Array <Int>
    private lateinit var titleList: Array <String>
    private lateinit var BottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_services)

        BottomNavigationView = findViewById(R.id.navmenu)
        BottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> {
                    val intent = Intent(this, Services::class.java)
                    startActivity(intent)
                    finish()}
//                R.id.bookings -> {
//                    val intent = Intent(this, Bookings::class.java)
//                    startActivity(intent)
//                    finish()}
                R.id.profile->{
                    val intent = Intent(this, Profile::class.java)
                    startActivity(intent)
                    finish()}
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
        recyclerView.adapter = ServicesAdapterClass(servicesList)
    }
}