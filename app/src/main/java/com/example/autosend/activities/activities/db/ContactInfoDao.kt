package com.example.autosend.activities.activities.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.autosend.activities.activities.db.entities.*

@Dao
interface ContactInfoDao {

    // Contact Info

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContactInfoToDb(contactInfo: ContactInfo)

    @Delete
    fun deleteContactInfoFromDb (contactInfo: ContactInfo)

    @Query("SELECT * FROM contactInfoTable")
    fun getAllContactInfo(): LiveData<List<ContactInfo>>

    @Query("SELECT * FROM contactInfoTable WHERE nameAndSurrname= :name")
    fun getContactInfoByName(name:String) : ContactInfo

    // Beauty Treatments

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBeautyTreatmentToDb(beautyTreatmentInfo: BeautyTreatmentInfo)

    @Delete
    fun deleteBeautyTreatment(beautyTreatmentInfo: BeautyTreatmentInfo)

    @Query("SELECT * FROM beutytreatmentstable")
    fun getAllBeautyTreatments() : LiveData<List<BeautyTreatmentInfo>>

    @Query("SELECT * FROM beutytreatmentstable WHERE name= :name")
    fun getBeautyTreatmentByName(name : String) : BeautyTreatmentInfo


    // User-Time-Treatment

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserTimeTreatment(userTimeTreatment: UserTimeTreatment)

    @Delete
    fun deleteUserTimeTreatment (userTimeTreatment: UserTimeTreatment)

    @Query("SELECT * FROM usertimetreatment WHERE day= :localDate")
    fun getUserTimeTreatmentByDay(localDate: String): LiveData<List<UserTimeTreatment>>

    @Query("SELECT * FROM usertimetreatment WHERE day= :localDate")
    fun getUserTimeTreatmentByDay2(localDate: String): List<UserTimeTreatment>

    @Query("SELECT * FROM usertimetreatment")
    fun getAllUserTimeTreatments() : LiveData<List<UserTimeTreatment>>

    @Update
    fun updateUserTimeTreatment(userTimeTreatment: UserTimeTreatment)

    @Query("DELETE FROM usertimetreatment WHERE day = :day")
    fun deleteUserTimeTreatmentByDay(day: String)
    //Message SMS

    @Query("SELECT * FROM MessageSMS")
    fun getMessageFromDb() : LiveData<MessageSMS>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessageToDb(messageSMS: MessageSMS)

    @Query("DELETE FROM MessageSMS")
    fun deleteMessageFromDb ()

    //Free Day Info

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFreeDayToDb(freeDayInfo: FreeDayInfo)

    @Query("DELETE FROM FreeDayInfo WHERE day =:day")
    fun deleteFreeDayFromDb(day: String)

    @Query("SELECT * FROM FreeDayInfo")
    fun getAllFreeDays() : LiveData<List<FreeDayInfo>>
}