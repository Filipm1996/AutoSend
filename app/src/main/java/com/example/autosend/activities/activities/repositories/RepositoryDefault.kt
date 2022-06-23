package com.example.autosend.activities.activities.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.autosend.activities.activities.db.entities.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

interface RepositoryDefault {

    suspend fun insertContactInfoToDb(contactInfo: ContactInfo)

    suspend fun deleteContactInfoFromDb(contactInfo: ContactInfo)

    fun getAllContactInfosFromDb(): LiveData<List<ContactInfo>>

    fun getContactInfoByName(name: String): ContactInfo?

    // Beauty Treatment

    suspend fun insertBeautyTreatment (beautyTreatmentInfo: BeautyTreatmentInfo)

    suspend fun deleteBeautyTreatment (beautyTreatmentInfo: BeautyTreatmentInfo)

    fun getAllBeautyTreatments (): LiveData<List<BeautyTreatmentInfo>>

    fun getBeautyTreatmentByName (name : String): BeautyTreatmentInfo?

    // User-Time-Treatment

    fun updateUserTimeTreatment(userTimeTreatment: UserTimeTreatment)

    fun getAllUserTimeTreatments(): LiveData<List<UserTimeTreatment>>

    suspend fun insertUserTimeTreatment(userTimeTreatment: UserTimeTreatment)

    suspend fun deleteUserTimeTreatment(userTimeTreatment: UserTimeTreatment)

    fun getUserTimeTreatmentByDay(localDate: String): LiveData<List<UserTimeTreatment>>

    fun getUserTimeTreatmentByDay2(localDate: String): List<UserTimeTreatment>

    suspend fun deleteUserTimeTreatmentByDay(day:String)
    //MessageSMS

    fun getMessageFromDb(): LiveData<MessageSMS>

    suspend fun deleteMessageFromDb()

    suspend fun insertMessageToDb(messageSMS: MessageSMS)

    // Free Day Info

    fun getAllFreeDays(): LiveData<List<FreeDayInfo>>

    suspend fun deleteFreeDayFromDb(freeDayInfo: String)

    suspend fun insertFreeDayToDb(freeDayInfo: FreeDayInfo)
}