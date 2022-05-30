package com.example.autosend.activities.activities.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MessageSMS (
    val message : String,
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null
    )