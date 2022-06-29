package com.rorono.a22recycler



import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.rorono.a22recycler.network.RetrofitInstance
import com.rorono.a22recycler.presentation.CurrencyTransferFragment
import com.rorono.a22recycler.presentation.MainActivity
import com.rorono.a22recycler.repository.Repository

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class CurrencyTransferFragmentTest {
   @get:Rule
    val mainActivity = ActivityScenarioRule(MainActivity::class.java)
    private lateinit var scenario: FragmentScenario<CurrencyTransferFragment>



    @Before

    fun setup() {

        scenario = launchFragmentInContainer()
        val retrofit = RetrofitInstance
        val repository = Repository(retrofit = retrofit)
        val viewModel = CurrencyViewModel(repository = repository)
    }

    @Test
    fun fixTest() {

        onView(withId(R.id.rates1)).check(matches(withText("ddddddddd")))
    }


}