package com.example.domain.usecase

import com.example.domain.repository.TokenRepository

class CleanTokensUseCase(
    private val repository: TokenRepository,
) {
    suspend fun cleanTokens() = repository.deleteToken()
}
