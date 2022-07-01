package dev.sukhrob.contacts.data.model.request.auth

data class VerifyRequest(
    val phone: String,
    val code: String
)