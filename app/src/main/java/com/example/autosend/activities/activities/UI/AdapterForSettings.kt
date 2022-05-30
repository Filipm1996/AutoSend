package com.example.autosend.activities.activities.UI

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.autosend.R
import com.example.autosend.activities.activities.db.entities.UserTimeTreatment


class AdapterForSettings : RecyclerView.Adapter<AdapterForSettings.ViewHolder>() {
    var listForToday = listOf<UserTimeTreatment>()
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val name : TextView = itemView.findViewById(R.id.nameToSend)
        val beautyTreatment : TextView = itemView.findViewById(R.id.beautyTreatment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.content_recyclerview_settings ,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userTimeTreatment = listForToday[position]
        holder.name.text = userTimeTreatment.name
        holder.beautyTreatment.text = userTimeTreatment.beautyTreatmentName
        }

    override fun getItemCount(): Int {
        return listForToday.size
    }

    fun updateList(list: List<UserTimeTreatment>){
        this.listForToday = list
    }
}