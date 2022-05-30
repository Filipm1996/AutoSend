package com.example.autosend.activities.activities.UI

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.autosend.R
import com.example.autosend.activities.activities.db.entities.UserTimeTreatment
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class AdapterForCalendar : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private var freeDay : Boolean = false
    private var setOnClickPosition : ((UserTimeTreatment?)->Unit)? = null
    private var listOfContent = mutableListOf<UserTimeTreatment>()
    companion object {
        const val viewHolder1_4 = 1
        const val viewHolder1_4v2 = 2
        const val viewHolderFull = 3
        const val viewHolderEmpty = 4
        const val viewHolder1_2 = 5
        const val viewHolder1_2v2 = 6
        const val viewHolder1_2_1 = 7
        const val viewHolder3_4 = 8
        const val viewHolder3_4v2 = 9
        const val viewHolder_free_day = 10
    }
    private var beautyTreatmentForPreviousHour = ""
    private var beautyTreatmentNameForHour = ""
    private var itemForHour : UserTimeTreatment? = null
    private var previousHour = 0
    private var itemForPreviousHour : UserTimeTreatment? = null
    private var previousEndHour : Int? = 0
    private var previousEndMinute : Int?= 0
    private var minuteOfEnd = 0
    private var hourOfEnd = 0
    private var hourNow = 0
    private var hourOfStart = 0
    private var minuteOfStart = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when(viewType){
            viewHolder1_4 -> ViewHolder_1_4(LayoutInflater.from(parent.context).inflate(R.layout.calendar_recycler_content_1_4 ,parent,false))
            viewHolder1_4v2 -> ViewHolder_1_4v2(LayoutInflater.from(parent.context).inflate(R.layout.calendar_recycler_content_1_4v2 ,parent,false))
            viewHolderFull-> ViewHolderFull(LayoutInflater.from(parent.context).inflate(R.layout.calendar_recycler_content_full ,parent,false))
            viewHolderEmpty -> ViewHolderEmpty(LayoutInflater.from(parent.context).inflate(R.layout.calendar_recycler_content_empty ,parent,false))
            viewHolder1_2-> ViewHolder_1_2(LayoutInflater.from(parent.context).inflate(R.layout.calendar_recycler_content_1_2 ,parent,false))
            viewHolder1_2v2 -> ViewHolder_1_2v2(LayoutInflater.from(parent.context).inflate(R.layout.calendar_recycler_content_1_2v2,parent,false))
            viewHolder1_2_1 -> ViewHolder_1_2_1(LayoutInflater.from(parent.context).inflate(R.layout.calendar_recycler_content_1_2_1,parent,false))
            viewHolder3_4 -> ViewHolder_3_4(LayoutInflater.from(parent.context).inflate(R.layout.calendar_recycler_content_3_4 ,parent,false))
            viewHolder3_4v2 -> ViewHolder_3_4v2(LayoutInflater.from(parent.context).inflate(R.layout.calendar_recycler_content_3_4v2 ,parent,false))
            viewHolder_free_day -> ViewHolderForFreeDay(LayoutInflater.from(parent.context).inflate(R.layout.free_day_content ,parent,false))
            else -> ViewHolder_1_4(LayoutInflater.from(parent.context).inflate(R.layout.calendar_recycler_content_1_4 ,parent,false))
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val minuteOfStartString: String = if(minuteOfStart < 10){
            "0$minuteOfStart"
        }else{
            minuteOfStart.toString()
        }
        val minuteOfEndString: String = if (minuteOfEnd < 10) {
            "0${minuteOfEnd}"
        } else {
            minuteOfEnd.toString()
        }
        var minuteOfPreviousEndString =""
        if(previousEndMinute !=null) {
            minuteOfPreviousEndString = if (previousEndMinute!! < 10) {
                "0${previousEndMinute}"
            } else {
                previousEndMinute.toString()
            }
        }
        if(itemForHour!=null) {
            beautyTreatmentNameForHour = if (itemForHour!!.beautyTreatmentName.length < 9) {
                itemForHour!!.beautyTreatmentName
            }else{
                "${itemForHour!!.beautyTreatmentName.substring(0,9)}."
            }
        }
        if(itemForPreviousHour!=null){
            beautyTreatmentForPreviousHour = if(itemForPreviousHour!!.beautyTreatmentName.length < 9) {
                itemForPreviousHour!!.beautyTreatmentName
            } else {
                "${itemForPreviousHour!!.beautyTreatmentName.substring(0,9)}."
            }
        }
        when (holder){
            is ViewHolderForFreeDay ->{
                if(position >0){
                    holder.text.text = ""
                }
            }
            is ViewHolderEmpty-> {
            }
            is ViewHolderFull ->{
                if(itemForHour!!.beautyTreatmentTime.toInt() + minuteOfStart > 60 && previousEndHour != hourNow && minuteOfEnd < 15){
                    holder.startTime.text = "$hourOfStart : $minuteOfStartString"
                    holder.beautyTreatment.text = beautyTreatmentNameForHour
                }else if(previousEndHour == hourNow && minuteOfStart + itemForHour!!.beautyTreatmentTime.toInt() <60){
                    holder.startTime.text = "$previousEndHour : $minuteOfPreviousEndString"
                    holder.beautyTreatment.text = beautyTreatmentNameForHour
                    holder.endTime.text = "$hourOfEnd : $minuteOfEndString"
                }else if( previousEndHour == hourNow && minuteOfStart >30 && minuteOfEnd <15){
                    holder.startTime.text = "$previousEndHour : $minuteOfPreviousEndString"
                    holder.beautyTreatment.text = beautyTreatmentNameForHour
                    holder.endTime.text = "$hourOfStart : $minuteOfStartString"
                }else if(previousEndHour == hourNow && minuteOfStart >30 && minuteOfEnd >15){
                    holder.startTime.text = "$previousEndHour : $minuteOfPreviousEndString"
                    holder.endTime.text = "$hourOfStart : $minuteOfStartString"
                }else if(previousEndHour!= hourNow && itemForHour!!.beautyTreatmentTime.toInt() >60 && minuteOfEnd >15){
                    holder.startTime.text = "$hourOfStart : $minuteOfStartString"
                }else{
                    holder.startTime.text = "$hourOfStart : $minuteOfStartString"
                    holder.beautyTreatment.text = beautyTreatmentNameForHour
                    holder.endTime.text = "$hourOfEnd : $minuteOfEndString"
                }
            }
            is ViewHolder_1_4 ->{
                holder.endTime.text = "$previousEndHour : $minuteOfPreviousEndString"

            }
            is ViewHolder_3_4 -> {
                if ( hourNow == previousEndHour){
                    holder.endTime.text = "$previousEndHour : $minuteOfPreviousEndString"
                    holder.beautyTreatment.text = itemForPreviousHour!!.beautyTreatmentName
                }else{
                    holder.startTime.text = "$hourOfStart : $minuteOfStartString"
                    holder.beautyTreatment.text = beautyTreatmentNameForHour
                    holder.endTime.text = "$hourOfEnd : $minuteOfEndString"
                }
            }
            is ViewHolder_3_4v2 -> {
                if (hourNow== hourOfStart && hourNow == previousEndHour){
                    holder.endTime.text = "$previousEndHour : $minuteOfPreviousEndString"
                    holder.beautyTreatment.text = beautyTreatmentNameForHour
                    holder.timeStart.text = "$hourOfStart : $minuteOfStartString"
                }else if( hourNow == hourOfStart && minuteOfEnd >14){
                    holder.timeStart.text = "$hourOfStart : $minuteOfStartString"
                }else {
                    holder.beautyTreatment.text = beautyTreatmentNameForHour
                    holder.timeStart.text = "$hourOfStart : $minuteOfStartString"
                }
            }
            is ViewHolder_1_2_1 ->{
                holder.startTime.text = "$hourOfStart : $minuteOfStartString"
                holder.endTime.text = "$previousEndHour : $minuteOfPreviousEndString"
            }
            is ViewHolder_1_2 -> {
                if(itemForHour!=null){
                    holder.beautyTreatment.text = beautyTreatmentNameForHour
                    holder.endTime.text = "$hourOfEnd : $minuteOfEndString"
                }else if (previousEndHour == hourNow){
                    holder.beautyTreatment.text = beautyTreatmentForPreviousHour
                    holder.endTime.text = "$hourOfEnd : $minuteOfEndString"
                }


            }
            is ViewHolder_1_2v2-> {
                holder.beautyTreatment.text = beautyTreatmentNameForHour
                holder.startTime.text = "$hourOfStart : $minuteOfStartString"

            }
            is ViewHolder_1_4v2 -> {
                holder.startTime.text = "$hourOfStart : $minuteOfStartString"
            }
            }

        holder.itemView.setOnClickListener{
            hourNow = position + 7
            val listOfHour : List<UserTimeTreatment> = listOfContent.filter {  LocalTime.parse(it.time, DateTimeFormatter.ofPattern("HH:mm")).hour ==  hourNow}
            val itemForHour : UserTimeTreatment? =
                if(listOfHour.isEmpty()){
                    null
                }else{
                    listOfHour[0]
                }
            val  userTimeTreatment = listOfContent.find { it == itemForHour }
            setOnClickPosition?.invoke(userTimeTreatment)
        }
        }

    override fun getItemCount(): Int {
        return  16

    }

    override fun getItemViewType(position: Int): Int {
        var typeOfViewHolder = viewHolderFull
        hourNow = position + 7
        previousHour = position + 6
            val listOfPreviousHour : List<UserTimeTreatment> = listOfContent.filter {  LocalTime.parse(it.time, DateTimeFormatter.ofPattern("HH:mm")).hour ==  previousHour}
            itemForPreviousHour =
                if(listOfPreviousHour.isEmpty()){
                    null
                }else{
                    listOfPreviousHour[0]
                }

        if (itemForPreviousHour!=null){
            previousEndHour =  itemForPreviousHour.let {
                LocalTime.parse(it!!.time, DateTimeFormatter.ofPattern("HH:mm")).plusMinutes(itemForPreviousHour!!.beautyTreatmentTime.toLong()).hour
            }
            previousEndMinute = itemForPreviousHour.let {
                LocalTime.parse(it!!.time, DateTimeFormatter.ofPattern("HH:mm")).plusMinutes(itemForPreviousHour!!.beautyTreatmentTime.toLong()).minute
            }
        }else{
            previousEndHour = null
            previousEndMinute = null
        }


        val listOfHour : List<UserTimeTreatment> = listOfContent.filter {  LocalTime.parse(it.time, DateTimeFormatter.ofPattern("HH:mm")).hour ==  hourNow}
        itemForHour =
            if(listOfHour.isEmpty()){
                null
            }else{
                listOfHour[0]
            }
        if (itemForHour !=null){
        hourOfStart = itemForHour!!.let {
            LocalTime.parse(it.time, DateTimeFormatter.ofPattern("HH:mm")).hour
        }
        minuteOfStart = itemForHour!!.let {
            LocalTime.parse(it.time, DateTimeFormatter.ofPattern("HH:mm")).minute
        }
        hourOfEnd = itemForHour!!.let {
            LocalTime.parse(it.time, DateTimeFormatter.ofPattern("HH:mm")).plusMinutes(itemForHour!!.beautyTreatmentTime.toLong()).hour
        }
        minuteOfEnd = itemForHour!!.let {
            LocalTime.parse(it.time, DateTimeFormatter.ofPattern("HH:mm")).plusMinutes(itemForHour!!.beautyTreatmentTime.toLong()).minute
        }

        val timeOfTreatment = itemForHour!!.beautyTreatmentTime.toInt()
            if(hourOfStart == hourNow && previousEndHour != hourNow){

                if(minuteOfStart < 15 && (minuteOfStart + timeOfTreatment) > 45 ){
                    typeOfViewHolder = viewHolderFull
                } else if (minuteOfStart in 31..44 && timeOfTreatment > 30 ){
                    typeOfViewHolder = viewHolder1_2v2
                }else if(minuteOfStart in 16..29  ) {
                    typeOfViewHolder = viewHolder3_4v2
                }else if (minuteOfStart >= 45 ){
                    typeOfViewHolder = viewHolder1_4v2
                }else if (minuteOfStart < 15 && minuteOfEnd <45){
                    typeOfViewHolder = viewHolder3_4
                }
            }
        else if (hourNow== hourOfStart && hourNow == previousEndHour){
            if(minuteOfStart >44  && previousEndMinute!! < 15){
                typeOfViewHolder =  viewHolder1_2_1
            }else if ( previousEndMinute in 16..29 && minuteOfStart > 44){
                typeOfViewHolder = viewHolderFull
            }else if(previousEndMinute!!< 15 && minuteOfStart < 45){
                typeOfViewHolder = viewHolder3_4
            }
        }
        }else if(hourNow == previousEndHour){
            when {
                minuteOfEnd in 1..14 -> {
                    typeOfViewHolder = viewHolder1_4
                }
                minuteOfEnd in 15..29 -> {
                    typeOfViewHolder = viewHolder1_2
                }
                minuteOfEnd in 30..44 -> {
                    typeOfViewHolder = viewHolder3_4
                }
                minuteOfEnd >45 -> {
                    typeOfViewHolder = viewHolderFull
                }
            }
        } else if(freeDay){
            typeOfViewHolder = viewHolder_free_day
        }else{
            typeOfViewHolder = viewHolderEmpty
        }
        return typeOfViewHolder
    }




    class ViewHolderFull(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val startTime : TextView = itemView.findViewById(R.id.startTime)
        val beautyTreatment : TextView = itemView.findViewById(R.id.beautyTreatment)
        val endTime : TextView = itemView.findViewById(R.id.endTime)
    }

    class ViewHolderEmpty(itemView : View) : RecyclerView.ViewHolder(itemView)

    class ViewHolder_1_4(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val endTime : TextView = itemView.findViewById(R.id.timeEnd)
    }

    class ViewHolder_1_4v2(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val startTime : TextView = itemView.findViewById(R.id.startTime)
    }

    class ViewHolder_1_2(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val endTime : TextView = itemView.findViewById(R.id.timeEnd)
        val beautyTreatment : TextView = itemView.findViewById(R.id.beautyTreatment)
    }

    class ViewHolder_1_2v2(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val startTime : TextView = itemView.findViewById(R.id.startTime)
        val beautyTreatment : TextView = itemView.findViewById(R.id.beautyTreatment)
    }
    class ViewHolder_1_2_1(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val startTime : TextView = itemView.findViewById(R.id.startTime)
        val endTime : TextView = itemView.findViewById(R.id.endTime)
    }

    class ViewHolder_3_4(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val endTime : TextView = itemView.findViewById(R.id.timeEnd)
        val startTime : TextView = itemView.findViewById(R.id.timeStart)
        val beautyTreatment : TextView = itemView.findViewById(R.id.beautyTreatment)
    }

    class ViewHolder_3_4v2(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val timeStart : TextView = itemView.findViewById(R.id.timeStart)
        val beautyTreatment : TextView = itemView.findViewById(R.id.beautyTreatment)
        val endTime : TextView = itemView.findViewById(R.id.endTime)
    }

    class ViewHolderForFreeDay(itemView: View) : RecyclerView.ViewHolder(itemView){
        val text : TextView = itemView.findViewById(R.id.textForFreeDay)
    }

    fun updateListOfContent(list : List<UserTimeTreatment>){
        this.listOfContent = list.toMutableList()
    }

    fun clickPosition(callback : (UserTimeTreatment?)-> Unit){
        this.setOnClickPosition = callback
    }

    fun setFreeDay(bool:Boolean){
        this.freeDay = bool
    }
}