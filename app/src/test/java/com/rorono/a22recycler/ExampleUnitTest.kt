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

    private val repository: Repository = Mockito.mock(Repository::class.java)
    private var currencyTransferFragment: CurrencyTransferFragment? = null

    @Before
    fun init() {
        //  repository = mock<Repository>()
        //  viewModel = CurrencyViewModel(repository = repository)
        currencyTransferFragment = CurrencyTransferFragment()
    }

    @After
    fun after() {
        Mockito.reset(repository)
    }

    /*  @Test
     suspend fun repository():Unit {
          val testDate = "2022-06-23"
          val viewModel = CurrencyViewModel(repository=repository)
          Mockito.`when`(repository.getCurrency("2022-06-23")).thenReturn(isNotNull())
          viewModel.getCurrency(testDate)
          val actual =viewModel.listCurrency.value
          assertEquals(isNotNull(),actual)
      }*/


    @Test
    fun getPositiveRoundingWithOneNumberAfterDecimalPoint() {
        val actual = Rounding.getTwoNumbersAfterDecimalPoint(56.9)
        val expected = 56.90
        val delta = 0.2
        assertEquals(expected, actual, delta)
    }

    @Test
    fun getPositiveRoundingWithNumbersAboveTwoAfterDecimalPoint() {
        val actual = Rounding.getTwoNumbersAfterDecimalPoint(56.98769)
        val expected = 56.99
        val delta = 0.2
        assertEquals(expected, actual, delta)
    }

    @Test
    fun getNegativeRoundingWithOneNumberAfterDecimalPoint() {
        val actual = Rounding.getTwoNumbersAfterDecimalPoint(-23.4)
        val expected = -23.40
        val delta = 0.2
        assertEquals(expected, actual, delta)
    }

    @Test
    fun getNegativeRoundingWithNumbersAboveTwoAfterDecimalPoint() {
        val actual = Rounding.getTwoNumbersAfterDecimalPoint(-0.98769)
        val expected = -0.99
        val delta = 0.2
        assertEquals(expected, actual, delta)
    }

    @Test
    fun getRoundingZero() {
        val actual = Rounding.getTwoNumbersAfterDecimalPoint(0.0)
        val expected = 0.00
        val delta = 0.2
        assertEquals(expected, actual, delta)
    }

    @Test
    fun getRoundingZeroWithNumbersAboveTwoAfterDecimalPoint() {
        val actual = Rounding.getTwoNumbersAfterDecimalPoint(0.00000009)
        val expected = 0.00
        val delta = 0.2
        assertEquals(expected, actual, delta)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getExceptionUseNegativeNumberConverterToCurrency() {
        currencyTransferFragment!!.converterToCurrency(rate = -23.69, -90.0)
    }

    @Test
    fun getPositiveRoundingConverterToCurrency() {
        val actual = currencyTransferFragment!!.converterToCurrency(rate = 89.23, 15.8)
        val expected = 0.18
        val delta = 0.2
        assertEquals(expected, actual, delta)
    }

    @Test
    fun getZeroConverterToCurrency() {
        val actual = currencyTransferFragment!!.converterToCurrency(rate = 89.23, 0.0)
        val expected = 0.0
        val delta = 0.2
        assertEquals(expected, actual, delta)
    }

    @Test
    fun getPositiveRoundingTransferToRubles() {
        val actual = currencyTransferFragment!!.transferToRubles(rate = 23.45, 15.89)
        val expected = 372.62
        val delta = 0.2
        assertEquals(expected, actual, delta)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getExceptionUseNegativeNumberTransferToRubles() {
        currencyTransferFragment!!.converterToCurrency(rate = -23.69, -90.0)
    }

    @Test
    fun getZeroTransferToRubles() {
        val actual = currencyTransferFragment!!.transferToRubles(rate = 0.0, 0.0)
        val expected = 0.0
        val delta = 0.2
        assertEquals(expected, actual, delta)
    }
}

class FakeRepository {
    fun getCurrency(data: String): Result {
        return Result.Error("Не удалось получить данные")
    }
}