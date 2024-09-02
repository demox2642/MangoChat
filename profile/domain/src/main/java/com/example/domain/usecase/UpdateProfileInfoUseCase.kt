package com.example.domain.usecase

import com.example.domain.models.UpdateProfileInfo
import com.example.domain.repository.ProfileRepository

class UpdateProfileInfoUseCase(
    private val repository: ProfileRepository,
) {
    suspend fun updateProfileInfo(profileData: UpdateProfileInfo) = repository.updateProfileInfo(newData = profileData)
}
