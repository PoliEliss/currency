package com.rorono.a22recycler

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.ViewModelProvider
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.rorono.a22recycler.network.RetrofitInstance
import com.rorono.a22recycler.presentation.CurrencyFragment
import com.rorono.a22recycler.presentation.MainActivity
import com.rorono.a22recycler.repository.Repository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class CurrencyFragmentTest {
    @get:Rule
    private lateinit var scenarioActivity: ActivityScenario<MainActivity>

    private lateinit var scenario: FragmentScenario<CurrencyFragment>

    @Before
    fun setup() {
        scenarioActivity = ActivityScenario.launch(MainActivity::class.java)
        val retrofit = RetrofitInstance
        val repository = Repository(retrofit)

      /*  scenario = launchFragmentInContainer()
        val viewModelFactory = MainViewModelFactory(repository)
        val viewModel = ViewModelProvider(this@scenarioActivity,viewModelFactory)[CurrencyViewModel::class.java]*/
    }

    @Test
    fun fixTest() {
        Espresso.onView(ViewMatchers.withId(R.id.etDate))
            .check(ViewAssertions.matches(ViewMatchers.withText("ddddddddd")))
    }

}
