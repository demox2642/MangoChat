package com.example.presentation.screens.code

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.CheckCode
import com.example.domain.usecase.CheckCodeUseCase
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
class CodeScreenViewModel
    @Inject
    constructor(
        private val saveAccessTokenUseCase: SaveAccessTokenUseCase,
        private val saveRefreshTokenUseCase: SaveRefreshTokenUseCase,
        private val checkCodeUseCase: CheckCodeUseCase,
    ) : ViewModel() {
        private val _screenState = MutableStateFlow(ScreenState.SUCCESS)
        val screenState = _screenState.asStateFlow()

        private val _errorText = MutableStateFlow("")
        val errorText = _errorText.asStateFlow()

        fun sendCode(
            phone: String,
            code: String,
            nextScreen: (Boolean) -> Unit,
        ) {
            viewModelScope.launch {
                checkCodeUseCase.checkCode(CheckCode(code, phone)).collect {
                    when (it) {
                        is ApiResponse.Success -> {
                            _screenState.value = ScreenState.SUCCESS
                            if (it.data.is_user_exists)
                                {
                                    it.data.access_token?.let { token ->
                                        saveAccessTokenUseCase.saveAccessToken(token)
                                    }
                                    it.data.refresh_token?.let { token ->
                                        saveRefreshTokenUseCase.saveRefreshToken(token)
                                    }
                                }
                            nextScreen(it.data.is_user_exists)
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

        fun closeErrorAlert() {
            _screenState.value = ScreenState.SUCCESS
        }
    }
