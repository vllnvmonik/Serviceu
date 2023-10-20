package com.example.serviceu

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
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
    private lateinit var serviceBooked: TextView
    private lateinit var dateBooked: TextView
    private lateinit var timeBooked: TextView

    @SuppressLint("MissingInflatedId")
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
            R.drawable.logo
        )
        titleList = arrayOf(
            "Laundry",
            "Electrician",
            "Cleaning")
        date = arrayOf("Oct 1, 2023")
        time = arrayOf("10:00 AM")
        status = arrayOf("pending")



        recyclerView = findViewById(R.id.rv_bookings)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        bookingsList = arrayListOf()

//        getBookDetails()

        getData()
    }
    fun getBookDetails(){
        val providerService = intent.getStringExtra("providerService")
        val selectedDate = intent.getStringExtra("selectedDate")
        val selectedTime = intent.getStringExtra("selectedTime")

        serviceBooked = findViewById(R.id.type_of_service_booked)
        dateBooked = findViewById(R.id.tv_date)
        timeBooked = findViewById(R.id.tv_time)

        serviceBooked.text = providerService
        dateBooked.text = selectedDate
        timeBooked.text = selectedTime
    }
    private fun getData(){
        for ( i in imageList.indices){
            val bookingsClass = BookingsClass(imageList[i], titleList[i], date[i], time[i], status[i])
            bookingsList.add(bookingsClass)
        }
        recyclerView.adapter = BookingsAdapterClass(bookingsList)

    }

}