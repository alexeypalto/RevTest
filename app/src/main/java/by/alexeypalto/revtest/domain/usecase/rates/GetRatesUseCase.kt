package by.alexeypalto.revtest.domain.usecase.rates

import by.alexeypalto.revtest.domain.entity.RatesObject
import io.reactivex.Flowable

interface GetRatesUseCase {

    fun execute(): Flowable<RatesObject>

    fun setBase(baseCode: String)

}