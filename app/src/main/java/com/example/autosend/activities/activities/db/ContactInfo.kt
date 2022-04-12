package com.example.autosend.activities.activities.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contactInfoTable")
data class ContactInfo (
    val nameAndSurrname : String,
    val number :  String,
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null
        )
