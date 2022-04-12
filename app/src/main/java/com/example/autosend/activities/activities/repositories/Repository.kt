package com.example.autosend.activities.activities.repositories

import android.content.Context
import androidx.room.Room
import com.example.autosend.activities.activities.db.ContactInfo
import com.example.autosend.activities.activities.db.ContactInfoDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Repository(
    context: Context
) {
    private val db : ContactInfoDatabase = Room.databaseBuilder(context,ContactInfoDatabase::class.java,"ContactInfoDb").build()

    fun insertContactInfoToDb(contactInfo: ContactInfo) = CoroutineScope(Dispatchers.IO).launch { db.contactInfoDao().insertContactInfoToDb(contactInfo)}


}