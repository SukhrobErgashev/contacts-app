package dev.sukhrob.contacts.domain.repository

import dev.sukhrob.contacts.data.model.request.contact.AddContactRequest
import dev.sukhrob.contacts.data.model.request.contact.EditContactRequest
import dev.sukhrob.contacts.data.model.Resource
import dev.sukhrob.contacts.data.model.response.contact.ContactResponse
import dev.sukhrob.contacts.domain.model.Contact
import kotlinx.coroutines.flow.Flow

interface ContactRepository {

    val contactListFromDb: Flow<List<Contact>>

    fun logoutUser() : Flow<Boolean>

    fun addContact(request: AddContactRequest): Flow<Unit>

    fun editContact(request: EditContactRequest): Flow<Unit>

    fun deleteContact(id: Int): Flow<Unit>

    suspend fun deleteAll()

    fun getContactList(): Flow<Unit>

}