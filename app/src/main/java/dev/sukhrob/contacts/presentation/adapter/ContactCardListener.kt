package dev.sukhrob.contacts.presentation.adapter

import dev.sukhrob.contacts.domain.model.Contact

class ContactCardListener(
    val deleteListener: ((contact: Contact) -> Unit),
    val updateListener: ((contact: Contact) -> Unit),
    val callListener: ((contact: Contact) -> Unit)
) {

    fun deleteListenerF(contact: Contact) {
        deleteListener(contact)
    }

    fun updateListenerF(contact: Contact) {
        updateListener(contact)
    }

    fun callListenerF(contact: Contact) {
        callListener(contact)
    }

}