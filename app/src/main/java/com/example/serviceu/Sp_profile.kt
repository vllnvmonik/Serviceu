package com.example.serviceu
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class Sp_profile : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var spProfileAdapter: sp_profileAdapter? = null
    private var profileList = ArrayList<Sp_profile_holder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sp_profile)

        recyclerView = findViewById(R.id.rvprofileList)
        spProfileAdapter = sp_profileAdapter(this, profileList)
        recyclerView?.layoutManager = GridLayoutManager(this, 1)
        recyclerView?.adapter = spProfileAdapter

        fetchDataAndSetData()
    }

    private fun fetchDataAndSetData() {
        val url = "https://serviceuapp.000webhostapp.com/fetch.php"

        Thread {
            try {
                val urlConnection = URL(url).openConnection() as HttpURLConnection
                val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val response = StringBuilder()

                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }

                runOnUiThread {
                    // Parse and display data in the RecyclerView
                    parseAndDisplayData(response.toString())
                }

                urlConnection.disconnect()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    private fun parseAndDisplayData(response: String) {
        try {
            val jsonArray = JSONArray(response)

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val name = jsonObject.getString("fullname")
                val category = jsonObject.getString("category")
                val contact = jsonObject.getString("phone")

                val profile = Sp_profile_holder(name, category, contact)
                profileList.add(profile)
            }
            spProfileAdapter?.notifyDataSetChanged()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
