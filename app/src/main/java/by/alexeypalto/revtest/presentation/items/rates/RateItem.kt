package by.alexeypalto.revtest.presentation.items.rates

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import by.alexeypalto.revtest.R
import by.alexeypalto.revtest.domain.entity.Rate
import by.alexeypalto.revtest.extensions.getRateFlagByCodeResId
import by.alexeypalto.revtest.extensions.getRateNameByCodeResId
import by.alexeypalto.revtest.presentation.items.BaseListItem
import kotlinx.android.synthetic.main.item_rate.view.*
import kotlin.math.roundToInt

private const val BASE_VALUE_TO_CONVERT = 100.0

class RateItem(
    val rate: Rate,
    private val valueToConvert: Double?,
    private val valueListener: ((value: Double?) -> Unit)? = null
) : BaseListItem {

    private var editTextWatcher: TextWatcher? = null

    override fun getViewId(): Int = R.layout.item_rate

    override fun renderView(view: View, positionInAdapter: Int) {
        updateItem(view, positionInAdapter)
        setValuesByRateCode(view)
        onFocusChangedListener(view)
        setEditableValue(positionInAdapter == 0, view)
    }

    @SuppressLint("DefaultLocale")
    override fun payloadView(view: View, positionInAdapter: Int, payloads: MutableList<Any>) {
        val payload = ((payloads.getOrNull(0)) as? MutableList<*>).orEmpty()
        updateItem(view, positionInAdapter)
        if (payload.contains(RATE_CODE)) {
            setValuesByRateCode(view)
        }
        if (view.ir_rate_code.hasFocus()) {
            updateTextChangedListener(view)
        }
        setEditableValue(positionInAdapter == 0, view)
    }

    private fun updateItem(view: View, positionInAdapter: Int) {
        if (positionInAdapter == 0) {
            updateFirstItem(view)
        } else {
            updateNonFirstItem(view)
        }
    }

    private fun updateTextChangedListener(view: View) {
        with(view) {
            if (editTextWatcher == null) {
                editTextWatcher = EditTextWatcher {
                    valueListener?.invoke(it)
                }
                ir_rate_value.addTextChangedListener(editTextWatcher)
            } else {
                ir_rate_value.removeTextChangedListener(editTextWatcher)
                editTextWatcher = null
            }
        }
    }

    private fun setValuesByRateCode(view: View) {
        with(view) {
            val flagResourceId = getRateFlagByCodeResId(context, rate.rateCode.toLowerCase())
            val rateNameResourceId =
                getRateNameByCodeResId(context, rate.rateCode.toLowerCase())
            ir_flag.setImageResource(if (flagResourceId != 0) flagResourceId else R.drawable.ic_placeholder)
            ir_rate_code.text = rate.rateCode
            ir_rate_name.text =
                if (rateNameResourceId != 0) context.getString(rateNameResourceId) else String()
        }
    }

    private fun updateFirstItem(view: View) {
        if (!view.ir_rate_value.hasFocus() && valueToConvert != null)
            view.ir_rate_value.setText(valueToConvert.toString())
    }

    private fun updateNonFirstItem(view: View) {
        view.ir_rate_value.setText(valueToConvert?.let { convertValue(it * rate.rateValue) } ?: "")
    }

    private fun onFocusChangedListener(view: View) {
        with(view) {
            ir_rate_value.setOnFocusChangeListener { _, isFocused ->
                setEditableValue(isFocused, this)
                updateTextChangedListener(this)
            }
        }
    }

    private fun setEditableValue(isSetEditable: Boolean, view: View) {
        with(view) {
            if (isSetEditable) {
                ir_rate_value.isClickable = true
                ir_rate_value.isEnabled = true
                ir_rate_value.setSelection(ir_rate_value.length())
            } else {
                ir_rate_value.isClickable = false
                ir_rate_value.isEnabled = false
            }
        }
    }

    private fun convertValue(value: Double): String {
        val convertedValue = (value * 100.00).roundToInt() / 100.00
        return convertedValue.toString()
    }

    override fun getChangePayload(oldItem: BaseListItem?): Any? {
        val payload = mutableListOf<Any>()
        if (oldItem !is RateItem) {
            return ALL
        }
        if (oldItem.rate.rateCode != rate.rateCode) {
            payload.add(RATE_CODE)
        }
        if (oldItem.valueToConvert != valueToConvert) {
            payload.add(VALUE_TO_CONVERT)
        }
        if (oldItem.rate.rateValue != rate.rateValue) {
            payload.add(RATE_VALUE)
        }
        return payload
    }

    override fun areContentsTheSame(oldItem: BaseListItem?): Boolean {
        return oldItem is RateItem &&
                oldItem.rate.rateCode == rate.rateCode &&
                oldItem.rate.rateValue == rate.rateValue &&
                oldItem.valueToConvert == valueToConvert
    }

    override fun areItemsTheSame(oldItem: BaseListItem?): Boolean {
        return oldItem is RateItem &&
                oldItem.rate.rateCode == rate.rateCode
    }

    companion object {
        const val RATE_CODE = 1
        const val RATE_VALUE = 2
        const val VALUE_TO_CONVERT = 3

        val ALL = mutableListOf<Any>(
            RATE_CODE,
            RATE_VALUE,
            VALUE_TO_CONVERT
        )
    }
}

private class EditTextWatcher(
    private val valueChangedListener: ((value: Double?) -> Unit)
) : TextWatcher {

    override fun afterTextChanged(s: Editable?) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(value: CharSequence?, p1: Int, p2: Int, p3: Int) {
        valueChangedListener.invoke(value?.toString()?.toDoubleOrNull())
    }
}