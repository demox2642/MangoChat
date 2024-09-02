package com.example.presentation.screens.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.User
import com.example.domain.usecase.RegisterUserUseCase
import com.example.domain.usecase.SaveAccessTokenUseCase
import com.example.domain.usecase.SaveRefreshTokenUseCase
import com.example.domain.utils.ApiResponse
import com.example.presentation.model.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel
    @Inject
    constructor(
        private val registerUserUseCase: RegisterUserUseCase,
        private val saveAccessTokenUseCase: SaveAccessTokenUseCase,
        private val saveRefreshTokenUseCase: SaveRefreshTokenUseCase,
    ) : ViewModel() {
        private val _screenState = MutableStateFlow(ScreenState.SUCCESS)
        val screenState = _screenState.asStateFlow()

        private val _errorText = MutableStateFlow("")
        val errorText = _errorText.asStateFlow()

        fun closeErrorAlert() {
            _screenState.value = ScreenState.SUCCESS
        }

        fun registration(
            phone: String,
            userName: String,
            login: String,
            nextScreen: () -> Unit,
        )  {
            viewModelScope.launch {
                registerUserUseCase.registerUser(User(name = userName, phone = phone, username = login)).collect {
                    when (it) {
                        is ApiResponse.Success -> {
                            _screenState.value = ScreenState.SUCCESS
                            saveAccessTokenUseCase.saveAccessToken(it.data.access_token)
                            saveRefreshTokenUseCase.saveRefreshToken(it.data.refresh_token)
                            nextScreen()
                        }
                        is ApiResponse.Failure -> {
                            _screenState.value = ScreenState.ERROR
                            _errorText.value = "Error ${it.code}. message: ${it.errorMessage}"
                        }
                        is ApiResponse.Loading -> {
                            _screenState.value = ScreenState.LOADING
                        }
                    }
                }
            }
        }
    }
