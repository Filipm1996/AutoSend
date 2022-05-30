package com.example.autosend.activities.activities.repositories

import android.content.Context
import androidx.room.Room
import com.example.autosend.activities.activities.db.ContactInfoDatabase
import com.example.autosend.activities.activities.db.entities.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class Repository(
    context: Context
) {
    private val db : ContactInfoDatabase = Room.databaseBuilder(context,ContactInfoDatabase::class.java,"ContactInfoDb").fallbackToDestructiveMigration().build()

    // Contact Info
    fun insertContactInfoToDb(contactInfo: ContactInfo) = CoroutineScope(Dispatchers.IO).launch { db.contactInfoDao().insertContactInfoToDb(contactInfo)}

    fun deleteContactInfoFromDb(contactInfo: ContactInfo) = CoroutineScope(Dispatchers.IO).launch { db.contactInfoDao().deleteContactInfoFromDb(contactInfo) }

    fun getAllContactInfosFromDb() = db.contactInfoDao().getAllContactInfo()

    fun getContactInfoByName(name: String) = db.contactInfoDao().getContactInfoByName(name)

    // Beauty Treatment

    fun insertBeautyTreatment (beautyTreatmentInfo: BeautyTreatmentInfo) = CoroutineScope(Dispatchers.IO).launch { db.contactInfoDao().insertBeautyTreatmentToDb(beautyTreatmentInfo) }

    fun deleteBeautyTreatment (beautyTreatmentInfo: BeautyTreatmentInfo) = CoroutineScope(Dispatchers.IO).launch { db.contactInfoDao().deleteBeautyTreatment(beautyTreatmentInfo) }

    fun getAllBeautyTreatments () = db.contactInfoDao().getAllBeautyTreatments()

    fun getBeautyTreatmentByName (name : String) = db.contactInfoDao().getBeautyTreatmentByName(name)

    // User-Time-Treatment

    fun updateUserTimeTreatment(userTimeTreatment: UserTimeTreatment) = db.contactInfoDao().updateUserTimeTreatment(userTimeTreatment)

    fun getAllUserTimeTreatments() = db.contactInfoDao().getAllUserTimeTreatments()

    fun insertUserTimeTreatment(userTimeTreatment: UserTimeTreatment) = CoroutineScope(Dispatchers.IO).launch { db.contactInfoDao().insertUserTimeTreatment(userTimeTreatment) }

    fun deleteUserTimeTreatment(userTimeTreatment: UserTimeTreatment) = CoroutineScope(Dispatchers.IO).launch { db.contactInfoDao().deleteUserTimeTreatment(userTimeTreatment) }

    fun getUserTimeTreatmentByDay(localDate: String) =  db.contactInfoDao().getUserTimeTreatmentByDay(localDate)

    fun getUserTimeTreatmentByDay2(localDate: String) = db.contactInfoDao().getUserTimeTreatmentByDay2(localDate)

    fun deleteUserTimeTreatmentByDay(day:String) = CoroutineScope(Dispatchers.IO).launch { db.contactInfoDao().deleteUserTimeTreatmentByDay(day) }
    //MessageSMS

    fun getMessageFromDb() = db.contactInfoDao().getMessageFromDb()

    fun deleteMessageFromDb() = CoroutineScope(Dispatchers.IO).launch { db.contactInfoDao().deleteMessageFromDb()}

    fun insertMessageToDb(messageSMS: MessageSMS) = CoroutineScope(Dispatchers.IO).launch { db.contactInfoDao().insertMessageToDb(messageSMS) }

    // Free Day Info

    fun getAllFreDays() = db.contactInfoDao().getAllFreeDays()

    fun deleteFreeDayFromDb(freeDayInfo: String) = CoroutineScope(Dispatchers.IO).launch { db.contactInfoDao().deleteFreeDayFromDb(freeDayInfo) }

    fun insertFreeDayToDb(freeDayInfo: FreeDayInfo) = CoroutineScope(Dispatchers.IO).launch { db.contactInfoDao().insertFreeDayToDb(freeDayInfo) }
}
