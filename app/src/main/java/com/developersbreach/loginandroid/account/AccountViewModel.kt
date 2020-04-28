package com.developersbreach.loginandroid.account


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.developersbreach.loginandroid.AuthenticationState
import com.developersbreach.loginandroid.model.UserLiveData


class AccountViewModel : ViewModel() {

    val authenticationState = UserLiveData().map { user ->
        if (validateUserAccount(user.username, user.password)) {
            Log.e("ViewModel", "AUTHENTICATED")
            AuthenticationState.AUTHENTICATED
        } else {
            Log.e("ViewModel", "UNAUTHENTICATED")
            AuthenticationState.UNAUTHENTICATED
        }
    }

//    fun verifyUser(username: String?, password: String?) {
//        if (validateUserAccount(username, password)) {
//            AuthenticationState.AUTHENTICATED
//        } else {
//            AuthenticationState.UNAUTHENTICATED
//        }
//    }

    private fun validateUserAccount(username: String?, password: String?): Boolean {
        var result = false
        if (username != null && password != null) {
            result = username.length >= 5 && password.length >= 5
        }
        return result
    }

    fun unAuthenticateUser() {
        AuthenticationState.UNAUTHENTICATED
    }
}
