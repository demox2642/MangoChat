package com.example.domain.models

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class ProfileInfo(
    val avatar: String?,
    val avatars: Avatars?,
    val birthday: LocalDate?,
    val city: String?,
    val completed_task: Int?,
    val created: LocalDateTime?,
    val id: Int,
    val instagram: String?,
    val last: LocalDateTime?,
    val name: String,
    val online: Boolean?,
    val phone: String,
    val status: String?,
    val username: String,
    val vk: String?,
)

fun ProfileInfo.toUpdateProfileInfo() =
    UpdateProfileInfo(
        avatar = this.avatar,
        birthday = if (this.birthday == null) "" else this.birthday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
        city = this.city,
        instagram = this.instagram,
        name = this.name,
        status = this.status,
        username = this.username,
        vk = this.vk,
    )
