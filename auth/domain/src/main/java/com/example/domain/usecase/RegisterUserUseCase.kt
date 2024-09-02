package com.example.domain.usecase

import com.example.domain.models.User
import com.example.domain.repository.AuthRepository

class RegisterUserUseCase(
    private val repository: AuthRepository,
) {
    suspend fun registerUser(user: User) = repository.registerUser(user)
}
