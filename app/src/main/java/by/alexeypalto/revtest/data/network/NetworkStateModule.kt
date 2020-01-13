package by.alexeypalto.revtest.data.network

import dagger.Module
import dagger.Provides

@Module
class NetworkStateModule {

    @Provides
    fun providesNetworkState(): NetworkState {
        return NetworkState()
    }
}