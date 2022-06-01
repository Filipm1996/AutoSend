package com.example.autosend.activities.activities.UI

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.autosend.R
import com.example.autosend.activities.activities.db.entities.ContactInfo


class ContactBookAdapter : RecyclerView.Adapter<ContactBookAdapter.ViewHolder>(){
    private var listOfContacts : List<ContactInfo>? = null
    private var onDeleteClick : ((ContactInfo)->Unit)? = null
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var nameText : TextView = itemView.findViewById(R.id.name)
        var phoneText : TextView = itemView.findViewById(R.id.phoneText)
        var deleteButton : ImageButton = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
         val view =LayoutInflater.from(parent.context).inflate(R.layout.contact_book_content,parent,false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contactInfo = listOfContacts!![position]
        holder.nameText.text = contactInfo.nameAndSurrname
        holder.phoneText.text = "tel.${contactInfo.number}"
        holder.deleteButton.setOnClickListener {
            onDeleteClick!!.invoke(contactInfo)
        }
    }

    override fun getItemCount(): Int {
        return listOfContacts!!.size
    }

    fun setOnDeleteClickListener(callback : (ContactInfo)-> Unit){
        this.onDeleteClick = callback
    }
    fun updateListOfContacts(list : List<ContactInfo>){
        this.listOfContacts = list
    }
}