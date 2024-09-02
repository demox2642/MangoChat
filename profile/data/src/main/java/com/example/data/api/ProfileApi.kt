package com.example.data.api

import com.example.data.models.Profile
import com.example.domain.models.UpdateProfileInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface ProfileApi {
    @GET("users/me/")
    suspend fun getProfileInfo(): Response<Profile>

    @PUT("users/me/")
    suspend fun updateProfileInfo(
        @Body checkCode: UpdateProfileInfo,
    ): Response<UpdateProfileInfo>
}
