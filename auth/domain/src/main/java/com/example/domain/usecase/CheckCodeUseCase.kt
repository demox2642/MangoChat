package com.example.domain.usecase

import com.example.domain.models.CheckCode
import com.example.domain.repository.AuthRepository

class CheckCodeUseCase(
    private val repository: AuthRepository,
) {
    suspend fun checkCode(checkCode: CheckCode) = repository.checkCode(checkCode)
}
