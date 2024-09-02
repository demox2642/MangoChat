package com.example.domain.usecase

import com.example.domain.repository.AuthRepository

class SendPhoneUseCase(
    private val repository: AuthRepository,
) {
    suspend fun sendPhone(phone: String) = repository.sendPhone(phone)
}
