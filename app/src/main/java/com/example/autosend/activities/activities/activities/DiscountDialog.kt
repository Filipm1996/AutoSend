package com.example.autosend.activities.activities.activities


import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.autosend.activities.activities.UI.AutoSendViewModel
import com.example.autosend.activities.activities.UI.ViewModelFactory
import com.example.autosend.activities.activities.db.entities.BeautyTreatmentInfo
import com.example.autosend.activities.activities.repositories.Repository
import com.example.autosend.databinding.DiscountDialogBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class DiscountDialog(
    val beautyTreatmentInfo: BeautyTreatmentInfo
) : DialogFragment() {
    private val myFormat = "yyyy-MM-dd"
    private val sdf = SimpleDateFormat(myFormat)
    lateinit var dateFromSetListener: DatePickerDialog.OnDateSetListener
    lateinit var dateToSetListener: DatePickerDialog.OnDateSetListener
    var cal: Calendar = Calendar.getInstance()
    private val viewModel: AutoSendViewModel by viewModels()
    private lateinit var binding: DiscountDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DiscountDialogBinding.inflate(layoutInflater)
        setUpDatePicker()
        setUpClickListeners()
        binding.beautyTreatmentInfo.text = beautyTreatmentInfo.name
    }

    private fun setUpDatePicker() {
        dateFromSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                binding.dateFrom.text = sdf.format(cal.time)
            }
        dateToSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                binding.dateTo.text = sdf.format(cal.time)
            }
    }

    private fun setUpClickListeners() {
        binding.changeDateFromButton.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                dateFromSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.changeDateToButton.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                dateToSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()

        }

        binding.changePriceButton.setOnClickListener {
            val from = binding.dateFrom.text.toString()
            val to = binding.dateTo.text.toString()
            val newPrice = binding.newPrice.text.toString()
            viewModel.updatePrices(to, from, beautyTreatmentInfo, newPrice)
            Toast.makeText(requireContext(), "Zmieniono cenę", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }
}
