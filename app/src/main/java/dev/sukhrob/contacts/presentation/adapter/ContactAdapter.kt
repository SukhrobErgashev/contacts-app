package dev.sukhrob.contacts.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import dev.sukhrob.contacts.domain.model.Contact
import dev.sukhrob.contacts.util.toListOfDataItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ClassCastException

class ContactAdapter(private val listener: ContactCardListener) :
    ListAdapter<ContactDataItem, ContactViewHolder>(DiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    companion object {
        const val HEADER_VIEW_TYPE = 0
        const val CONTACT_VIEW_TYPE = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ContactDataItem.HeaderCardItem -> 0
            is ContactDataItem.ContactCardItem -> 1
        }
    }

    fun submitContactList(list: List<Contact>?) {
        adapterScope.launch() {
            val listDataItem = list?.toListOfDataItem()
            withContext(Dispatchers.Main) {
                submitList(listDataItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return when (viewType) {
            HEADER_VIEW_TYPE -> ContactViewHolder.HeaderCardVH.from(parent)
            CONTACT_VIEW_TYPE -> ContactViewHolder.ContactCardVH.from(parent)
            else -> throw ClassCastException("View type not recognized: $viewType")
        }
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        when (holder) {
            is ContactViewHolder.HeaderCardVH -> {
                val item = getItem(position) as ContactDataItem.HeaderCardItem
                holder.bind(item)
            }
            is ContactViewHolder.ContactCardVH -> {
                val item = getItem(position) as ContactDataItem.ContactCardItem
                holder.bind(item, listener)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ContactDataItem>() {
        override fun areItemsTheSame(
            oldItem: ContactDataItem,
            newItem: ContactDataItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ContactDataItem,
            newItem: ContactDataItem
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }

}
