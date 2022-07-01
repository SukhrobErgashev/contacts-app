package dev.sukhrob.contacts.data.model.request.auth

data class LoginRequest(
    val phone: String,
    val password: String
)