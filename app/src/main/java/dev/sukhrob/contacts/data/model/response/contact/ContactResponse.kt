package dev.sukhrob.contacts.data.model.response.contact

import dev.sukhrob.contacts.domain.model.Contact

data class ContactResponse(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val phone: String
)

fun ContactResponse.toContactEntity(): Contact {
    return Contact(
        this.id,
        this.firstName,
        this.lastName,
        this.phone.toLong()
    )
}