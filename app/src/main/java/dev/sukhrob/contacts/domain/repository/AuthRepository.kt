package dev.sukhrob.contacts.domain.repository

import dev.sukhrob.contacts.data.model.request.auth.LoginRequest
import dev.sukhrob.contacts.data.model.request.auth.RegisterRequest
import dev.sukhrob.contacts.data.model.request.auth.VerifyRequest
import dev.sukhrob.contacts.data.model.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun loginUser(request: LoginRequest): Flow<Resource<Unit>>

    fun registerUser(request: RegisterRequest): Flow<Resource<Unit>>

    fun verifyUser(request: VerifyRequest): Flow<Resource<Unit>>

}