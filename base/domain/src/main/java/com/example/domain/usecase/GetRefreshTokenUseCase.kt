package com.example.domain.usecase

import com.example.domain.repository.TokenRepository

class GetRefreshTokenUseCase(
    private val repository: TokenRepository,
) {
    fun getRefreshToken() = repository.getRefreshToken()
}
