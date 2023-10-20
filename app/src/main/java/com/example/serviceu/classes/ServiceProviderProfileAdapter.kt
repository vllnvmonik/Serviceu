package com.example.serviceu.classes

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceu.Book
import com.example.serviceu.R
import com.example.serviceu.ServiceProviderProfile


class ServiceProviderProfileAdapter(
    private val getActivity: ServiceProviderProfile,
    private val profilelists: MutableList<ServiceProviderProfileHolder>,
): RecyclerView.Adapter<ServiceProviderProfileAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MyViewHolder {
        val  view = LayoutInflater.from(parent.context).inflate(R.layout.spprof_recyclerview_layout,parent, false)
        return MyViewHolder(view)
    }
    override fun getItemCount(): Int {
        return profilelists.size
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = profilelists[position]
        holder.name.text = currentItem.name
//        holder.profilepic.setImageResource( profilelists[position].img1)
        holder.category.text = currentItem.category
        holder.contact.text = currentItem.contact

        holder.cardview.setOnClickListener{
            val name = profilelists[position].name
            val serviceCategory = profilelists[position].category


            Toast.makeText(getActivity, profilelists[position].name, Toast.LENGTH_LONG).show()
            val intent = Intent(getActivity, Book::class.java)
            intent.putExtra("providerName", name)
            intent.putExtra("providerService", serviceCategory)
            getActivity.startActivity(intent)
        }
    }



    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val profilepic : ImageView = itemView.findViewById(R.id.img_profilepic)
        val name : TextView = itemView.findViewById(R.id.tv_spname)
        val category: TextView = itemView.findViewById(R.id.tv_sp_category)
        val contact : TextView = itemView.findViewById(R.id.tv_sp_number)
        val cardview : CardView = itemView.findViewById(R.id.cardview)
    }


    fun setData(newData: List<ServiceProviderProfileHolder>) {
        profilelists.clear()
        profilelists.addAll(newData)
        notifyDataSetChanged()
    }
}
