package com.rorono.a22recycler.presentation


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.rorono.a22recycler.R
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        val viewGroup = onView(
            allOf(
                withParent(withParent(withId(R.id.recyclerView))),
                isDisplayed()
            )
        )
        viewGroup.check(matches(isDisplayed()))

        val viewGroup2 = onView(
            allOf(
                withParent(withParent(withId(R.id.recyclerView))),
                isDisplayed()
            )
        )
        viewGroup2.check(matches(isDisplayed()))

        val viewGroup3 = onView(
            allOf(
                withParent(withParent(withId(R.id.recyclerView))),
                isDisplayed()
            )
        )
        viewGroup3.check(matches(isDisplayed()))
    }
}
