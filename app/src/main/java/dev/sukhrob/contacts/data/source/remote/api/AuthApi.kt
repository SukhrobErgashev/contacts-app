package dev.sukhrob.contacts.data.source.remote.api

import dev.sukhrob.contacts.data.model.request.auth.LoginRequest
import dev.sukhrob.contacts.data.model.request.auth.RegisterRequest
import dev.sukhrob.contacts.data.model.request.auth.VerifyRequest
import dev.sukhrob.contacts.data.model.response.auth.LoginResponse
import dev.sukhrob.contacts.data.model.response.auth.RegisterResponse
import dev.sukhrob.contacts.data.model.response.auth.VerifyResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("api/v1/register")
    suspend fun registerUser(
        @Body registerRequest: RegisterRequest
    ): Response<RegisterResponse>

    @POST("api/v1/register/verify")
    suspend fun verifyUser(
        @Body verifyRequest: VerifyRequest
    ): Response<VerifyResponse>

    @POST("api/v1/login")
    suspend fun loginUser(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

}