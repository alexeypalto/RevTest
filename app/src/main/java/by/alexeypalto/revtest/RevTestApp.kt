package by.alexeypalto.revtest

import android.app.Application
import by.alexeypalto.revtest.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class RevTestApp : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchAndroidInjector: DispatchingAndroidInjector<Any>

    companion object {
        var INSTANCE: RevTestApp? = null
            private set
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchAndroidInjector
    }

    override fun onCreate() {
        INSTANCE = this
        super.onCreate()
        DaggerAppComponent.builder().application(this).build().injectApp(this)
    }
}