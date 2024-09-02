package com.example.presentation.screens.chat_detail

import androidx.lifecycle.ViewModel
import com.example.domain.models.Chat
import com.example.domain.usecase.GetChatDetailUseCase
import com.example.presentation.model.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ChatDetailViewModel
    @Inject
    constructor(
        private val getChatDetailUseCase: GetChatDetailUseCase,
    ) : ViewModel(){
    private val _screenState = MutableStateFlow(ScreenState.LOADING)
    val screenState = _screenState.asStateFlow()

    private val _chat = MutableStateFlow<Chat?>(null)
    val chat = _chat.asStateFlow()

    fun getChatDetail(id: Long){
        _chat.value = getChatDetailUseCase.getChatDetail(id)
        _screenState.value = ScreenState.SUCCESS
    }
    }
