package com.example.autosend.activities.activities.UI

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.autosend.R
import com.example.autosend.activities.activities.db.ContactInfo
import org.w3c.dom.Text

class ContactBookAdapter : RecyclerView.Adapter<ContactBookAdapter.ViewHolder>(){
    private var listOfContacts : List<ContactInfo>? = null
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var nameText : TextView = itemView.findViewById(R.id.name)
        var phoneText : TextView = itemView.findViewById(R.id.phoneText)
        var deleteButton : ImageButton = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
         val view =LayoutInflater.from(parent.context).inflate(R.layout.contact_book_content,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return listOfContacts!!.size
    }

    fun updateListOfContacts(list : List<ContactInfo>){
        this.listOfContacts = list
    }
}