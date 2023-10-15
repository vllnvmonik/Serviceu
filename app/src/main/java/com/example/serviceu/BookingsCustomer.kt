package com.example.serviceu

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceu.classes.BookingsAdapterClass
import com.example.serviceu.classes.BookingsClass

class BookingsCustomer : AppCompatActivity() {

    private lateinit var backButton: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var bookingsList: ArrayList<BookingsClass>
    private lateinit var imageList: ArrayList<Int>
    private lateinit var titleList: Array<String>
    private lateinit var date: Array<String>
    private lateinit var time: Array<String>
    private lateinit var status: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookings_customer)

        backButton = findViewById(R.id.back_button)
        backButton.setOnClickListener{
            val intent = Intent(this, Services::class.java)
            startActivity(intent)
            finish()
        }


        // recycler view
        imageList = arrayListOf(
            R.drawable.laundry
        )
        titleList = arrayOf(
            "Laundry",
            "Electrician",
            "Cleaning")

        date = arrayOf("Jan 23, 2022")
        time = arrayOf("12:00")
        status = arrayOf("PENDING")

        recyclerView = findViewById(R.id.rv_bookings)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        bookingsList = arrayListOf()

        getData()
    }
    private fun getData(){
        for ( i in imageList.indices){
            val bookingsClass = BookingsClass(imageList[i], titleList[i], date[i], time[i], status[i])
            bookingsList.add(bookingsClass)
        }
        recyclerView.adapter = BookingsAdapterClass(bookingsList)

    }

}