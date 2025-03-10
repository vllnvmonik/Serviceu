package com.example.serviceu

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceu.classes.FetchData
import com.example.serviceu.classes.ServiceProviderBookingClass
import com.example.serviceu.classes.ServiceProviderBookingsAdapter

class BookingsServiceProvider : AppCompatActivity() {

    private lateinit var backButton: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var serviceBookingsAdapter: ServiceProviderBookingsAdapter
    private var serviceBookingsList = ArrayList<ServiceProviderBookingClass>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookings_service_provider)


        recyclerView = findViewById(R.id.rv_spbookings)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        backButton = findViewById(R.id.back_button)

        backButton.setOnClickListener {
            val intent = Intent(this, ServiceProviderInterface::class.java)
            startActivity(intent)
            finish()
        }

        serviceBookingsAdapter = ServiceProviderBookingsAdapter(this, serviceBookingsList)

        recyclerView.adapter = serviceBookingsAdapter

        FetchData.fetchAndDisplayServiceProviderBookingsData(this, serviceBookingsAdapter, serviceBookingsList)
    }

}