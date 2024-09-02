package com.example.data.repository

import com.example.data.services.AuthApi
import com.example.domain.models.CheckCode
import com.example.domain.models.PhoneNum
import com.example.domain.models.User
import com.example.domain.repository.AuthRepository
import com.example.domain.utils.apiRequestFlow
import javax.inject.Inject

class AuthRepositoryImpl
    @Inject
    constructor(
        private val authApi: AuthApi,
    ) : AuthRepository {
        override suspend fun sendPhone(phone: String) =
            com.example.domain.utils.apiRequestFlow {
                authApi.sendPhone(PhoneNum(phone))
            }

        override suspend fun checkCode(checkCode: CheckCode) =
            com.example.domain.utils.apiRequestFlow {
                authApi.checkCode(checkCode)
            }

        override suspend fun registerUser(user: User) =
            com.example.domain.utils.apiRequestFlow {
                authApi.registerUser(user)
            }
    }
