package com.example.serviceu.classes

import android.app.Activity
import android.content.Context
import com.example.serviceu.R
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class FetchData {
    companion object {

        fun fetchAndDisplayData(
            context: Context,
            adapter: ServiceProviderProfileAdapter?,
            profileList: ArrayList<ServiceProviderProfileHolder>,
            selectedCategory: String
        ) {
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

                    (context as Activity).runOnUiThread {
                        val filteredData = parseAndFilterData(response.toString(), profileList, selectedCategory)
                        adapter?.setData(filteredData)
                    }

                    urlConnection.disconnect()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }.start()
        }

        private fun parseAndFilterData(
            response: String,
            profileList: ArrayList<ServiceProviderProfileHolder>,
            selectedCategory: String
        ): List<ServiceProviderProfileHolder> {
            val filteredData = ArrayList<ServiceProviderProfileHolder>()
            try {
                val jsonArray = JSONArray(response)

                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val name = jsonObject.getString("fullname")
                    val category = jsonObject.getString("category")
                    val contact = jsonObject.getString("phone")

                    if (category == selectedCategory) {
                        val profile = ServiceProviderProfileHolder(name, category, contact)
                        filteredData.add(profile)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return filteredData
        }

        fun fetchAndDisplayBookingsData(
            context: Context,
            adapter: BookingsAdapterClass?,
            bookingsList: ArrayList<BookingsClass>
        ) {
            val url = "https://serviceuapp.000webhostapp.com/fetchBookingsData.php"
            Thread {
                try {
                    val urlConnection = URL(url).openConnection() as HttpURLConnection
                    val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                    val response = StringBuilder()

                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        response.append(line)
                    }

                    (context as Activity).runOnUiThread {
                        val filteredData = parseAndFilterBookingsData(response.toString())
                        adapter?.setData(filteredData)
                    }
                    urlConnection.disconnect()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }.start()
        }

        private fun parseAndFilterBookingsData(response: String): List<BookingsClass> {
            val filteredData = ArrayList<BookingsClass>()

            try {
                val jsonArray = JSONArray(response)

                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val providerName = jsonObject.getString("provider_name")
                    val serviceName = jsonObject.getString("bookedservice")
                    val date = jsonObject.getString("date")
                    val time = jsonObject.getString("time")
                    val status = jsonObject.getString("status")

                    val booking = BookingsClass(R.drawable.logo1, providerName, serviceName, date,time, status)
                    filteredData.add(booking)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return filteredData
        }
    }
}
