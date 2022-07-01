package dev.sukhrob.contacts.data.model.request.auth

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val phone: String,
    val password: String
)