package com.grela.remote_datasource.model

import com.google.gson.annotations.SerializedName

data class UserRemoteEntity(
    @SerializedName("username") val userName: String,
    @SerializedName("email") val email: String,
    @SerializedName("role") val role: RoleRemoteEntity
)

data class RoleRemoteEntity(
    @SerializedName("id") val roleId: Int
)