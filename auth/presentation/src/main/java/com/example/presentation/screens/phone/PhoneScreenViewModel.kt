package com.example.presentation.screens.phone

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.SendPhoneUseCase
import com.example.domain.utils.ApiResponse
import com.example.presentation.model.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhoneScreenViewModel
    @Inject
    constructor(
        private val sendPhoneUseCase: SendPhoneUseCase,
    ) : ViewModel() {
        private val _screenState = MutableStateFlow(ScreenState.SUCCESS)
        val screenState = _screenState.asStateFlow()

        private val _errorText = MutableStateFlow("")
        val errorText = _errorText.asStateFlow()

        fun sendPhone(
            phone: String,
            nextScreen: (String) -> Unit,
        ) {
            viewModelScope.launch {
                sendPhoneUseCase.sendPhone(phone).collect {
                    when (it) {
                        is ApiResponse.Success -> {
                            _screenState.value = ScreenState.SUCCESS
                            nextScreen(phone)
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
