package com.developersbreach.loginandroid.authentication

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData

class PrefUtils {

    companion object {

        private var PREFERENCE_NAME_USER_DETAILS = "pref_key_user"
        private var PREFERENCE_VALUE_USER_USERNAME: String = "username"
        private var PREFERENCE_VALUE_USER_PASSWORD: String = "password"
        var PREFERENCE_VALUE_DEFAULT_USERNAME: String = "User not logged in"
        var PREFERENCE_VALUE_DEFAULT_PASSWORD: String = "User not logged in"


        fun verifyAuthentication(
            application: Application,
            authenticationState: MutableLiveData<AuthenticationState>
        ) {
            val username: String? = getUsernamePrefs(application.applicationContext)
            val password: String? = getPasswordPrefs(application.applicationContext)
            if (!username!!.contentEquals(PREFERENCE_VALUE_DEFAULT_USERNAME) &&
                !password!!.contentEquals(PREFERENCE_VALUE_DEFAULT_PASSWORD)
            ) {
                authenticationState.postValue(AuthenticationState.AUTHENTICATED)
            } else {
                authenticationState.postValue(AuthenticationState.UNAUTHENTICATED)
            }
        }

        fun saveToPreferences(username: String, password: String, context: Context) {
            val namePreferences: SharedPreferences = context.getSharedPreferences(
                PREFERENCE_NAME_USER_DETAILS,
                Context.MODE_PRIVATE
            )
            with(namePreferences.edit()) {
                putString(PREFERENCE_VALUE_USER_USERNAME, username)
                putString(PREFERENCE_VALUE_USER_PASSWORD, password)
                commit()
            }
        }

        fun removeFromPreferences(context: Context) {
            val namePreferences: SharedPreferences = context.getSharedPreferences(
                PREFERENCE_NAME_USER_DETAILS,
                Context.MODE_PRIVATE
            )
            with(namePreferences.edit()) {
                putString(PREFERENCE_VALUE_USER_USERNAME, PREFERENCE_VALUE_DEFAULT_USERNAME)
                putString(PREFERENCE_VALUE_USER_PASSWORD, PREFERENCE_VALUE_DEFAULT_PASSWORD)
                commit()
            }
        }

        fun getUsernamePrefs(context: Context): String? {
            val sharedPref = context.getSharedPreferences(
                PREFERENCE_NAME_USER_DETAILS,
                Context.MODE_PRIVATE
            )
            return sharedPref.getString(
                PREFERENCE_VALUE_USER_USERNAME,
                PREFERENCE_VALUE_DEFAULT_USERNAME
            )
        }

        fun getPasswordPrefs(context: Context): String? {
            val sharedPref = context.getSharedPreferences(
                PREFERENCE_NAME_USER_DETAILS,
                Context.MODE_PRIVATE
            )
            return sharedPref.getString(
                PREFERENCE_VALUE_USER_PASSWORD,
                PREFERENCE_VALUE_DEFAULT_PASSWORD
            )
        }
    }
}