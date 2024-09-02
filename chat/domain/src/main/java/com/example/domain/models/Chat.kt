package com.example.domain.models

data class Chat(
    val avatarUrl: String,
    val name: String,
    val description: String,
    val id: Long,
    val message: List<Message>,
)
