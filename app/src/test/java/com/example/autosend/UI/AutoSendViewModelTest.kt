package com.example.autosend.UI

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.autosend.activities.activities.UI.AutoSendViewModel
import com.example.autosend.activities.activities.db.entities.BeautyTreatmentInfo
import com.example.autosend.activities.activities.db.entities.MonthlyIncomeInfo
import com.example.autosend.activities.activities.db.entities.UserTimeTreatment
import com.example.autosend.activities.activities.repositories.FakeRepository
import com.example.autosend.activities.activities.repositories.Repository
import com.example.autosend.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import io.mockk.InternalPlatformDsl.toStr
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import java.time.LocalDate


@ExperimentalCoroutinesApi
class AutoSendViewModelTest {
    private lateinit var repository : Repository
    private lateinit var viewModel : AutoSendViewModel

    @ExperimentalCoroutinesApi
    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        repository = mockk()
        viewModel = AutoSendViewModel(repository)
            Dispatchers.setMain(testDispatcher)
    }
    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `sumUpWeeklyIncome return correct income pt1`() = runBlocking {
        val localDate = LocalDate.parse("2022-05-30")
        val listOfUserTimeTreatment = listOf(UserTimeTreatment("Filip","534231453","15:05","2022-06-11","wodorowe",
            "170","30","60"))
        every { repository.getUserTimeTreatmentByDay2("2022-05-30") } returns listOfUserTimeTreatment
        every { repository.getUserTimeTreatmentByDay2("2022-05-31") } returns listOf()
        every { repository.getUserTimeTreatmentByDay2("2022-06-01") } returns listOf()
        every { repository.getUserTimeTreatmentByDay2("2022-06-02") } returns listOf()
        every { repository.getUserTimeTreatmentByDay2("2022-06-03") } returns listOf()
        val income = viewModel.sumUpWeeklyIncome(localDate)
        assertThat(income == 140.00).isTrue()
    }

    @Test
    fun `sumUpWeeklyIncome return correct income pt2`() = runBlocking {
        val localDate = LocalDate.parse("2022-05-30")
        val listOfUserTimeTreatment = listOf(UserTimeTreatment("Filip","534231453","15:05","2022-06-11","wodorowe",
            "170","30","60"))
        val listOfUserTimeTreatment2 = listOf(UserTimeTreatment("Filip","534231453","15:04","2022-06-12","depilacja",
            "90","20","60"))
        every { repository.getUserTimeTreatmentByDay2("2022-05-30") } returns listOfUserTimeTreatment
        every { repository.getUserTimeTreatmentByDay2("2022-05-31") } returns listOfUserTimeTreatment2
        every { repository.getUserTimeTreatmentByDay2("2022-06-01") } returns listOf()
        every { repository.getUserTimeTreatmentByDay2("2022-06-02") } returns listOf()
        every { repository.getUserTimeTreatmentByDay2("2022-06-03") } returns listOf()
        val income = viewModel.sumUpWeeklyIncome(localDate)
        assertThat(income == 210.00).isTrue()
    }
    @Test
    fun `sumUpWeeklyIncome return correct 0 income pt3`() = runBlocking {
        val localDate = LocalDate.parse("2022-05-30")
        every { repository.getUserTimeTreatmentByDay2(any()) } returns listOf()
        val income = viewModel.sumUpWeeklyIncome(localDate)
        assertThat(income == 0.00).isTrue()
    }

    @Test
    fun `updatingListOfMonthlyIncome return correct listOfMonthlyIncome`() = runBlocking {
        viewModel = spyk(AutoSendViewModel(repository), recordPrivateCalls = true)
        every { viewModel["calculateForMonth"](any<Int>(), any<List<UserTimeTreatment>>()) } returns MonthlyIncomeInfo("0","100", 400.00,300.00,150.00,35.00)
        every { viewModel.updatingListOfMonthlyIncome(any()) } answers  {callOriginal()}
        val listOfMonthlyIncome = viewModel.updatingListOfMonthlyIncome(listOf())
        assertThat(listOfMonthlyIncome[0] == MonthlyIncomeInfo("0","100", 400.00,300.00,150.00,35.00)).isTrue()
    }

    @Test
    fun `getUserTimeTreatmentsForToday return correct listForToday`() = runBlocking {
        viewModel = spyk(AutoSendViewModel(repository), recordPrivateCalls = true)
        val listOfUserTimeTreatment = listOf(UserTimeTreatment("Filip","534231453","15:05","2022-06-11","wodorowe",
            "170","30","60"),UserTimeTreatment("Filip","534231453","15:04","2022-06-12","depilacja",
            "90","20","60"))
        val date = LocalDate.parse("2022-06-11")
        every { viewModel["getToday"] () } returns date
        every { viewModel.getUserTimeTreatmentsForToday(any()) } answers {callOriginal()}
        val listForToday = viewModel.getUserTimeTreatmentsForToday(listOfUserTimeTreatment)
        assertThat(listForToday.size == 1).isTrue()
        assertThat(listForToday[0] ==UserTimeTreatment("Filip","534231453","15:05","2022-06-11","wodorowe",
            "170","30","60") ).isTrue()
    }

    @Test
    fun `getUserTimeTreatmentsForToday return correct listForToday pt2`() = runBlocking {
        viewModel = spyk(AutoSendViewModel(repository), recordPrivateCalls = true)
        val listOfUserTimeTreatment = listOf(UserTimeTreatment("Filip","534231453","15:05","2022-06-11","wodorowe",
            "170","30","60"),UserTimeTreatment("Filip","534231453","12:04","2022-06-11","depilacja",
            "90","20","60"))
        val date = LocalDate.parse("2022-06-11")
        every { viewModel["getToday"] () } returns date
        every { viewModel.getUserTimeTreatmentsForToday(any()) } answers {callOriginal()}
        val listForToday = viewModel.getUserTimeTreatmentsForToday(listOfUserTimeTreatment)
        assertThat(listForToday.size == 2).isTrue()
        assertThat(listForToday == listOfUserTimeTreatment ).isTrue()
    }

    @Test
    fun `updatePrices correctly change prices`() = runBlocking {
        val listOfUserTimeTreatment = listOf(UserTimeTreatment("Filip","534231453","15:05","2022-06-11","wodorowe",
            "110","30","70"))
        val listOfUserTimeTreatment2 = listOf(UserTimeTreatment("Filip","534231453","15:04","2022-06-12","wodorowe",
            "110","30","70"))
        val fakeRepository = FakeRepository()
        viewModel = spyk(AutoSendViewModel(fakeRepository), recordPrivateCalls = true)
        every { viewModel.getUserTimeTreatmentByDay2("2022-06-11") } returns listOfUserTimeTreatment
        every { viewModel.getUserTimeTreatmentByDay2("2022-06-12") } returns listOfUserTimeTreatment2
        coEvery { viewModel.updatePrices(any(),any(),any(),any()) } answers {callOriginal()}
        coEvery { viewModel.insertUserTimeTreatmentFromDb(any()) } answers {callOriginal()}
        coEvery { viewModel.deleteBeautyTreatmentFromDb(any()) } answers {callOriginal()}
        every { viewModel.getAllUserTimeTreatments() } answers {callOriginal()}
        viewModel.updatePrices("2022-06-12","2022-06-11", BeautyTreatmentInfo("wodorowe","120","70","30"),"150")
        val list = viewModel.getAllUserTimeTreatments().getOrAwaitValue()
        delay(100)
        assertThat(list.size == 2).isTrue()
        assertThat(list[0].beautyTreatmentPrice == "150").isTrue()
        assertThat(list[1].beautyTreatmentPrice == "150").isTrue()

    }

    @Test
    fun `updatePrices correctly change prices pt2`() = runBlocking {
        val listOfUserTimeTreatment = listOf(UserTimeTreatment("Filip","534231453","15:05","2022-06-11","depilacja",
            "100","30","70"))
        val listOfUserTimeTreatment2 = listOf(UserTimeTreatment("Filip","534231453","15:04","2022-06-12","wodorowe",
            "130","30","70"))
        val fakeRepository2 = FakeRepository()
        viewModel = spyk(AutoSendViewModel(fakeRepository2), recordPrivateCalls = true)
        every { viewModel.getUserTimeTreatmentByDay2("2022-06-11") } returns listOfUserTimeTreatment
        every { viewModel.getUserTimeTreatmentByDay2("2022-06-12") } returns listOfUserTimeTreatment2
        coEvery { viewModel.updatePrices(any(),any(),any(),any()) } answers {callOriginal()}
        coEvery { viewModel.insertUserTimeTreatmentFromDb(any()) } answers {callOriginal()}
        coEvery  { viewModel.deleteBeautyTreatmentFromDb(any()) } answers {callOriginal()}
        every { viewModel.getAllUserTimeTreatments() } answers {callOriginal()}
        viewModel.updatePrices("2022-06-12","2022-06-11", BeautyTreatmentInfo("wodorowe","120","70","30"),"150")
        val list = viewModel.getAllUserTimeTreatments().getOrAwaitValue()
        delay(100)
        assertThat(list.size == 1).isTrue()
        assertThat(list[0].beautyTreatmentPrice == "150").isTrue()

    }

    @Test
    fun `checkIfHourIsCorrect returns correct Boolean`() = runBlocking {
        val listOfUserTimeTreatment = listOf(UserTimeTreatment("Filip","534231453","15:05","2022-06-11","depilacja",
            "100","30","70"))
        viewModel = spyk(AutoSendViewModel(repository), recordPrivateCalls = true)
        coEvery { viewModel.getUserTimeTreatmentByDay2("2022-06-11") } returns listOfUserTimeTreatment
        every { viewModel.checkIfTheHourIsCorrect(any(),any()) } answers {callOriginal()}
        val day = LocalDate.parse("2022-06-11")
        val bool = viewModel.checkIfTheHourIsCorrect(day,
            UserTimeTreatment("Janina","534232133","14:05","2022-06-11","wodorowe",
                "150","40","80")
        )
        assertThat(bool).isFalse()
    }

    @Test
    fun `checkIfHourIsCorrect returns correct Boolean pt2`() = runBlocking {
        val listOfUserTimeTreatment = listOf(UserTimeTreatment("Filip","534231453","15:05","2022-06-11","depilacja",
            "100","30","70"))
        viewModel = spyk(AutoSendViewModel(repository), recordPrivateCalls = true)
        every { viewModel.getUserTimeTreatmentByDay2("2022-06-11") } returns listOfUserTimeTreatment
        every { viewModel.checkIfTheHourIsCorrect(any(),any()) } answers {callOriginal()}
        val day = LocalDate.parse("2022-06-11")
        val bool = viewModel.checkIfTheHourIsCorrect(day,
            UserTimeTreatment("Janina","534232133","14:05","2022-06-11","wodorowe",
                "150","40","59")
        )
        assertThat(bool).isTrue()
    }
}