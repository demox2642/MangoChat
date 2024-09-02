package com.example.data.models

import com.example.data.ext.toDataTime
import com.example.data.ext.toDate
import com.example.domain.models.ProfileInfo

data class Profile(
    val profile_data: ProfileData,
)

fun Profile.toProfileInfo(): ProfileInfo =
    ProfileInfo(
        avatar = this.profile_data.avatar,
        avatars = this.profile_data.avatars,
        birthday = if (this.profile_data.birthday.isNullOrEmpty()) null else this.profile_data.birthday.toDate(),
        city = this.profile_data.city,
        completed_task = this.profile_data.completed_task,
        created = if (this.profile_data.created.isNullOrEmpty()) null else this.profile_data.created.toDataTime(),
        id = this.profile_data.id,
        instagram = this.profile_data.instagram,
        last = if (this.profile_data.last.isNullOrEmpty()) null else this.profile_data.last.toDataTime(),
        name = this.profile_data.name,
        online = this.profile_data.online,
        phone = this.profile_data.phone,
        status = this.profile_data.status,
        username = this.profile_data.username,
        vk = this.profile_data.vk,
    )
