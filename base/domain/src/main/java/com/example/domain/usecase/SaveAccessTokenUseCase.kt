package com.example.domain.usecase

import com.example.domain.repository.TokenRepository

class SaveAccessTokenUseCase(
    private val repository: TokenRepository,
) {
    suspend fun saveAccessToken(token: String) = repository.saveAccessToken(token)
}
