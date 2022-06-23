package com.example.autosend.activities.activities.repositories

import android.content.Context
import androidx.room.Room
import com.example.autosend.activities.activities.db.ContactInfoDao
import com.example.autosend.activities.activities.db.ContactInfoDatabase
import com.example.autosend.activities.activities.db.entities.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

class Repository @Inject constructor(private val contactInfoDao: ContactInfoDao) : RepositoryDefault {

    // Contact Info
    override suspend fun insertContactInfoToDb(contactInfo: ContactInfo) =   contactInfoDao.insertContactInfoToDb(contactInfo)

    override suspend fun deleteContactInfoFromDb(contactInfo: ContactInfo) = contactInfoDao.deleteContactInfoFromDb(contactInfo)

    override fun getAllContactInfosFromDb() = contactInfoDao.getAllContactInfo()

    override fun getContactInfoByName(name: String) = contactInfoDao.getContactInfoByName(name)

    // Beauty Treatment

    override suspend fun insertBeautyTreatment (beautyTreatmentInfo: BeautyTreatmentInfo) = contactInfoDao.insertBeautyTreatmentToDb(beautyTreatmentInfo)

    override suspend fun deleteBeautyTreatment (beautyTreatmentInfo: BeautyTreatmentInfo) = contactInfoDao.deleteBeautyTreatment(beautyTreatmentInfo)

    override fun getAllBeautyTreatments () = contactInfoDao.getAllBeautyTreatments()

    override fun getBeautyTreatmentByName (name : String) = contactInfoDao.getBeautyTreatmentByName(name)

    // User-Time-Treatment

    override fun updateUserTimeTreatment(userTimeTreatment: UserTimeTreatment) = contactInfoDao.updateUserTimeTreatment(userTimeTreatment)

    override fun getAllUserTimeTreatments() = contactInfoDao.getAllUserTimeTreatments()

    override suspend fun insertUserTimeTreatment(userTimeTreatment: UserTimeTreatment) =  contactInfoDao.insertUserTimeTreatment(userTimeTreatment)

    override suspend fun deleteUserTimeTreatment(userTimeTreatment: UserTimeTreatment) =  contactInfoDao.deleteUserTimeTreatment(userTimeTreatment)

    override fun getUserTimeTreatmentByDay(localDate: String) =  contactInfoDao.getUserTimeTreatmentByDay(localDate)

    override fun getUserTimeTreatmentByDay2(localDate: String) = contactInfoDao.getUserTimeTreatmentByDay2(localDate)

    override suspend fun deleteUserTimeTreatmentByDay(day:String) = contactInfoDao.deleteUserTimeTreatmentByDay(day)

    //MessageSMS

    override fun getMessageFromDb() = contactInfoDao.getMessageFromDb()

    override suspend fun deleteMessageFromDb() = contactInfoDao.deleteMessageFromDb()

    override suspend fun insertMessageToDb(messageSMS: MessageSMS) =  contactInfoDao.insertMessageToDb(messageSMS)

    // Free Day Info

    override fun getAllFreeDays() = contactInfoDao.getAllFreeDays()

    override suspend fun deleteFreeDayFromDb(freeDayInfo: String) =  contactInfoDao.deleteFreeDayFromDb(freeDayInfo)

    override suspend fun insertFreeDayToDb(freeDayInfo: FreeDayInfo) =  contactInfoDao.insertFreeDayToDb(freeDayInfo)
}
