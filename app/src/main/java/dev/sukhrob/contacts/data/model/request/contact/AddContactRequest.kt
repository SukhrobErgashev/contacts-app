package dev.sukhrob.contacts.data.model.request.contact

data class AddContactRequest(
    val firstName: String,
    val lastName: String,
    val phone: String
)