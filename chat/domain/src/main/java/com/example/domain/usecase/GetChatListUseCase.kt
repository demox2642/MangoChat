package com.example.domain.usecase

import com.example.domain.repository.ChatRepository

class GetChatListUseCase(
    private val repository: ChatRepository,
) {
    fun getChatList() = repository.getChatList()
}
