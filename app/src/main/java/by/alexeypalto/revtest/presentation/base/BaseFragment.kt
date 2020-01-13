package by.alexeypalto.revtest.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.alexeypalto.revtest.di.Injectable
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment : Fragment(), HasAndroidInjector {

    var androidInjector: DispatchingAndroidInjector<Any>? = null
        @Inject set

    protected open val elevateToolbar = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (parentFragment == null) {
            getBaseActivity()?.setToolbarElevation(elevateToolbar)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (this is Injectable) {
            AndroidSupportInjection.inject(this)
        }
    }

    override fun androidInjector(): AndroidInjector<Any>? {
        return androidInjector
    }

    protected fun getBaseActivity(): BaseActivity? {
        return activity as? BaseActivity
    }

    protected fun setTitle(titleResId: Int) {
        activity?.setTitle(titleResId)
    }
}