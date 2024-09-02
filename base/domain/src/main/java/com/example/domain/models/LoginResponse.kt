package com.example.domain.models

data class LoginResponse(
    val access_token: String,
    val refresh_token: String,
    val user_id: Int,
)
