package dev.sukhrob.contacts.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.sukhrob.contacts.data.model.request.contact.AddContactRequest
import dev.sukhrob.contacts.data.model.request.contact.EditContactRequest

@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey
    val id: Int? = null,
    val firstName: String,
    val lastName: String,
    val phone: Long,
    val isBookmarked: Boolean = false
)

fun Contact.toAddContactRequest(): AddContactRequest {
    return AddContactRequest(
        this.firstName,
        this.lastName,
        "+998" + this.phone.toString()
    )
}

fun Contact.toEditContactRequest(): EditContactRequest {
    return EditContactRequest(
        this.id!!,
        this.firstName,
        this.lastName,
        this.phone.toString()
    )
}