package com.example.autosend.activities.activities.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.autosend.activities.activities.db.entities.BeautyTreatmentInfo
import com.example.autosend.activities.activities.db.entities.UserTimeTreatment
import com.example.autosend.databinding.InfoDialogBinding

class InfoDialog(
    private val userTimeTreatment: UserTimeTreatment,
) : DialogFragment() {
    private var setUpdeleteClickListener : ((UserTimeTreatment)->Unit)? = null
    private lateinit var binding : InfoDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = InfoDialogBinding.inflate(layoutInflater)
        setUpInfo()
        setUpClickListeners()
        return binding.root
    }

    private fun setUpClickListeners() {
        binding.deleteButton.setOnClickListener {
            setUpdeleteClickListener?.invoke(userTimeTreatment)
            dismiss()
        }

        binding.dismissButton.setOnClickListener {
            dismiss()
        }
    }

    private fun setUpInfo() {
        binding.nameOfPerson.text = userTimeTreatment.name
        binding.nameOfBeautyTreatment.text = userTimeTreatment.beautyTreatmentName
        binding.startTime.text = userTimeTreatment.time
        binding.duration.text = "${userTimeTreatment.beautyTreatmentTime} min"
        binding.price.text = "${userTimeTreatment.beautyTreatmentPrice} zł"
    }

    fun deleteUserTimeTreatment(callback :(UserTimeTreatment)->Unit){
        this.setUpdeleteClickListener = callback
    }
}