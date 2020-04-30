package com.developersbreach.loginandroid.authentication

import android.content.Context
import android.content.SharedPreferences

class PrefUtils {

    companion object {

        var PREFERENCE_NAME_USER_DETAILS = "pref_key_user"
        var PREFERENCE_VALUE_USER_USERNAME: String = "username"
        var PREFERENCE_VALUE_USER_PASSWORD: String = "password"
        var PREFERENCE_VALUE_DEFAULT_USERNAME: String = "User not logged in"
        var PREFERENCE_VALUE_DEFAULT_PASSWORD: String = "User not logged in"


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
                putString(PREFERENCE_VALUE_USER_USERNAME, "username")
                putString(PREFERENCE_VALUE_USER_PASSWORD, "password")
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