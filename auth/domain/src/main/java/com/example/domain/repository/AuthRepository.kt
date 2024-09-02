package com.example.domain.repository

import com.example.domain.models.CheckCode
import com.example.domain.models.CheckCodeResult
import com.example.domain.models.CheckPhoneResult
import com.example.domain.models.LoginResponse
import com.example.domain.models.User
import com.example.domain.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun sendPhone(phone: String): Flow<ApiResponse<CheckPhoneResult>>

    suspend fun checkCode(checkCode: CheckCode): Flow<ApiResponse<CheckCodeResult>>

    suspend fun registerUser(user: User): Flow<ApiResponse<LoginResponse>>
}
