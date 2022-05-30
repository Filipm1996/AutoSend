package com.example.autosend.activities.activities.UI

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.autosend.R

class AdapterForHours: RecyclerView.Adapter<AdapterForHours.ViewHolder1>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder1 {
        return ViewHolder1(LayoutInflater.from(parent.context).inflate(R.layout.calendar_recycler_hours_content ,parent,false))
    }

    override fun getItemCount(): Int {
        return 16
    }

    class ViewHolder1(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val timeText : TextView = itemView.findViewById(R.id.textTime)
    }

    override fun onBindViewHolder(holder: ViewHolder1, position: Int) {
        val hourNow = position + 7
        holder.timeText.text = "$hourNow"
    }
}