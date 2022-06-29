package com.rorono.a22recycler


import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import com.rorono.a22recycler.presentation.MainActivity

import org.junit.Before
import org.junit.Test


class MainActivityTest {
    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = launch(MainActivity::class.java)


    }

    @Test
    fun test() {
      //  onView(ViewMatchers.withId(R.id.toolbar)).check(matches(isDisplayed)))

    }
}