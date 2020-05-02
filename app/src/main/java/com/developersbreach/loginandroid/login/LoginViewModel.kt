package com.developersbreach.loginandroid.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.developersbreach.loginandroid.authentication.AuthenticationState
import com.developersbreach.loginandroid.authentication.PrefUtils
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private var mApplication: Application = application
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _authenticationState = MutableLiveData<AuthenticationState>()
    val authenticationState: MutableLiveData<AuthenticationState>
        get() = _authenticationState

    private val _navigateToListFragment = MutableLiveData<Boolean>()
    val navigateToListFragment: MutableLiveData<Boolean>
        get() = _navigateToListFragment


    init {
        coroutineScope.launch {
            PrefUtils.verifyAuthentication(application, _authenticationState)
        }
    }

    fun loginUser(username: String, password: String) {
        if (validateUserAccount(username, password)) {
            PrefUtils.saveToPreferences(username, password, mApplication.applicationContext)
            _navigateToListFragment.value = true
        } else {
            _navigateToListFragment.value = false
        }
    }

    private fun validateUserAccount(username: String?, password: String?): Boolean {
        var result = false
        if (username != null && password != null) {
            result = username.length >= 5 && password.length >= 5
        }
        return result
    }

    fun setUsernameError(usernameEditText: TextInputEditText): Boolean {
        return usernameEditText.length() < 5
    }

    fun setPasswordError(passwordEditText: TextInputEditText): Boolean {
        return passwordEditText.length() < 5
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}