package by.alexeypalto.revtest.domain.usecase.networkstate

import by.alexeypalto.revtest.domain.repository.NetworkStateRepository
import io.reactivex.Observable
import javax.inject.Inject

class ObserveNetworkStateUseCaseImpl @Inject constructor(
    private val networkStateRepository: NetworkStateRepository
) : ObserveNetworkStateUseCase {

    override fun observeNetworkState(): Observable<Boolean> =
        networkStateRepository.observeNetwork()

}