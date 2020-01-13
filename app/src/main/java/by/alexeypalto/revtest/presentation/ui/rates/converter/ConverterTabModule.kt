package by.alexeypalto.revtest.presentation.ui.rates.converter

import androidx.lifecycle.ViewModelProviders
import by.alexeypalto.revtest.domain.usecase.networkstate.ObserveNetworkStateUseCase
import by.alexeypalto.revtest.domain.usecase.rates.GetRatesUseCase
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
interface ConverterTabModule {

    @Module
    object ProvidesModule {
        @Provides
        fun providesViewModule(
            fragment: ConverterTabFragment,
            getRatesUseCase: GetRatesUseCase,
            observeNetworkStateUseCase: ObserveNetworkStateUseCase
        ): ConverterViewModel {
            return ViewModelProviders.of(
                fragment,
                ConverterViewModel.Factory(getRatesUseCase, observeNetworkStateUseCase)
            )[ConverterViewModel::class.java]
        }
    }

    @ContributesAndroidInjector(modules = [ProvidesModule::class])
    fun bindConverterTabFragment(): ConverterTabFragment
}