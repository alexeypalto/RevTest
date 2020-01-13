package by.alexeypalto.revtest.domain.usecase

import by.alexeypalto.revtest.domain.usecase.networkstate.ObserveNetworkStateUseCase
import by.alexeypalto.revtest.domain.usecase.networkstate.ObserveNetworkStateUseCaseImpl
import by.alexeypalto.revtest.domain.usecase.rates.GetRatesUseCase
import by.alexeypalto.revtest.domain.usecase.rates.GetRatesUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface UseCaseModule {

    @Binds
    fun bindGetRatesUseCase(getRatesUseCase: GetRatesUseCaseImpl): GetRatesUseCase

    @Binds
    fun bindObserveNetworkStateUseCase(observeNetworkStateUseCase: ObserveNetworkStateUseCaseImpl): ObserveNetworkStateUseCase
}