package dev.sukhrob.contacts.presentation.adapter

import dev.sukhrob.contacts.domain.model.Contact

sealed class ContactDataItem {

    abstract val id: Int

    data class ContactCardItem(
        val contact: Contact,
        override val id: Int = contact.id!!
    ) : ContactDataItem()

    data class HeaderCardItem(
        val letter: Char,
        override val id: Int = letter.code
    ): ContactDataItem()

}