package by.alexeypalto.revtest.presentation.ui

import android.os.Bundle
import by.alexeypalto.revtest.R
import by.alexeypalto.revtest.adapter.ViewPagerAdapter
import by.alexeypalto.revtest.presentation.base.BaseActivity
import by.alexeypalto.revtest.presentation.ui.rates.all.rates.AllRatesTabFragment
import by.alexeypalto.revtest.presentation.ui.rates.converter.ConverterTabFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_tabs.*

class MainActivity : BaseActivity() {

    private val pagerAdapter by lazy {
        ViewPagerAdapter(
            supportFragmentManager,
            listOf(
                Pair(AllRatesTabFragment.newInstance(), getString(R.string.title_tab_rates)),
                Pair(ConverterTabFragment.newInstance(), getString(R.string.title_tab_converter))
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        setToolbar(am_toolbar)
        setTitle(R.string.title_main_activity)
        f_pager.adapter = pagerAdapter
        f_pager.offscreenPageLimit = pagerAdapter.count
        f_tabs.setupWithViewPager(f_pager)
    }

    override fun setTitle(titleId: Int) {
        am_toolbar_title.setText(titleId)
    }

    override fun setTitle(title: CharSequence?) {
        am_toolbar_title.text = title
    }
}
