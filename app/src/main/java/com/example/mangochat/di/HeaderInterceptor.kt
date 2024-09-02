package com.example.mangochat.di

import com.example.domain.usecase.GetAccessTokenUseCase
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor
    @Inject
    constructor(
        private val getAccessTokenUseCase: GetAccessTokenUseCase,
    ) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val token =
                runBlocking {
                    getAccessTokenUseCase.getAccessToken()
                }

            val request = chain.request().newBuilder()
            if (token.isNullOrEmpty().not())
                {
                    request.addHeader("Authorization", "Bearer $token")
                }

            return chain.proceed(request.build())
        }
    }
