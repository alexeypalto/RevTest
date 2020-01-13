package by.alexeypalto.revtest.data.repository

import by.alexeypalto.revtest.beans.RatesBean
import by.alexeypalto.revtest.data.api.ApiService
import by.alexeypalto.revtest.domain.repository.RatesRepository
import io.reactivex.Single
import javax.inject.Inject

class RatesRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : RatesRepository {

    override fun getRates(rateCode: String?): Single<RatesBean> {
        return apiService.getRates(rateCode)
    }

}