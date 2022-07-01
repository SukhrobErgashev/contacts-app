package dev.sukhrob.contacts.data.model.response.auth

data class LoginResponse(
    val token: String,
    val phone: String
)