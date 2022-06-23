package com.example.autosend.activities.activities.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.autosend.activities.activities.db.entities.*

class FakeRepository : RepositoryDefault{
    private val mutableLivedataUserTimeTreatment = MutableLiveData<List<UserTimeTreatment>>()
    private val listUserTimeTreatment = mutableListOf<UserTimeTreatment>()

    private val mutableLiveDataContactInfo = MutableLiveData<List<ContactInfo>>()
    private val listOfContactInfo = mutableListOf<ContactInfo>()

    private val mutableLiveDataBeautyTreatment = MutableLiveData<List<BeautyTreatmentInfo>>()
    private val listOfBeautyTreatmentInfo = mutableListOf<BeautyTreatmentInfo>()

    private val mutableLiveDataUserTimeTreatment = MutableLiveData<List<UserTimeTreatment>>()
    private val listOfUserTimeTreatment = mutableListOf<UserTimeTreatment>()

    var message = MessageSMS("")
    private val mutableLiveDataMessage = MutableLiveData<MessageSMS>()

    private val freeDaysList = mutableListOf<FreeDayInfo>()
    private val mutableLiveDataFreeDayInfo = MutableLiveData<List<FreeDayInfo>> ()

    override suspend fun deleteUserTimeTreatment(userTimeTreatment: UserTimeTreatment){
        listUserTimeTreatment.remove(userTimeTreatment)
        mutableLivedataUserTimeTreatment.value = listUserTimeTreatment
        updateLiveDataList()
    }

    override suspend fun insertUserTimeTreatment(userTimeTreatment: UserTimeTreatment){
        listUserTimeTreatment.add(userTimeTreatment)
        mutableLivedataUserTimeTreatment.value = listUserTimeTreatment
        updateLiveDataList()
    }

    private fun updateLiveDataList(){
        mutableLivedataUserTimeTreatment.postValue(listUserTimeTreatment)
    }
    override fun getAllUserTimeTreatments() : LiveData<List<UserTimeTreatment>> {
        return mutableLivedataUserTimeTreatment
    }

    override suspend fun insertContactInfoToDb(contactInfo: ContactInfo) {
        listOfContactInfo.add(contactInfo)
        mutableLiveDataContactInfo.postValue(listOfContactInfo)
    }

    override suspend fun deleteContactInfoFromDb(contactInfo: ContactInfo) {
        listOfContactInfo.remove(contactInfo)
        mutableLiveDataContactInfo.postValue(listOfContactInfo)
    }

    override fun getAllContactInfosFromDb(): LiveData<List<ContactInfo>> {
        return mutableLiveDataContactInfo
    }

    override fun getContactInfoByName(name: String): ContactInfo? {
        return listOfContactInfo.find { it.nameAndSurrname == name }
    }

    override suspend fun insertBeautyTreatment(beautyTreatmentInfo: BeautyTreatmentInfo) {
        listOfBeautyTreatmentInfo.add(beautyTreatmentInfo)
        mutableLiveDataBeautyTreatment.postValue(listOfBeautyTreatmentInfo)
    }

    override suspend fun deleteBeautyTreatment(beautyTreatmentInfo: BeautyTreatmentInfo) {
        listOfBeautyTreatmentInfo.remove(beautyTreatmentInfo)
        mutableLiveDataBeautyTreatment.postValue(listOfBeautyTreatmentInfo)
    }

    override fun getAllBeautyTreatments(): LiveData<List<BeautyTreatmentInfo>> {
        return mutableLiveDataBeautyTreatment
    }

    override fun getBeautyTreatmentByName(name: String): BeautyTreatmentInfo? {
        return listOfBeautyTreatmentInfo.find { it.name == name }
    }

    override fun updateUserTimeTreatment(userTimeTreatment: UserTimeTreatment) {
        val elementOfList =  listOfUserTimeTreatment.find { it == userTimeTreatment }
        val indexOf = listOfUserTimeTreatment.indexOf(elementOfList)
        listOfUserTimeTreatment[indexOf] = userTimeTreatment
        mutableLiveDataUserTimeTreatment.postValue(listOfUserTimeTreatment)
    }

    override fun getUserTimeTreatmentByDay(localDate: String): LiveData<List<UserTimeTreatment>> {
        val newLiveData = MutableLiveData<List<UserTimeTreatment>>()
        val newList = listOfUserTimeTreatment.filter { it.day == localDate }
        newLiveData.postValue(newList)
        return newLiveData
    }

    override fun getUserTimeTreatmentByDay2(localDate: String): List<UserTimeTreatment> {
        return listOfUserTimeTreatment.filter { it.day == localDate }
    }

    override suspend fun deleteUserTimeTreatmentByDay(day: String) {
        listOfUserTimeTreatment.forEach{
            if(it.day == day){
                listOfUserTimeTreatment.remove(it)
            }
        }
    }

    override fun getMessageFromDb(): LiveData<MessageSMS> {
        return mutableLiveDataMessage
    }

    override suspend fun deleteMessageFromDb() {
        mutableLiveDataMessage.postValue(MessageSMS(""))
    }

    override suspend fun insertMessageToDb(messageSMS: MessageSMS) {
        message = messageSMS
        mutableLiveDataMessage.postValue(message)
    }

    override fun getAllFreeDays(): LiveData<List<FreeDayInfo>> {
        return mutableLiveDataFreeDayInfo
    }

    override suspend fun deleteFreeDayFromDb(freeDayInfo: String) {
        freeDaysList.remove(FreeDayInfo(freeDayInfo))
        mutableLiveDataFreeDayInfo.postValue(freeDaysList)
    }

    override suspend fun insertFreeDayToDb(freeDayInfo: FreeDayInfo) {
        freeDaysList.add(freeDayInfo)
        mutableLiveDataFreeDayInfo.postValue(freeDaysList)
    }
}