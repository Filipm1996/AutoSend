package com.example.autosend.activities.activities.db.entities

import androidx.room.PrimaryKey

data class MonthlyIncomeInfo(
    val month : String,
    val cost : String,
    val proceeds : Double,
    val revenue :  Double,
    val time :  Double ,
    val perHour : Double,
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null
)
