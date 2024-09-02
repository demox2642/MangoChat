package com.example.domain.models

data class CheckCodeResult(
    val access_token: String?,
    val is_user_exists: Boolean,
    val refresh_token: String?,
    val user_id: Int?,
)
