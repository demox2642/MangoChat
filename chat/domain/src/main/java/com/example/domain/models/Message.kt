package com.example.domain.models

import java.time.LocalDateTime

data class Message(
    val selfMessage: Boolean,
    val date: LocalDateTime,
    val message: String,
)
