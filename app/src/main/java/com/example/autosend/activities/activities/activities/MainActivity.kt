package com.example.autosend.activities.activities.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.autosend.R
import com.example.autosend.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setClickListeners()
        setContentView(binding.root)
    }

    private fun setClickListeners() {
        binding.cotactBookButton.setOnClickListener {
            val intent = Intent (this, ContactBook::class.java )
            startActivity(intent)
        }
    }
}