package com.example.autosend.activities.activities.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContactInfoToDb(contactInfo: ContactInfo)

    @Delete
    fun deleteContactInfoFromDb (contactInfo: ContactInfo)

    @Query("SELECT * FROM contactInfoTable")
    fun getAllContactInfo(): LiveData<List<ContactInfo>>

    @Query("SELECT * FROM contactInfoTable WHERE nameAndSurrname= :name")
    fun getContactInfoByName(name:String) : ContactInfo
}