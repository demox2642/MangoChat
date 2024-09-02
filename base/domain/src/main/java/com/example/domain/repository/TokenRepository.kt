package com.example.domain.repository

interface TokenRepository {
    fun getRefreshToken(): String?

    suspend fun saveRefreshToken(token: String)

    fun getAccessToken(): String?

    suspend fun saveAccessToken(token: String)

    suspend fun deleteToken()
}
