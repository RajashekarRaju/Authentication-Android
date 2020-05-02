package com.developersbreach.loginandroid.account

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.developersbreach.loginandroid.authentication.AuthenticationState
import com.developersbreach.loginandroid.authentication.PrefUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class AccountViewModel(application: Application) : AndroidViewModel(application) {

    private var mApplication: Application = application
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _authenticationState = MutableLiveData<AuthenticationState>()
    val authenticationState: MutableLiveData<AuthenticationState>
        get() = _authenticationState

    private val _username = MutableLiveData<String>()
    val username: MutableLiveData<String>
        get() = _username


    init {
        coroutineScope.launch {
            PrefUtils.verifyAuthentication(application, _authenticationState)
            _username.postValue(PrefUtils.getUsernamePrefs(application.applicationContext))
        }
    }

    fun logoutUser() {
        coroutineScope.launch {
            PrefUtils.removeFromPreferences(mApplication.applicationContext)
            _authenticationState.postValue(AuthenticationState.UNAUTHENTICATED)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
