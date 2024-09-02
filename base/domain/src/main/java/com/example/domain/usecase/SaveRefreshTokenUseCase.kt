package com.example.domain.usecase

import com.example.domain.repository.TokenRepository

class SaveRefreshTokenUseCase(
    private val repository: TokenRepository,
) {
    suspend fun saveRefreshToken(token: String) = repository.saveRefreshToken(token)
}
