package com.example.presentation.screens.chat

import androidx.lifecycle.ViewModel
import com.example.domain.models.Chat
import com.example.domain.usecase.GetChatListUseCase
import com.example.presentation.model.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ChatScreenViewModel @Inject constructor(
    private val getChatListUseCase: GetChatListUseCase
): ViewModel(){
    private val _screenState = MutableStateFlow(ScreenState.LOADING)
    val screenState = _screenState.asStateFlow()

    private val _chatList = MutableStateFlow(emptyList<Chat>())
    val chatList = _chatList.asStateFlow()

    init {
        _chatList.value = getChatListUseCase.getChatList()
        _screenState.value = ScreenState.SUCCESS
    }
}
