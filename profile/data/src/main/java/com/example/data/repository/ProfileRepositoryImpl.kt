package com.example.data.repository

import com.example.data.api.ProfileApi
import com.example.data.models.toProfileInfo
import com.example.domain.models.ProfileInfo
import com.example.domain.models.UpdateProfileInfo
import com.example.domain.repository.ProfileRepository
import com.example.domain.utils.ApiResponse
import com.example.domain.utils.apiRequestFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProfileRepositoryImpl
    @Inject
    constructor(
        private val profileApi: ProfileApi,
    ) : ProfileRepository {
        override suspend fun getProfileInfo(): Flow<ApiResponse<ProfileInfo>> =
            apiRequestFlow {
                profileApi.getProfileInfo()
            }.map { value ->
                when (value) {
                    is ApiResponse.Success -> {
                        ApiResponse.Success(value.data.toProfileInfo())
                    }

                    is ApiResponse.Failure -> {
                        ApiResponse.Failure(value.errorMessage, value.code)
                    }

                    is ApiResponse.Loading -> {
                        ApiResponse.Loading
                    }
                }
            }

        override suspend fun updateProfileInfo(newData: UpdateProfileInfo): Flow<ApiResponse<Boolean>> =
            apiRequestFlow {
                profileApi.updateProfileInfo(newData)
            }.map { value ->
                when (value) {
                    is ApiResponse.Success -> {
                        ApiResponse.Success(true)
                    }

                    is ApiResponse.Failure -> {
                        ApiResponse.Failure(value.errorMessage, value.code)
                    }

                    is ApiResponse.Loading -> {
                        ApiResponse.Loading
                    }
                }
            }
    }
