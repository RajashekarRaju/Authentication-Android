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

    private val _authenticationState = MutableLiveData<AuthenticationState>()

    val authenticationState: MutableLiveData<AuthenticationState>
        get() = _authenticationState

    private var mApplication: Application = application
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        coroutineScope.launch {
            PrefUtils.verifyAuthentication(application, _authenticationState)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun logoutUser() {
        coroutineScope.launch {
            PrefUtils.removeFromPreferences(mApplication.applicationContext)
            _authenticationState.postValue(AuthenticationState.UNAUTHENTICATED)
        }
    }
}
