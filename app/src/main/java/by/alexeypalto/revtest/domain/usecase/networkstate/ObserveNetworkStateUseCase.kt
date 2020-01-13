package by.alexeypalto.revtest.domain.usecase.networkstate

import io.reactivex.Observable


interface ObserveNetworkStateUseCase {

    fun observeNetworkState(): Observable<Boolean>
}