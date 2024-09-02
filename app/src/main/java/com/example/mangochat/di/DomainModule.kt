package com.example.mangochat.di

import com.example.domain.repository.AuthRepository
import com.example.domain.repository.ChatRepository
import com.example.domain.repository.ProfileRepository
import com.example.domain.usecase.CheckCodeUseCase
import com.example.domain.usecase.GetChatDetailUseCase
import com.example.domain.usecase.GetChatListUseCase
import com.example.domain.usecase.GetProfileInfoUseCase
import com.example.domain.usecase.RegisterUserUseCase
import com.example.domain.usecase.UpdateProfileInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {
    @Provides
    fun provideCheckCodeUseCase(authRepository: AuthRepository) = CheckCodeUseCase(authRepository)

    @Provides
    fun provideRegisterUserUseCase(authRepository: AuthRepository) = RegisterUserUseCase(authRepository)

    @Provides
    fun provideGetProfileInfoUseCase(profileRepository: ProfileRepository) = GetProfileInfoUseCase(profileRepository)

    @Provides
    fun provideUpdateProfileInfoUseCase(profileRepository: ProfileRepository) = UpdateProfileInfoUseCase(profileRepository)

    @Provides
    fun provideGetChatListUseCase(chatRepository: ChatRepository) = GetChatListUseCase(chatRepository)

    @Provides
    fun provideGetChatDetailUseCase(chatRepository: ChatRepository) = GetChatDetailUseCase(chatRepository)
}
