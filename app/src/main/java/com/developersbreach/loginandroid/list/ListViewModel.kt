package com.developersbreach.loginandroid.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.developersbreach.loginandroid.AuthenticationState

class ListViewModel : ViewModel() {

    var authenticationState = MutableLiveData<AuthenticationState>()

    init {
        authenticationState.value = AuthenticationState.AUTHENTICATED
    }
}
