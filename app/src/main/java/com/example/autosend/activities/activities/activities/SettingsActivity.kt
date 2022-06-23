package com.example.autosend.activities.activities.activities


import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.SmsManager
import android.telephony.SmsManager.getSmsManagerForSubscriptionId
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.fragment.app.viewModels
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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {
    private lateinit var smsManager: SmsManager
    lateinit var binding: ActivitySettingsBinding
    private val viewModel: AutoSendViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getMessageFromDb().observe(this) {
            setUpMessage(it)
        }
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
        viewModel.getAllUserTimeTreatments().observe(this) {
            val list = viewModel.getUserTimeTreatmentsForToday(it)
            adapter.updateList(list)
            adapter.notifyDataSetChanged()
            setUpClickListeners(list)
        }
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
        smsManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            this.getSystemService(SmsManager::class.java).createForSubscriptionId(requestCode)
        } else {
            getSmsManagerForSubscriptionId(requestCode)
        }
        val sentPI: PendingIntent = PendingIntent.getBroadcast(this, requestCode, Intent("SMS_SENT"), PendingIntent.FLAG_IMMUTABLE)
        smsManager.sendTextMessage(fullPhoneNumber, null, messageToSend, sentPI, null)
    }



    private fun setUpNotification(phoneNumber: String, name: String){
        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel("sms_channel",
            "sms_channel",
            NotificationManager.IMPORTANCE_DEFAULT)
        channel.description = "sms_channel"
        mNotificationManager.createNotificationChannel(channel)

        val builder = NotificationCompat.Builder(this, "sms_channel")
            .setContentTitle("Wysłano sms")
            .setSmallIcon(R.drawable.ic_baseline_face_retouching_natural_24)
            .setContentText("Wysłano sms do $phoneNumber - $name")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        mNotificationManager.notify(0, builder.build())
    }


}