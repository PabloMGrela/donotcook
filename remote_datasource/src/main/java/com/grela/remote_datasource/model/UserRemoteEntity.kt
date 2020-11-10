package com.grela.remote_datasource.model

import com.google.gson.annotations.SerializedName
import com.grela.domain.model.ProfileGeneralModel
import com.grela.domain.model.UserRole

data class UserRemoteWrapper(
    @SerializedName("jwt") val token: String,
    @SerializedName("user") val user: UserRemoteEntity
)

data class UserRemoteEntity(
    @SerializedName("username") val userName: String,
    @SerializedName("email") val email: String,
    @SerializedName("role") val role: RoleRemoteEntity
)

fun UserRemoteWrapper.toProfileGeneralModel() =
    ProfileGeneralModel(user.userName, token, user.role.toUserRole())

private fun RoleRemoteEntity.toUserRole(): UserRole = UserRole.fromInt(this.roleId)

data class RoleRemoteEntity(
    @SerializedName("id") val roleId: Int
)