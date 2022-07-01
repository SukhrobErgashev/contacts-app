package dev.sukhrob.contacts.presentation.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sukhrob.contacts.data.model.Resource
import dev.sukhrob.contacts.data.model.request.auth.LoginRequest
import dev.sukhrob.contacts.data.model.request.auth.RegisterRequest
import dev.sukhrob.contacts.data.model.request.auth.VerifyRequest
import dev.sukhrob.contacts.domain.repository.AuthRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepo: AuthRepository
) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _gotoContact = MutableLiveData<Boolean>()
    val gotoContact: LiveData<Boolean> get() = _gotoContact

    private val _gotoVerify = MutableLiveData<Boolean>()
    val gotoVerify: LiveData<Boolean> get() = _gotoVerify

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun registerUser(request: RegisterRequest) {
        authRepo.registerUser(request).onEach {
            when (it) {
                is Resource.Loading -> {
                    _loading.postValue(true)
                }
                is Resource.Success -> {
                    _loading.postValue(false)
                    _gotoVerify.postValue(true)
                }
                is Resource.Error -> {
                    _loading.postValue(false)
                    _errorMessage.postValue(it.message)
                }
            }
        }.catch {
            Log.d("TTTT", "registerUser: ")
        }.launchIn(viewModelScope)
    }

    fun verifyUser(request: VerifyRequest) {
        authRepo.verifyUser(request).onEach {
            when(it) {
                is Resource.Loading -> {
                    _loading.postValue(true)
                }
                is Resource.Success -> {
                    _loading.postValue(false)
                    _gotoContact.postValue(true)
                }
                is Resource.Error -> {
                    _loading.postValue(false)
                    _errorMessage.postValue(it.message)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun loginUser(request: LoginRequest) {
        authRepo.loginUser(request).onEach {
            when(it) {
                is Resource.Loading -> {
                    _loading.postValue(true)
                }
                is Resource.Success -> {
                    _loading.postValue(false)
                    _gotoContact.postValue(true)
                }
                is Resource.Error -> {
                    _loading.postValue(false)
                    _errorMessage.postValue(it.message)
                }
            }
        }.launchIn(viewModelScope)
    }

}