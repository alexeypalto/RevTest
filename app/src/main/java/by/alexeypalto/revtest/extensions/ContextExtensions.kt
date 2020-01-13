package by.alexeypalto.revtest.extensions

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentActivity

fun getRateNameByCodeResId(context: Context, rateCode: String) =
    context.resources.getIdentifier(
        "currency_" + rateCode + "_name", "string",
        context.packageName
    )

fun getRateFlagByCodeResId(context: Context, rateCode: String) = context.resources.getIdentifier(
    "ic_" + rateCode + "_flag", "drawable", context.packageName
)


fun Context?.isAvailable(): Boolean {
    if (this == null) {
        return false
    } else if (this !is Application) {
        if (this is FragmentActivity) {
            return !this.isDestroyed
        } else if (this is Activity) {
            return !this.isDestroyed
        }
    }
    return true
}