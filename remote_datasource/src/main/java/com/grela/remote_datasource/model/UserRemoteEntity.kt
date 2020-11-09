package com.grela.remote_datasource.model

import com.google.gson.annotations.SerializedName
import com.grela.domain.model.ProfileGeneralModel

data class UserRemoteWrapper(
    @SerializedName("jwt") val token: String,
    @SerializedName("user") val user: UserRemoteEntity
)

data class UserRemoteEntity(
    @SerializedName("username") val userName: String,
    @SerializedName("email") val email: String,
    @SerializedName("role") val role: RoleRemoteEntity
)

fun UserRemoteWrapper.toProfileGeneralModel() = ProfileGeneralModel(user.email, token)

data class RoleRemoteEntity(
    @SerializedName("id") val roleId: Int
)