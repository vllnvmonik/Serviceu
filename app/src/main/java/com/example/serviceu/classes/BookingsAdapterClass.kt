package com.example.serviceu.classes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceu.R

class BookingsAdapterClass (
    private val bookingsList: ArrayList<BookingsClass>
): RecyclerView.Adapter<BookingsAdapterClass.ViewHolderClass>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.bookings_layout, parent, false)
        return ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
        return bookingsList.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = bookingsList[position]
        holder.rvImageHolder.setImageResource(currentItem.bookingsImg)
        holder.rvProviderHolder.text = currentItem.providerName
        holder.rvTitleHolder.text = currentItem.serviceTitle
        holder.rvDateHolder.text = currentItem.date
        holder.rvTimeHolder.text = currentItem.time
        holder.rvStatusHolder.text = currentItem.status
    }


    class ViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rvImageHolder: ImageView = itemView.findViewById(R.id.img_bookings)
        val rvProviderHolder: TextView = itemView.findViewById(R.id.tv_service_provider_name)
        val rvTitleHolder: TextView = itemView.findViewById(R.id.tv_service_booked)
        val rvDateHolder: TextView = itemView.findViewById(R.id.tv_date)
        val rvTimeHolder: TextView = itemView.findViewById(R.id.tv_time)
        val rvStatusHolder: TextView = itemView.findViewById(R.id.booking_status)
    }

    fun setData(newData: List<BookingsClass>) {
        bookingsList.clear()
        bookingsList.addAll(newData)
        notifyDataSetChanged()
    }
}
