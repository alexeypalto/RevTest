package by.alexeypalto.revtest.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.alexeypalto.revtest.presentation.items.BaseListItem
import com.pawegio.kandroid.inflateLayout

open class BaseRecyclerAdapter(
    private val dataset: MutableList<BaseListItem> = mutableListOf(),
    private var onItemClickListener: ((item: BaseListItem, position: Int, view: View?) -> Unit)? = null
) : RecyclerView.Adapter<BaseViewHolder>() {

    companion object {
        const val HOLDER_TAG_KEY = 0x03000285
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.renderItem(dataset[position])
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            holder.payloadItem(dataset[position], payloads)
        }
    }

    override fun getItemCount(): Int = dataset.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = parent.context.inflateLayout(viewType, parent, false)
        val holder = BaseViewHolder(view, onItemClickListener)
        view.setTag(HOLDER_TAG_KEY, holder)
        return holder
    }

    override fun getItemViewType(position: Int): Int = dataset[position].getViewId()


    fun replaceElements(newElements: List<BaseListItem>) {
        dataset.clear()
        dataset.addAll(newElements)
    }

    fun replaceElementsWithDiffUtil(items: List<BaseListItem>, enableAnimation: Boolean = true) {
        val diffUtilCallback = DiffUtilCallback(dataset.toList(), items)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback, enableAnimation)
        replaceElements(items)
        diffResult.dispatchUpdatesTo(this)
    }


    fun addElement(position: Int, item: BaseListItem) {
        dataset.add(position, item)
    }

    fun changeElementsByPositions(item: BaseListItem, position: Int) {
        removeElementByPosition(position)
        addElement(0, item)
        notifyItemMoved(position, 0)
    }

    fun removeElementByPosition(position: Int) {
        if (dataset.size > position) {
            dataset.removeAt(position)
        }
    }
}

class DiffUtilCallback(
    private var oldList: List<BaseListItem>,
    private var newList: List<BaseListItem>
) : DiffUtil.Callback() {

    /*
    * Returns the size of the old list.
    */
    override fun getOldListSize(): Int = oldList.size

    /*
    * Returns the size of the new list.
    */
    override fun getNewListSize(): Int = newList.size

    /*
    * Called by the DiffUtil to decide whether two object represent the same Item.
    */
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return newItem.areItemsTheSame(oldItem)
    }

    /*
    * Called by the DiffUtil when it wants to check whether two items have the same data.
    * DiffUtil uses this information to detect if the contents of an item has changed.
    */
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return newItem.areContentsTheSame(oldItem)
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return newItem.getChangePayload(oldItem)
    }
}