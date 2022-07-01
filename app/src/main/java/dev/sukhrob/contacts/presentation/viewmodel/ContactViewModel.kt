package dev.sukhrob.contacts.presentation.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sukhrob.contacts.domain.model.Contact
import dev.sukhrob.contacts.domain.model.toAddContactRequest
import dev.sukhrob.contacts.domain.model.toEditContactRequest
import dev.sukhrob.contacts.domain.repository.ContactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val contactRepo: ContactRepository
) : ViewModel() {

    val contactList: LiveData<List<Contact>> = contactRepo.contactListFromDb.asLiveData()

    private val _logoutUserLiveData = MutableLiveData<Boolean>()
    val logoutUserLiveData: LiveData<Boolean> get() = _logoutUserLiveData

    fun logoutUser() {
        contactRepo.logoutUser().onEach {
            if (it) {
                _logoutUserLiveData.postValue(true)
            }
        }.launchIn(viewModelScope)
    }

    fun addContact(contact: Contact) {
        contactRepo.addContact(contact.toAddContactRequest()).launchIn(viewModelScope)
    }

    fun editContact(contact: Contact) {
        contactRepo.editContact(contact.toEditContactRequest()).launchIn(viewModelScope)
    }

    fun deleteContact(contact: Contact) {
        contactRepo.deleteContact(contact.id!!).launchIn(viewModelScope)
    }

    fun deleteAll() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            contactRepo.deleteAll()
        }
    }

}