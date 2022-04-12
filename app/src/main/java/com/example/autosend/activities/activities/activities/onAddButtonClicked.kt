package com.example.autosend.activities.activities.activities

import com.example.autosend.activities.activities.db.ContactInfo

interface onAddButtonClicked {
    fun addToDb(contactInfo: ContactInfo)
}