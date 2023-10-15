package com.example.serviceu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class sp_profileAdapter(
    private val getActivity: Sp_profile,
    private val profilelists: MutableList<Sp_profile_holder>
) :
    RecyclerView.Adapter<sp_profileAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val  view = LayoutInflater.from(parent.context).inflate(R.layout.spprof_recyclerview_layout,parent, false)
        return MyViewHolder(view)
    }
    override fun getItemCount(): Int {
        return profilelists.size
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = profilelists[position].name
//        holder.profilepic.setImageResource( profilelists[position].img1)
        holder.category.text = profilelists[position].category
        holder.contact.text = profilelists[position].contact

        holder.cardview.setOnClickListener{
            Toast.makeText(getActivity, profilelists[position].name, Toast.LENGTH_LONG).show()
        }
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val profilepic : ImageView = itemView.findViewById(R.id.img_profilepic)
        val name : TextView = itemView.findViewById(R.id.tv_spname)
        val category: TextView = itemView.findViewById(R.id.tv_sp_category)
        val contact : TextView = itemView.findViewById(R.id.tv_sp_number)
        val cardview : CardView = itemView.findViewById(R.id.cardview)
    }
}