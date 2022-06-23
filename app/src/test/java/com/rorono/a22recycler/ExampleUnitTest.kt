package com.rorono.a22recycler

import com.rorono.a22recycler.repository.Repository
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

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

    var repository: FakeRepository? = null
    //var viewModel: CurrencyViewModel? = null

    @Before
    fun init() {
        repository = FakeRepository()
       // viewModel = CurrencyViewModel(repository = repository)

    }


}

class FakeRepository {
    fun getCurrency(data: String): Result {
        return Result.Error("Не удалось получить данные")
    }
}