package by.alexeypalto.revtest.presentation.items

import android.view.View
import androidx.annotation.LayoutRes

interface BaseListItem {

    @LayoutRes
    fun getViewId(): Int

    fun renderView(view: View, positionInAdapter: Int = 0)

    fun payloadView(view: View, positionInAdapter: Int, payloads: MutableList<Any>) {
    }

    fun areItemsTheSame(oldItem: BaseListItem?): Boolean {
        return hashCode() == oldItem?.hashCode()
    }

    fun areContentsTheSame(oldItem: BaseListItem?): Boolean {
        return equals(oldItem)
    }

    fun getChangePayload(oldItem: BaseListItem?): Any? {
        return null
    }
}