package by.alexeypalto.revtest.data

import by.alexeypalto.revtest.MockData
import by.alexeypalto.revtest.data.api.ApiService
import by.alexeypalto.revtest.data.repository.RatesRepositoryImpl
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RatesRepositoryTest {

    private lateinit var apiService: ApiService
    private lateinit var ratesRepository: RatesRepositoryImpl

    @Before
    fun before() {
        apiService = mockk(relaxed = true)
        ratesRepository = RatesRepositoryImpl(apiService)
    }

    @Test
    fun testGetRatesFromApi() {
        val testBaseRate = MockData.generateRandomRate()
        val testRatesBean = MockData.generateRandomRatesBeanWithBase(testBaseRate)

        every { apiService.getRates(testBaseRate.rateCode) } returns Single.just(
            testRatesBean
        )

        ratesRepository.getRates(testBaseRate.rateCode)
            .test()
            .assertSubscribed()
            .assertValue(testRatesBean)
            .assertComplete()
            .dispose()
    }
}