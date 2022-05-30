package com.example.autosend.activities.activities.activities


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.autosend.R
import com.example.autosend.activities.activities.UI.AutoSendViewModel
import com.example.autosend.activities.activities.UI.ContactBookAdapter
import com.example.autosend.activities.activities.UI.ViewModelFactory
import com.example.autosend.activities.activities.repositories.Repository
import com.example.autosend.databinding.ActivityContactBookBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ContactBook : AppCompatActivity() {
    private lateinit var autoSendViewModel: AutoSendViewModel
    private lateinit var binding: ActivityContactBookBinding
    private lateinit var contactBookAdapter: ContactBookAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityContactBookBinding.inflate(layoutInflater)
        setContentView(binding.root)
        autoSendViewModel = ViewModelProvider(this,ViewModelFactory(Repository(this)))[AutoSendViewModel::class.java]
        contactBookAdapter = ContactBookAdapter()
        setOnClickLiseners()
        setUpRecyclerView()

    }

    private fun setUpRecyclerView() {
        autoSendViewModel.getAllContactInfoFromDb().observe(this, Observer {
            contactBookAdapter.updateListOfContacts(it)
            binding.recyclerViewContactBook.layoutManager = LinearLayoutManager(this)
            binding.recyclerViewContactBook.adapter = contactBookAdapter
        })

    }

    private fun setOnClickLiseners() {
        val floatingAddButton = findViewById<FloatingActionButton>(R.id.addButton)
        floatingAddButton.setOnClickListener {
            val fm = supportFragmentManager
            val alertDialog = AddContactDialog()
            alertDialog.show(fm,"fragment alert")
            alertDialog.setUpAddButtonClicked {
                autoSendViewModel.insertContactToDb(it)
            }
            alertDialog.setUpCancelButtonClicked {
                alertDialog.dismiss()
            }
        }
        contactBookAdapter.setOnDeleteClickListener {
            autoSendViewModel.deleteContactFromDb(it)
        }
    }


}