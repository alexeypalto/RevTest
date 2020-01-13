package by.alexeypalto.revtest.data.repository

import by.alexeypalto.revtest.data.network.NetworkState
import by.alexeypalto.revtest.domain.repository.NetworkStateRepository
import io.reactivex.Observable
import javax.inject.Inject

class NetworkStateRepositoryImpl @Inject constructor(
    private val networkState: NetworkState
) : NetworkStateRepository {

    override fun observeNetwork(): Observable<Boolean> =
        networkState.observeNetworkConnection()

}