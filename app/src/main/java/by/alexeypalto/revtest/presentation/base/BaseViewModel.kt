package by.alexeypalto.revtest.presentation.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    protected val disposable = CompositeDisposable()

    protected infix fun add(d: Disposable) {
        disposable.add(d)
    }

    override fun onCleared() {
        disposable.clear()
    }
}