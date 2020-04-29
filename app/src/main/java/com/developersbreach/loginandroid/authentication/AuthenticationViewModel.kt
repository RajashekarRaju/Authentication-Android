package com.developersbreach.loginandroid.authentication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthenticationViewModel : ViewModel() {

    val authenticationState = MutableLiveData<AuthenticationState>()

    init {
        authenticationState.value = AuthenticationState.UNAUTHENTICATED
    }

    fun verifyUser(username: String?, password: String?) {
        if (validateUserAccount(username, password)) {
            authenticationState.value = AuthenticationState.AUTHENTICATED
        } else {
            authenticationState.value = AuthenticationState.UNAUTHENTICATED
        }
    }

    private fun validateUserAccount(username: String?, password: String?): Boolean {
        var result = false
        if (username != null && password != null) {
            result = username.length >= 5 && password.length >= 5
        }
        return result
    }

    fun unAuthenticateUser() {
        authenticationState.value = AuthenticationState.UNAUTHENTICATED
    }
}