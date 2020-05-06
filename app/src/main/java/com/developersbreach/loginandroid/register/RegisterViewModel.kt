package com.developersbreach.loginandroid.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.developersbreach.loginandroid.R
import com.developersbreach.loginandroid.authentication.AuthenticationState
import com.developersbreach.loginandroid.authentication.PrefUtils
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private var mApplication: Application = application
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _authenticationState = MutableLiveData<AuthenticationState>()
    val authenticationState: MutableLiveData<AuthenticationState>
        get() = _authenticationState

    init {
        coroutineScope.launch {
            PrefUtils.verifyAuthentication(application, _authenticationState)
        }
    }

    fun validateFields(
        firstNameEditText: String,
        firstNameInputLayout: TextInputLayout,
        lastNameEditText: String,
        lastNameInputLayout: TextInputLayout,
        mailEditText: String,
        mailInputLayout: TextInputLayout,
        countryEditText: String,
        countryInputLayout: TextInputLayout,
        passwordEditText: String,
        passwordInputLayout: TextInputLayout
    ) {
        if (firstNameEditText.isNotEmpty() && lastNameEditText.isNotEmpty() &&
            mailEditText.isNotEmpty() && countryEditText.isNotEmpty() && passwordEditText.isNotEmpty()
        ) {
            validateRegistrationAndLogin(firstNameEditText, passwordEditText)
        } else {
            if (firstNameEditText.isEmpty())
                firstNameInputLayout.error = mApplication.getString(R.string.requires_field)
            else firstNameInputLayout.error = null
            if (lastNameEditText.isEmpty())
                lastNameInputLayout.error = mApplication.getString(R.string.requires_field)
            else lastNameInputLayout.error = null
            if (mailEditText.isEmpty())
                mailInputLayout.error = mApplication.getString(R.string.requires_field)
            else mailInputLayout.error = null
            if (countryEditText.isEmpty())
                countryInputLayout.error = mApplication.getString(R.string.requires_field)
            else countryInputLayout.error = null
            if (passwordEditText.isEmpty())
                passwordInputLayout.error = mApplication.getString(R.string.requires_field)
            else passwordInputLayout.error = null
        }
    }

    private fun validateRegistrationAndLogin(username: String, password: String) {
        coroutineScope.launch {
            PrefUtils.saveToPreferences(username, password, mApplication.applicationContext)
            _authenticationState.postValue(AuthenticationState.AUTHENTICATED)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
