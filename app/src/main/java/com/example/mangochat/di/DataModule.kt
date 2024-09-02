package com.example.mangochat.di
import com.example.data.api.ProfileApi
import com.example.data.pref.Preferences
import com.example.data.repository.AuthRepositoryImpl
import com.example.data.repository.ChatRepositoryImpl
import com.example.data.repository.ProfileRepositoryImpl
import com.example.data.repository.TokenRepositoryImpl
import com.example.data.services.AuthApi
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.ChatRepository
import com.example.domain.repository.ProfileRepository
import com.example.domain.repository.TokenRepository
import com.example.domain.usecase.CleanTokensUseCase
import com.example.domain.usecase.GetAccessTokenUseCase
import com.example.domain.usecase.GetRefreshTokenUseCase
import com.example.domain.usecase.SaveAccessTokenUseCase
import com.example.domain.usecase.SaveRefreshTokenUseCase
import com.example.domain.usecase.SendPhoneUseCase
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    fun provideUrl(): String = "https://plannerok.ru/api/v1/"

    @Provides
    @Named("headers")
    fun provideHeaderInterceptor(getAccessTokenUseCase: GetAccessTokenUseCase): Interceptor = HeaderInterceptor(getAccessTokenUseCase)

    @Provides
    fun provideAuthAuthenticator(
        baseUrl: String,
        getAccessTokenUseCase: GetAccessTokenUseCase,
        getRefreshTokenUseCase: GetRefreshTokenUseCase,
        cleanTokensUseCase: CleanTokensUseCase,
        saveAccessTokenUseCase: SaveAccessTokenUseCase,
        saveRefreshTokenUseCase: SaveRefreshTokenUseCase,
    ): Authenticator =
        AuthAuthenticator(
            baseUrl,
            getAccessTokenUseCase,
            getRefreshTokenUseCase,
            cleanTokensUseCase,
            saveAccessTokenUseCase,
            saveRefreshTokenUseCase,
        )

    @Singleton
    @Provides
    fun provideHttpClient(
        authInterceptor: HeaderInterceptor,
        authAuthenticator: AuthAuthenticator,
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient
            .Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .authenticator(authAuthenticator)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        baseUrl: String,
        client: OkHttpClient,
    ): Retrofit.Builder {
        val gson =
            GsonBuilder()
                .setLenient()
                .create()

        return Retrofit
            .Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideAuthApi(retrofit: Retrofit.Builder): AuthApi =
        retrofit
            .build()
            .create(AuthApi::class.java)

    @Singleton
    @Provides
    fun provideAuthRepository(authApi: AuthApi): AuthRepository = AuthRepositoryImpl(authApi)

    @Singleton
    @Provides
    fun provideTokenRepository(preferences: Preferences): TokenRepository = TokenRepositoryImpl(preferences)

    @Singleton
    @Provides
    fun provideProfileApi(retrofit: Retrofit.Builder): ProfileApi =
        retrofit
            .build()
            .create(ProfileApi::class.java)

    @Singleton
    @Provides
    fun provideProfileRepository(profileApi: ProfileApi): ProfileRepository = ProfileRepositoryImpl(profileApi)

    @Singleton
    @Provides
    fun provideChatRepository(): ChatRepository = ChatRepositoryImpl()

    @Provides
    fun provideSendPhoneUseCase(authRepository: AuthRepository) = SendPhoneUseCase(authRepository)

    @Provides
    fun provideCleanTokensUseCase(tokenRepository: TokenRepository) = CleanTokensUseCase(tokenRepository)

    @Provides
    fun provideGetAccessTokenUseCase(tokenRepository: TokenRepository) = GetAccessTokenUseCase(tokenRepository)

    @Provides
    fun provideGetRefreshTokenUseCase(tokenRepository: TokenRepository) = GetRefreshTokenUseCase(tokenRepository)

    @Provides
    fun provideSaveRefreshTokenUseCase(tokenRepository: TokenRepository) = SaveRefreshTokenUseCase(tokenRepository)

    @Provides
    fun provideSaveAccessTokenUseCase(tokenRepository: TokenRepository) = SaveAccessTokenUseCase(tokenRepository)
}
//
