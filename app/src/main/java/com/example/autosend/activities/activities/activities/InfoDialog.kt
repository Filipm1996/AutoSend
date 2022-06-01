package com.example.autosend.activities.activities.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.autosend.activities.activities.db.entities.UserTimeTreatment
import com.example.autosend.databinding.InfoDialogBinding

class InfoDialog(
    private val userTimeTreatment: List<UserTimeTreatment>?,
) : DialogFragment() {
    private var setUpdeleteClickListener : ((List<UserTimeTreatment>)->Unit)? = null
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
            setUpdeleteClickListener?.invoke(userTimeTreatment!!)
            dismiss()
        }

        binding.dismissButton.setOnClickListener {
            dismiss()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUpInfo() {
        if(userTimeTreatment!!.size == 1) {
            binding.nameOfPerson.text = userTimeTreatment[0].name
            binding.nameOfBeautyTreatment.text = userTimeTreatment[0].beautyTreatmentName
            binding.startTime.text = userTimeTreatment[0].time
            binding.duration.text = "${userTimeTreatment[0].beautyTreatmentTime} min"
            binding.price.text = "${userTimeTreatment[0].beautyTreatmentPrice} zł"
        }else{
            binding.nameOfPerson.text = userTimeTreatment[0].name + " , " + userTimeTreatment[1].name
            binding.nameOfBeautyTreatment.text = userTimeTreatment[0].beautyTreatmentName +" , " + userTimeTreatment[1].beautyTreatmentName
            binding.startTime.text = userTimeTreatment[0].time + " , " + userTimeTreatment[1].time
            binding.duration.text = userTimeTreatment[0].beautyTreatmentTime + " , " + userTimeTreatment[1].beautyTreatmentTime + " min"
            binding.price.text = userTimeTreatment[0].beautyTreatmentPrice + " , " + userTimeTreatment[1].beautyTreatmentPrice + " zł"
        }
    }

    fun deleteUserTimeTreatment(callback :(List<UserTimeTreatment>)->Unit){
        this.setUpdeleteClickListener = callback
    }
}