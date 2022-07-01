package dev.sukhrob.contacts.data.repository

import android.util.Log
import dev.sukhrob.contacts.data.source.local.datastore.UserPreferences
import dev.sukhrob.contacts.data.source.remote.api.AuthApi
import dev.sukhrob.contacts.data.model.request.auth.LoginRequest
import dev.sukhrob.contacts.data.model.request.auth.RegisterRequest
import dev.sukhrob.contacts.data.model.request.auth.VerifyRequest
import dev.sukhrob.contacts.data.model.Resource
import dev.sukhrob.contacts.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val userPreferences: UserPreferences
) : AuthRepository {

    override fun loginUser(request: LoginRequest) = flow<Resource<Unit>> {

        emit(Resource.Loading())
        val response = authApi.loginUser(request)

        if (response.isSuccessful) {
            response.body()?.let {
                userPreferences.apply {
                    saveAuthToken(it.token)
                    savePhone(it.phone)
                }
                emit(Resource.Success(Unit))
            }
        } else {
            emit(Resource.Error("Oops, something is wrong!"))
        }

    }.catch {
        emit(Resource.Error("Error, from internet!"))
    }.flowOn(Dispatchers.IO)


    override fun registerUser(request: RegisterRequest) = flow {

        emit(Resource.Loading())
        val response = authApi.registerUser(request)

        if (response.isSuccessful) {
            response.body()?.let {
                Log.d("TTTT", "registerUser() from AuthRepo successfully")
            }
            emit(Resource.Success(Unit))
        } else {
            emit(Resource.Error("Oops, something is wrong!"))
        }

    }.catch {
        emit(Resource.Error("Error, from internet!"))
    }.flowOn(Dispatchers.IO)


    override fun verifyUser(request: VerifyRequest) = flow<Resource<Unit>> {

        emit(Resource.Loading())
        val response = authApi.verifyUser(request)

        if (response.isSuccessful) {
            response.body()?.let {
                userPreferences.apply {
                    saveAuthToken(it.token)
                    savePhone(it.phone)
                }
                emit(Resource.Success(Unit))
            }
        } else {
            emit(Resource.Error("Oops, something is wrong!"))
        }

    }.catch {
        emit(Resource.Error("Error, from internet!"))
    }.flowOn(Dispatchers.IO)

}