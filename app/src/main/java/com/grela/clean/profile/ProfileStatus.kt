package com.grela.clean.profile

data class Profile(
    val status: ProfileStatus,
    val name: String = ""
)

enum class ProfileStatus {
    LOGGED_IN,
    LOGGED_OUT
}