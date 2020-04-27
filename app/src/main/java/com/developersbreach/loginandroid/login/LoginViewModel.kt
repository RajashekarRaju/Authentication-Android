package com.developersbreach.loginandroid.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.developersbreach.loginandroid.AuthenticationState

class LoginViewModel : ViewModel() {

    var authenticationState = MutableLiveData<AuthenticationState>()
    private var mUsername: MutableLiveData<String> = MutableLiveData()
    private var mPassword: MutableLiveData<String> = MutableLiveData()

    init {
        authenticationState.value = AuthenticationState.UNAUTHENTICATED
        mUsername.value = ""
    }

    fun refuseAuthentication() {
        authenticationState.value = AuthenticationState.UNAUTHENTICATED
    }

    fun authenticate(username: String, password: String) {
        if (validateUserAccount(username, password)) {
            mUsername.value = username
            authenticationState.value = AuthenticationState.AUTHENTICATED
        } else {
            authenticationState.value = AuthenticationState.INVALID_AUTHENTICATION
        }
    }

    private fun validateUserAccount(username: String, password: String): Boolean {
        var result = false

        if (username.isNotEmpty() && password.isNotEmpty()) {
            if (username.length >= 5 && password.length >= 5) {
                result = true
            }
        }
        return result
    }

//    fun validateUsername(resultValue: CharSequence?): LiveData<String> {
//        mUsername.value = resultValue.toString()
//        return mUsername
//    }
//
//    fun validatePassword(resultValue: CharSequence?): LiveData<String> {
//        mPassword.value = resultValue.toString()
//        return mPassword
//    }

}
