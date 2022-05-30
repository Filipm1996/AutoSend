package com.example.autosend.activities.activities.activities


import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.autosend.R
import com.example.autosend.activities.activities.UI.AdapterForSettings
import com.example.autosend.activities.activities.UI.AutoSendViewModel
import com.example.autosend.activities.activities.UI.ViewModelFactory
import com.example.autosend.activities.activities.db.entities.MessageSMS
import com.example.autosend.activities.activities.db.entities.UserTimeTreatment
import com.example.autosend.activities.activities.repositories.Repository
import com.example.autosend.databinding.ActivitySettingsBinding


class SettingsActivity : AppCompatActivity() {
    lateinit var binding: ActivitySettingsBinding
    private lateinit var viewModel : AutoSendViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViewModel()
        viewModel.getMessageFromDb().observe(this, Observer {
            setUpMessage(it)
        })
        setUpRecyclerView()
    }

    private fun setUpMessage(message: MessageSMS?) {
        binding.message.text = message?.message ?: "Wpisz wiadomość"
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpRecyclerView() {
        val adapter = AdapterForSettings()
        binding.recyclerViewForSending.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewForSending.adapter = adapter
        viewModel.getAllUserTimeTreatments().observe(this, Observer {
            val list = viewModel.getUserTimeTreatmentsForToday(it)
            adapter.updateList(list)
            adapter.notifyDataSetChanged()
            setUpClickListeners(list)
        })
    }

    private fun setUpViewModel() {
        val factory = ViewModelFactory(Repository(this))
        viewModel = ViewModelProvider(this,factory)[AutoSendViewModel::class.java]
    }

    private fun setUpClickListeners(list : List<UserTimeTreatment>) {
        binding.editButton.setOnClickListener {
            val fm = supportFragmentManager
            val alertDialog = EditMessageTextDialog()
            alertDialog.setOnConfirmButtonClick {
                viewModel.deleteMessageFromDb()
                viewModel.insertMessageToDb(MessageSMS(it))
                binding.message.text = it
            }
            alertDialog.show(fm,"fragment alert")
        }
        binding.sendButton.setOnClickListener {
            val message = binding.message.text.toString()
            var requestCode = 0
            if(message.isNotEmpty()){
                for (i in list) {
                    sendSms(i.number, message, i.time, requestCode)
                    setUpNotification(i.number, i.name)
                    requestCode++
                }
            }
        }
    }


    @SuppressLint("UnspecifiedImmutableFlag")
    private fun sendSms(phoneNumber: String, message: String, time : String, requestCode : Int) {
        val fullPhoneNumber = "+48${phoneNumber}"
        val messageToSend = "$message \n\n Godzina zabiegu :  $time"
        val smsManager = this.getSystemService(SmsManager::class.java)
        val sentPI: PendingIntent = PendingIntent.getBroadcast(this, requestCode, Intent("SMS_SENT"), PendingIntent.FLAG_UPDATE_CURRENT)
        smsManager.sendTextMessage(fullPhoneNumber, null, messageToSend, sentPI, null)
    }



    private fun setUpNotification(phoneNumber: String, name: String){
        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel("sms_channel",
            "sms_channel",
            NotificationManager.IMPORTANCE_DEFAULT)
        channel.description = "sms_channel"
        mNotificationManager.createNotificationChannel(channel)

        var builder = NotificationCompat.Builder(this, "sms_channel")
            .setContentTitle("Wysłano sms")
            .setSmallIcon(R.drawable.ic_baseline_face_retouching_natural_24)
            .setContentText("Wysłano sms do $phoneNumber - $name")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        mNotificationManager.notify(0, builder.build())
    }


}