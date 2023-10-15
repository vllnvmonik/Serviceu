package com.example.serviceu
import FetchData
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Sp_profile : AppCompatActivity() {
    private lateinit var selectedCategory: SelectedCategory
    private var recyclerView: RecyclerView? = null
    private var spProfileAdapter: sp_profileAdapter? = null
    private var profileList = ArrayList<Sp_profile_holder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sp_profile)

        selectedCategory = SelectedCategory(intent.getStringExtra("category") ?: "Null")

        recyclerView = findViewById(R.id.rvprofileList)
        spProfileAdapter = sp_profileAdapter(this, profileList)
        recyclerView?.layoutManager = GridLayoutManager(this, 1)
        recyclerView?.adapter = spProfileAdapter

        val currentCategory = selectedCategory.selectedCategory
        FetchData.fetchAndDisplayData(this, spProfileAdapter, profileList, currentCategory)

    }

}
