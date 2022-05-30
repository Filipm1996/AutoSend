package com.example.autosend.activities.activities.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.autosend.activities.activities.db.entities.*

@Database(
    entities = [ContactInfo::class, BeautyTreatmentInfo::class, UserTimeTreatment::class , MessageSMS::class, FreeDayInfo::class],
    version = 2,
    exportSchema = false
)
abstract class ContactInfoDatabase : RoomDatabase() {

abstract fun contactInfoDao() : ContactInfoDao

}