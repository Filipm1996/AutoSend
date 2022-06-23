package layout

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.autosend.activities.activities.UI.AutoSendViewModel
import com.example.autosend.activities.activities.UI.MonthlyIncomeAdapter
import com.example.autosend.activities.activities.UI.ViewModelFactory
import com.example.autosend.activities.activities.db.entities.MonthlyIncomeInfo
import com.example.autosend.activities.activities.db.entities.UserTimeTreatment
import com.example.autosend.activities.activities.repositories.Repository
import com.example.autosend.databinding.ActivityMonthlyIncomeBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.Month

@AndroidEntryPoint
class ActivityMonthlyIncome : AppCompatActivity(){
    private  var monthlyIncomeAdapter = MonthlyIncomeAdapter()
    private val viewModel: AutoSendViewModel by viewModels()
    private lateinit var binding : ActivityMonthlyIncomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMonthlyIncomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.year.text = viewModel.thisYear.toString()
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        binding.recyclerViewForMonthlyIncome.layoutManager = LinearLayoutManager(this)
            viewModel.getAllUserTimeTreatments().observe(this, Observer { listOfUserTimeTreatments ->
                val list = viewModel.updatingListOfMonthlyIncome(listOfUserTimeTreatments)
                setUpClickListeners(listOfUserTimeTreatments)
                monthlyIncomeAdapter.updateList(list)
                binding.recyclerViewForMonthlyIncome.adapter = monthlyIncomeAdapter
            })

    }

    private fun setUpClickListeners(listOfUserTimeTreatments: List<UserTimeTreatment>) {
        binding.backButton.setOnClickListener {
            viewModel.thisYear = viewModel.thisYear.minus(1)
            binding.year.text = viewModel.thisYear.toString()
            val list = viewModel.updatingListOfMonthlyIncome(listOfUserTimeTreatments)
            monthlyIncomeAdapter.updateList(list)
        }
        binding.forwardButton.setOnClickListener {
            viewModel.thisYear = viewModel.thisYear.plus(1)
            binding.year.text = viewModel.thisYear.toString()
            val list = viewModel.updatingListOfMonthlyIncome(listOfUserTimeTreatments)
            monthlyIncomeAdapter.updateList(list)
        }
    }





}