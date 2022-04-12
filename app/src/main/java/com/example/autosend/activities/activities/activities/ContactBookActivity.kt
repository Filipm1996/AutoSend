package com.example.autosend.activities.activities.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.autosend.activities.activities.UI.AutoSendViewModel
import com.example.autosend.activities.activities.UI.ViewModelFactory
import com.example.autosend.activities.activities.db.ContactInfo
import com.example.autosend.activities.activities.repositories.Repository
import com.example.autosend.databinding.ActivityContactBookBinding


class ContactBook : AppCompatActivity() {
    private lateinit var autoSendViewModel: AutoSendViewModel
    private lateinit var binding: ActivityContactBookBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityContactBookBinding.inflate(layoutInflater)
        autoSendViewModel = ViewModelProvider(this,ViewModelFactory(Repository(this)))[AutoSendViewModel::class.java]
        setOnClickLiseners()
        setContentView(binding.root)
    }
    private fun setOnClickLiseners() {
        binding.addButton.setOnClickListener {
            AddContactDialog(this, object:onAddButtonClicked {
                override fun addToDb(contactInfo: ContactInfo) {
                    autoSendViewModel.insertContactToDb(contactInfo)
                }

            })
        }
    }
}