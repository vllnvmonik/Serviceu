package com.example.serviceu
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceu.classes.FetchData
import com.example.serviceu.classes.SelectedCategory
import com.example.serviceu.classes.ServiceProviderProfileAdapter
import com.example.serviceu.classes.ServiceProviderProfileHolder

class ServiceProviderProfile : AppCompatActivity() {
    private lateinit var selectedCategory: SelectedCategory
    private lateinit var backButton:ImageView
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
        backButton = findViewById(R.id.back_button)


        backButton.setOnClickListener {
            val intent = Intent(this, Services::class.java)
            startActivity(intent)
            finish()
        }

        val currentCategory = selectedCategory.selectedCategory
        FetchData.fetchAndDisplayData(this, spProfileAdapter, profileList, currentCategory)

//        if (currentCategory == "null"){
//            Toast.makeText(this, "No detail", Toast.LENGTH_LONG).show()
//        }

    }

}
