package by.alexeypalto.revtest.data.network

import android.net.NetworkInfo
import by.alexeypalto.revtest.RevTestApp
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.Observable
import javax.inject.Inject

class NetworkState @Inject constructor() {

    fun observeNetworkConnection(): Observable<Boolean> = ReactiveNetwork
        .observeNetworkConnectivity(RevTestApp.INSTANCE?.applicationContext)
        .map { it.state() }
        .filter { it == NetworkInfo.State.CONNECTED || it == NetworkInfo.State.DISCONNECTED }
        .map { it == NetworkInfo.State.CONNECTED }
}