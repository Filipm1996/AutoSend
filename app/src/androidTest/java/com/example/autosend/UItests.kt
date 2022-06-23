package com.example.autosend






import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import com.example.autosend.MyViewAction.waitForView
import com.example.autosend.activities.activities.UI.BeautyTreatmentAdapter
import com.example.autosend.activities.activities.UI.ContactBookAdapter
import com.example.autosend.activities.activities.activities.BeautyTreatmentsActivity
import com.example.autosend.activities.activities.activities.ContactBook
import com.example.autosend.activities.activities.activities.MainActivity
import com.example.autosend.activities.activities.activities.SettingsActivity
import com.example.autosend.activities.activities.dagger_hilt.Module
import com.example.autosend.activities.activities.db.entities.BeautyTreatmentInfo
import com.example.autosend.activities.activities.db.entities.ContactInfo
import com.example.autosend.activities.activities.repositories.FakeRepository
import com.example.autosend.activities.activities.repositories.RepositoryDefault
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Singleton


@UninstallModules(Module::class)
@HiltAndroidTest
class UItests {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fakeRepository: RepositoryDefault

    @Before
    fun setUp(){
        hiltRule.inject()
    }

    @dagger.Module
    @InstallIn(SingletonComponent::class)
    object  TestModule {

        @Singleton
        @Provides
        fun provideRepository() : RepositoryDefault = FakeRepository()
    }

    @Test
    fun contact_book_button_click_switch_to_contact_book_activity () {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.cotactBookButton)).perform(click())
        onView(withId(R.id.constraintLayoutContactBook)).check(matches(isDisplayed()))
    }

    @Test
    fun beauty_treatments_button_switch_to_beauty_treatment_activity() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.beauty_treatmentsButton)).perform(click())
        onView(withId(R.id.constraintLayoutBeautyTreatments)).check(matches(isDisplayed()))
    }

    @Test
    fun calendarButton_button_switch_to_calendar_activity() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.calendarButton)).perform(click())
        onView(withId(R.id.calendatConstraintLayout)).check(matches(isDisplayed()))
    }

    @Test
    fun settingsSMSButton_switch_to_settings_activity() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.settingsSMSButton)).perform(click())
        onView(withId(R.id.contraintLayoutSettings)).check(matches(isDisplayed()))
    }

    @Test
    fun monthlyIncomeButton_switch_to_monthly_income_activity() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.monthlyIncomeButton)).perform(click())
        onView(isRoot()).perform(waitForView(R.id.constraintLayoutMonthlyIncome, 100))
        onView(withId(R.id.constraintLayoutMonthlyIncome)).check(matches(isDisplayed()))
    }

    @Test
    fun click_add_button_in_contact_activity_start_dialog_fragment(){
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.cotactBookButton)).perform(click())
        onView(withId(R.id.constraintLayoutContactBook)).check(matches(isDisplayed()))
        onView(withId(R.id.addButton)).perform(click())
        onView(withId(R.id.constraintLayoutAddDialog)).check(matches(isDisplayed()))
    }

    @Test
    fun add_contact_to_db(){
        ActivityScenario.launch(ContactBook::class.java)
        onView(isRoot()).perform(waitForView(R.id.addButton, 100))
        onView(withId(R.id.addButton)).perform(click())
        onView(withId(R.id.constraintLayoutAddDialog)).check(matches(isDisplayed()))
        onView(withId(R.id.editTextNameAndSurrname)).perform(typeText("Filip Midura"))
        onView(withId(R.id.editTextPhone)).perform(typeText("432543645"), closeSoftKeyboard())
        onView(withId(R.id.addButton)).perform(click())
        onView(withId(R.id.constraintLayoutContactBook)).check(matches(isDisplayed()))
    }

    @Test
    fun recycler_view_display_correct_name_in_contact_book() {
        ActivityScenario.launch(ContactBook::class.java)
        runBlocking {
            fakeRepository.insertContactInfoToDb(ContactInfo("Filip Midura", "432532521"))
        }
        onView(withId(R.id.recyclerViewContactBook)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1))
            .check(matches(hasDescendant(withText("Filip Midura"))))
    }
    
    @Test
    fun clicking_delete_button_delete_item_from_recyclerView_in_contact_book() {
        val uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        ActivityScenario.launch(ContactBook::class.java)
        runBlocking {
            fakeRepository.insertContactInfoToDb(ContactInfo("Filip Midura", "432532521"))
            fakeRepository.insertContactInfoToDb(ContactInfo("Filip Miduraa", "432532522"))
        }
        onView(withId(R.id.recyclerViewContactBook))
            .perform(actionOnItemAtPosition<ContactBookAdapter.ViewHolder>(1, MyViewAction.clickChildViewWithId(R.id.deleteButton)))
        val button = uiDevice.findObject(UiSelector().text("TAK"))
        if (button.exists() && button.isEnabled) {
            button.click()
        }
        onView(isRoot()).perform(waitForView(R.id.recyclerViewContactBook, 100))
        onView(withId(R.id.recyclerViewContactBook)).check(matches(hasChildCount(1)))
        }

    @Test
    fun clicking_add_button_add_beautyTreatment_to_Db(){
        ActivityScenario.launch(BeautyTreatmentsActivity::class.java)
        onView(withId(R.id.addBeautyTreatmentFloatingButton)).perform(click())
        onView(withId(R.id.beautyTreatmentDialog)).check(matches(isDisplayed()))
        onView(withId(R.id.nameOfBeautyTreatment)).perform(typeText("depilacja"))
        onView(withId(R.id.priceOFBeautyTreatment)).perform(typeText("100"))
        onView(withId(R.id.timeOfBeautyTreatment)).perform(typeText("60"))
        onView(withId(R.id.costOfBeautyTreatment)).perform(typeText("10"))
        onView(withId(R.id.addBeautyTreatmentButton)).perform(click())
        onView(withId(R.id.recyclerViewBeautyTreatments)).check(matches(isDisplayed()))
    }

    @Test
    fun clicking_delete_button_remove_item_from_recyclerView_in_beautyTreatmentActivity (){
        val uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        ActivityScenario.launch(BeautyTreatmentsActivity::class.java)
        runBlocking {
            fakeRepository.insertBeautyTreatment(BeautyTreatmentInfo("depilacja","100","60","10"))
            fakeRepository.insertBeautyTreatment(BeautyTreatmentInfo("oczyszczanie skory","70","30","20"))
        }
        onView(withId(R.id.recyclerViewBeautyTreatments))
            .perform(actionOnItemAtPosition<BeautyTreatmentAdapter.ViewHolder>(0, MyViewAction.clickChildViewWithId(R.id.deleteBeautyTreatmentButton)))
        val button = uiDevice.findObject(UiSelector().text("TAK"))
        if (button.exists() && button.isEnabled) {
            button.click()
        }
        onView(isRoot()).perform(waitForView(R.id.recyclerViewBeautyTreatments, 100))
        onView(withId(R.id.recyclerViewBeautyTreatments)).check(matches(hasChildCount(1)))
    }

//    @Test
//    fun clicking_edit_button_display_dialog(){
//        val uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
//        ActivityScenario.launch(SettingsActivity::class.java)
//        val button = uiDevice.findObject(UiSelector().text("EDYCJA TEKSTU"))
//        if (button.exists() && button.isEnabled) {
//            button.click()
//        }
//        onView(isRoot()).perform(waitForView(R.id.constraintLayoutEditTextDialog, 1000))
//        onView(withId(R.id.constraintLayoutEditTextDialog)).check(matches(isDisplayed()))
//    }
    }
