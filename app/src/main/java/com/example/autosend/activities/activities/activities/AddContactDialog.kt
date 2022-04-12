package com.example.autosend.activities.activities.activities

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import androidx.core.text.isDigitsOnly
import com.example.autosend.activities.activities.db.ContactInfo
import com.example.autosend.activities.activities.repositories.Repository
import com.example.autosend.databinding.AddContactDialogBinding

class AddContactDialog(context: Context, var onAddButtonClicked: onAddButtonClicked) : AppCompatDialog(context) {
    private lateinit var binding: AddContactDialogBinding
    private lateinit var repository: Repository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddContactDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        repository = Repository(context)
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.addButton.setOnClickListener {
            val name = binding.editTextNameAndSurrname.text.toString()
            val number = binding.editTextPhone.text.toString()
            if( name.isNotEmpty() && number.isNotEmpty() && number.isDigitsOnly()){
                val contactInfo = ContactInfo(name, number)
                onAddButtonClicked.addToDb(contactInfo)
                Toast.makeText(context, "Dodano ${contactInfo.nameAndSurrname} do bazy kontaktów", Toast.LENGTH_LONG).show()
            }else { Toast.makeText(context, "Wprowadź prawidłowe dane", Toast.LENGTH_LONG).show()}
        }
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }
}