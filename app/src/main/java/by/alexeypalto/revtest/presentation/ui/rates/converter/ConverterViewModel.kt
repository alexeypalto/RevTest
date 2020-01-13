package by.alexeypalto.revtest.presentation.ui.rates.converter

import androidx.lifecycle.*
import by.alexeypalto.revtest.beans.common.Resource
import by.alexeypalto.revtest.domain.entity.Rate
import by.alexeypalto.revtest.domain.entity.RatesObject
import by.alexeypalto.revtest.domain.usecase.networkstate.ObserveNetworkStateUseCase
import by.alexeypalto.revtest.domain.usecase.rates.GetRatesUseCase
import by.alexeypalto.revtest.presentation.base.BaseViewModel
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy

const val DEFAULT_CONVERTED_VALUE: Double = 100.0

class ConverterViewModel(
    private val getRatesUseCase: GetRatesUseCase,
    observeNetworkStateUseCase: ObserveNetworkStateUseCase
) : BaseViewModel() {

    private var ratesDisposable: Disposable? = null

    private val _ratesLiveData = MediatorLiveData<Resource<List<Rate>>>()
    val ratesObjectLiveData: LiveData<Resource<List<Rate>>>
        get() = _ratesLiveData

    val networkStateLiveData = MutableLiveData<Boolean>()

    var valueToConvert: Double? = DEFAULT_CONVERTED_VALUE
    lateinit var mappedRatesList: List<Rate>

    init {
        this add observeNetworkStateUseCase.observeNetworkState()
            .subscribeBy(
                onNext = { isNetworkConnected ->
                    networkStateLiveData.postValue(isNetworkConnected)
                    if (isNetworkConnected) getRates()
                },
                onError = {
                    networkStateLiveData.postValue(false)
                }
            )
    }

    private fun getRates() {
        ratesDisposable?.let { disposable.remove(it) }
        ratesDisposable = getRatesUseCase.execute()
            .doOnSubscribe { _ratesLiveData.postValue(Resource.loading()) }
            .subscribeBy(
                onNext = {
                    _ratesLiveData.postValue(Resource.success(calculateWithBase(it)))
                },
                onError = {
                    _ratesLiveData.postValue(Resource.error(it))
                }
            ).also {
                this add it
            }
    }

    private fun calculateWithBase(ratesObject: RatesObject): List<Rate> {
        val listOfRates = ratesObject.rates.toMutableList()
        val baseRateObject = Rate(ratesObject.base, valueToConvert ?: DEFAULT_CONVERTED_VALUE)
        listOfRates.add(0, baseRateObject)
        mappedRatesList = listOfRates
        return listOfRates
    }

    fun changeBaseRate(rate: Rate) {
        getRatesUseCase.setBase(rate.rateCode)
    }

    class Factory(
        private val getRatesUseCase: GetRatesUseCase,
        private val observeNetworkStateUseCase: ObserveNetworkStateUseCase
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ConverterViewModel(getRatesUseCase, observeNetworkStateUseCase) as T
        }
    }

}