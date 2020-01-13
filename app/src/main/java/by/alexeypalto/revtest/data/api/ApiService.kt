package by.alexeypalto.revtest.data.api

import by.alexeypalto.revtest.beans.RatesBean
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

const val SERVICE_DEFAULT_INTERVAL = 1L

interface ApiService {

    companion object {
        const val BASE_URL = "https://revolut.duckdns.org/"
    }

    @GET("latest")
    fun getRates(@Query("base") codeRout: String?): Single<RatesBean>
}