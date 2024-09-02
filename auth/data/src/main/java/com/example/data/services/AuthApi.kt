package com.example.data.services

import com.example.domain.models.CheckCode
import com.example.domain.models.CheckCodeResult
import com.example.domain.models.CheckPhoneResult
import com.example.domain.models.LoginResponse
import com.example.domain.models.PhoneNum
import com.example.domain.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("users/send-auth-code/")
    suspend fun sendPhone(
        @Body phone: PhoneNum,
    ): Response<CheckPhoneResult>

    @POST("users/check-auth-code/")
    suspend fun checkCode(
        @Body checkCode: CheckCode,
    ): Response<CheckCodeResult>

    @POST("users/register/")
    suspend fun registerUser(
        @Body user: User,
    ): Response<LoginResponse>
}
