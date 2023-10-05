package com.example.serviceu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ServicesAdapterClass(private val servicesList: ArrayList<ServicesClass>): RecyclerView.Adapter<ServicesAdapterClass.ViewHolderClass>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.services_layout, parent, false)
        return ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
        return servicesList.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = servicesList[position]
        holder.rvImageHolder.setImageResource(currentItem.serviceImage)
        holder.rvTextViewHolder.text = currentItem.serviceTitle
    }


    class ViewHolderClass (itemView: View): RecyclerView.ViewHolder(itemView){
        val rvImageHolder: ImageView = itemView.findViewById(R.id.ivServiceImage)
        val rvTextViewHolder: TextView = itemView.findViewById(R.id.tvServiceTitle)
    }
}