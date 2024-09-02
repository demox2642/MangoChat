package com.example.data.service

import com.example.data.model.RefreshToken
import com.example.domain.models.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UpdateTokenApiService {
    @POST("users/refresh_token")
    suspend fun refreshToken(
        @Body refreshToken: RefreshToken,
    ): Response<LoginResponse>
}
