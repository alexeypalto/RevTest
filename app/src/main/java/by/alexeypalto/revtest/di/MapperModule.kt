package by.alexeypalto.revtest.di

import by.alexeypalto.revtest.data.mapper.RatesMapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object MapperModule {

    @Provides
    @Singleton
    fun providesRatesMapper(): RatesMapper {
        return RatesMapper()
    }

}