package com.example.autosend.activities.activities.activities


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.DialogFragment
import com.example.autosend.activities.activities.db.entities.ContactInfo
import com.example.autosend.databinding.AddContactDialogBinding


class AddContactDialog : DialogFragment(){
    private lateinit var binding: AddContactDialogBinding
    private var onAddButtonClicked : ((ContactInfo)-> Unit)? = null
    private var onCancelButtonClicked :  (()->Unit)? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddContactDialogBinding.inflate(layoutInflater)
        setUpClickListeners()
        return binding.root
    }

    fun setUpAddButtonClicked (callback : (ContactInfo)->Unit){
        this.onAddButtonClicked = callback
    }

    fun setUpCancelButtonClicked (callback : ()-> Unit){
        this.onCancelButtonClicked = callback
    }
    private fun setUpClickListeners() {
        binding.addButton.setOnClickListener {
            val nameText = binding.editTextNameAndSurrname.text.toString()
            val number = binding.editTextPhone.text.toString()

            if(nameText.isNotEmpty() && nameText.length<25 &&  number.length==9 && number.isDigitsOnly()){
                val contactInfo = ContactInfo(nameText,number)
                onAddButtonClicked!!.invoke(contactInfo)
                Toast.makeText(requireContext(),"Dodano ${contactInfo.nameAndSurrname} do kontaktów!", Toast.LENGTH_LONG).show()
                dismiss()
            }else {
                Toast.makeText(requireContext(),"Wpisz poprawne dane",Toast.LENGTH_LONG).show()
            }
        }
        binding.cancelButton.setOnClickListener {
            onCancelButtonClicked!!.invoke()
        }
    }
}
