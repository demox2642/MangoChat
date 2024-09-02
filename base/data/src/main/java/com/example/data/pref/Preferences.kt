package com.example.data.pref

import android.content.Context

class Preferences(
    context: Context,
) {
    companion object {
        private val PREFS_KEY = "ru.mango_chat.android.Preferences"
        private val ACCESS_TOKEN = "acccess_token"
        private val REFRESH_TOKEN = "refresh_token"
    }

    private val prefs = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)

    fun getAccessToken(): String? = prefs.getString(ACCESS_TOKEN, null)

    suspend fun saveAccessToken(token: String) {
        prefs.edit().putString(ACCESS_TOKEN, token).apply()
    }

    fun getRefreshToken(): String? = prefs.getString(REFRESH_TOKEN, null)

    suspend fun saveRefreshToken(token: String) {
        prefs.edit().putString(REFRESH_TOKEN, token).apply()
    }

    suspend fun deleteToken() {
        saveRefreshToken("")
        saveAccessToken("")
    }
}
