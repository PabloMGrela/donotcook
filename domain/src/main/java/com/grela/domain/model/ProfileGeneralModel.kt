package com.grela.domain.model

data class ProfileGeneralModel(
    val userName: String,
    val token: String,
    val role: UserRole
)


enum class UserRole(val id: Int) {
    ADMIN(8),
    OWNER(7),
    USER(1),
    UNAUTH(-3);

    companion object {
        fun fromInt(id: Int): UserRole {
            values().forEach {
                if (it.id == id) {
                    return it
                }
            }
            return UNAUTH
        }
    }
}