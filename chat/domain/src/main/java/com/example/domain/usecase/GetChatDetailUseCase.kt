package com.example.domain.usecase

import com.example.domain.repository.ChatRepository

class GetChatDetailUseCase(
    private val repository: ChatRepository,
) {
    fun getChatDetail(id: Long) = repository.getChatDetail(id)
}
