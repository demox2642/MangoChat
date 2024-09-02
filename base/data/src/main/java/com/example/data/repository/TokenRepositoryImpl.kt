package com.example.data.repository

import com.example.data.pref.Preferences
import com.example.domain.repository.TokenRepository

class TokenRepositoryImpl(
    private val prefs: Preferences,
) : TokenRepository {
    override fun getAccessToken(): String? = prefs.getAccessToken()

    override suspend fun saveAccessToken(token: String) = prefs.saveAccessToken(token)

    override fun getRefreshToken(): String? = prefs.getRefreshToken()

    override suspend fun saveRefreshToken(token: String) = prefs.saveRefreshToken(token)

    override suspend fun deleteToken() = prefs.deleteToken()
}
