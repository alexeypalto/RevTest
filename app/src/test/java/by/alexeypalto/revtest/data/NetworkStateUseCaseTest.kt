package by.alexeypalto.revtest.data

import by.alexeypalto.revtest.data.network.NetworkState
import by.alexeypalto.revtest.data.repository.NetworkStateRepositoryImpl
import by.alexeypalto.revtest.domain.usecase.networkstate.ObserveNetworkStateUseCaseImpl
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NetworkStateUseCaseTest {
    private lateinit var networkState: NetworkState
    private lateinit var observerNetworkStateUseCase: ObserveNetworkStateUseCaseImpl

    @Before
    fun before() {
        networkState = mockk(relaxed = true)
        observerNetworkStateUseCase = ObserveNetworkStateUseCaseImpl(NetworkStateRepositoryImpl(networkState))
    }

    @Test
    fun testSendNetworkTrue() {
        every { networkState.observeNetworkConnection() } returns Observable.just(true)

        observerNetworkStateUseCase.observeNetworkState()
            .test()
            .assertSubscribed()
            .assertResult(true)
            .assertComplete()
    }

    @Test
    fun testSendNetworkFalse() {
        every { networkState.observeNetworkConnection() } returns Observable.just(false)

        observerNetworkStateUseCase.observeNetworkState()
            .test()
            .assertSubscribed()
            .assertResult(true)
            .assertComplete()
    }
}