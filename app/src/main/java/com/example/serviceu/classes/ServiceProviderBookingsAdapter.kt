package com.example.serviceu.classes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceu.R

class ServiceProviderBookingsAdapter (
    private val serviceBookingsList: ArrayList<ServiceProviderBookingClass>
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

    }

    class ViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rvImageHolder: ImageView = itemView.findViewById(R.id.img_bookings)
        val rvTitleHolder: TextView = itemView.findViewById(R.id.tv_customer_name)
        val rvServiceHolder: TextView = itemView.findViewById(R.id.tv_service_booked)
        val rvDateHolder: TextView = itemView.findViewById(R.id.tv_date)
        val rvTimeHolder: TextView = itemView.findViewById(R.id.tv_time)

    }

    fun setData(newData: List<ServiceProviderBookingClass>) {
        serviceBookingsList.clear()
        serviceBookingsList.addAll(newData)
        notifyDataSetChanged()
    }
}
