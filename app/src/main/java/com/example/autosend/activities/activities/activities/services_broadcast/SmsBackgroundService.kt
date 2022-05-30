package com.example.autosend.activities.activities.activities


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.example.autosend.R
import com.example.autosend.activities.activities.db.entities.UserTimeTreatment
import com.example.autosend.activities.activities.repositories.Repository
import kotlinx.coroutines.*
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class SmsBackgroundService: LifecycleService(){
    private lateinit var repository: Repository
    private var listOfUserTimeTreatment = listOf<UserTimeTreatment>()
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        repository = Repository(this)
        repository.getAllUserTimeTreatments().observe(this) {
            listOfUserTimeTreatment = it
            CoroutineScope(Dispatchers.IO).launch {
                setUpLoop(listOfUserTimeTreatment)
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun getUserTimeTreatmentsForToday(intentData : List<UserTimeTreatment>) : List<UserTimeTreatment> {
        val todays =LocalDate.now().plusDays(1)
        val listForToday = mutableListOf<UserTimeTreatment>()
        for (i in intentData ){
            if(LocalDate.parse(i.day) == todays){
                listForToday.add(i)
            }
        }
        println(listForToday.size.toString())
        return listForToday
    }

    private fun setUpLoop(list: List<UserTimeTreatment>) {
        val istZoneId: ZoneId = ZoneId.of("Europe/Warsaw")
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val t = Timer()
        t.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val listForToday = getUserTimeTreatmentsForToday(list)
                val zdt = ZonedDateTime.now(istZoneId)
                val timeNow = formatter.format(zdt)
                println(timeNow)
                for (i in listForToday) {
                    val time = i.time
                    println(time)
                    if (time == timeNow) {
                        sendSms(i)
                    }
                }
                }
    }, 0, 60000)
    }

    private fun sendSms(userTimeTreatment: UserTimeTreatment) {
        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel("sms_channel",
            "sms_channel",
            NotificationManager.IMPORTANCE_DEFAULT)
        channel.description = "sms_channel"
        mNotificationManager.createNotificationChannel(channel)

        val builder = NotificationCompat.Builder(this, "sms_channel")
            .setContentTitle("Sms has been sent")
            .setSmallIcon(R.drawable.ic_baseline_face_retouching_natural_24)
            .setContentText("Sms dla ${userTimeTreatment.name} na zabieg ${userTimeTreatment.beautyTreatmentName} ")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        mNotificationManager.notify(0, builder.build())
    }

}