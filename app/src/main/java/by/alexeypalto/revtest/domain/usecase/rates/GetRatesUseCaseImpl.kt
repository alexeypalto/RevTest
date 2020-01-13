package by.alexeypalto.revtest.domain.usecase.rates

import by.alexeypalto.revtest.data.api.SERVICE_DEFAULT_INTERVAL
import by.alexeypalto.revtest.data.mapper.RatesMapper
import by.alexeypalto.revtest.domain.entity.RatesObject
import by.alexeypalto.revtest.domain.repository.RatesRepository
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val DEFAULT_RATE = "EUR"

class GetRatesUseCaseImpl @Inject constructor(
    private val repository: RatesRepository,
    private val ratesMapper: RatesMapper
) : GetRatesUseCase {

    private val baseCodeSubject: BehaviorSubject<String> by lazy {
        BehaviorSubject.createDefault(
            DEFAULT_RATE
        )
    }

    override fun execute(): Flowable<RatesObject> =
        Flowable
            .interval(0L, SERVICE_DEFAULT_INTERVAL, TimeUnit.SECONDS, Schedulers.io())
            .flatMap {
                repository.getRates(baseCodeSubject.value)
                    .map { ratesMapper.mapFromEntity(it) }
                    .toFlowable()
            }

    override fun setBase(baseCode: String) {
        baseCodeSubject.onNext(baseCode)
    }
}