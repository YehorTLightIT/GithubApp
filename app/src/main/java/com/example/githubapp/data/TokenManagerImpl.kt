package com.example.githubapp.data

import android.content.Context
import androidx.core.content.edit

class TokenManagerImpl(private val context: Context) : TokenManager{
    companion object {
        private const val PREFS_NAME = "prefs_auth"
        private const val KEY_TOKEN = "key_token"
        private const val KEY_USERNAME = "username"
    }

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun saveToken(token: String){
        prefs.edit()
            .putString(KEY_TOKEN, token)
            .commit()
    }

    override fun getToken(): String {
        return prefs.getString(KEY_TOKEN, "") ?: ""
    }
}