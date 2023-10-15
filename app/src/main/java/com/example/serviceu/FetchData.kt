import android.app.Activity
import android.content.Context
import com.example.serviceu.Sp_profile_holder
import com.example.serviceu.sp_profileAdapter
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class FetchData {

    companion object {
        fun fetchAndDisplayData(
            context: Context,
            adapter: sp_profileAdapter?,
            profileList: ArrayList<Sp_profile_holder>,
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
                        // Parse and display data in the RecyclerView based on the selected category
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
            profileList: ArrayList<Sp_profile_holder>,
            selectedCategory: String
        ): List<Sp_profile_holder> {
            val filteredData = ArrayList<Sp_profile_holder>()

            try {
                val jsonArray = JSONArray(response)

                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val name = jsonObject.getString("fullname")
                    val category = jsonObject.getString("category")
                    val contact = jsonObject.getString("phone")

                    if (category == selectedCategory) {
                        val profile = Sp_profile_holder(name, category, contact)
                        filteredData.add(profile)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return filteredData
        }
    }
}
