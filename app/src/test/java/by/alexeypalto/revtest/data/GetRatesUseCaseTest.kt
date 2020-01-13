package by.alexeypalto.revtest.data

import by.alexeypalto.revtest.MockData
import by.alexeypalto.revtest.data.api.ApiService
import by.alexeypalto.revtest.data.mapper.RatesMapper
import by.alexeypalto.revtest.data.repository.RatesRepositoryImpl
import by.alexeypalto.revtest.domain.entity.RatesObject
import by.alexeypalto.revtest.domain.usecase.networkstate.ObserveNetworkStateUseCaseImpl
import by.alexeypalto.revtest.domain.usecase.rates.GetRatesUseCaseImpl
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
class GetRatesUseCaseTest {

    private val mapper = RatesMapper()
    private lateinit var apiService: ApiService
    private lateinit var mockedRatesRepository: RatesRepositoryImpl
    private lateinit var mockedObservableNetworkState: ObserveNetworkStateUseCaseImpl
    private lateinit var getRatesUseCase: GetRatesUseCaseImpl
    private lateinit var testScheduler: TestScheduler

    @Before
    fun beforeEach() {
        testScheduler = TestScheduler()
        apiService = mockk(relaxed = true)
        mockedObservableNetworkState = mockk()
        mockedRatesRepository = mockk()
        getRatesUseCase = GetRatesUseCaseImpl(mockedRatesRepository, mapper)
    }

    @Test
    fun getRatesWithNetworkConnected() {
        val testBaseRate = MockData.generateRandomRate()
        val testRatesBean = MockData.generateRandomRatesBeanWithBase(testBaseRate)
        val mappedTestRates = mapper.mapFromEntity(testRatesBean)

        every { mockedObservableNetworkState.observeNetworkState() } returns Observable.create {
            it.onNext(true)
        }

        every { mockedRatesRepository.getRates(testBaseRate.rateCode) } returns Single.just(
            testRatesBean
        )

        getRatesUseCase.setBase(testBaseRate.rateCode)
        getRatesUseCase.execute()
            .subscribeOn(testScheduler)
            .test()
            .apply { testScheduler.triggerActions() }
            .assertSubscribed()
            .awaitCount(2)
            .assertValueAt(0, mappedTestRates)
            .assertValueAt(1, mappedTestRates)
            .assertNotComplete()
            .dispose()
    }
}