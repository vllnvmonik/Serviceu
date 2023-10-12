package com.example.serviceu

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Sp_profile : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var spProfileadapter: sp_profileAdapter? = null
    private var profileList = mutableListOf<Sp_profile_holder>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sp_profile)

        profileList = ArrayList()

        recyclerView = findViewById<View>(R.id.rvprofileList) as RecyclerView
        spProfileadapter = sp_profileAdapter(this@Sp_profile, profileList)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 1)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.adapter = spProfileadapter

        prepareProfileListData()


    }

    private fun prepareProfileListData() {
        var profile = Sp_profile_holder("MONICA", R.drawable.baseline_person_outline_24,"095652332")
        profileList.add(profile)
        profile = Sp_profile_holder("glaiza maylan",R.drawable.baseline_person_outline_24,"099129391")
        profileList.add(profile)
        profile = Sp_profile_holder("monica dikoalam",R.drawable.baseline_person_outline_24,"4124142e")
        profileList.add(profile)
        profile = Sp_profile_holder("vinna diko alam",R.drawable.baseline_person_outline_24,"3267487238")
        profileList.add(profile)
        profile = Sp_profile_holder("sige diko alam",R.drawable.baseline_person_outline_24,"123127372")
        profileList.add(profile)
        profile = Sp_profile_holder("talaga",R.drawable.baseline_person_outline_24,"124124124")
        profileList.add(profile)
    }
}