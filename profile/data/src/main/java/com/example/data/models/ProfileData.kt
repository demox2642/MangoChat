package com.example.data.models

import com.example.domain.models.Avatars

data class ProfileData(
    val avatar: String?,
    val avatars: Avatars?,
    val birthday: String?,
    val city: String?,
    val completed_task: Int?,
    val created: String?,
    val id: Int,
    val instagram: String?,
    val last: String?,
    val name: String,
    val online: Boolean?,
    val phone: String,
    val status: String?,
    val username: String,
    val vk: String?,
)
