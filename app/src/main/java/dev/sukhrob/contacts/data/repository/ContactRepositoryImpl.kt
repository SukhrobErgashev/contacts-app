package dev.sukhrob.contacts.data.repository

import dev.sukhrob.contacts.data.source.local.datastore.UserPreferences
import dev.sukhrob.contacts.data.source.local.db.ContactDao
import dev.sukhrob.contacts.data.source.remote.api.ContactApi
import dev.sukhrob.contacts.data.model.request.contact.AddContactRequest
import dev.sukhrob.contacts.data.model.request.contact.EditContactRequest
import dev.sukhrob.contacts.data.model.Resource
import dev.sukhrob.contacts.data.model.response.contact.*
import dev.sukhrob.contacts.domain.model.Contact
import dev.sukhrob.contacts.domain.repository.ContactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ContactRepositoryImpl @Inject constructor(
    private val dao: ContactDao,
    private val contactApi: ContactApi,
    private val userPreferences: UserPreferences
) : ContactRepository {

    override val contactListFromDb: Flow<List<Contact>> = dao.getContacts()

    override fun logoutUser(): Flow<Boolean> = flow {
        userPreferences.clear()
        emit(true)
    }.flowOn(Dispatchers.IO)

    override fun addContact(request: AddContactRequest) = flow<Unit> {
        val response = contactApi.addContact(userPreferences.authToken.first().toString(), request)

        if (response.isSuccessful) {
            response.body()?.let {
                dao.insert(it.toContactEntity())
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun editContact(request: EditContactRequest) = flow<Unit> {
        val response = contactApi.editContact(userPreferences.authToken.toString(), request)

        if (response.isSuccessful) {
            response.body()?.let {
                dao.insert(it.toContactEntity())
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun deleteContact(id: Int) = flow<Unit> {
        val response = contactApi.deleteContact(userPreferences.authToken.toString(), id)

        if (response.isSuccessful) {
            response.body()?.let {
                dao.delete(it.toContactEntity())
            }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteAll() {
        dao.deleteAll()
    }

    override fun getContactList() = flow<Unit> {
        val response = contactApi.getAll(userPreferences.authToken.first().toString())

        if (response.isSuccessful) {
            response.body()?.let {
                for (i in it) {
                    dao.insert(i.toContactEntity())
                }
            }
        }
    }.flowOn(Dispatchers.IO)

}