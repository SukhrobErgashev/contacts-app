package dev.sukhrob.contacts.data.model.request.contact

data class EditContactRequest(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val phone: String
)