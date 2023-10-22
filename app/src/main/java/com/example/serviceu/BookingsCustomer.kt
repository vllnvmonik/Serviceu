package com.example.serviceu

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceu.classes.BookingsAdapterClass
import com.example.serviceu.classes.BookingsClass
import com.example.serviceu.classes.FetchData

class BookingsCustomer : AppCompatActivity() {

    private lateinit var backButton: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var bookingsAdapter: BookingsAdapterClass
    private var bookingsList = ArrayList<BookingsClass>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookings_customer)

        recyclerView = findViewById(R.id.rv_bookings)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        backButton = findViewById(R.id.back_button)

        backButton.setOnClickListener {
            val intent = Intent(this, Services::class.java)
            startActivity(intent)
            finish()
        }

        bookingsAdapter = BookingsAdapterClass(bookingsList)

        recyclerView.adapter = bookingsAdapter

        FetchData.fetchAndDisplayBookingsData(this, bookingsAdapter, bookingsList)
    }

}