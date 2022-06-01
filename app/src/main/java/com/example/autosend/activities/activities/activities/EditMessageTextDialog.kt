package com.example.autosend.activities.activities.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.autosend.databinding.EditMessageTextDialogBinding

class EditMessageTextDialog : DialogFragment(){
    private var onConfirmButtonClick : ((String)->Unit)? = null
    private lateinit var binding : EditMessageTextDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EditMessageTextDialogBinding.inflate(layoutInflater)
        setUpClickListeners()
        return binding.root
    }

    private fun setUpClickListeners() {
        binding.cancelEditTextButton.setOnClickListener {
            dismiss()
        }
        binding.confirmEditTextButton.setOnClickListener {
            if(binding.editText.text.isNotEmpty()){
                val editText : String = binding.editText.text.toString()
                onConfirmButtonClick!!.invoke(editText)
            }else{
                Toast.makeText(requireContext(),"Uzupełnij poprawnie dane", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun setOnConfirmButtonClick(callback : (String)->Unit){
        this.onConfirmButtonClick = callback
    }
}