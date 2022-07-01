package dev.sukhrob.contacts.data.source.remote.api

import dev.sukhrob.contacts.data.model.request.contact.AddContactRequest
import dev.sukhrob.contacts.data.model.request.contact.EditContactRequest
import dev.sukhrob.contacts.data.model.response.contact.ContactResponse

import retrofit2.Response
import retrofit2.http.*

interface ContactApi {

    @GET("api/v1/contact")
    suspend fun getContactList(
        @Header("token") token: String
    ): Response<List<ContactResponse>>

    @POST("api/v1/contact")
    suspend fun addContact(
        @Header("token") token: String,
        @Body request: AddContactRequest
    ): Response<ContactResponse>

    @PUT("api/v1/contact")
    suspend fun editContact(
        @Header("token") token: String,
        @Body request: EditContactRequest
    ): Response<ContactResponse>

    @DELETE("api/v1/contact")
    suspend fun deleteContact(
        @Header("token") token: String,
        @Query("id") id: Int
    ): Response<ContactResponse>

    @GET("api/v1/contact")
    suspend fun getAll(
        @Header("token") token: String
    ): Response<List<ContactResponse>>

}