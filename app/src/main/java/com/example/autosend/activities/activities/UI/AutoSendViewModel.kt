package com.example.autosend.activities.activities.UI

import androidx.lifecycle.ViewModel
import com.example.autosend.activities.activities.db.ContactInfo
import com.example.autosend.activities.activities.repositories.Repository

class AutoSendViewModel(
    private val repository: Repository
) : ViewModel() {
    fun insertContactToDb(contactInfo: ContactInfo) = repository.insertContactInfoToDb(contactInfo)
}