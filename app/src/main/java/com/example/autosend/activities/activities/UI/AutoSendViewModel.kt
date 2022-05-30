package com.example.autosend.activities.activities.UI

import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.autosend.activities.activities.db.entities.*
import com.example.autosend.activities.activities.repositories.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalTime
import java.time.Month
import java.time.format.DateTimeFormatter

class AutoSendViewModel(
    private val repository: Repository
) : ViewModel() {
    var thisYear = LocalDate.now().year
    private val polishMonths = listOf(
        "Styczeń",
        "Luty",
        "Marzec",
        "Kwiecień",
        "Maj",
        "Czerwiec",
        "Lipiec",
        "Sierpień",
        "Wrzesień",
        "Październik",
        "Listopad",
        "Grudzień"
    )
    // Contact Info

    fun insertContactToDb(contactInfo: ContactInfo) = repository.insertContactInfoToDb(contactInfo)

    fun deleteContactFromDb(contactInfo: ContactInfo) =
        repository.deleteContactInfoFromDb(contactInfo)

    fun getAllContactInfoFromDb() = repository.getAllContactInfosFromDb()

    fun getContactInfoByName(name: String) = repository.getContactInfoByName(name)

    // Beauty Treatment

    fun insertBeautyTreatmentToDb(beautyTreatmentInfo: BeautyTreatmentInfo) =
        repository.insertBeautyTreatment(beautyTreatmentInfo)

    fun deleteBeautyTreatmentFromDb(beautyTreatmentInfo: BeautyTreatmentInfo) =
        repository.deleteBeautyTreatment(beautyTreatmentInfo)

    fun getAllBeautyTreatments() = repository.getAllBeautyTreatments()

    fun getBeautyTreatmentByName(name: String) = repository.getBeautyTreatmentByName(name)

    // User-Time-Treatment
    fun updateUserTimeTreatment(userTimeTreatment: UserTimeTreatment) =
        repository.updateUserTimeTreatment(userTimeTreatment)

    fun getAllUserTimeTreatments() = repository.getAllUserTimeTreatments()

    fun insertUserTimeTreatmentFromDb(userTimeTreatment: UserTimeTreatment) =
        repository.insertUserTimeTreatment(userTimeTreatment)

    fun deleteUsertTimeTreatmentFromDb(userTimeTreatment: UserTimeTreatment) =
        repository.deleteUserTimeTreatment(userTimeTreatment)

    fun getUserTimeTreatmentByDay(localDate: String) =
        repository.getUserTimeTreatmentByDay(localDate)

    fun getUserTimeTreatmentByDay2(localDate: String) =
        repository.getUserTimeTreatmentByDay2(localDate)

    fun deleteUserTimeTreatmentByDay(day : String) = repository.deleteUserTimeTreatmentByDay(day)
    //MessageSMS

    fun getMessageFromDb() = repository.getMessageFromDb()

    fun deleteMessageFromDb() = repository.deleteMessageFromDb()

    fun insertMessageToDb(messageSMS: MessageSMS) = repository.insertMessageToDb(messageSMS)

    // Free Day info

    fun getAllFreDays() = repository.getAllFreDays()

    fun insertFreeDayToDb(freeday : FreeDayInfo) = repository.insertFreeDayToDb(freeday)

    fun deleteFreeDayFromDb(freeday: String) = repository.deleteFreeDayFromDb(freeday)

    //Calendar Activity

    fun sumUpWeeklyIncome(firstDay: LocalDate): Double {
        val listOfUserTimeTreatment = mutableListOf<UserTimeTreatment>()
        var day = firstDay
        for (i in 0..4) {
            val listOfDay = getUserTimeTreatmentByDay2(day.toString())
            listOfDay.forEach {
                listOfUserTimeTreatment.add(it)
            }
            day = day.plusDays(1)
        }
        var income = 0.0
        for (i in listOfUserTimeTreatment) {

            income = income + i.beautyTreatmentPrice.toDouble() - i.beautyTreatmentCost.toDouble()
        }
        return income
    }

    //Monthly Income Activity

    private fun calculateForMonth(
        monthInt: Int,
        listOfUserTimeTreatments: List<UserTimeTreatment>
    ): MonthlyIncomeInfo {
        val month = Month.of(monthInt)
        val polishMonth = polishMonths[monthInt - 1]
        val listForMonth =
            listOfUserTimeTreatments.filter { LocalDate.parse(it.day).month == month }
        var cost = 0.0
        var proceeds = 0.0
        var revenue = 0.0
        var time = 0.0
        var perHour = 0.0
        listForMonth.forEach { time += it.beautyTreatmentTime.toInt() }
        listForMonth.forEach { cost += it.beautyTreatmentCost.toDouble() }
        listForMonth.forEach { revenue += it.beautyTreatmentPrice.toDouble() }
        proceeds = revenue - cost
        if (proceeds != 0.0 && time != 0.0) {
            perHour = proceeds / (time / 60)
        }
        return MonthlyIncomeInfo(polishMonth, cost.toString(), proceeds, revenue, time, perHour)
    }

    fun updatingListOfMonthlyIncome(listOfUserTimeTreatments: List<UserTimeTreatment>): MutableList<MonthlyIncomeInfo> {
        val listOfMonthlyIncome = mutableListOf<MonthlyIncomeInfo>()
        val listForYear =
            listOfUserTimeTreatments.filter { LocalDate.parse(it.day).year == thisYear }
        var monthInt = 1
        for (i in 0..11) {
            listOfMonthlyIncome.add(calculateForMonth(monthInt, listForYear))
            monthInt += 1
        }
        return listOfMonthlyIncome
    }

    //Settings Activity

    fun getUserTimeTreatmentsForToday(intentData: List<UserTimeTreatment>): List<UserTimeTreatment> {
        val todays = LocalDate.now().plusDays(1)
        val listForToday = mutableListOf<UserTimeTreatment>()
        for (i in intentData) {
            if (LocalDate.parse(i.day) == todays) {
                listForToday.add(i)
            }
        }
        println(listForToday.size.toString())
        return listForToday
    }

    //Discount Dialog

    fun updatePrices(
        to: String,
        from: String,
        beautyTreatmentInfo: BeautyTreatmentInfo,
        newPrice: String
    ) {
        var formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val toDate = LocalDate.parse(to, formatter)
        var fromDate = LocalDate.parse(from, formatter)
        CoroutineScope(Dispatchers.IO).launch {
            while (fromDate != toDate.plusDays(1)) {
                val listToUpdate = getUserTimeTreatmentByDay2(fromDate.toString())
                for (i in listToUpdate) {
                    if (i.beautyTreatmentName == beautyTreatmentInfo.name) {
                        println("Yes")
                        println(newPrice)
                        deleteUsertTimeTreatmentFromDb(i)
                        insertUserTimeTreatmentFromDb(
                            UserTimeTreatment(
                                i.name,
                                i.number,
                                i.time,
                                i.day,
                                i.beautyTreatmentName,
                                newPrice,
                                i.beautyTreatmentCost,
                                i.beautyTreatmentTime
                            )
                        )
                    }
                }
                fromDate = fromDate.plusDays(1)
            }
        }
    }

    //Calendar Dialog
    fun checkIfTheHourIsCorrect(
        day: LocalDate,
        userTimeTreatment: UserTimeTreatment,
        beautyTreatmentObj: BeautyTreatmentInfo
    ): Boolean {
        val listForDay = getUserTimeTreatmentByDay2(day.toString())
        var bool = true
        if (listForDay.isNotEmpty()) {
            val startOfAdded = LocalTime.parse(
                userTimeTreatment.time,
                DateTimeFormatter.ofPattern("HH:mm")
            )
            val endOfAdded = startOfAdded.plusMinutes(beautyTreatmentObj.time.toLong())
            listForDay.forEach {
                val timeStart = LocalTime.parse(
                    it.time,
                    DateTimeFormatter.ofPattern("HH:mm")
                )
                val timeEnd = timeStart.plusMinutes(it.beautyTreatmentTime.toLong())
                if (startOfAdded < timeStart) {
                    if (endOfAdded > timeStart) {
                        bool = false
                    }
                } else if (startOfAdded > timeStart) {
                    if (startOfAdded < timeEnd) {
                        bool = false
                    }
                }
            }

        }
        return bool
    }
}