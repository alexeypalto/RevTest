package by.alexeypalto.revtest.extensions

import android.content.Context
import android.view.View

fun View.show() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun Context.convertDpToPx(dp: Float): Int = (dp * resources.displayMetrics.density + 0.5f).toInt()
