package by.alexeypalto.revtest.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import by.alexeypalto.revtest.presentation.items.BaseListItem

class BaseViewHolder(
    view: View,
    private val onItemClickListener: ((item: BaseListItem, position: Int, view: View?) -> Unit)? = null
) : RecyclerView.ViewHolder(view) {

    var item: BaseListItem? = null

    fun renderItem(holderItem: BaseListItem?) {
        item = holderItem
        holderItem?.let {
            it.renderView(itemView, adapterPosition)
            setClickListeners(itemView, it)
        }
    }

    fun payloadItem(holderItem: BaseListItem?, payloads: MutableList<Any>) {
        item = holderItem
        holderItem?.let {
            it.payloadView(itemView, adapterPosition, payloads)
            setClickListeners(itemView, it)
        }
    }

    private fun setClickListeners(view: View?, holderItem: BaseListItem) {
        view?.setOnClickListener {
            onItemClickListener?.invoke(holderItem, adapterPosition, view)
        }
    }
}