package com.example.domain.repository

import com.example.domain.models.ProfileInfo
import com.example.domain.models.UpdateProfileInfo
import com.example.domain.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getProfileInfo(): Flow<ApiResponse<ProfileInfo>>

    suspend fun updateProfileInfo(newData: UpdateProfileInfo): Flow<ApiResponse<Boolean>>
}
