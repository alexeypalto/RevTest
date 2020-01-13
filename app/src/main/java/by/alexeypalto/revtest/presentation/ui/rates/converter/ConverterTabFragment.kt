package by.alexeypalto.revtest.presentation.ui.rates.converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.Observer
import by.alexeypalto.revtest.R
import by.alexeypalto.revtest.adapter.BaseRecyclerAdapter
import by.alexeypalto.revtest.beans.common.Status
import by.alexeypalto.revtest.di.Injectable
import by.alexeypalto.revtest.domain.entity.Rate
import by.alexeypalto.revtest.extensions.gone
import by.alexeypalto.revtest.extensions.show
import by.alexeypalto.revtest.presentation.base.BaseFragment
import by.alexeypalto.revtest.presentation.items.BaseListItem
import by.alexeypalto.revtest.presentation.items.rates.RateItem
import kotlinx.android.synthetic.main.fragment_converter.*
import kotlinx.android.synthetic.main.fragment_recycler.*
import javax.inject.Inject

class ConverterTabFragment : BaseFragment(), Injectable {

    @Inject
    lateinit var viewModel: ConverterViewModel

    override val elevateToolbar: Boolean = false

    companion object {
        fun newInstance(): ConverterTabFragment {
            return ConverterTabFragment()
        }
    }

    private var valueListener: ((value: Double?) -> Unit)? = null

    private val adapter by lazy {
        BaseRecyclerAdapter(onItemClickListener = { item, position, view ->
            if (item is RateItem)
                if (viewModel.networkStateLiveData.value == true) {
                    if (position != 0) {
                        view?.findViewById<EditText>(R.id.ir_rate_value)?.apply {
                            this.requestFocus()
                            this.setSelection(this.length())
                        }
                        viewModel.changeBaseRate(item.rate)
                        moveItem(item, position)
                    }
                }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_converter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        valueListener = {
            viewModel.valueToConvert = it
            setItems(viewModel.mappedRatesList, it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        valueListener = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initView() {
        with(fr_recycler) {
            clipToPadding = false
            adapter = this@ConverterTabFragment.adapter
        }
    }

    private fun initViewModel() {
        viewModel.networkStateLiveData.observe(viewLifecycleOwner, Observer {
            setNetworkState(it)
        })
        viewModel.ratesObjectLiveData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> showSuccess(it.data)
                Status.LOADING -> showLoading()
                Status.ERROR -> showError(it.throwable)
            }
        })
    }

    private fun setNetworkState(isNetworkConnected: Boolean) {
        if (isNetworkConnected) {
            fc_network_offline_mode.gone()
        } else {
            fc_network_offline_mode?.apply {
                alpha = 0f
                visibility = View.VISIBLE

                animate()
                    .alpha(1f)
                    .setDuration(400L)
                    .setListener(null)
            }
        }
    }

    private fun showSuccess(ratesList: List<Rate>?) {
        ratesList?.let { setItems(it, viewModel.valueToConvert) }
        fr_progress.gone()
        fr_recycler.show()
    }

    private fun setItems(ratesList: List<Rate>, valueToConvert: Double?) {
        if (!fr_recycler.isComputingLayout) {
            adapter.replaceElementsWithDiffUtil(ratesList.mapIndexed { index, rate ->
                RateItem(
                    rate,
                    valueToConvert,
                    valueListener
                )
            })
        }
    }


    private fun showLoading() {
        fr_progress.show()
    }

    private fun showError(throwable: Throwable?) {
        fr_progress.gone()
        fr_recycler.show()
        fr_error_message.text = throwable?.message
    }


    private fun moveItem(item: BaseListItem, position: Int) {
        adapter.changeElementsByPositions(item, position)
        fr_recycler?.postDelayed({
            fr_recycler.smoothScrollToPosition( 0)
        }, 500L)
    }

}