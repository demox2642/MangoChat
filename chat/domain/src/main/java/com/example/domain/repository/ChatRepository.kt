package com.example.domain.repository

import com.example.domain.models.Chat

interface ChatRepository {
    fun getChatList(): List<Chat>

    fun getChatDetail(id: Long): Chat
}
