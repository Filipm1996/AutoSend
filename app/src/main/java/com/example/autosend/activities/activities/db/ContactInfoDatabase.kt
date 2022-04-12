package com.example.autosend.activities.activities.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ContactInfo::class],
    version = 1
)
abstract class ContactInfoDatabase : RoomDatabase() {

abstract fun contactInfoDao() : ContactInfoDao

}