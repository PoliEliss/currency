package com.rorono.a22recycler

import com.rorono.a22recycler.repository.Repository
import com.rorono.a22recycler.utils.Rounding
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mockito
import org.mockito.kotlin.isNotNull
import org.mockito.kotlin.mock

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    val repository = mock<Repository>()


    @Before
    fun init() {
        //  repository = mock<Repository>()
        //  viewModel = CurrencyViewModel(repository = repository)

    }
    @After
    fun after(){
        Mockito.reset(repository)
    }

  /*  @Test
   suspend fun repository():Unit {
        val testDate = "2022-06-23"
        val viewModel = CurrencyViewModel(repository=repository)
        viewModel.getCurrency(testDate)
        val actual =viewModel.listCurrency.value
        assertEquals(isNotNull(),actual)

    }*/

    @Test
    fun getPositiveRounding() { //округляем положительное число без чисел после запятой
        val actual = Rounding.getTwoNumbersAfterDecimalPoint(36.8599)
        val expected = 36.85
        val delta = 0.2
        assertEquals(expected, actual, delta)
    }

    @Test
    fun getPositiveRoundingWith() { //округляем положительное число без чисел после запятой
        val actual = Rounding.getTwoNumbersAfterDecimalPoint(12.349999)
        val expected = 12.34
        val delta = 0.0
        assertEquals(expected, actual, delta)
    }

}

class FakeRepository {
    fun getCurrency(data: String): Result {
        return Result.Error("Не удалось получить данные")
    }
}