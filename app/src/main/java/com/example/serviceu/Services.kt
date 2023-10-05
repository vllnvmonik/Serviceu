package com.example.serviceu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Services : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var servicesList: ArrayList<ServicesClass>
    private lateinit var imageList: Array <Int>
    private lateinit var titleList: Array <String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_services)
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

        servicesList = arrayListOf<ServicesClass>()
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