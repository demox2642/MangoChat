package com.example.mangochat.di

import com.example.data.model.RefreshToken
import com.example.data.service.UpdateTokenApiService
import com.example.domain.models.LoginResponse
import com.example.domain.usecase.CleanTokensUseCase
import com.example.domain.usecase.GetAccessTokenUseCase
import com.example.domain.usecase.GetRefreshTokenUseCase
import com.example.domain.usecase.SaveAccessTokenUseCase
import com.example.domain.usecase.SaveRefreshTokenUseCase
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class AuthAuthenticator
    @Inject
    constructor(
        private val baseUrl: String,
        private val getAccessTokenUseCase: GetAccessTokenUseCase,
        private val getRefreshTokenUseCase: GetRefreshTokenUseCase,
        private val cleanTokensUseCase: CleanTokensUseCase,
        private val saveAccessTokenUseCase: SaveAccessTokenUseCase,
        private val saveRefreshTokenUseCase: SaveRefreshTokenUseCase,
    ) : Authenticator {
        override fun authenticate(
            route: Route?,
            response: Response,
        ): Request? {
            val token =
                runBlocking {
                    getRefreshTokenUseCase.getRefreshToken()
                }

            return runBlocking {
                val newToken = getNewToken(token)

                if (!newToken.isSuccessful || newToken.body() != null) {
                    cleanTokensUseCase.cleanTokens()
                }

                newToken.body()?.let {
                    saveAccessTokenUseCase.saveAccessToken(it.access_token)
                    saveRefreshTokenUseCase.saveRefreshToken(it.refresh_token)
                    response.request
                        .newBuilder()
                        .header("Authorization", "Bearer ${it.access_token}")
                        .build()
                }
            }
        }

        private suspend fun getNewToken(refreshToken: String?): retrofit2.Response<LoginResponse> {
            val loggingInterceptor = HttpLoggingInterceptor()
            val authInterceptor = HeaderInterceptor(getAccessTokenUseCase = getAccessTokenUseCase)
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val okHttpClient =
                OkHttpClient
                    .Builder()
                    .addInterceptor(authInterceptor)
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(loggingInterceptor)
                    .build()

            val retrofit =
                Retrofit
                    .Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()

            val service = retrofit.create(UpdateTokenApiService::class.java)

            return service.refreshToken(RefreshToken(refreshToken))
        }
    }
