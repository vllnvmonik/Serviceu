package com.example.serviceu.classes

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceu.R
import com.vishnusivadas.advanced_httpurlconnection.PutData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ServiceProviderBookingsAdapter (
    private val context: Context,
    private val serviceBookingsList: ArrayList<ServiceProviderBookingClass>,
): RecyclerView.Adapter<ServiceProviderBookingsAdapter.ViewHolderClass>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.service_provider_bookings_layout, parent, false)
        return ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
        return serviceBookingsList.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = serviceBookingsList[position]
        holder.rvImageHolder.setImageResource(currentItem.bookingsImg)
        holder.rvTitleHolder.text = currentItem.customerName
        holder.rvServiceHolder.text = currentItem.bookedService
        holder.rvDateHolder.text = currentItem.date
        holder.rvTimeHolder.text = currentItem.time
        holder.rvBookIdHolder.text = currentItem.bookId.toString()

        holder.declineButton.tag = position
        holder.acceptButton.tag = position

        holder.declineButton.setOnClickListener {
            updateBookingStatus(position, "Rejected")
            Log.d("Decline Button", "clicked $position")

        }
        holder.acceptButton.setOnClickListener {
            updateBookingStatus(position, "Accepted")
            Log.d("Accept Button", "clicked $position")
        }
    }
    private fun updateBookingStatus(position: Int, status: String) {
        val currentItem = serviceBookingsList[position]
        val bookId = currentItem.bookId
        CoroutineScope(Dispatchers.IO).launch {
            val sharedPreferenceHelper = SharedPreferenceClass(context)

            val field = arrayOf("bookId", "status")
            val data = arrayOf(
                bookId.toString(),
                status
            )

            val putData = PutData("https://serviceuapp.000webhostapp.com/updateStatus.php", "POST", field, data)
            if (putData.startPut()) {
                if (putData.onComplete()) {
                    val result = putData.result
                    withContext(Dispatchers.Main) {
                        if (result == "Status Updated Successfully") {
                            sharedPreferenceHelper.saveBookId(bookId)
                            Toast.makeText(context, "Booking $status", Toast.LENGTH_SHORT).show()
                            // remove the item from the list and update the rv
                            serviceBookingsList.removeAt(position)
                            notifyItemRemoved(position)
                            notifyItemRangeChanged(position, serviceBookingsList.size)
                        } else {
                            Toast.makeText(context, "Status Update Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    class ViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rvImageHolder: ImageView = itemView.findViewById(R.id.img_bookings)
        val rvTitleHolder: TextView = itemView.findViewById(R.id.tv_customer_name)
        val rvServiceHolder: TextView = itemView.findViewById(R.id.tv_service_booked)
        val rvDateHolder: TextView = itemView.findViewById(R.id.tv_date)
        val rvTimeHolder: TextView = itemView.findViewById(R.id.tv_time)
        val rvBookIdHolder: TextView = itemView.findViewById(R.id.tv_book_id)

        val declineButton: Button = itemView.findViewById(R.id.bt_decline)
        val acceptButton: Button = itemView.findViewById(R.id.bt_accept)

    }

    fun setData(newData: List<ServiceProviderBookingClass>) {
        serviceBookingsList.clear()
        serviceBookingsList.addAll(newData)
        notifyDataSetChanged()
    }

}
