package com.example.domain.usecase

import com.example.domain.repository.ProfileRepository

class GetProfileInfoUseCase(
    private val repository: ProfileRepository,
) {
    suspend fun getProfileInfo() = repository.getProfileInfo()
}
