package com.example.autosend

import android.view.View
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.util.HumanReadables
import androidx.test.espresso.util.TreeIterables
import org.hamcrest.CoreMatchers.any
import org.hamcrest.Matcher
import java.util.concurrent.TimeoutException


object MyViewAction {
    fun clickChildViewWithId(id: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            override fun getDescription(): String {
                return "Click on a child view with specified id."
            }

            override fun perform(uiController: UiController, view: View) {
                val v = view.findViewById<View>(id)
                v.performClick()
            }
        }
    }

    fun waitForView(viewId: Int, timeout: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return isRoot()
            }

            override fun getDescription(): String {
                return "wait for a specific view with id $viewId; during $timeout millis."
            }

            override fun perform(uiController: UiController, rootView: View) {
                uiController.loopMainThreadUntilIdle()
                val startTime = System.currentTimeMillis()
                val endTime = startTime + timeout
                val viewMatcher = withId(viewId)

                do {
                    // Iterate through all views on the screen and see if the view we are looking for is there already
                    for (child in TreeIterables.breadthFirstViewTraversal(rootView)) {
                        // found view with required ID
                        if (viewMatcher.matches(child)) {
                            return
                        }
                    }
                    // Loops the main thread for a specified period of time.
                    // Control may not return immediately, instead it'll return after the provided delay has passed and the queue is in an idle state again.
                    uiController.loopMainThreadForAtLeast(100)
                } while (System.currentTimeMillis() < endTime) // in case of a timeout we throw an exception -> test fails
                throw PerformException.Builder()
                    .withCause(TimeoutException())
                    .withActionDescription(this.description)
                    .withViewDescription(HumanReadables.describe(rootView))
                    .build()
            }
        }
    }

    class WaitUntilGoneAction(private val timeout: Long) : ViewAction {

        override fun getConstraints(): Matcher<View> {
            return any(View::class.java)
        }

        override fun getDescription(): String {
            return "wait up to $timeout milliseconds for the view to be gone"
        }

        override fun perform(uiController: UiController, view: View) {

            val endTime = System.currentTimeMillis() + timeout

            do {
                if (view.visibility == View.GONE) return
                uiController.loopMainThreadForAtLeast(50)
            } while (System.currentTimeMillis() < endTime)

            throw PerformException.Builder()
                .withActionDescription(description)
                .withCause(TimeoutException("Waited $timeout milliseconds"))
                .withViewDescription(HumanReadables.describe(view))
                .build()
        }

        fun waitUntilGone(timeout: Long): ViewAction {
            return WaitUntilGoneAction(timeout)
        }
    }
}