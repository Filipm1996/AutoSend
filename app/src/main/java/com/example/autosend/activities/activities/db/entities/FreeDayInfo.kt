package com.example.autosend.activities.activities.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FreeDayInfo (
    val day : String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)