package com.rorono.a22recycler

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rorono.a22recycler.database.CurrencyDataBase
import com.rorono.a22recycler.network.RetrofitInstance
import com.rorono.a22recycler.presentation.CurrencyTransferFragment
import com.rorono.a22recycler.repository.Repository
import com.rorono.a22recycler.utils.Rounding
import kotlinx.coroutines.*
import kotlinx.coroutines.test.setMain
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(MockitoJUnitRunner::class)
class ExampleUnitTest {

    @Mock
    val mockRepository = mock<Repository>()
    @Mock
    private val dataBase:CurrencyDataBase? = mock<CurrencyDataBase>()
    private var viewModel: CurrencyViewModel? = null
    private var currencyTransferFragment: CurrencyTransferFragment? = null
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = CurrencyViewModel(mockRepository, dataBase = dataBase!!.currencyDao())
        currencyTransferFragment = CurrencyTransferFragment()
    }

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun successGetData() = runBlocking {
        Mockito.`when`(mockRepository.getCurrency("2022-06-01")).thenReturn(Result.Error("jjj"))
        viewModel!!.getCurrency("2022-06-01")
        val observer = Observer<String> {}
        //Получаем LiveData
        val liveData = viewModel!!.messageError
        val actual = viewModel!!.messageError.value
        liveData.observeForever(observer)
        assertNotNull(liveData.value)
    }

    @Test
    fun testRepository() {
        runBlocking {
            Mockito.`when`(mockRepository.getCurrency("2022-06-01")).thenReturn(Result.Error("jjj"))
            assertEquals(Result.Error("jjj"), mockRepository.getCurrency("2022-06-01"))
        }
    }

    @Test
    fun getData() {
        val actual = viewModel!!.getData()
        val expected = "2022-06-28"
        assertEquals(expected, actual)
    }

    @Test
    fun getPositiveRoundingWithOneNumberAfterDecimalPoint() {
        val actual = Rounding.getTwoNumbersAfterDecimalPoint(56.44449)
        val expected = 56.45
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
        val actual = viewModel!!.converterToCurrency(rate = -23.69, 7.0)

        val expected = IllegalArgumentException()
        assertEquals(expected, actual)
    }

    @Test
    fun getPositiveRoundingConverterToCurrency() {
        val actual = viewModel!!.converterToCurrency(rate = 89.23, 15.8)
        val expected = 0.18
        val delta = 0.2
        assertEquals(expected, actual, delta)
    }

    @Test
    fun getZeroConverterToCurrency() {
        val actual = viewModel!!.converterToCurrency(rate = 89.23, 0.0)
        val expected = 0.0
        val delta = 0.2
        assertEquals(expected, actual, delta)
    }

    @Test
    fun getPositiveRoundingTransferToRubles() {
        val actual = viewModel!!.transferToRubles(rate = 23.45, 15.89)
        val expected = 372.62
        val delta = 0.2
        assertEquals(expected, actual, delta)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getExceptionUseNegativeNumberTransferToRubles() {
        viewModel!!.converterToCurrency(rate = -23.69, -90.0)
    }

    @Test
    fun getZeroTransferToRubles() {
        val actual = viewModel!!.converterToCurrency(rate = 2.81, 1.23)
        val expected = 0.0
        val delta = 0.2
        assertEquals(expected, actual, delta)
    }


}

class FakeRepository : Repository(retrofit = RetrofitInstance) {
    override suspend fun getCurrency(data: String): Result {
        return Result.Error("Не удалось получить данные")
    }
}