package com.example.autosend.activities.activities.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.autosend.activities.activities.UI.AutoSendViewModel
import com.example.autosend.activities.activities.activitie.CalendarActivity
import com.example.autosend.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import layout.ActivityMonthlyIncome

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var permissionsLauncher: ActivityResultLauncher<Array<String>>
    private var sendSMSpermissionGranted = false
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setClickListeners()

        setContentView(binding.root)
        permissionsLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                sendSMSpermissionGranted = permissions[Manifest.permission.READ_EXTERNAL_STORAGE]
                    ?: sendSMSpermissionGranted
            }
        requestSmsPermission()
    }



    private fun setClickListeners() {
        binding.cotactBookButton.setOnClickListener {
            val intent = Intent(this, ContactBook::class.java)
            startActivity(intent)
        }
        binding.beautyTreatmentsButton.setOnClickListener {
            val intent = Intent(this, BeautyTreatmentsActivity::class.java)
            startActivity(intent)
        }
        binding.calendarButton.setOnClickListener {
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)
        }

        binding.settingsSMSButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        binding.monthlyIncomeButton.setOnClickListener {
            val intent = Intent(this, ActivityMonthlyIncome::class.java)
            startActivity(intent)
        }
    }


    private fun requestSmsPermission() {
        val sendSMSPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.SEND_SMS
        ) == PackageManager.PERMISSION_GRANTED

        sendSMSpermissionGranted = sendSMSPermission
        val requestList = mutableListOf<String>()
        if (!sendSMSPermission) {
            requestList.add(Manifest.permission.SEND_SMS)

        }
        if (requestList.isNotEmpty()) {
            permissionsLauncher.launch(requestList.toTypedArray())
        }
    }
}
