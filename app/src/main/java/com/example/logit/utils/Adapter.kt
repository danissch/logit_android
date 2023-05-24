package com.example.logit.utils

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.logit.R
import com.example.logit.views.CardInfo
import com.example.logit.views.UpdateCard

class Adapter(var data: List<CardInfo>): RecyclerView.Adapter<Adapter.viewHolder>() {

    class viewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var layout: LinearLayout = itemView.findViewById(R.id.mylayout)
        var title: TextView = itemView.findViewById(R.id.title)
        var description:TextView = itemView.findViewById(R.id.description)
        var status: TextView = itemView.findViewById(R.id.status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.view, parent, false)
        return viewHolder(itemView)

    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        when (data[position].status){
            "Abierto" -> holder.layout.setBackgroundColor(Color.parseColor("#F05454"))
            "Completado" -> holder.layout.setBackgroundColor(Color.parseColor("#0ED158")) //0ED158, EDC988
            else -> holder.layout.setBackgroundColor(Color.parseColor("#00917C"))
        }

        holder.title.text = data[position].title
        holder.description.text = data[position].description
        holder.status.text = data[position].status

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateCard::class.java)
            intent.putExtra("id", position)
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }
}