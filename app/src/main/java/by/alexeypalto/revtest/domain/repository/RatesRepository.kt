package by.alexeypalto.revtest.domain.repository

import by.alexeypalto.revtest.beans.RatesBean
import io.reactivex.Single

interface RatesRepository {

    fun getRates(rateCode: String?): Single<RatesBean>

}