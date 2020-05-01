package com.developersbreach.loginandroid.login

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.developersbreach.loginandroid.authentication.AuthenticationState
import com.developersbreach.loginandroid.authentication.PrefUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val _authenticationState = MutableLiveData<AuthenticationState>()
    val authenticationState: MutableLiveData<AuthenticationState>
        get() = _authenticationState

    private val _navigateToListFragment = MutableLiveData<Boolean>()
    val navigateToListFragment: MutableLiveData<Boolean>
        get() = _navigateToListFragment

    private var mApplication: Application = application
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        coroutineScope.launch {
            val username: String? = PrefUtils.getUsernamePrefs(application.applicationContext)
            val password: String? = PrefUtils.getPasswordPrefs(application.applicationContext)
            if (!username!!.contentEquals(PrefUtils.PREFERENCE_VALUE_DEFAULT_USERNAME) &&
                !password!!.contentEquals(PrefUtils.PREFERENCE_VALUE_DEFAULT_PASSWORD)
            ) {
                _authenticationState.postValue(AuthenticationState.AUTHENTICATED)
            } else {
                _authenticationState.postValue(AuthenticationState.UNAUTHENTICATED)
            }
        }
    }

    fun loginUser(username: String, password: String) {
        if (validateUserAccount(username, password)) {
            PrefUtils.saveToPreferences(username, password, mApplication.applicationContext)
            _navigateToListFragment.value = true
        } else {
            Toast.makeText(mApplication.applicationContext, "No values Saved", Toast.LENGTH_SHORT).show()
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

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}