package com.grela.clean.profile

data class Profile(
    val status: ProfileStatus
)

enum class ProfileStatus {
    LOGGED_IN,
    LOGGED_OUT
}