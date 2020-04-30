package com.developersbreach.loginandroid.account

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.developersbreach.loginandroid.authentication.AuthenticationState
import com.developersbreach.loginandroid.authentication.PrefUtils
import com.developersbreach.loginandroid.authentication.PrefUtils.Companion.PREFERENCE_VALUE_DEFAULT_PASSWORD
import com.developersbreach.loginandroid.authentication.PrefUtils.Companion.PREFERENCE_VALUE_DEFAULT_USERNAME


class AccountViewModel(application: Application) : AndroidViewModel(application) {

    val authenticationState = MutableLiveData<AuthenticationState>()
    private var mApplication: Application = application


    init {
        val username: String? = PrefUtils.getUsernamePrefs(application.applicationContext)
        val password: String? = PrefUtils.getPasswordPrefs(application.applicationContext)
        if (!username!!.contentEquals(PREFERENCE_VALUE_DEFAULT_USERNAME) &&
            !password!!.contentEquals(PREFERENCE_VALUE_DEFAULT_PASSWORD)
        ) {
            authenticationState.value = AuthenticationState.AUTHENTICATED
        } else {
            authenticationState.value = AuthenticationState.UNAUTHENTICATED
        }
    }

    fun verifyUser(username: String, password: String) {
        if (validateUserAccount(username, password)) {
            PrefUtils.saveToPreferences(username, password, mApplication.applicationContext)
            Log.e("AccountViewModel", "$username $password")
        } else {
            Toast.makeText(mApplication.applicationContext, "No values Saved", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateUserAccount(username: String?, password: String?): Boolean {
        var result = false
        if (username != null && password != null) {
            result = username.length >= 5 && password.length >= 5
        }
        return result
    }

    fun logoutUser() {
        PrefUtils.removeFromPreferences(mApplication.applicationContext)
        authenticationState.value = AuthenticationState.UNAUTHENTICATED
    }
}
