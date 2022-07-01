package dev.sukhrob.contacts.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import dev.sukhrob.contacts.data.source.local.datastore.UserPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userPref: UserPreferences
) : ViewModel() {

    private val _timer = MutableLiveData<Boolean>()
    val timer: LiveData<Boolean>
        get() = _timer

//    init {
//        viewModelScope.launch {
//            delay(3000)
//            _timer.postValue(true)
//        }
//    }

    init {
        checkUserPref()
    }

    private fun checkUserPref() {
        userPref.authToken.onEach {
            delay(3000)
            if (it == null) {
                _timer.postValue(false)
            } else {
                _timer.postValue(true)
            }
        }.launchIn(viewModelScope)
    }

//    private fun checkUserPref() = viewModelScope.launch {
//        val a = userPref.authToken.first()
//        if (a == null) {
//            _timer.postValue(false)
//        } else {
//            _timer.postValue(true)
//        }
//    }
}