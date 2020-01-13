package by.alexeypalto.revtest.domain.repository

import io.reactivex.Observable

interface NetworkStateRepository {

    fun observeNetwork(): Observable<Boolean>
}