package com.example.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.ProfileInfo
import com.example.domain.models.toUpdateProfileInfo
import com.example.domain.usecase.GetProfileInfoUseCase
import com.example.domain.usecase.UpdateProfileInfoUseCase
import com.example.domain.utils.ApiResponse
import com.example.presentation.model.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel
    @Inject
    constructor(
        private val getProfileInfoUseCase: GetProfileInfoUseCase,
        private val updateProfileInfoUseCase: UpdateProfileInfoUseCase,
    ) : ViewModel() {
        private val _screenState = MutableStateFlow(ScreenState.SUCCESS)
        val screenState = _screenState.asStateFlow()

        private val _errorText = MutableStateFlow("")
        val errorText = _errorText.asStateFlow()

        private val _userInfo = MutableStateFlow<ProfileInfo?>(null)
        val userInfo = _userInfo.asStateFlow()

        private val _canEditState = MutableStateFlow(false)
        val canEditState = _canEditState.asStateFlow()

        init {
            getProfileInfo()
        }

        fun getProfileInfo() {
            viewModelScope.launch(Dispatchers.IO) {
                getProfileInfoUseCase.getProfileInfo().collect {
                    when (it) {
                        is ApiResponse.Success -> {
                            _screenState.value = ScreenState.SUCCESS
                            _userInfo.value = it.data
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

        fun changeUserInfo(userInfo: ProfileInfo) {
            _userInfo.value = userInfo
        }

        fun updateProfile() {
            viewModelScope.launch(Dispatchers.IO) {
                updateProfileInfoUseCase.updateProfileInfo(_userInfo.value!!.toUpdateProfileInfo()).collect {
                    when (it) {
                        is ApiResponse.Success -> {
                            _screenState.value = ScreenState.SUCCESS
                            getProfileInfo()
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

        fun changeEdit()  {
            _canEditState.value = _canEditState.value.not()
        }

        fun closeErrorAlert() {
            _screenState.value = ScreenState.SUCCESS
        }
    }
