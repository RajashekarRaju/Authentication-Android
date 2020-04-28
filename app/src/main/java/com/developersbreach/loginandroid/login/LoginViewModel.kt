package com.developersbreach.loginandroid.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.developersbreach.loginandroid.AuthenticationState

class LoginViewModel : ViewModel() {

    var authenticationState = MutableLiveData<AuthenticationState>()

    init {
        authenticationState.value = AuthenticationState.UNAUTHENTICATED
    }

    fun authenticate(username: String, password: String) {
        if (validateUserAccount(username, password)) {
            authenticationState.value = AuthenticationState.AUTHENTICATED
        } else {
            authenticationState.value = AuthenticationState.UNAUTHENTICATED
        }
    }

    private fun validateUserAccount(username: String, password: String): Boolean {
        return if (username.isNotEmpty() && password.isNotEmpty()) {
            username.length >= 5 && password.length >= 5
        } else {
            false
        }
    }

    fun skippedAuthentication() {
        authenticationState.value = AuthenticationState.UNAUTHENTICATED
    }
}
