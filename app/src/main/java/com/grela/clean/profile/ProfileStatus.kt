package com.grela.clean.profile

import com.grela.domain.model.UserRole

data class Profile(
    val status: ProfileStatus,
    val name: String = "",
    val role: UserRole
)


enum class ProfileStatus {
    LOGGED_IN,
    LOGGED_OUT
}