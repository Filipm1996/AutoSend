package com.example.autosend.activities.activities.repositories

import android.content.Context
import androidx.room.Room
import com.example.autosend.activities.activities.db.ContactInfoDatabase
import com.example.autosend.activities.activities.db.entities.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class Repository(context: Context) : RepositoryDefault {
    private val db : ContactInfoDatabase = Room.databaseBuilder(context,ContactInfoDatabase::class.java,"ContactInfoDb").fallbackToDestructiveMigration().build()

    // Contact Info
    override suspend fun insertContactInfoToDb(contactInfo: ContactInfo) =  db.contactInfoDao().insertContactInfoToDb(contactInfo)

    override suspend fun deleteContactInfoFromDb(contactInfo: ContactInfo) =  db.contactInfoDao().deleteContactInfoFromDb(contactInfo) 

    override fun getAllContactInfosFromDb() = db.contactInfoDao().getAllContactInfo()

    override fun getContactInfoByName(name: String) = db.contactInfoDao().getContactInfoByName(name)

    // Beauty Treatment

    override suspend fun insertBeautyTreatment (beautyTreatmentInfo: BeautyTreatmentInfo) =  db.contactInfoDao().insertBeautyTreatmentToDb(beautyTreatmentInfo) 

    override suspend fun deleteBeautyTreatment (beautyTreatmentInfo: BeautyTreatmentInfo) = db.contactInfoDao().deleteBeautyTreatment(beautyTreatmentInfo) 

    override fun getAllBeautyTreatments () = db.contactInfoDao().getAllBeautyTreatments()

    override fun getBeautyTreatmentByName (name : String) = db.contactInfoDao().getBeautyTreatmentByName(name)

    // User-Time-Treatment

    override fun updateUserTimeTreatment(userTimeTreatment: UserTimeTreatment) = db.contactInfoDao().updateUserTimeTreatment(userTimeTreatment)

    override fun getAllUserTimeTreatments() = db.contactInfoDao().getAllUserTimeTreatments()

    override suspend fun insertUserTimeTreatment(userTimeTreatment: UserTimeTreatment) =  db.contactInfoDao().insertUserTimeTreatment(userTimeTreatment) 

    override suspend fun deleteUserTimeTreatment(userTimeTreatment: UserTimeTreatment) =  db.contactInfoDao().deleteUserTimeTreatment(userTimeTreatment) 

    override fun getUserTimeTreatmentByDay(localDate: String) =  db.contactInfoDao().getUserTimeTreatmentByDay(localDate)

    override fun getUserTimeTreatmentByDay2(localDate: String) = db.contactInfoDao().getUserTimeTreatmentByDay2(localDate)

    override suspend fun deleteUserTimeTreatmentByDay(day:String) = db.contactInfoDao().deleteUserTimeTreatmentByDay(day) 
    //MessageSMS

    override fun getMessageFromDb() = db.contactInfoDao().getMessageFromDb()

    override suspend fun deleteMessageFromDb() = db.contactInfoDao().deleteMessageFromDb()

    override suspend fun insertMessageToDb(messageSMS: MessageSMS) =  db.contactInfoDao().insertMessageToDb(messageSMS) 

    // Free Day Info

    override fun getAllFreeDays() = db.contactInfoDao().getAllFreeDays()

    override suspend fun deleteFreeDayFromDb(freeDayInfo: String) =  db.contactInfoDao().deleteFreeDayFromDb(freeDayInfo)

    override suspend fun insertFreeDayToDb(freeDayInfo: FreeDayInfo) =  db.contactInfoDao().insertFreeDayToDb(freeDayInfo)
}
