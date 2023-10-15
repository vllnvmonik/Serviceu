package com.example.serviceu
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceu.classes.FetchData
import com.example.serviceu.classes.SelectedCategory
import com.example.serviceu.classes.ServiceProviderProfileHolder
import com.example.serviceu.classes.ServiceProviderProfileAdapter

class ServiceProviderProfile : AppCompatActivity() {
    private lateinit var selectedCategory: SelectedCategory
    private var recyclerView: RecyclerView? = null
    private var spProfileAdapter: ServiceProviderProfileAdapter? = null
    private var profileList = ArrayList<ServiceProviderProfileHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sp_profile)

        selectedCategory = SelectedCategory(intent.getStringExtra("category") ?: "Null")

        recyclerView = findViewById(R.id.rvprofileList)
        spProfileAdapter = ServiceProviderProfileAdapter(this, profileList)
        recyclerView?.layoutManager = GridLayoutManager(this, 1)
        recyclerView?.adapter = spProfileAdapter

        val currentCategory = selectedCategory.selectedCategory
        FetchData.fetchAndDisplayData(this, spProfileAdapter, profileList, currentCategory)

    }

}
