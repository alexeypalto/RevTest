package by.alexeypalto.revtest.presentation.ui.rates.all.rates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.alexeypalto.revtest.R
import by.alexeypalto.revtest.presentation.base.BaseFragment

class AllRatesTabFragment : BaseFragment() {

    companion object {
        fun newInstance(): AllRatesTabFragment {
            return AllRatesTabFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_placeholder, container, false)
    }
}