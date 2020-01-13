package by.alexeypalto.revtest.di

import by.alexeypalto.revtest.data.repository.NetworkStateRepositoryImpl
import by.alexeypalto.revtest.data.repository.RatesRepositoryImpl
import by.alexeypalto.revtest.domain.repository.NetworkStateRepository
import by.alexeypalto.revtest.domain.repository.RatesRepository
import dagger.Binds
import dagger.Module

@Module
interface UseCaseRepositoryModule {

    @Binds
    fun bindRatesRepository(ratesRepository: RatesRepositoryImpl): RatesRepository

    @Binds
    fun bindNetworkStateRepository(networkStateRepository: NetworkStateRepositoryImpl): NetworkStateRepository
}