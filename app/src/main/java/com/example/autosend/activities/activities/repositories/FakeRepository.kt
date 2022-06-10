package com.example.autosend.activities.activities.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.autosend.activities.activities.db.entities.*

class FakeRepository : RepositoryDefault{
    val mutableLivedata = MutableLiveData<List<UserTimeTreatment>>()
    val list = mutableListOf<UserTimeTreatment>()
    override suspend fun deleteUserTimeTreatment(userTimeTreatment: UserTimeTreatment){
        list.remove(userTimeTreatment)
        updateLiveDataList()
    }

    override suspend fun insertUserTimeTreatment(userTimeTreatment: UserTimeTreatment){
        list.add(userTimeTreatment)
        updateLiveDataList()
    }

    private fun updateLiveDataList(){
        mutableLivedata.postValue(list)
    }
    override fun getAllUserTimeTreatments() : LiveData<List<UserTimeTreatment>> {
        return mutableLivedata
    }

    override suspend fun insertContactInfoToDb(contactInfo: ContactInfo) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteContactInfoFromDb(contactInfo: ContactInfo) {
        TODO("Not yet implemented")
    }

    override fun getAllContactInfosFromDb(): LiveData<List<ContactInfo>> {
        TODO("Not yet implemented")
    }

    override fun getContactInfoByName(name: String): ContactInfo {
        TODO("Not yet implemented")
    }

    override suspend fun insertBeautyTreatment(beautyTreatmentInfo: BeautyTreatmentInfo) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteBeautyTreatment(beautyTreatmentInfo: BeautyTreatmentInfo) {
        TODO("Not yet implemented")
    }

    override fun getAllBeautyTreatments(): LiveData<List<BeautyTreatmentInfo>> {
        TODO("Not yet implemented")
    }

    override fun getBeautyTreatmentByName(name: String): BeautyTreatmentInfo {
        TODO("Not yet implemented")
    }

    override fun updateUserTimeTreatment(userTimeTreatment: UserTimeTreatment) {
        TODO("Not yet implemented")
    }

    override fun getUserTimeTreatmentByDay(localDate: String): LiveData<List<UserTimeTreatment>> {
        TODO("Not yet implemented")
    }

    override fun getUserTimeTreatmentByDay2(localDate: String): List<UserTimeTreatment> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUserTimeTreatmentByDay(day: String) {
        TODO("Not yet implemented")
    }

    override fun getMessageFromDb(): LiveData<MessageSMS> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMessageFromDb() {
        TODO("Not yet implemented")
    }

    override suspend fun insertMessageToDb(messageSMS: MessageSMS) {
        TODO("Not yet implemented")
    }

    override fun getAllFreeDays(): LiveData<List<FreeDayInfo>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFreeDayFromDb(freeDayInfo: String) {
        TODO("Not yet implemented")
    }

    override suspend fun insertFreeDayToDb(freeDayInfo: FreeDayInfo) {
        TODO("Not yet implemented")
    }
}