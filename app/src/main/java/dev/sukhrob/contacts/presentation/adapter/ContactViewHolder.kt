package dev.sukhrob.contacts.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import dev.sukhrob.contacts.databinding.ItemContactBinding
import dev.sukhrob.contacts.databinding.ItemHeaderBinding

sealed class ContactViewHolder(open val binding: ViewBinding): RecyclerView.ViewHolder(binding.root) {

    class ContactCardVH(override val binding: ItemContactBinding): ContactViewHolder(binding) {

        fun bind(item: ContactDataItem.ContactCardItem, listener: ContactCardListener) {
            with(binding) {
                this.listener = listener
                this.contact = item.contact
                executePendingBindings()
            }
        }

        companion object {
            fun from(parent: ViewGroup): ContactCardVH {
                val binding = ItemContactBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ContactCardVH(binding)
            }
        }

    }

    class HeaderCardVH(override val binding: ItemHeaderBinding): ContactViewHolder(binding) {

        fun bind(item: ContactDataItem.HeaderCardItem) {
            with(binding) {
                tvHeader.text = item.letter.toString()
            }
        }

        companion object {
            fun from(parent: ViewGroup): HeaderCardVH {
                val binding = ItemHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return HeaderCardVH(binding)
            }
        }

    }

}