package com.developersbreach.loginandroid.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.developersbreach.loginandroid.authentication.AuthenticationState
import com.developersbreach.loginandroid.authentication.PrefUtils
import com.developersbreach.loginandroid.model.Sports
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class ListViewModel(application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _authenticationState = MutableLiveData<AuthenticationState>()
    val authenticationState: MutableLiveData<AuthenticationState>
        get() = _authenticationState

    private val _username = MutableLiveData<String>()
    val username: MutableLiveData<String>
        get() = _username

    private var sports: MutableLiveData<List<Sports>> = MutableLiveData()

    init {
        coroutineScope.launch {
            PrefUtils.verifyAuthentication(application, _authenticationState)
            _username.postValue(PrefUtils.getUsernamePrefs(application.applicationContext))
            val sportsList: List<Sports> = Sports.sportsList(application.applicationContext)
            sports.value = sportsList
        }
    }

    fun getSports(): LiveData<List<Sports>> {
        return sports
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}