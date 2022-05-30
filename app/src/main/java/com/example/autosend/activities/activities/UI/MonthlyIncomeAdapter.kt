package com.example.autosend.activities.activities.UI

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.autosend.R
import com.example.autosend.activities.activities.db.entities.MonthlyIncomeInfo
import org.w3c.dom.Text

class MonthlyIncomeAdapter : RecyclerView.Adapter<MonthlyIncomeAdapter.ViewHolder>() {

    private var listOfMonthlyIncome = listOf<MonthlyIncomeInfo>()
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var month : TextView = itemView.findViewById(R.id.month)
        var cost : TextView = itemView.findViewById(R.id.cost)
        var proceeds : TextView = itemView.findViewById(R.id.proceeds)
        var revenue : TextView = itemView.findViewById(R.id.revenue)
        var time : TextView = itemView.findViewById(R.id.time)
        var perHour : TextView = itemView.findViewById(R.id.perHour)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.content_recycler_view_monthly_incom ,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val monthIncomeInfo = listOfMonthlyIncome[position]
        val perHourFormatted = String.format("%.2f",monthIncomeInfo.perHour)
        holder.month.text = monthIncomeInfo.month
        holder.cost.text = "Koszt : ${monthIncomeInfo.cost} zł"
        holder.proceeds.text = "Dochód : ${monthIncomeInfo.proceeds} zł"
        holder.revenue.text = "Przychód : ${monthIncomeInfo.revenue} zł"
        val hour : Int = (monthIncomeInfo.time/60).toInt()
        val minutes  : Int = (monthIncomeInfo.time % 60).toInt()
        holder.time.text = "Czas : $hour h $minutes min"
        holder.perHour.text = "Na godzine : $perHourFormatted zł/h"
    }

    override fun getItemCount(): Int {
        return listOfMonthlyIncome.size
    }

    fun updateList(list : List<MonthlyIncomeInfo>){
        this.listOfMonthlyIncome = list
        notifyDataSetChanged()
    }
}